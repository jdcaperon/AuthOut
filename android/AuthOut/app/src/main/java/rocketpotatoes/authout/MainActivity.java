package rocketpotatoes.authout;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.*;
import com.camerakit.CameraKitView;

import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private CameraKitView cameraKitView;
    private FaceDetector faceDetector;
    private Bitmap currentImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        cameraKitView = findViewById(R.id.cameraKitView);
        faceDetector = new FaceDetector.Builder(this)
                .setTrackingEnabled(true)
                .setProminentFaceOnly(true)
                .build();

        handler.postDelayed(runnable, 200);
    }

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            takePicture();
            Face face = faceProcessing();
            if (face != null) {
                Log.i("MainActivity", "Face Detected");
                Toast.makeText(MainActivity.this, "Face Detected", Toast.LENGTH_SHORT).show();
            } else {
                Log.i("MainActivity", "No Face Detected");
                Toast.makeText(MainActivity.this, "No Face Detected", Toast.LENGTH_SHORT).show();
            }
            handler.postDelayed(this, 200);
        }
    };

    /**
     * Instructs a picture to be taken and sets {@link MainActivity#currentImage}
     */
    public void takePicture() {
        cameraKitView.captureImage(new CameraKitView.ImageCallback() {
            @Override
            public void onImage(CameraKitView cameraKitView, byte[] bytes) {
                currentImage = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                Log.i("random", currentImage.toString());
                Log.i("MainActivity", "Image captured");
            }
        });
    }

    public Face faceProcessing() {
        if (currentImage == null) return null;

        Frame outputFrame = new Frame.Builder().setBitmap(currentImage).build();
        SparseArray<Face> sparseArray = faceDetector.detect(outputFrame);
        if (sparseArray.size() == 0) return null;

        return sparseArray.valueAt(0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraKitView.onResume();
    }

    @Override
    protected void onPause() {
        cameraKitView.onPause();
        super.onPause();
    }
}
