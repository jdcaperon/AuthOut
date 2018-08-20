package rocketpotatoes.authout;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.util.SparseArray;
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


public class MainActivity extends AppCompatActivity {
    private static final int TIME_BETWEEN_PHOTOS = 500;
    private static final String AUTHOUT_SERVER_URL = "http://httpbin.org/post";
    private CameraKitView cameraKitView;
    private FaceDetector faceDetector;
    private Bitmap currentImage;
    private byte[] currentImageBytes;
    private RequestQueue requestQueue;

    // Handler for intermittent execution
    private Handler handler = new Handler();

    /** Runnable to be executed every {@link MainActivity#TIME_BETWEEN_PHOTOS} milliseconds */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            takePicture();
            Face face = faceProcessing();
            if (face != null) {
                Log.i("MainActivity", "Face Detected");
                Toast.makeText(MainActivity.this, "Face Detected", Toast.LENGTH_SHORT).show();


                //requestQueue.add(createRequest();

            } else {    
                Log.i("MainActivity", "No Face Detected");
                Toast.makeText(MainActivity.this, "No Face Detected", Toast.LENGTH_SHORT).show();
            }
            handler.postDelayed(this, TIME_BETWEEN_PHOTOS);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        cameraKitView = findViewById(R.id.cameraKitView);
        faceDetector = new FaceDetector.Builder(this)
                .setTrackingEnabled(true)
                .setProminentFaceOnly(true)
                .build();

        requestQueue = Volley.newRequestQueue(this);
        handler.postDelayed(runnable, TIME_BETWEEN_PHOTOS);
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


    private JsonObjectRequest createRequest(String base64Image) {
        JSONObject json = new JSONObject();
        Log.i("Image", base64Image);
        try {
            json.put("Image", base64Image);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JsonObjectRequest
                (Request.Method.POST, AUTHOUT_SERVER_URL, null , new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("Response", response.toString());
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
                currentImageBytes = bytes;
                Log.i("MainActivity", "Image captured");
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
