package com.example.weather;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditCityActivity extends AppCompatActivity {

    public static final String NEW_CITY = "weather_new_city";

    EditText cityEditField;
    Button cancelBtn;
    Button okBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_city);

        cityEditField = findViewById(R.id.cityEditField);
        cancelBtn = findViewById(R.id.cancelBtn);
        okBtn = findViewById(R.id.okBtn);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                onBackPressed();
            }
        });

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(NEW_CITY, cityEditField.getText().toString());
                setResult(RESULT_OK, intent);
                onBackPressed();
            }
        });

        cityEditField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER){
                    okBtn.callOnClick();
                }

                return false;
            }
        });
    }
}
