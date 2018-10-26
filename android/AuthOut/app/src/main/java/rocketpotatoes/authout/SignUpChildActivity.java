/*
 * MIT License

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

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class SignUpChildActivity extends AppCompatActivity {

    private Calendar myCalendar;
    private DatePickerDialog.OnDateSetListener date;
    public EditText firstName;
    public EditText surname;
    public EditText dateOfBirth;
    private ArrayList<ArrayList<String>> children;
    private ChildSignupListAdapter childSignupListAdapter;

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
        RecyclerView childSignupSelectorView = findViewById(R.id.child_selector);
        assert (childSignupSelectorView != null);

        childSignupSelectorView.setLayoutManager(layoutManager);
        childSignupListAdapter = new ChildSignupListAdapter(children, this);
        childSignupSelectorView.setAdapter(childSignupListAdapter);

    }

    /** User clicked the back button
     *
     * @param v - the current view
     */
    public void backClicked(View v) {
        finish();
    }


    /** User clicked the next button
     *
     * @param v - the current view
     */
    public void nextClicked(View v) {
        if (children.size() > 0) {
            Intent intent = new Intent(this, SignUpReviewActivity.class);
            intent.putExtra("PARENT_DETAILS", getIntent().getSerializableExtra("PARENT_DETAILS"));
            intent.putExtra("PHOTO", getIntent().getSerializableExtra("PHOTO"));
            intent.putExtra("CHILDREN", children);
            startActivity(intent);
        } else {
            Toast.makeText(this, R.string.must_have_one_child, Toast.LENGTH_LONG).show();
        }
    }

    /** 'Register Child' button clicked
     *
     * @param v - the current view
     */
    public void addChild(View v) {
        if (validateInputs()) {
            children.add(new ArrayList<>(Arrays.asList(firstName.getText().toString(),
                    surname.getText().toString(), dateOfBirth.getText().toString())));

            childSignupListAdapter.notifyDataSetChanged();
            clearTexts();
        } else {
            Toast.makeText(this, R.string.fix_input, Toast.LENGTH_LONG).show();
        }
    }

    /** Clears the {@link EditText} widgets for child information*/
    private void clearTexts() {
        firstName.getText().clear();
        surname.getText().clear();
        dateOfBirth.getText().clear();
    }

    /** Evaluates the {@link EditText} widgets on screen and checks if they are empty,
     *
     * @return boolean value determining if the edit texts are valid
     */
    private boolean validateInputs() {
        boolean isValid = true;
        List<EditText> inputs = new ArrayList<>(Arrays.asList(firstName, surname, dateOfBirth));
        for (EditText input : inputs) {
            if (input.length() == 0) {
                input.setBackground(getDrawable(R.drawable.signup_input_error));
                isValid = false;
            } else {
                input.setBackground(getDrawable(R.drawable.signup_input_background));
            }
        }

        return isValid;
    }

    /** Function to set up the calendar picker for date of births*/
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
                DatePickerDialog dialog = new DatePickerDialog(SignUpChildActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                dialog.show();
            }
        });
    }

    /** Updates the date of birth text based on the date selected in the calendar picker */
    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);
        dateOfBirth.setText(sdf.format(myCalendar.getTime()));
        dateOfBirth.setBackground(getDrawable(R.drawable.signup_input_background));
    }
}
