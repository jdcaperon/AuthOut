package rocketpotatoes.authout;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SignUpActivity extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 1888;
    private static final String REGEX = ".+\\@.+\\..+";
    private Calendar myCalendar;
    private DatePickerDialog.OnDateSetListener date;
    private EditText firstName;
    private EditText surname;
    private EditText mobile;
    private EditText email;
    private EditText dateOfBirth;
    private EditText takePhoto;
    private File userImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firstName = findViewById(R.id.firstName);
        surname = findViewById(R.id.surname);
        mobile = findViewById(R.id.mobile);
        email = findViewById(R.id.email);
        dateOfBirth= findViewById(R.id.dob);
        takePhoto = findViewById(R.id.photo);

        setUpCalendarPicker();

        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                try {
                    userImage = createImageFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (userImage != null) {
                    userImage.deleteOnExit();
                    Uri photoUri = FileProvider.getUriForFile(
                            SignUpActivity.this, "com.example.android.fileprovider", userImage);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                    startActivityForResult(cameraIntent, 1);
                }
            }
        });
    }


    private boolean isValidContent() {
        boolean isValid = true;

        if (firstName.length() == 0) {
            firstName.setBackground(getDrawable(R.drawable.signup_input_error));
            isValid = false;
        } else {
            firstName.setBackground(getDrawable(R.drawable.signup_input_background));
        }

        if (surname.length() == 0) {
            surname.setBackground(getDrawable(R.drawable.signup_input_error));
            isValid = false;
        } else {
            surname.setBackground(getDrawable(R.drawable.signup_input_background));
        }

        if (mobile.length() == 0) {
            mobile.setBackground(getDrawable(R.drawable.signup_input_error));
            isValid = false;
        } else {
            mobile.setBackground(getDrawable(R.drawable.signup_input_background));
        }

        if (email.length() == 0 || !email.getText().toString().matches(REGEX)) {
            email.setBackground(getDrawable(R.drawable.signup_input_error));
            isValid = false;
        } else {
            email.setBackground(getDrawable(R.drawable.signup_input_background));
        }

        if (dateOfBirth.length() == 0) {
            dateOfBirth.setBackground(getDrawable(R.drawable.signup_input_error));
            isValid = false;
        } else {
            dateOfBirth.setBackground(getDrawable(R.drawable.signup_input_background));
        }

        if (takePhoto.length() == 0) {
            takePhoto.setBackground(getDrawable(R.drawable.signup_input_error));
            isValid = false;
        }

        return isValid;
    }

    public void reviewContent(View v) {
        if (isValidContent()) {
            Intent intent = new Intent(this, SignUpReviewActivity.class);
            intent.putExtra("FIRST_NAME", firstName.getText().toString());
            intent.putExtra("SURNAME", surname.getText().toString());
            intent.putExtra("EMAIL", email.getText().toString());
            intent.putExtra("PHONE", mobile.getText().toString());
            intent.putExtra("PHOTO", userImage);
            intent.putExtra("DOB", dateOfBirth.getText().toString());
            startActivity(intent);
        }
    }


    private void setUpCalendarPicker() {

        myCalendar = Calendar.getInstance();
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        dateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(SignUpActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);
        dateOfBirth.setText(sdf.format(myCalendar.getTime()));
    }

    private File createImageFile() throws IOException {
        long timeStamp = System.currentTimeMillis();
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            takePhoto.setText("Image Captured!");
            takePhoto.setBackground(getDrawable(R.drawable.signup_input_background));
        }
    }

    public void backClicked(View v) {
        finish();
    }
}
