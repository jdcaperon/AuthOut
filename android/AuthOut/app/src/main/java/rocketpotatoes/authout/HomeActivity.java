/*
 * MIT License

 Copyright (c) 2018 Ryan Kurz

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */
package rocketpotatoes.authout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;
import android.view.View;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.camerakit.CameraKitView;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import rocketpotatoes.authout.Helpers.CameraPermissionHelper;
import rocketpotatoes.authout.Helpers.Child;
import rocketpotatoes.authout.Helpers.NotRecognizedDialog;
import rocketpotatoes.authout.Helpers.Parent;
import rocketpotatoes.authout.Helpers.StoragePermissionHelper;
import rocketpotatoes.authout.Helpers.Util;


public class HomeActivity extends AppCompatActivity {
    private static final int INITIAL_DELAY = 2000;
    private static final int TIME_BETWEEN_PHOTOS = 500;
    private static final double SIZE_OF_FACE_RELATIVE_TO_SCREEN = 0.50;
    private static final double MIN_SIZE_OF_FACE_RELATIVE_TO_SCREEN = 0.35;
    private static final String AUTHOUT_IMAGE_CHECK = "http://httpbin.org/post";

    private CameraKitView camera;
    private FaceDetector faceDetector;
    private Bitmap currentImage;
    private RequestQueue requestQueue;
    private AlertDialog moveCloserDialog;
    private View progressOverlay;

    private Point screenSize = new Point();

    // Handler for intermittent execution
    private Handler handler = new Handler();

    private Runnable dialogDismiss = new Runnable() {
        @Override
        public void run() {
            moveCloserDialog.dismiss();
        }
    };

    /** Runnable to be executed every {@link HomeActivity#TIME_BETWEEN_PHOTOS} milliseconds */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            takePicture();
            Face face = faceProcessing();
            // Ensure face is appropriate size to move forwards
            if (face != null) {
                if (face.getWidth() > screenSize.x * SIZE_OF_FACE_RELATIVE_TO_SCREEN) {
                    moveCloserDialog.dismiss();
                    Log.i("MainActivity", "Face Detected");

                    Request request = createRequest(currentFaceToBase64(face.getWidth(), face.getHeight(), face.getPosition()));
                    request.setRetryPolicy(new DefaultRetryPolicy( 50000, 5,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    requestQueue.add(request);
                    Util.animateView(progressOverlay, View.VISIBLE, 0.8f, 200);
                    handler.removeCallbacks(this);
                } else if (face.getWidth() > screenSize.x * MIN_SIZE_OF_FACE_RELATIVE_TO_SCREEN){
                    if (!moveCloserDialog.isShowing()) {
                        moveCloserDialog.show();
                        handler.postDelayed(dialogDismiss, TIME_BETWEEN_PHOTOS * 4);
                    }

                    handler.postDelayed(this, TIME_BETWEEN_PHOTOS);
                } else {
                    handler.postDelayed(this, TIME_BETWEEN_PHOTOS);
                }
            } else {
                moveCloserDialog.dismiss();
                Log.v("MainActivity", "No Face Detected");
                handler.postDelayed(this, TIME_BETWEEN_PHOTOS);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_home);

        //create dialog to show if necessary
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this, R.style.CustomAlertDialog);
        builder.setTitle("Move closer");
        builder.setMessage("Please move closer to the camera.");
        moveCloserDialog = builder.create();

        //get screen size in order to get face size in relation to total screen size
        Display display = getWindowManager().getDefaultDisplay();
        display.getSize(screenSize);

        progressOverlay = findViewById(R.id.progress_overlay);
        camera = findViewById(R.id.camera);
        camera.setAdjustViewBounds(true);
        faceDetector = new FaceDetector.Builder(this)
                .setTrackingEnabled(true)
                .setProminentFaceOnly(true)
                .build();

        requestQueue = Volley.newRequestQueue(this);
    }

    @Override
    protected void onResume()
    {
        super.onResume();


        if (!StoragePermissionHelper.hasStoragePermission(this)) {
            StoragePermissionHelper.requestStoragePermission(this);
            return;
        }

        if (!CameraPermissionHelper.hasCameraPermission(this)) {
            CameraPermissionHelper.requestCameraPermission(this);
            return;
        }

        camera.onResume();
        handler.postDelayed(runnable, INITIAL_DELAY);

    }

    @Override
    protected void onPause() {
        camera.onPause();
        handler.removeCallbacks(runnable);
        super.onPause();
    }

