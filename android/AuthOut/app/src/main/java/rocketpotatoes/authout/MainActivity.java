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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        cameraKitView = findViewById(R.id.cameraKitView);
        faceDetector = new FaceDetector.Builder(this)
                .setTrackingEnabled(true)
                .build();
    }

    public void onCaptureButtonClicked(View view) {
        cameraKitView.captureImage(new CameraKitView.ImageCallback() {
            @Override
            public void onImage(CameraKitView cameraKitView, byte[] bytes) {
                Log.i("MainActivity", "Image captured");
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);



                Frame outputFrame = new Frame.Builder().setBitmap(bmp).build();
                SparseArray<Face> sparseArray = faceDetector.detect(outputFrame);
                try {
                    Toast.makeText(MainActivity.this, "Detected " + sparseArray.size(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {

                    Toast.makeText(MainActivity.this, sparseArray.get(0).toString() , Toast.LENGTH_SHORT).show();
                }



            }
        });
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
