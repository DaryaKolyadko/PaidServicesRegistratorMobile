package com.darya.paidserviceregistrator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
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
        setSupActionBar();
        setNumberPickerStringLength(MIN_STRING_LENGTH, MAX_STRING_LENGTH);

    }

    private void setSupActionBar() {
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    private void setComponents() {
        numberPickerStringLength = (NumberPicker) findViewById(R.id.numberPickerStringLength);
    }

    private void setNumberPickerStringLength(int min, int max) {
        numberPickerStringLength.setMinValue(min);
        numberPickerStringLength.setMaxValue(max);
        numberPickerStringLength.setValue(min);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(intent);
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
