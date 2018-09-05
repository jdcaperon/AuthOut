package rocketpotatoes.authout.Helpers;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import rocketpotatoes.authout.EnterCodeActivity;
import rocketpotatoes.authout.R;
import rocketpotatoes.authout.SelectStudentActivity;

public class NotRecognizedDialog extends Dialog {
    private Handler handler;
    private Runnable runnable;
    private int delay;
    private Activity activity;

    public NotRecognizedDialog(Activity a, Handler handler, Runnable runnable, int delay) {
        super(a);
        this.activity = a;
        this.handler = handler;
        this.runnable = runnable;
        this.delay = delay;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_not_recognized);

        Button retryButton = findViewById(R.id.retryButton);
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                handler.postDelayed(runnable, 0);
            }
        });

        Button cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                handler.postDelayed(runnable, delay);
            }
        });

        Button codeButton = findViewById(R.id.codeButton);
        codeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                Intent intent = new Intent(activity, EnterCodeActivity.class);
                activity.startActivity(intent);
            }
        });
    }



}
