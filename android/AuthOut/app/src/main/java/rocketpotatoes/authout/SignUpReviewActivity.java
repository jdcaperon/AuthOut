/*
 * MIT License

 Copyright (c) 2018 Ryan Kurz

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */
package rocketpotatoes.authout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


import rocketpotatoes.authout.Helpers.Util;


public class SignUpReviewActivity extends AppCompatActivity {
    private static final String CREATE_PARENT_URL = "https://deco3801.wisebaldone.com/api/kiosk/register";
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
    private ArrayList<ArrayList<String>> children;
    private RecyclerView childSignupSelectorView;

    private RequestQueue requestQueue;

    private View progressOverlay;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_review);
        setUpVariables();
        setUpLayout();
        setUpChildList();
        requestQueue = Volley.newRequestQueue(this);
    }

    public void back(View v) {
        finish();
    }


    /** Sets up the child list and the {@link ChildSignupListAdapter} */
    private void setUpChildList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView childSignupSelectorView = findViewById(R.id.child_selector);
        assert (childSignupSelectorView != null);

        childSignupSelectorView.setLayoutManager(layoutManager);
        ChildSignupListAdapter childSignupListAdapter = new ChildSignupListAdapter(children, this);
        childSignupSelectorView.setAdapter(childSignupListAdapter);
    }

    /** Registers the edit texts and sets variables to be used later*/
    private void setUpVariables() {
        progressOverlay = findViewById(R.id.progress_overlay);
        fullName = findViewById(R.id.fullName);
        mobile = findViewById(R.id.mobile);
        email = findViewById(R.id.email);
        dateOfBirth= findViewById(R.id.dob);
        userImage = findViewById(R.id.userImage);

        takePhotoData = (File) getIntent().getSerializableExtra("PHOTO");
        children = (ArrayList<ArrayList<String>>) getIntent().getSerializableExtra("CHILDREN");

        HashMap<String, String> parentDetails = (HashMap<String, String>) getIntent().getSerializableExtra("PARENT_DETAILS");
        firstNameData = parentDetails.get("FIRST_NAME");
        surnameData = parentDetails.get("SURNAME");
        mobileData = parentDetails.get("PHONE");
        dateOfBirthData = parentDetails.get("DOB");
        emailData = parentDetails.get("EMAIL");
    }


    /** Sets up the {@link TextView} components texts and the user image */
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

    /** Rotates the user image taken based on phone type */
    private Bitmap rotateUserImage(Bitmap bitmap) throws IOException {
        ExifInterface ei = new ExifInterface(takePhotoData.getAbsolutePath());
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED);

        Bitmap rotatedBitmap;
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

    /** Rotates the user image taken based on phone type */
    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    /** Function called when user 'Confirms Registration'
     *
     * @param v - the current view
     */
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

        Log.i("CreatingRequest", "CreatingRequest");
        //Adding contents to request
        try {
            JSONObject temp = new JSONObject();
            temp.put("email", emailData);
            temp.put("first_name", firstNameData);
            temp.put("last_name", surnameData);
            temp.put("date_of_birth", dateOfBirthData);
            temp.put("mobile_number", mobileData);
            json.put("parent", temp);


            JSONObject[] childrenObjects = new JSONObject[children.size()];
            for (int i = 0; i < children.size(); i++) {
                temp = new JSONObject();
                temp.put("first_name", children.get(i).get(0));
                temp.put("last_name", children.get(i).get(1));
                temp.put("date_of_birth", children.get(i).get(2));
                childrenObjects[i] = temp;
            }

            json.put("children", Arrays.toString(childrenObjects));
            json.put("user_photo", Util.bitmapToBase64(userBitmap, 50));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new JsonObjectRequest
                (Request.Method.POST, CREATE_PARENT_URL, json , new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Util.animateView(progressOverlay, View.GONE, 0, 100);
                        Intent intent = new Intent(SignUpReviewActivity.this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("ResponseError", error.toString());
                    }

                });
    }
}
