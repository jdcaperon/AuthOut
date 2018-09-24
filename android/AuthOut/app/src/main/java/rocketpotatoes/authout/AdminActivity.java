package rocketpotatoes.authout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
    }

    /**
     *
     * @param v - current View
     */
    public void signUpClicked(View v) {
        Intent intent = new Intent(this, RegisterChildrenActivity.class);
        startActivity(intent);
    }


}
