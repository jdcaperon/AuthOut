package rocketpotatoes.authout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class AdminLoginActivity extends AppCompatActivity {

    private static final String AUTHOUT_ADMIN_CHECK = "https://deco3801.wisebaldone.com/api/kiosk/login";
    private View progressOverlay;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        progressOverlay = findViewById(R.id.progress_overlay);
        requestQueue = Volley.newRequestQueue(this);
    }


    public void login(View v) {
        requestQueue.add(createRequest());
    }

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
        try {
            json.put("email", "email");
            json.put("password", "password");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new JsonObjectRequest
                (Request.Method.POST, AUTHOUT_ADMIN_CHECK, json , new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Util.animateView(progressOverlay, View.GONE, 0, 200);
                        //todo finish this
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Util.animateView(progressOverlay, View.GONE, 0, 200);

                        Log.i("ResponseError", error.toString());
                    }
                });
    }
}
