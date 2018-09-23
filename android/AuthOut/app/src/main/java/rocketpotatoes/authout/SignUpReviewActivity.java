package rocketpotatoes.authout;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

public class SignUpReviewActivity extends AppCompatActivity {

    private TextView fullName;
    private TextView mobile;
    private TextView email;
    private TextView dateOfBirth;
    private ImageView userImage;

    private String firstNameData;
    private String surnameData;
    private String mobileData;
    private String emailData;
    private String dateOfBirthData;
    private File takePhotoData;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_review);
        setUpVariables();
        setUpLayout();
    }

    public void back(View v) {
        finish();
    }


    private void setUpVariables() {
        fullName = findViewById(R.id.fullName);
        mobile = findViewById(R.id.mobile);
        email = findViewById(R.id.email);
        dateOfBirth= findViewById(R.id.dob);
        userImage = findViewById(R.id.userImage);

        firstNameData = getIntent().getExtras().getString("FIRST_NAME");
        surnameData = getIntent().getExtras().getString("SURNAME");
        mobileData = getIntent().getExtras().getString("PHONE");
        dateOfBirthData = getIntent().getExtras().getString("DOB");
        emailData = getIntent().getExtras().getString("EMAIL");
        takePhotoData = (File) getIntent().getSerializableExtra("PHOTO");
    }

    private void setUpLayout() {
        fullName.setText(getResources().getString(R.string.full_name, firstNameData, surnameData));
        mobile.setText(mobileData);
        email.setText(emailData);
        dateOfBirth.setText(dateOfBirthData);

        Bitmap myBitmap = BitmapFactory.decodeFile(takePhotoData.getAbsolutePath());

        try {
            myBitmap = rotateUserImage(myBitmap);
        } catch (IOException e){
            e.printStackTrace();
        }

        userImage.setMaxWidth(userImage.getWidth());
        userImage.setMaxHeight(userImage.getHeight());
        userImage.setImageBitmap(myBitmap);
    }

    private Bitmap rotateUserImage(Bitmap bitmap) throws IOException {
        ExifInterface ei = new ExifInterface(takePhotoData.getAbsolutePath());
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED);

        Bitmap rotatedBitmap = null;
        switch(orientation) {

            case ExifInterface.ORIENTATION_ROTATE_90:
                rotatedBitmap = rotateImage(bitmap, 90);
                break;

            case ExifInterface.ORIENTATION_ROTATE_180:
                rotatedBitmap = rotateImage(bitmap, 180);
                break;

            case ExifInterface.ORIENTATION_ROTATE_270:
                rotatedBitmap = rotateImage(bitmap, 270);
                break;

            case ExifInterface.ORIENTATION_NORMAL:
            default:
                rotatedBitmap = bitmap;
        }
        return rotatedBitmap;
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }
}
