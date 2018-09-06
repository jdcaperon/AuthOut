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

    private StringBuilder codeInputBuilder;
    private EditText codeInput;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_code);
        submitButton = findViewById(R.id.submitCode);
        submitButton.setEnabled(false);
        codeInputBuilder = new StringBuilder();
        codeInput = findViewById(R.id.editText);
    }

    @Override
    public void onClick(View v) {
        if (codeInputBuilder.length() == 4) return;
        Button button = findViewById(v.getId());
        codeInputBuilder.append(button.getText());
        codeInput.setText(codeInputBuilder.toString());
        if (codeInputBuilder.length() == 4) submitButton.setEnabled(true);
    }

    public void submitCode(View v) {

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
}
