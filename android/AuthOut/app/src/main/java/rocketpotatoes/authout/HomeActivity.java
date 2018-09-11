package rocketpotatoes.authout;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;

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


public class HomeActivity extends AppCompatActivity {
    private static final int INITIAL_DELAY = 2000;
    private static final int TIME_BETWEEN_PHOTOS = 500;
    private static final double SIZE_OF_FACE_RELATIVE_TO_SCREEN = 0.50;
    private static final String AUTHOUT_SERVER_URL = "http://httpbin.org/post";
    private CameraKitView camera;
    private FaceDetector faceDetector;
    private Bitmap currentImage;
    private RequestQueue requestQueue;
    private AlertDialog moveCloserDialog;

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
                    //onPressSignIn(findViewById(android.R.id.content)); //todo temp go to next activity
                    requestQueue.add(createRequest(currentFaceToBase64(face.getWidth(), face.getHeight(), face.getPosition())));
                    handler.removeCallbacks(this);

                } else {
                    if (!moveCloserDialog.isShowing()) {
                        moveCloserDialog.show();
                        handler.postDelayed(dialogDismiss, TIME_BETWEEN_PHOTOS * 4);
                    }

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

        camera = findViewById(R.id.camera);
        camera.setAdjustViewBounds(true);
        faceDetector = new FaceDetector.Builder(this)
                .setTrackingEnabled(true)
                .setProminentFaceOnly(true)
                .build();

        requestQueue = Volley.newRequestQueue(this);
    }



    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(runnable, INITIAL_DELAY);
        camera.onResume();
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

        //bitmap to base64 string
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmapToSend.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(byteArray, Base64.DEFAULT);
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
                (Request.Method.POST, AUTHOUT_SERVER_URL, json , new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //TODO Create and set parent object here
                        Log.i("Response", response.toString().substring(0, 100));
                        //TODO if face is matched onResponse should move to the next activity
                        //TODO if the response is null/not matched we move to a different activity

                        //TODO Remove this once implementation is finished above.
                        /*NotRecognizedDialog dialog = new NotRecognizedDialog(
                                HomeActivity.this, handler, runnable, INITIAL_DELAY);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.show();*/

                        //Intent intent = new Intent(HomeActivity.this, SelectStudentActivity.class);
                        //startActivity(intent);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
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
        if (currentImage == null) return null;
        Frame outputFrame = new Frame.Builder().setBitmap(currentImage).build();
        SparseArray<Face> sparseArray = faceDetector.detect(outputFrame);
        if (sparseArray.size() == 0) return null;

        return sparseArray.valueAt(0);
    }
}
