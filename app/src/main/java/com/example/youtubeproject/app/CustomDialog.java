package com.example.youtubeproject.app;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.youtubeproject.R;

public class CustomDialog extends Dialog {

    public CustomDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.custom_dialog);
        setTitle(R.string.youtube_explanation);

        TextView tv = findViewById(R.id.text);

        tv.setText(R.string.youtube_explanation);

        ImageView iv = findViewById(R.id.image);
        iv.setImageResource(R.drawable.ic_15);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
