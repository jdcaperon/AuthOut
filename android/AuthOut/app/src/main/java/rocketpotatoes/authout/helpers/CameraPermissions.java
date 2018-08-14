package rocketpotatoes.authout.helpers;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public class CameraPermissions {
    private static final int CAMERA_PERMISSION_CODE = 0;
    private static final String CAMERA_PERMISSION = Manifest.permission.CAMERA;

    /** Checks if the activity has the necessary permissions for the camera
     *
     * @param activity - the activity to evaluate
     * @return boolean value of if the activity has permission
     */
    public static boolean hasPermissionForCamera(Activity activity) {
        return ContextCompat.checkSelfPermission(activity, CAMERA_PERMISSION) == PackageManager.PERMISSION_GRANTED;
    }

    /** Asks the user for camera permissions
     *
     * @param activity - the activity on which to ask for the permission.
     */
    public static void askForCameraPermission(Activity activity) {
        ActivityCompat.requestPermissions(activity, new String[] {CAMERA_PERMISSION} , CAMERA_PERMISSION_CODE);
    }

    /** Determines if the request rationale should be shown
     *
     * @param activity - the activity on which to evaluate
     * @return boolean value of if the rationale should be shown.
     */
    public static boolean shouldShowRequestPermissionRationale(Activity activity) {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, CAMERA_PERMISSION);
    }

    /** Opens settings in order to change permissions
     * 
     * @param activity - the activity in which to launch the permission settings
     */
    public static void launchAppSettings(Activity activity) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", activity.getPackageName(), null));
        activity.startActivity(intent);
    }



}
