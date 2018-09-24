package rocketpotatoes.authout;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class SignUpChildActivity extends AppCompatActivity {

    private Calendar myCalendar;
    private DatePickerDialog.OnDateSetListener date;
    public EditText firstName;
    public EditText surname;
    public EditText dateOfBirth;
    private ListView listview;
    private ArrayList<ArrayList<String>> children;
    private ChildSignupListAdapter childSignupListAdapter;
    private RecyclerView childSignupSelectorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_children);

        firstName = findViewById(R.id.firstName);
        surname = findViewById(R.id.surname);
        dateOfBirth = findViewById(R.id.dob);
        children = new ArrayList<>();
        setUpCalendarPicker();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        childSignupSelectorView = findViewById(R.id.child_selector);
        assert (childSignupSelectorView != null);

        childSignupSelectorView.setLayoutManager(layoutManager);
        childSignupListAdapter = new ChildSignupListAdapter(children, this);
        childSignupSelectorView.setAdapter(childSignupListAdapter);


    }

    public void backClicked(View v) {
        finish();
    }

    public void nextClicked(View v) {
        Intent intent = new Intent(this, SignUpReviewActivity.class);
        intent.putExtra("PARENT_DETAILS", getIntent().getSerializableExtra("PARENT_DETAILS"));
        intent.putExtra("PHOTO", getIntent().getSerializableExtra("PHOTO"));
        intent.putExtra("CHILDREN", children);
        startActivity(intent);

    }

    public void addChild(View v) {
        if (validateInputs()) {
            children.add(new ArrayList<String>(Arrays.asList(firstName.getText().toString(),
                    surname.getText().toString(), dateOfBirth.getText().toString())));

            childSignupListAdapter.notifyDataSetChanged();
            clearTexts();
        }
    }

    private void clearTexts() {
        firstName.setText("");
        surname.setText("");
        dateOfBirth.setText("");
    }

    public void removeChild() {

    }

    private boolean validateInputs() {
        boolean isValid = true;
        if (firstName.length() == 0) {
            firstName.setBackground(getDrawable(R.drawable.signup_input_error));
            isValid = false;
        } else {
            firstName.setBackground(getDrawable(R.drawable.signup_input_background));
        }

        if (surname.length() == 0) {
            surname.setBackground(getDrawable(R.drawable.signup_input_error));
            isValid = false;
        } else {
            surname.setBackground(getDrawable(R.drawable.signup_input_background));
        }

        if (dateOfBirth.length() == 0) {
            dateOfBirth.setBackground(getDrawable(R.drawable.signup_input_error));
            isValid = false;
        } else {
            dateOfBirth.setBackground(getDrawable(R.drawable.signup_input_background));
        }

        return isValid;
    }

    private void setUpCalendarPicker() {

        myCalendar = Calendar.getInstance();
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        dateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(SignUpChildActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);
        dateOfBirth.setText(sdf.format(myCalendar.getTime()));
        dateOfBirth.setBackground(getDrawable(R.drawable.signup_input_background));
    }
}
