package com.example.rachamim.hacaktonproj;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class FindCarActivity extends AppCompatActivity {

    private EditText licence;
    private ImageButton send;
    private Spinner spinner;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = database.getReference().getRoot();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_car);


        licence = (EditText) findViewById(R.id.licence);
        send = (ImageButton) findViewById(R.id.sendBtn);
        spinner = (Spinner) findViewById(R.id.spinner);

        List<String > list = new ArrayList<>();
        list.add("Blocked");
        list.add("Blocking");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);


//        email = (EditText) findViewById(R.id.et_login_email);
//        password = (EditText) findViewById(R.id.et_login_password);
//        login = (Button) findViewById(R.id.btn_login_login);
//        signup = (TextView) findViewById(R.id.tv_login_signup);
//        mAuth = FirebaseAuth.getInstance();


    }
}
