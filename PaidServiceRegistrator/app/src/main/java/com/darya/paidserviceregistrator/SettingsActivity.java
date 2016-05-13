package com.darya.paidserviceregistrator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.NumberPicker;

public class SettingsActivity extends AppCompatActivity {
    private NumberPicker numberPickerStringLength;
    private int MIN_STRING_LENGTH = 15;
    private int MAX_STRING_LENGTH = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setComponents();
        setNumberPickerStringLength(MIN_STRING_LENGTH, MAX_STRING_LENGTH);
    }

    private void setComponents() {
        numberPickerStringLength = (NumberPicker) findViewById(R.id.numberPickerStringLength);
    }

    private void setNumberPickerStringLength(int min, int max) {
        numberPickerStringLength.setMinValue(min);
        numberPickerStringLength.setMaxValue(max);
        numberPickerStringLength.setValue(min);
    }
}
