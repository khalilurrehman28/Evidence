package com.dupleit.kotlin.primaryschoolassessment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

public class tryActivity extends AppCompatActivity implements NumberPicker.OnValueChangeListener{

    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_try);

        btn = findViewById(R.id.ButtonNP);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NumberPickerDialog newFragment = new NumberPickerDialog();
                newFragment.setValueChangeListener(tryActivity.this);

                newFragment.show(getFragmentManager(), "time picker");
            }
        });


    }

    @Override
    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
        Toast.makeText(this, "selected number " + numberPicker.getValue(), Toast.LENGTH_SHORT).show();
    }
}
