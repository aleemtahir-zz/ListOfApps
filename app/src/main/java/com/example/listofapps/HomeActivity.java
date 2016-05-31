package com.example.listofapps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    Button btnCurrentProfile;
    Button btnManualProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnCurrentProfile=(Button)findViewById(R.id.btnCurrentWork);
        btnCurrentProfile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomeActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        btnManualProfile=(Button)findViewById(R.id.btnManuallySelect);
        btnManualProfile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomeActivity.this,ProfileActivity.class);
                startActivity(intent);
            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_enable:
                Toast.makeText(this, "You have selected Enable Menu", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_boot:
                Toast.makeText(this, "You have selected Boot Menu", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_help:
                Toast.makeText(this, "You have selected Help Menu", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_exit:
                Toast.makeText(this, "You have selected Exit Menu", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void exit() {
        finish();
        System.exit(0);
    }


}
