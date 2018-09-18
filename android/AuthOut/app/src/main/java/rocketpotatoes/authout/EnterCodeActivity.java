package rocketpotatoes.authout;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class EnterCodeActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String AUTHOUT_CODE_URL = "http://httpbin.org/post";
    private StringBuilder codeInputBuilder;
    private EditText codeInput;
    private Button submitButton;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_code);
        submitButton = findViewById(R.id.submitCode);
        submitButton.setEnabled(false);
        codeInputBuilder = new StringBuilder();
        codeInput = findViewById(R.id.editText);
        requestQueue = Volley.newRequestQueue(this);
    }

    @Override
    public void onClick(View v) {
        if (codeInputBuilder.length() == 4) {
            codeInput.setBackgroundColor(Color.RED);
            return;
        } else {
            codeInput.setBackgroundColor(Color.BLACK);
        }

        Button button = findViewById(v.getId());
        codeInputBuilder.append(button.getText());
        codeInput.setText(codeInputBuilder.toString());
        if (codeInputBuilder.length() == 4) submitButton.setEnabled(true);
    }

    public void submitCode(View v) {
        requestQueue.add(createRequest(codeInputBuilder.toString()));
    }

    public void cancel(View v) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public void backspace(View v) {
        if (codeInputBuilder.length() <= 0) return;
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
            json.put("Code", code);
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

                        Intent intent = new Intent(EnterCodeActivity.this, SelectStudentActivity.class);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.i("ResponseError", error.toString());
                    }

                });
    }
}
