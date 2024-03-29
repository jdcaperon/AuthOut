/* MIT License

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
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Set;

import rocketpotatoes.authout.Helpers.Child;
import rocketpotatoes.authout.Helpers.ChildSelectorAdapter;
import rocketpotatoes.authout.Helpers.DynamicButtonOption;
import rocketpotatoes.authout.Helpers.Parent;
import rocketpotatoes.authout.Helpers.Util;

public class SelectStudentActivity extends AppCompatActivity {
    private static final String AUTHOUT_SIGNINOUT_URL = "https://deco3801.wisebaldone.com/api/kiosk/signin";
    private Parent currentUser;
    private Button dynamicButton;
    private TextView dynamicText;
    private ChildSelectorAdapter mChildSelectorAdapter;
    private RequestQueue requestQueue;
    private List<Child> displayedChildren;
    private View progressOverlay;
    private String photo;

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

        if (getIntent().getExtras() == null) {
            throw new IllegalStateException("No trusted children boolean packaged with intent");
        }

        currentUser = getIntent().getExtras().getParcelable("PARENT");
        photo = getIntent().getExtras().getString("PHOTO");

        if (currentUser == null) {
            throw new IllegalStateException("No parent object packaged with intent");
        }

        setUpLayout(currentUser);

        mChildSelectorAdapter = new ChildSelectorAdapter(displayedChildren, this);
        mChildSelectorView.setAdapter(mChildSelectorAdapter);
        requestQueue = Volley.newRequestQueue(this);
    }

    /** Sets up UI based on parental information
     *
     * @param parent - current signed in user
     */
    private void setUpLayout(Parent parent) throws IllegalStateException {
        TextView welcomeText = findViewById(R.id.welcomeText);
        welcomeText.setText(getString(R.string.welcome_message, parent.getFirstName()));

        progressOverlay = findViewById(R.id.progress_overlay);
        dynamicText = findViewById(R.id.dynamicText);
        dynamicButton = findViewById(R.id.dynamicButton);

        if (getIntent().getExtras() == null) {
            throw new IllegalStateException("No trusted children boolean packaged with intent");
        }

        boolean shouldDisplayTrustedChildren =
                getIntent().getExtras().getBoolean("DISPLAY_TRUSTED_CHILDREN");

        if (shouldDisplayTrustedChildren) {
            displayedChildren = currentUser.getTrustedChildren();
            findViewById(R.id.welcomeText).setVisibility(View.GONE);
            findViewById(R.id.thisIsNotMe).setVisibility(View.GONE);
        } else {
            findViewById(R.id.back).setVisibility(View.GONE);
            displayedChildren = currentUser.getChildren();
        }

        Button signInOthers = findViewById(R.id.signInOthers);
        if (shouldDisplayTrustedChildren || parent.getTrustedChildren().size() == 0) {
            signInOthers.setVisibility(View.GONE);
        }

        changeButtonSettings(getOptionByChildren(displayedChildren));
    }

    /** Function called when user clicks 'Sign in Others' button
     *
     * @param view - the current view
     */
    public void signInOthers(View view) {
        Intent intent = new Intent(SelectStudentActivity.this, SelectStudentActivity.class);
        intent.putExtra("PARENT", currentUser);
        intent.putExtra("DISPLAY_TRUSTED_CHILDREN", true);
        startActivity(intent);
    }

    /** Function called when user clicks the dynamic button
     *
     * @param view - the current view
     */
    public void onMadeSelection(View view) {
        Set<Child> selectedChildren = mChildSelectorAdapter.getSelectedItems();

        requestQueue.add(createRequest(selectedChildren));
        Util.animateView(progressOverlay, View.VISIBLE, 0.8f, 200);
    }

    /** Returns informational string based on children selected
     *
     * @param children - list of children selected
     * @return a string that represents the action that will take place
     */
    public String createDynamicString(List<Child> children) {
        if (children.size() == 0) {
            return getString(R.string.no_children_selected);
        } else if (children.size() == 1) {
            return children.get(0).getFirstName();
        } else if (children.size() == 2) {
            return getString(R.string.two_children_string, children.get(0).getFirstName(),
                    children.get(1).getFirstName());
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < children.size() - 1; i++) {
            sb.append(children.get(i).getFirstName());
            sb.append(", ");
        }
        sb.append("and ");
        sb.append(children.get(children.size() - 1).getFirstName());
        return sb.toString();
    }

    /** Single function to alter dynamic text as well as dynamic button
     *
     * @param option - a {@link DynamicButtonOption} of what to adapt the dynamic button to
     * @param children - list of children selected
     */
    public void changeTextAndButton(DynamicButtonOption option, List<Child> children) {
        changeText(option, children);
        changeButtonSettings(option);
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

    public void finish(View v) {
        finish();
    }

    /**
     *
     * @param option - {@link DynamicButtonOption} to change text for
     * @param children - list of currently selected children
     */
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

    /** Alters button look and onClick listener based on {@link DynamicButtonOption}
     *
     * @param option - a {@link DynamicButtonOption}
     * */
    private void changeButtonSettings(DynamicButtonOption option) {
        switch(option) {
            case SIGN_IN:
                dynamicButton.setText(R.string.sign_in);
                dynamicButton.setOnClickListener(madeSelectionListener);
                dynamicButton.setBackground(ContextCompat.getDrawable(this, R.drawable.authout_button));
                break;
            case SIGN_OUT:
                dynamicButton.setText(R.string.sign_out);
                dynamicButton.setOnClickListener(madeSelectionListener);
                dynamicButton.setBackground(ContextCompat.getDrawable(this, R.drawable.authout_button));
                break;
            case NOT_COMPATIBLE:
                dynamicButton.setOnClickListener(null);
                dynamicButton.setText(R.string.not_compatible);
                dynamicButton.setBackground(ContextCompat.getDrawable(this, R.drawable.authout_button_disabled));
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
        if (children.size() == 0) { return DynamicButtonOption.NOT_COMPATIBLE;}
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
    private JsonObjectRequest createRequest(final Set<Child> children) {
        JSONObject json = new JSONObject();

        //Adding contents to request
        JSONArray childrenJSON = new JSONArray();

        try {
            json.put("parent_id", currentUser.getId());
            for (Child child : children) {
                JSONObject temp = new JSONObject();
                temp.put("id", child.getId());
                boolean status = !child.getStatus().equals("Signed-In");
                temp.put("status", status);
                childrenJSON.put(temp);
            }
            json.put("children", childrenJSON);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new JsonObjectRequest
                (Request.Method.POST, AUTHOUT_SIGNINOUT_URL, json , new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Util.animateView(progressOverlay, View.GONE, 0.8f, 200);

                        Intent intent = new Intent(SelectStudentActivity.this, ConfirmFinishActivity.class);
                        intent.putExtra("PHOTO", photo);
                        startActivity(intent);
                        finish();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("ResponseError", error.toString());
                        Util.animateView(progressOverlay, View.GONE, 0.8f, 200);
                        Toast.makeText(SelectStudentActivity.this, "Oops, something went wrong. Try again.", Toast.LENGTH_SHORT).show();
                    }

                });
    }
}
