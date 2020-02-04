package com.example.youtubeproject.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.CheckBox;

import com.example.youtubeproject.R;

public class SettingsActivity extends AppCompatActivity {

    private final String PREFERENCE ="com.example.youtubeproject";
    private final String KEY = "key";

    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = findViewById(R.id.toolbar);

        checkBox = findViewById(R.id.checkbox);
        checkBox.setChecked(getPreferenceBoolean(KEY));

        toolbar.setTitle(R.string.settings);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private boolean getPreferenceBoolean(String key){
        SharedPreferences preferences = getSharedPreferences(PREFERENCE,MODE_PRIVATE);
        return preferences.getBoolean(key,false);
    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences preferences = getSharedPreferences(PREFERENCE,MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(KEY,checkBox.isChecked());
        editor.commit();




    }
}
