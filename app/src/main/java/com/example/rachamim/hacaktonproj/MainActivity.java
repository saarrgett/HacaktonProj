package com.example.rachamim.hacaktonproj;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.rachamim.hacaktonproj.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    static Context context;
    private static final String TAG = "MainActivity";
    ViewPager viewPager;
    String email, uuid;
    Boolean blocked, blocking;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference dbRootRef = database.getReference().getRoot();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView currentMessageElem = (TextView) findViewById(R.id.message);
        final TextView currentLicensePlateElem = (TextView) findViewById(R.id.licenceNumber);
        final Button currentButtonElem = (Button) findViewById(R.id.sendBtn);
        final Boolean[] isBlocked = {false};
        final Boolean[] isBlocking = {false};
        Intent intent = getIntent();
        email = intent.getStringExtra("LoggedInEmail");
        uuid = intent.getStringExtra("LoggedInUUID");
        final DatabaseReference blockedRef = dbRootRef.child("users").child(uuid).child("blocked");
        final DatabaseReference blockingRef = dbRootRef.child("users").child(uuid).child("blocking");

        ValueEventListener blockedListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                isBlocked[0] = (Boolean) dataSnapshot.getValue();
                if (isBlocked[0] == true && isBlocking[0] == false) {
                    currentMessageElem.setText(R.string.you_blocked_message);
                    currentButtonElem.setText(R.string.call);

                    dbRootRef.child("users").child(uuid).child("otherUserId").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            final String uuidOfOtherUser = dataSnapshot.getValue().toString();
                            dbRootRef.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot userData : dataSnapshot.getChildren()) {
                                        if (userData.getKey().equals(uuidOfOtherUser)) {
                                            final User user = userData.getValue(User.class);
                                            String licensePlateStr = user.getLicensePlate();
                                            currentLicensePlateElem.setText(licensePlateStr);
                                            currentButtonElem.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    Intent intent = new Intent(Intent.ACTION_DIAL);
                                                    intent.setData(Uri.parse("tel:" + user.getPhone()));
                                                    startActivity(intent);
                                                }
                                            });
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                if (isBlocked[0] == false && isBlocking[0] == false) {
                    currentMessageElem.setText(R.string.no_updates);
                    currentLicensePlateElem.setText("");
                    currentButtonElem.setText(R.string.find_a_car);
                    currentButtonElem.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(MainActivity.this, FindACarActivity.class);

                            intent.putExtra("LoggedInEmail", email);
                            intent.putExtra("LoggedInUUID", uuid);

                            startActivity(intent);
                        }
                    });
                }
                Log.d(TAG, MainActivity.class.getName() + " - " + blockedRef.toString() + " dataChanged");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        ValueEventListener blockingListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                isBlocking[0] = (Boolean) dataSnapshot.getValue();
                if (isBlocked[0] == false && isBlocking[0] == true) {
                    currentMessageElem.setText(R.string.you_blocking_message);
                    currentButtonElem.setText(R.string.un_block);
                    dbRootRef.child("users").child(uuid).child("otherUserId").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            final String uuidOfOtherUser = dataSnapshot.getValue().toString();
                            dbRootRef.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (final DataSnapshot userData : dataSnapshot.getChildren()) {
                                        if (userData.getKey().equals(uuidOfOtherUser)) {
                                            final User user = userData.getValue(User.class);
                                            String licensePlateStr = user.getLicensePlate();
                                            currentLicensePlateElem.setText(licensePlateStr);
                                            currentButtonElem.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    dbRootRef.child("users").child(uuidOfOtherUser).child("blocked").setValue(false);
                                                    dbRootRef.child("users").child(uuid).child("blocking").setValue(false);
                                                }
                                            });

                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                if (isBlocked[0] == false && isBlocking[0] == false) {
                    currentMessageElem.setText(R.string.no_updates);
                    currentLicensePlateElem.setText("");
                    currentButtonElem.setText(R.string.find_a_car);
                    currentButtonElem.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(MainActivity.this, FindACarActivity.class);

                            intent.putExtra("LoggedInEmail", email);
                            intent.putExtra("LoggedInUUID", uuid);

                            startActivity(intent);
                        }
                    });
                }
                Log.d(TAG, MainActivity.class.getName() + " - " + blockedRef.toString() + " dataChanged");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        blockedRef.addValueEventListener(blockedListener);
        blockingRef.addValueEventListener(blockingListener);
        context = getApplicationContext();
    }
}
