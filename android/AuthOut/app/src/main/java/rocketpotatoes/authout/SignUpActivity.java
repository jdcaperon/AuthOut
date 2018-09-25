package rocketpotatoes.authout;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class SignUpActivity extends AppCompatActivity {
    private static final String REGEX = ".+@.+\\..+";
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
        List<EditText> inputs = new ArrayList<>(Arrays.asList(firstName, surname, mobile, email, dateOfBirth, takePhoto));
        for (EditText input : inputs) {
            if (input.length() == 0 || input.equals(email) &&
                    !input.getText().toString().matches(REGEX)) {
                input.setBackground(getDrawable(R.drawable.signup_input_error));
                isValid = false;
            } else {
                input.setBackground(getDrawable(R.drawable.signup_input_background));
            }
        }

        return isValid;
    }

    public void reviewContent(View v) {
        if (isValidContent()) {
            Intent intent = new Intent(this, SignUpChildActivity.class);
            HashMap<String, String> signUpInfo = new HashMap<>();
            signUpInfo.put("FIRST_NAME", firstName.getText().toString());
            signUpInfo.put("SURNAME", surname.getText().toString());
            signUpInfo.put("EMAIL", email.getText().toString());
            signUpInfo.put("PHONE", mobile.getText().toString());
            signUpInfo.put("DOB", dateOfBirth.getText().toString());

            intent.putExtra("PARENT_DETAILS", signUpInfo);
            intent.putExtra("PHOTO", userImage);
            startActivity(intent);
        } else {
            Toast.makeText(this, R.string.fix_input, Toast.LENGTH_LONG).show();
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
            takePhoto.setText(getString(R.string.photoCaptured));
            takePhoto.setBackground(getDrawable(R.drawable.signup_input_background));
        }
    }

    public void backClicked(View v) {
        finish();
    }
}
