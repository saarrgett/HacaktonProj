package com.example.rachamim.hacaktonproj;


import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rachamim.hacaktonproj.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.category;

public class FindACarActivity extends AppCompatActivity {

    private EditText licence;
    private Button send;
    private Button blockedBtn;
    private Button blockingBtn;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = database.getReference().getRoot();
    private boolean isBlocked = true;
    private boolean isBlocking = false;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_car);

        final String email = getIntent().getStringExtra("LoggedInEmail").toString();
        final String uuid = getIntent().getStringExtra("LoggedInUUID").toString();

        licence = (EditText) findViewById(R.id.licence);
        send = (Button) findViewById(R.id.sendBtn);
        blockedBtn = (Button) findViewById(R.id.blockedbtn);
        blockingBtn = (Button) findViewById(R.id.blockingBtn);

        blockedBtn.setBackground(getDrawable(R.drawable.btn2));
        blockingBtn.setBackground(getDrawable(R.drawable.btn2));

        blockingBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                blockingBtn.setBackground(getDrawable(R.drawable.btn1));
                blockedBtn.setBackground(getDrawable(R.drawable.btn2));
                isBlocking = true;
                isBlocked = false;
                send.setText("Send");
            }
        });

        blockedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blockingBtn.setBackground(getDrawable(R.drawable.btn2));
                blockedBtn.setBackground(getDrawable(R.drawable.btn1));
                isBlocking = false;
                isBlocked = true;
                send.setText("Call");
            }
        });


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String licencePlate = licence.getText().toString();


                if (isBlocking) {
                    dbRef.child("users").child(uuid).child("blocking").setValue(true);
                    dbRef.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            boolean flag = false;
                            for (DataSnapshot userData : dataSnapshot.getChildren()) {
                                User user = userData.getValue(User.class);
                                user.setUUID(userData.getKey());
                                if (user.getLicensePlate().equals(licencePlate)) {
                                    flag = true;
                                    dbRef.child("users").child(user.getUUID()).child("blocked").setValue(true);
                                    dbRef.child("users").child(user.getUUID()).child("otherUserId").setValue(uuid);
                                    dbRef.child("users").child(uuid).child("otherUserId").setValue(user.getUUID());

                                    Intent intent = new Intent(FindACarActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                            if (!flag)
                                Toast.makeText(getApplicationContext(), "Unable to find licence plate", Toast.LENGTH_LONG).show();
                            flag = false;
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    android.support.v4.app.NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(getApplicationContext())
                                    .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                                    .setContentTitle("G Parking")
                                    .setContentText("You are blocked by " + email.split("@")[0] + " :(");
                    int mNotificationId = 001;
                    // Gets an instance of the NotificationManager service
                    NotificationManager mNotifyMgr =
                            (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    // Builds the notification and issues it.
                    mNotifyMgr.notify(mNotificationId, mBuilder.build());
                } else {
                    dbRef.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            boolean flag = false;
                            for (DataSnapshot userData : dataSnapshot.getChildren()) {
                                User user = userData.getValue(User.class);
                                if (user.getLicensePlate().equals(licencePlate)) {
                                    Intent intent = new Intent(Intent.ACTION_DIAL);
                                    intent.setData(Uri.parse("tel:" + user.getPhone()));
                                    startActivity(intent);
                                }
                            }
                            if (!flag)
                                Toast.makeText(getApplicationContext(), "Unable to find licence plate", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }
            }
        });


//        email = (EditText) findViewById(R.id.et_login_email);
//        password = (EditText) findViewById(R.id.et_login_password);
//        login = (Button) findViewById(R.id.btn_login_login);
//        signup = (TextView) findViewById(R.id.tv_login_signup);
//        mAuth = FirebaseAuth.getInstance();


    }
}
