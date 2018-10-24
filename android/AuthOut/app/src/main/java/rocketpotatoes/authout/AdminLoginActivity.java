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
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import rocketpotatoes.authout.Helpers.Util;

public class AdminLoginActivity extends AppCompatActivity {

    private static final String AUTHOUT_ADMIN_CHECK = "https://deco3801.wisebaldone.com/api/login";
    private View progressOverlay;
    private RequestQueue requestQueue;
    private EditText username;
    private EditText password;
    private TextView hintText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        progressOverlay = findViewById(R.id.progress_overlay);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        hintText = findViewById(R.id.pleaseLogin);
        requestQueue = Volley.newRequestQueue(this);
    }


    /** onClick for login button
     *
     * @param v - the current view
     */
    public void login(View v) {
        boolean valid = true;
        if (username.length() == 0) {
            hintText.setText(getString(R.string.required_fields));
            hintText.setTextColor(Color.RED);
            username.setBackground(getDrawable(R.drawable.signup_input_error));
            valid = false;
        }

        if (password.length() == 0) {
            hintText.setText(getString(R.string.required_fields));
            hintText.setTextColor(Color.RED);
            password.setBackground(getDrawable(R.drawable.signup_input_error));
            valid = false;
        }
        if (valid) {
            requestQueue.add(createRequest());
        }
    }

    /** onClick for cancel button
     *
     * @param v - the current view
     */
    public void cancel(View v) {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    /**
     * Creates a {@link JsonObjectRequest} with a listener in order to handle response
     * @return a {@link JsonObjectRequest}
     */
    private JsonObjectRequest createRequest() {
        JSONObject json = new JSONObject();

        //Adding contents to request
        final String usernameText = username.getText().toString();
        final String passwordText = password.getText().toString();

        return new JsonObjectRequest
                (Request.Method.GET, AUTHOUT_ADMIN_CHECK, json , new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Util.animateView(progressOverlay, View.GONE, 0, 200);
                        Intent intent = new Intent(AdminLoginActivity.this, AdminActivity.class);
                        startActivity(intent);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Util.animateView(progressOverlay, View.GONE, 0.8f, 200);
                        Toast.makeText(AdminLoginActivity.this, "Oops, something went wrong. Try again.", Toast.LENGTH_SHORT).show();
                        hintText.setText(getString(R.string.incorrect_info));
                        hintText.setTextColor(Color.RED);
                        Log.i("ResponseError", error.toString());
                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String>  params = new HashMap<>();
                        String userAndPassword = usernameText + ":" + passwordText;
                        final String basicAuth = "Basic " + Base64.encodeToString(userAndPassword.getBytes(), Base64.NO_WRAP);
                        params.put("Authorization", basicAuth);
                        return params;
                    }
        };
    }
}
