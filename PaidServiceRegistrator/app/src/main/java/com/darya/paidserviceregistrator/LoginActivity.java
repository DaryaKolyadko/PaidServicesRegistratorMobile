package com.darya.paidserviceregistrator;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;

import com.darya.paidserviceregistrator.entities.Service;
import com.darya.paidserviceregistrator.resourcereader.ResourceReader;
import com.darya.paidserviceregistrator.util.SoapObjectParser;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
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
                    getServicesAsyncTask.execute("admin", "pass",
                            ResourceReader.getString(ResourceReader.getServiceList));
                    SoapObject result = getServicesAsyncTask.get();
                    List<Service> serviceList = SoapObjectParser.parse(result);

                   // ServiceAsyncTask getParamNameAsyncTask = new ServiceAsyncTask();
                   // getParamNameAsyncTask.execute(ResourceReader.getString(ResourceReader.getParamValue));

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

    public class ServiceAsyncTask extends AsyncTask<String, Void, SoapObject> {
        ProgressDialog mDialog;
        private int paramCount = 3;

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
        protected SoapObject doInBackground(String... params) {
            if (params.length == paramCount) {
                String commandName = params[2];
                SoapObject request = new SoapObject(ResourceReader.getString(ResourceReader.wcfServiceNamespace),
                        commandName);
                request.addProperty("login", params[0]);
                request.addProperty("password", params[1]);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                        SoapEnvelope.VER11);
                envelope.dotNet = true;

                envelope.setOutputSoapObject(request);

                try {
                    HttpTransportSE androidHttpTransport = new HttpTransportSE(ResourceReader.
                            getString(ResourceReader.wcfServiceUrl));
                    androidHttpTransport.call(ResourceReader.getString(ResourceReader.commandPath) +
                            commandName, envelope);

                    return (SoapObject) envelope.getResponse();
                } catch (XmlPullParserException | IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }


        @Override
        protected void onPostExecute(SoapObject result) {
            super.onPostExecute(result);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            mDialog.dismiss();
        }
    }
}
