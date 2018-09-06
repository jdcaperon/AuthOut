package rocketpotatoes.authout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class EnterCodeActivity extends AppCompatActivity implements View.OnClickListener{

    StringBuilder codeInputBuilder;
    EditText codeInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_code);
        codeInputBuilder = new StringBuilder();
        codeInput = findViewById(R.id.editText);
        /*Button zero  = findViewById(R.id.button0);
        zero.setOnClickListener(this);
        Button one   = findViewById(R.id.button1);
        one.setOnClickListener(this);
        Button two   = findViewById(R.id.button2);
        two.setOnClickListener(this);
        Button three = findViewById(R.id.button3);
        three.setOnClickListener(this);
        Button four  = findViewById(R.id.button4);
        four.setOnClickListener(this);
        Button five  = findViewById(R.id.button5);
        five.setOnClickListener(this);
        Button six   = findViewById(R.id.button6);
        six.setOnClickListener(this);
        Button seven = findViewById(R.id.button7);
        seven.setOnClickListener(this);
        Button eight = findViewById(R.id.button8);
        eight.setOnClickListener(this);
        Button nine  = findViewById(R.id.button9);
        nine.setOnClickListener(this);*/

    }

    @Override
    public void onClick(View v) {
        Button button = findViewById(v.getId());
        codeInputBuilder.append(button.getText());
        codeInput.setText(codeInputBuilder.toString());

    }

    public void submitCode(View v) {

    }

    public void cancel(View v) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
