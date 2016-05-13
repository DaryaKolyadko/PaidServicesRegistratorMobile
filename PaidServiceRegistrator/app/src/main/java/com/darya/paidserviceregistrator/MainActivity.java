package com.darya.paidserviceregistrator;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    private ImageButton buttonService;
    private ImageButton buttonSettings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpComponents();
        setButtonsListeners();
    }

    private void setButtonsListeners() {
        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        buttonService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ServiceListActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setUpComponents() {
        buttonService = (ImageButton) findViewById(R.id.imgBtnServices);
        buttonSettings = (ImageButton) findViewById(R.id.imgBtnSettings);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.menu.main_activity_menu:
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
