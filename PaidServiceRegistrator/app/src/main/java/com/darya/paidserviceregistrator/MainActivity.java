package com.darya.paidserviceregistrator;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toolbar;

import com.darya.paidserviceregistrator.resourcereader.ResourceReader;

public class MainActivity extends AppCompatActivity {
    private ImageButton buttonService;
    private ImageButton buttonSettings;
    private String login;
    private String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpComponents();
        setButtonsListeners();
        initCredentials();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    private void initCredentials() {
        login = getIntent().getStringExtra(ResourceReader.getString(ResourceReader.parameterLogin));
        password = getIntent().getStringExtra(ResourceReader.getString(ResourceReader.parameterPassword));
    }

    private void setButtonsListeners() {

        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                putExtras(intent);
                startActivity(intent);
            }
        });

        buttonService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ServiceListActivity.class);
                putExtras(intent);
                startActivity(intent);
            }
        });
    }

    private void putExtras(Intent intent) {
        intent.putExtra(ResourceReader.getString(ResourceReader.parameterLogin),
                login);
        intent.putExtra(ResourceReader.getString(ResourceReader.parameterPassword),
                password);
    }

    private void setUpComponents() {
        buttonService = (ImageButton) findViewById(R.id.imgBtnServices);
        buttonSettings = (ImageButton) findViewById(R.id.imgBtnSettings);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
