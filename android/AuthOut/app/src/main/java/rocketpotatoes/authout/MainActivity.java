package rocketpotatoes.authout;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.*;
import com.camerakit.CameraKitView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {
    private static final int TIME_BETWEEN_PHOTOS = 500;
    private static final double SIZE_OF_FACE_RELATIVE_TO_SCREEN = 0.70;
    private static final String AUTHOUT_SERVER_URL = "http://httpbin.org/post";
    private CameraKitView cameraKitView;
    private FaceDetector faceDetector;
    private Bitmap currentImage;
    private RequestQueue requestQueue;

    private Display display;
    private Point screenSize = new Point();

    // Handler for intermittent execution
    private Handler handler = new Handler();

    /** Runnable to be executed every {@link MainActivity#TIME_BETWEEN_PHOTOS} milliseconds */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            takePicture();
            Face face = faceProcessing();
            // Ensure face is appropriate size to move forwards
            if (face != null) {
                if (face.getWidth() > screenSize.x * SIZE_OF_FACE_RELATIVE_TO_SCREEN) {
                    Log.i("MainActivity", "Face Detected");
                    Toast.makeText(MainActivity.this, "Face Detected", Toast.LENGTH_SHORT).show();
                    requestQueue.add(createRequest());

                    //stop the handler from taking photos until the response is received
                    handler.removeCallbacks(this);
                } else {
                    Toast.makeText(MainActivity.this, "Please move closer to the camera", Toast.LENGTH_LONG).show();
                    handler.postDelayed(this, TIME_BETWEEN_PHOTOS);
                }
            } else {    
                Log.v("MainActivity", "No Face Detected");
                Toast.makeText(MainActivity.this, "No Face Detected", Toast.LENGTH_SHORT).show();
                handler.postDelayed(this, TIME_BETWEEN_PHOTOS);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //
        display = getWindowManager().getDefaultDisplay();
        display.getSize(screenSize);

        cameraKitView = findViewById(R.id.cameraKitView);
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
        cameraKitView.onResume();
    }

    @Override
    protected void onPause() {
        cameraKitView.onPause();
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
     * Instructs a picture to be taken and sets {@link MainActivity#currentImage}
     */
    public void takePicture() {
        cameraKitView.captureImage(new CameraKitView.ImageCallback() {
            @Override
            public void onImage(CameraKitView cameraKitView, byte[] bytes) {
                currentImage = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            }
        });
    }

    /**
     * Handles the currently selected {@link MainActivity#currentImage} and detects any faces
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
