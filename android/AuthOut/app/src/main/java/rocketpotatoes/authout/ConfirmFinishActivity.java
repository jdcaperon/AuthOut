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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import rocketpotatoes.authout.Helpers.Child;
import rocketpotatoes.authout.Helpers.Parent;
import rocketpotatoes.authout.Helpers.Util;


public class ConfirmFinishActivity extends AppCompatActivity {
    private static final String AUTHOUT_IMAGE_CHECK = "https://deco3801.wisebaldone.com/api/kiosk/login";
    private RequestQueue requestQueue;
    private String photo;
    private View progressOverlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        progressOverlay = findViewById(R.id.progress_overlay);
        requestQueue = Volley.newRequestQueue(this);

        photo = getIntent().getExtras() == null ? null : getIntent().getExtras().getString("PHOTO");

    }

    /** Clears all current activities on the back stack and stars the home activity
     *
     * @param view - current View
     */
    public void onPressConfirm(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void anotherAction(View v) {
        requestQueue.add(createRequest(photo));
    }

    private JsonObjectRequest createRequest(final String userPhoto) {
        JSONObject json = new JSONObject();

        //Adding contents to request
        try {
            json.put("user_photo", userPhoto);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new JsonObjectRequest
                (Request.Method.POST, AUTHOUT_IMAGE_CHECK, json , new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Util.animateView(progressOverlay, View.GONE, 0, 200);

                        List<Child> childrenList = Util.buildChildList(response, false);
                        List<Child> trustedChildrenList = Util.buildChildList(response, true);

                        Parent parent = Util.buildParent(response, childrenList, trustedChildrenList);

                        Intent intent = new Intent(ConfirmFinishActivity.this, SelectStudentActivity.class);
                        intent.putExtra("PARENT", parent);
                        intent.putExtra("DISPLAY_TRUSTED_CHILDREN", false);
                        intent.putExtra("PHOTO", userPhoto);
                        startActivity(intent);
                        finish();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Intent intent = new Intent(ConfirmFinishActivity.this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                });
    }
}
