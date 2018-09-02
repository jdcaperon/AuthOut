package rocketpotatoes.authout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Toast;

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

    private static final int TIME_BETWEEN_PHOTOS = 500;
    private static final String AUTHOUT_SERVER_URL = "http://httpbin.org/post";
    private CameraKitView camera;
    private FaceDetector faceDetector;
    private Bitmap currentImage;
    private RequestQueue requestQueue;

    // Handler for intermittent execution
    private Handler handler = new Handler();

    /** Runnable to be executed every {@link HomeActivity#TIME_BETWEEN_PHOTOS} milliseconds */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            takePicture();
            Face face = faceProcessing();
            if (face != null) {
                Log.i("HomeActivity", "Face Detected");
                Toast.makeText(HomeActivity.this, "Face Detected", Toast.LENGTH_SHORT).show();
                requestQueue.add(createRequest());

                //stop the handler from taking photos until the response is received
                handler.removeCallbacks(this);
            } else {
                Log.v("HomeActivity", "No Face Detected");
                Toast.makeText(HomeActivity.this, "No Face Detected", Toast.LENGTH_SHORT).show();
                handler.postDelayed(this, TIME_BETWEEN_PHOTOS);
            }
        }
    };

    public void onPressSignIn(View view) {
        Intent intent = new Intent(this, UserConfirmActivity.class);
        startActivity(intent);
    }

    public void onPressSignOut(View view){
        Intent intent = new Intent(this, UserConfirmActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

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
        handler.postDelayed(runnable, TIME_BETWEEN_PHOTOS);
        camera.onResume();
    }

    @Override
    protected void onPause() {
        camera.onPause();
        handler.removeCallbacks(runnable);
        super.onPause();
    }

    /**
     * Creates a {@link JsonObjectRequest} with a listener in order to handle response
     * @return a {@link JsonObjectRequest}
     */
    private JsonObjectRequest createRequest() {
        JSONObject json = new JSONObject();

        //bitmap to base64 string
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        currentImage.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String userPhoto = Base64.encodeToString(byteArray, Base64.DEFAULT);

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
                        Log.i("Response", response.toString().substring(0, 100));
                        //TODO if face is matched onResponse should move to the next activity with
                        //TODO user ID specified in order to progress.

                        //TODO if the user ID isn't found, then restart the picture handler
                        handler.postDelayed(runnable, TIME_BETWEEN_PHOTOS);
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