    /**
     * Crops the current image to the prominent face and converts to {@link Base64} string
     * @param faceWidth    - the width of the face in pixels
     * @param faceHeight   - the height of the face in pixels
     * @param facePosition - a {@link Point} of the top left of the image
     * @return Base64 String of the Face data
     */
    public String currentFaceToBase64(float faceWidth, float faceHeight, PointF facePosition) {
        int FACE_CROP_OFFSET = 10; //pixels

        int bottomRightXPos = Math.max(0, Math.round(facePosition.x) - FACE_CROP_OFFSET);
        int bottomRightYPos = Math.max(0, Math.round(facePosition.y) - FACE_CROP_OFFSET);

        int totalCropWidth = Math.min(currentImage.getWidth() - bottomRightXPos,
                Math.round(faceWidth) + (FACE_CROP_OFFSET * 2));
        int totalCropHeight = Math.min(currentImage.getHeight() - bottomRightYPos,
                Math.round(faceHeight) + (FACE_CROP_OFFSET * 2));

        Bitmap bitmapToSend = Bitmap.createBitmap(
                currentImage, bottomRightXPos, bottomRightYPos, totalCropWidth, totalCropHeight);

        return Util.bitmapToBase64(bitmapToSend, 50);
    }

    /**
     * Creates a {@link JsonObjectRequest} with a listener in order to handle response
     * @return a {@link JsonObjectRequest}
     */
    private JsonObjectRequest createRequest(String userPhoto) {
        JSONObject json = new JSONObject();

        //Adding contents to request
        try {
            json.put("UserPhoto", userPhoto);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new JsonObjectRequest
                (Request.Method.POST, AUTHOUT_IMAGE_CHECK, json , new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //TODO Create and set parent object here
                        Log.i("Response", response.toString().substring(0, 100));
                        Util.animateView(progressOverlay, View.GONE, 0, 200);
                        //TODO if face is matched onResponse should move to the next activity
                        //TODO if the response is null/not matched we move to a different activity

                        // ----------- Creating Dummy Parent -----------------------
                        List<Child> dummyChildren = new ArrayList<>();
                        List<Child> dummyTrusted = new ArrayList<>();
                        dummyChildren.add(new Child("Ryan", "Bloggs", "Signed-Out"));
                        dummyChildren.add(new Child("Jack", "Bloggs", "Signed-Out"));
                        dummyChildren.add(new Child("Evan", "Bloggs", "Signed-Out"));
                        dummyTrusted.add(new Child("Jack", "Bloggs", "Signed-Out"));


                        Parent dummyParent = new Parent("Katie", "Bloggs", dummyChildren, dummyTrusted);
                        // ---------------------------------------------------------


                        //TODO Remove this once implementation is finished above.
                        NotRecognizedDialog dialog = new NotRecognizedDialog(
                                HomeActivity.this, handler, runnable, INITIAL_DELAY);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.show();

                        /*Intent intent = new Intent(HomeActivity.this, SelectStudentActivity.class);
                        intent.putExtra("PARENT", dummyParent);
                        intent.putExtra("DISPLAY_TRUSTED_CHILDREN", false);
                        startActivity(intent);
                        finish();*/
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("ResponseError", error.toString());
                    }

                });
    }

    /**
     * Instructs a picture to be taken and sets {@link HomeActivity#currentImage}
     */
    public void takePicture() {
        camera.captureImage(new CameraKitView.ImageCallback() {
            @Override
            public void onImage(CameraKitView cameraKitView, byte[] bytes) {
                currentImage = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            }
        });
    }

    /**
     * Handles the currently selected {@link HomeActivity#currentImage} and detects any faces
     * @return a {@link Face} of the most prominent face
     */
    public Face faceProcessing() {
        if (currentImage == null) {
            return null;
        }
        Frame outputFrame = new Frame.Builder().setBitmap(currentImage).build();
        SparseArray<Face> sparseArray = faceDetector.detect(outputFrame);

        if (sparseArray.size() == 0) {
            return null;
        }

        return sparseArray.valueAt(0);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] results) {
        if (requestCode == 0) {
            if (!StoragePermissionHelper.hasStoragePermission(this)) {
                Toast.makeText(this, getString(R.string.storage_perms), Toast.LENGTH_LONG)
                        .show();
                if (!StoragePermissionHelper.shouldShowRequestPermissionRationale(this)) {
                    // Permission denied with checking "Do not ask again".
                    StoragePermissionHelper.launchPermissionSettings(this);
                }
                finish();
            }
        } else {
            if (!CameraPermissionHelper.hasCameraPermission(this)) {
                Toast.makeText(this, getString(R.string.camera_perms), Toast.LENGTH_LONG)
                        .show();
                if (!CameraPermissionHelper.shouldShowRequestPermissionRationale(this)) {
                    // Permission denied with checking "Do not ask again".
                    CameraPermissionHelper.launchPermissionSettings(this);
                }
                finish();
            }
        }
    }
}
