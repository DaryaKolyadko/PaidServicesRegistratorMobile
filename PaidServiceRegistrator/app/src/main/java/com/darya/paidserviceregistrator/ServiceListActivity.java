package com.darya.paidserviceregistrator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ListView;

import com.darya.paidserviceregistrator.entities.Service;
import com.darya.paidserviceregistrator.resourcereader.ResourceReader;
import com.darya.paidserviceregistrator.util.ListViewServiceAdapter;
import com.darya.paidserviceregistrator.util.ServiceNameJsonParser;
import com.darya.paidserviceregistrator.util.SoapObjectParser;

import org.ksoap2.serialization.SoapObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class ServiceListActivity extends AppCompatActivity {

    private ListView listViewServises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_list);
        setSupActionBar();
        setUpComponents();


        String ret;
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.config);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
                ServiceNameJsonParser parser = new ServiceNameJsonParser();

                List services = parser.parse(ret);
                ret += "";
                listViewServises.setAdapter(new ListViewServiceAdapter(this, services));
            }
        }
        catch (FileNotFoundException e) {

        } catch (IOException e) {

        }
    }

    private void setUpComponents() {
        listViewServises = (ListView) findViewById(R.id.listViewServices);
    }

    private void setSupActionBar() {
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
//        SoapObject result = getServicesAsyncTask.get();
//        ServiceAsyncTask getServicesAsyncTask = new ServiceAsyncTask();
//        getServicesAsyncTask.execute("admin", "pass",
//                ResourceReader.getString(ResourceReader.getServiceList));
//        List<Service> serviceList = SoapObjectParser.parse(result);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(ServiceListActivity.this, MainActivity.class);
                startActivity(intent);
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
