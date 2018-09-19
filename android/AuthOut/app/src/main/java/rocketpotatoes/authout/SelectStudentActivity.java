package rocketpotatoes.authout;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
import java.util.Set;

import rocketpotatoes.authout.Helpers.Child;
import rocketpotatoes.authout.Helpers.ChildSelectorAdapter;
import rocketpotatoes.authout.Helpers.DynamicButtonOption;
import rocketpotatoes.authout.Helpers.Parent;

public class SelectStudentActivity extends AppCompatActivity {
    private static final String AUTHOUT_SIGNINOUT_URL = "http://httpbin.org/post";
    private Parent currentUser;
    private Button dynamicButton;
    private TextView dynamicText;
    private GradientDrawable dynamicButtonBackground;
    private ChildSelectorAdapter mChildSelectorAdapter;
    private RequestQueue requestQueue;

    private View.OnClickListener madeSelectionListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onMadeSelection(v);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_student);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView mChildSelectorView = findViewById(R.id.child_selector);
        assert (mChildSelectorView != null);

        mChildSelectorView.setLayoutManager(layoutManager);

        currentUser = getIntent().getExtras().getParcelable("PARENT");

        setUpLayout(currentUser);

        mChildSelectorAdapter = new ChildSelectorAdapter(currentUser.getChildren(), this);
        mChildSelectorView.setAdapter(mChildSelectorAdapter);
        requestQueue = Volley.newRequestQueue(this);
    }

    private void setUpLayout(Parent dummyParent) {
        String welcomeMessage = "Hey there " + dummyParent.getFirstName();
        TextView welcomeText = findViewById(R.id.welcomeText);
        welcomeText.setText(welcomeMessage);

        dynamicText = findViewById(R.id.dynamicText);
        dynamicButton = findViewById(R.id.dynamicButton);
        dynamicButtonBackground = (GradientDrawable) dynamicButton.getBackground();

        Button signInOthers = findViewById(R.id.signInOthers);
        if (dummyParent.getTrustedChildren().size() == 0) {
            signInOthers.setVisibility(View.GONE);
        }

        changeButtonSettings(getOptionByChildren(dummyParent.getChildren()));
    }

    public void onMadeSelection(View view) {
        dynamicButton.setEnabled(false);
        Set<Child> selectedChildren = mChildSelectorAdapter.getSelectedItems();
        //TODO send proper request to the server
        requestQueue.add(createRequest(selectedChildren.toString()));
    }

    public String createDynamicString(List<Child> children) {
        if (children.size() == 0) return getString(R.string.no_children_selected);
        if (children.size() == 1) return children.get(0).getFirstName();
        if (children.size() == 2) return children.get(0).getFirstName() + " and " +
                                         children.get(1).getFirstName();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < children.size() - 1; i++) {
            sb.append(children.get(i).getFirstName());
            sb.append(", ");
        }
        sb.append("and ");
        sb.append(children.get(children.size() - 1).getFirstName());
        return sb.toString();
    }

    public void changeTextAndButton(DynamicButtonOption option, List<Child> children) {
        changeText(option, children);
        changeButtonSettings(option);
    }

    public void cancel(View v) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    private void changeText(DynamicButtonOption option, List<Child> children) {
        StringBuilder text = new StringBuilder(createDynamicString(children));
        if (!text.toString().equals(getString(R.string.no_children_selected))) {
            switch (option) {
                case SIGN_IN:
                    text.append(" will be signed in.");
                    break;
                case SIGN_OUT:
                    text.append(" will be signed out.");
                    break;
                case NOT_COMPATIBLE:
                    text.append(" have some conflicting status");
                    break;
            }
        }
        dynamicText.setText(text);
    }

    private void changeButtonSettings(DynamicButtonOption option) {
        switch(option) {
            case SIGN_IN:
                dynamicButton.setText(R.string.sign_in);
                dynamicButton.setOnClickListener(madeSelectionListener);
                dynamicButtonBackground.setColor(getResources().getColor(R.color.colorAccent));
                break;
            case SIGN_OUT:
                dynamicButton.setText(R.string.sign_out);
                dynamicButton.setOnClickListener(madeSelectionListener);
                dynamicButtonBackground.setColor(getResources().getColor(R.color.colorAccent));
                break;
            case NOT_COMPATIBLE:
                dynamicButton.setOnClickListener(null);
                dynamicButton.setText(R.string.not_compatible);
                dynamicButtonBackground.setColor(getResources().getColor(R.color.errorButtonColor));
                break;
        }
    }

    /** Checks if a single option can be fit to a list of children.
     *
     * @param children - a list of child objects to check
     * @return a {@link DynamicButtonOption} of which button form to take
     */
    public DynamicButtonOption getOptionByChildren(List<Child> children) {
        String signedInText = "Signed-In";
        if (children.size() == 0) return DynamicButtonOption.NOT_COMPATIBLE;
        if (children.size() == 1) {
            if (children.get(0).getStatus().equals(signedInText)) {
                return DynamicButtonOption.SIGN_OUT;
            }
            return DynamicButtonOption.SIGN_IN;
        } else {
            for (int i = 0; i < children.size() - 1; i++) {
                if (!children.get(i).getStatus().equals(children.get(i + 1).getStatus())) {
                    return DynamicButtonOption.NOT_COMPATIBLE;
                }
            }
        }
        return children.get(0).getStatus().equals(signedInText) ?
                DynamicButtonOption.SIGN_OUT : DynamicButtonOption.SIGN_IN;
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
                (Request.Method.POST, AUTHOUT_SIGNINOUT_URL, json , new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //TODO ensure there's no error, otherwise just move to final activity.
                        Log.i("Response", response.toString().substring(0, 100));
                        Intent intent = new Intent(SelectStudentActivity.this, ConfirmationActivity.class);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: If this errors we retry the request.
                        Log.i("ResponseError", error.toString());
                    }

                });
    }
}
