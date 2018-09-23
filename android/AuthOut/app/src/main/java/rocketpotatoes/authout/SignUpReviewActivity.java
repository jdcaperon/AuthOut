package rocketpotatoes.authout;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import rocketpotatoes.authout.Helpers.Util;


public class SignUpReviewActivity extends AppCompatActivity {
    private static final String CREATE_PARENT_URL = "http://httpbin.org/post";
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
    private Bitmap userBitmap;
    private RequestQueue requestQueue;

    private View progressOverlay;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_review);
        setUpVariables();
        setUpLayout();
        requestQueue = Volley.newRequestQueue(this);
    }

    public void back(View v) {
        finish();
    }


    private void setUpVariables() {
        progressOverlay = findViewById(R.id.progress_overlay);
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

        userBitmap = BitmapFactory.decodeFile(takePhotoData.getAbsolutePath());

        try {
            userBitmap = rotateUserImage(userBitmap);
        } catch (IOException e){
            e.printStackTrace();
        }

        userImage.setMaxWidth(userImage.getWidth());
        userImage.setMaxHeight(userImage.getHeight());
        userImage.setImageBitmap(userBitmap);
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

    public void confirmUserSubmission(View v) {
        Request request = createParentRequest();
        request.setRetryPolicy(new DefaultRetryPolicy( 50000, 5,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);
        Util.animateView(progressOverlay, View.VISIBLE, 0.8f, 100);
    }


    /**
     * Creates a {@link JsonObjectRequest} with a listener in order to handle response
     * @return a {@link JsonObjectRequest}
     */
    private JsonObjectRequest createParentRequest() {
        JSONObject json = new JSONObject();

        //Adding contents to request
        try {
            json.put("firstName", firstNameData);
            json.put("surname", surnameData);
            json.put("email", emailData);
            json.put("mobile", mobileData);
            json.put("dateOfBirth", dateOfBirthData);
            json.put("userPhoto", Util.bitmapToBase64(userBitmap, 50));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new JsonObjectRequest
                (Request.Method.POST, CREATE_PARENT_URL, json , new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //TODO Create and set parent object here
                        Util.animateView(progressOverlay, View.GONE, 0, 100);
                        Log.i("Response", response.toString().substring(0, 100));
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("ResponseError", error.toString());
                    }

                });
    }
}
