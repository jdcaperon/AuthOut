package rocketpotatoes.authout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SelectStudentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView mChildSelectorView = (RecyclerView) findViewById(R.id.child_selector);

        mChildSelectorView.setHasFixedSize(true);
        mChildSelectorView.setLayoutManager(layoutManager);



        ChildSelectorAdapter mChildSelectorAdapter = new ChildSelectorAdapter();
        mChildSelectorView.setAdapter(mChildSelectorAdapter);

        setContentView(R.layout.activity_select_student);
    }

    public void onPressConfirm(View view) {
        Intent intent = new Intent(this, ConfirmationActivity.class);
        startActivity(intent);
    }
}
