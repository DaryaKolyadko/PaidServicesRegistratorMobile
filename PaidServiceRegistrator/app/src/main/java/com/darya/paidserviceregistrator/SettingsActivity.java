package com.darya.paidserviceregistrator;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.darya.paidserviceregistrator.resourcereader.ResourceReader;
import com.darya.paidserviceregistrator.util.ServiceAsyncTask;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class SettingsActivity extends AppCompatActivity {
    private NumberPicker numberPickerStringLength;
    private int MIN_STRING_LENGTH = 15;
    private int MAX_STRING_LENGTH = 30;
    private String login;
    private String password;
    private Button changeStringLengthButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setComponents();
        setSupActionBar();
        setNumberPickerStringLength(MIN_STRING_LENGTH, MAX_STRING_LENGTH);
        initCredentials();
        setRandomStringValue();
        setButtonsListeners();
    }

    private void setButtonsListeners() {
        changeStringLengthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServiceAsyncTask setValueAsyncTask = new ServiceAsyncTask();
                setValueAsyncTask.execute(login, password,
                        ResourceReader.getString(ResourceReader.setParamValue),
                        ResourceReader.getString(ResourceReader.randomLineLength),
                        String.valueOf(numberPickerStringLength.getValue()));
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Changes saved", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    private void initCredentials() {
        login = getIntent().getStringExtra(ResourceReader.getString(ResourceReader.parameterLogin));
        password = getIntent().getStringExtra(ResourceReader.getString(ResourceReader.parameterPassword));
    }

    private void setRandomStringValue() {
        try {
            ServiceAsyncTask getValueAsyncTask = new ServiceAsyncTask();

            getValueAsyncTask.execute(login, password, ResourceReader.getString(ResourceReader.getParamValue),
                    ResourceReader.getString(ResourceReader.randomLineLength));
            int randomLineLength = Integer.parseInt(getValueAsyncTask.get().toString());
            numberPickerStringLength.setValue(randomLineLength);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
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
        changeStringLengthButton = (Button) findViewById(R.id.buttonChangeStringLength);
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
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}