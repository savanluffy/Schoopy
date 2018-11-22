package com.example.melanie.schoopyapp.Misc;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.melanie.schoopyapp.Data.Database;
import com.example.melanie.schoopyapp.R;

public class Login extends AppCompatActivity {
    private Database db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        try{
            db.setURL("http://192.168.196.169:28389/");
            ActionBar actionBar = getSupportActionBar();
            actionBar.hide();
            //initComponents();
            //setListener();
        }catch (Exception ex){
            Toast.makeText(this,"Error: " +ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}
