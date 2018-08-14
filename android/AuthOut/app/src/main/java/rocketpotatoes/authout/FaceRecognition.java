package rocketpotatoes.authout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import android.hardware.camera2.*;

import rocketpotatoes.authout.helpers.*;

public class FaceRecognition extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_recognition);

    }
    
    @Override
    protected void onResume() {
        super.onResume();

        if (!CameraPermissions.hasPermissionForCamera(this)) {
            CameraPermissions.askForCameraPermission(this);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] results) {
        if (!CameraPermissions.hasPermissionForCamera(this)) {
            Toast.makeText(this, "Camera permission is needed to run this application", Toast.LENGTH_LONG)
                    .show();
            if (!CameraPermissions.shouldShowRequestPermissionRationale(this)) {
                // Permission denied with checking "Do not ask again".
                CameraPermissions.launchAppSettings(this);
            }
            finish();
        }
    }
}
