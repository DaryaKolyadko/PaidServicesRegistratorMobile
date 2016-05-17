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
import com.darya.paidserviceregistrator.util.ServiceAsyncTask;
import com.darya.paidserviceregistrator.util.ServiceNameJsonParser;
import com.darya.paidserviceregistrator.util.SoapObjectParser;

import org.ksoap2.serialization.SoapObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ServiceListActivity extends AppCompatActivity {
    private String login;
    private String password;
    private ListView listViewServises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_list);
        setSupActionBar();
        setUpComponents();
        initCredentials();
        setListAdapter();
    }

    private void setListAdapter() {
        ServiceAsyncTask serviceAsyncTask = new ServiceAsyncTask();
        serviceAsyncTask.execute(login, password, ResourceReader.getString(ResourceReader.getServiceList));
        List<Service> services = null;
        try {
            services = SoapObjectParser.parse((SoapObject) serviceAsyncTask.get());
            listViewServises.setAdapter(new ListViewServiceAdapter(this, services));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void initCredentials() {
        login = getIntent().getStringExtra(ResourceReader.getString(ResourceReader.parameterLogin));
        password = getIntent().getStringExtra(ResourceReader.getString(ResourceReader.parameterPassword));
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
