package rocketpotatoes.authout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import rocketpotatoes.authout.helpers.*;

public class FaceRecognition extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_recognition);

        if (null == savedInstanceState) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, CameraFragment.newInstance())
                    .commit();
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] results) {
        if (!CameraPermissions.hasPermissionForCamera(this)) {
            Toast.makeText(this, getString(R.string.request_permissions), Toast.LENGTH_LONG)
                    .show();
            if (!CameraPermissions.shouldShowRequestPermissionRationale(this)) {
                // Permission denied with checking "Do not ask again".
                CameraPermissions.launchAppSettings(this);
            }
            finish();
        }
    }
}
