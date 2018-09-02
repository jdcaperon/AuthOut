package rocketpotatoes.authout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class SelectStudentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_student);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        RecyclerView mChildSelectorView = (RecyclerView) findViewById(R.id.child_selector);
        assert (mChildSelectorView != null);

        mChildSelectorView.setLayoutManager(layoutManager);

        List<Child> dummyChildren = new ArrayList<>();

        dummyChildren.add(new Child("Joe", "Bloggs", "Signed-In"));
        dummyChildren.add(new Child("Joe2", "Bloggs", "Signed-In"));

        ChildSelectorAdapter mChildSelectorAdapter = new ChildSelectorAdapter(dummyChildren);
        mChildSelectorView.setAdapter(mChildSelectorAdapter);
    }

    public void onPressConfirm(View view) {
        Intent intent = new Intent(this, ConfirmationActivity.class);
        startActivity(intent);
    }
}
