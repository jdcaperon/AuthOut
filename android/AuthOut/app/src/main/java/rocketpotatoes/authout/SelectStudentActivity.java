package rocketpotatoes.authout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SelectStudentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_student);
    }

    public void onPressConfirm(View view) {
        Intent intent = new Intent(this, ConfirmationActivity.class);
        startActivity(intent);
    }
}
