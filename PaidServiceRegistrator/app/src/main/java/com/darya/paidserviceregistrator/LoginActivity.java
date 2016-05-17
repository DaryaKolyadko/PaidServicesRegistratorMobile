package com.darya.paidserviceregistrator;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.darya.paidserviceregistrator.entities.Service;
import com.darya.paidserviceregistrator.resourcereader.ResourceReader;
import com.darya.paidserviceregistrator.util.ServiceAsyncTask;
import com.darya.paidserviceregistrator.util.SoapObjectParser;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    private Button buttonLogin;
    private AutoCompleteTextView loginTextView;
    private EditText passwordEditText;

    private String login;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ResourceReader.init(getApplicationContext());
        setContentView(R.layout.activity_login);
        setComponents();
        setButtonsListeners();
    }

    private void setButtonsListeners() {
        buttonLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String login = loginTextView.getText().toString();
                    String password = passwordEditText.getText().toString();
                    ServiceAsyncTask getServicesAsyncTask = new ServiceAsyncTask();
                    getServicesAsyncTask.execute(login, password, ResourceReader.
                            getString(ResourceReader.checkCredentials));
                    boolean isSuccess = Boolean.parseBoolean(getServicesAsyncTask.get().toString());

                    if(isSuccess) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra(ResourceReader.getString(ResourceReader.parameterLogin),
                               login);
                        intent.putExtra(ResourceReader.getString(ResourceReader.parameterPassword),
                               password);

                        startActivity(intent);
                        LoginActivity.this.finish();
                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Invalid credentials", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setComponents() {
        buttonLogin = (Button) findViewById(R.id.login_button);
        loginTextView = (AutoCompleteTextView) findViewById(R.id.login);
        passwordEditText = (EditText) findViewById(R.id.password);
    }
}
