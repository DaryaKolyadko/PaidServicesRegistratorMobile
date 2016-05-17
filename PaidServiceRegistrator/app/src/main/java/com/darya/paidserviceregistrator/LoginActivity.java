package com.darya.paidserviceregistrator;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;

import com.darya.paidserviceregistrator.resourcereader.ResourceReader;
import com.darya.paidserviceregistrator.wcfcontroller.WcfController;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutionException;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    private Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ResourceReader.init(getApplicationContext());
        setContentView(R.layout.activity_login);

        setComponents();
        buttonLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ServiceAsyncTask getServicesAsyncTask = new ServiceAsyncTask();
                    getServicesAsyncTask.execute(ResourceReader.getString(ResourceReader.getServiceList));
                    String result = getServicesAsyncTask.get();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    LoginActivity.this.finish();
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
    }

    public class ServiceAsyncTask extends AsyncTask<String, Void, String> {
        ProgressDialog mDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            mDialog = new ProgressDialog(LoginActivity.this);
            mDialog.setMessage("Authentication...");
            mDialog.setCancelable(false);
            mDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            if (params.length == 1) {
                SoapObject request = new SoapObject(ResourceReader.getString(ResourceReader.wcfServiceNamespace),
                        params[0]);
                request.addProperty("login", "admin");
                request.addProperty("password", "pass");

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                        SoapEnvelope.VER11);
                envelope.dotNet = true;

                envelope.setOutputSoapObject(request);

                try {
                    HttpTransportSE androidHttpTransport = new HttpTransportSE(ResourceReader.
                            getString(ResourceReader.wcfServiceUrl));
                    androidHttpTransport.call(ResourceReader.getString(ResourceReader.commandGetServiceList),
                            envelope);

                    SoapObject result = (SoapObject) envelope.getResponse();
                    return result.toString();
                } catch (XmlPullParserException | IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            mDialog.dismiss();
        }
    }
}