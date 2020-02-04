package com.example.youtubeproject.app;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.youtubeproject.Activity.HomeActivity;
import com.example.youtubeproject.R;

public class FinishDialog extends Dialog {

    public FinishDialog(@NonNull final Context context) {
        super(context);
        setContentView(R.layout.finish_dialog);

        TextView tv = findViewById(R.id.text);

        tv.setText(R.string.notice);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 dismiss();
                ((HomeActivity)context).finish();
            }
        });

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
