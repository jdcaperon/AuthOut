package rocketpotatoes.authout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import rocketpotatoes.authout.Helpers.Child;
import rocketpotatoes.authout.Helpers.Parent;
import rocketpotatoes.authout.Helpers.Util;

public class AdminLoginActivity extends AppCompatActivity {

    private static final String AUTHOUT_ADMIN_CHECK = "https://deco3801.wisebaldone.com/api/kiosk/login"; //todo fix this
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


    public void login(View v) {
        if (username.getText().toString().equals("Ryan")) {
            Intent intent = new Intent(this, AdminActivity.class);
            startActivity(intent);
        } else {
            hintText.setText("Incorrect username or password");
            hintText.setTextColor(Color.RED);
        }
        //todo add back requestQueue.add(createRequest());
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
            json.put("email", username.getText().toString());
            json.put("password", password.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new JsonObjectRequest
                (Request.Method.POST, AUTHOUT_ADMIN_CHECK, json , new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Util.animateView(progressOverlay, View.GONE, 0, 200);

                        if (true) {  //todo finish this and set hint text if wrong data
                            Intent intent = new Intent(AdminLoginActivity.this, AdminActivity.class);
                            startActivity(intent);
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Util.animateView(progressOverlay, View.GONE, 0.8f, 200);
                        Toast.makeText(AdminLoginActivity.this, "Oops, something went wrong. Try again.", Toast.LENGTH_SHORT).show();
                        Log.i("ResponseError", error.toString());
                    }
                });
    }
}
