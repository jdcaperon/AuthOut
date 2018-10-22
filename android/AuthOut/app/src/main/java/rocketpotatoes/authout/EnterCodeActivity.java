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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import rocketpotatoes.authout.Helpers.Child;
import rocketpotatoes.authout.Helpers.Parent;
import rocketpotatoes.authout.Helpers.Util;

public class EnterCodeActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String AUTHOUT_CODE_URL = "http://deco3801.wisebaldone.com/api/kiosk/signin_code";
    private StringBuilder codeInputBuilder;
    private EditText codeInput;
    private Button submitButton;
    private RequestQueue requestQueue;
    private View progressOverlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_code);
        submitButton = findViewById(R.id.submitCode);
        submitButton.setEnabled(false);
        codeInputBuilder = new StringBuilder();
        progressOverlay = findViewById(R.id.progress_overlay);
        codeInput = findViewById(R.id.editText);
        requestQueue = Volley.newRequestQueue(this);
    }

    @Override
    public void onClick(View v) {
        if (codeInputBuilder.length() == 4) {
            return;
        }

        Button button = findViewById(v.getId());
        codeInputBuilder.append(button.getText());
        codeInput.setText(codeInputBuilder.toString());

        if (codeInputBuilder.length() == 4) {
            submitButton.setEnabled(true);
        }
    }

    /** Adds a request to the {@link RequestQueue}
     *
     * @param v - current View
     */
    public void submitCode(View v) {
        Request request = createRequest(codeInputBuilder.toString());
        request.setRetryPolicy(new DefaultRetryPolicy( 50000, 5,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);
        Util.animateView(progressOverlay, View.VISIBLE, 0.8f, 200);
    }

    /** Returns to the home activity
     *
     * @param v - current View
     */
    public void cancel(View v) {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    /** Removes a number from the currently entered code
     *
     * @param v - current View
     */
    public void backspace(View v) {
        if (codeInputBuilder.length() <= 0) {
            return;
        }
        codeInputBuilder.deleteCharAt(codeInputBuilder.length() - 1);
        codeInput.setText(codeInputBuilder.toString());
        submitButton.setEnabled(false);
    }

    /**
     * Creates a {@link JsonObjectRequest} with a listener in order to handle response
     * @return a {@link JsonObjectRequest}
     */
    private JsonObjectRequest createRequest(String code) {
        JSONObject json = new JSONObject();

        //Adding contents to request
        try {
            json.put("code", code);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new JsonObjectRequest
                (Request.Method.POST, AUTHOUT_CODE_URL, json , new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("Response", response.toString().substring(0, 100));

                        //TODO if the code is matched move to the next activity with parent
                        //TODO if the code is not match put up a toast/handle it
                        Util.animateView(progressOverlay, View.GONE, 0.8f, 200);


                        List<Child> childrenList = Util.buildChildList(response, false);
                        List<Child> trustedChildrenList = Util.buildChildList(response, true);

                        Parent parent = Util.buildParent(response, childrenList, trustedChildrenList);

                        Intent intent = new Intent(EnterCodeActivity.this, SelectStudentActivity.class);
                        intent.putExtra("PARENT", parent);
                        intent.putExtra("DISPLAY_TRUSTED_CHILDREN", false);
                        startActivity(intent);
                        finish();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Util.animateView(progressOverlay, View.GONE, 0.8f, 200);
                        Log.i("ResponseError", error.toString());
                    }

                });
    }
}
