package com.example.rachamim.hacaktonproj;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rachamim.hacaktonproj.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends ActionBarActivity implements ActionBar.TabListener {
    static Context context;
    private static final String TAG = "MainActivity";
    ViewPager viewPager;
    String email, uuid;
    Boolean blocked, blocking;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = database.getReference().getRoot();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView currentMessageElem = (TextView) findViewById(R.id.message);
        final TextView currentLicensePlateElem = (TextView) findViewById(R.id.licensePlate);
        final Button currentButtonElem = (Button) findViewById(R.id.sendBtn);
        final Boolean[] isBlocked = {false};
        final Boolean[] isBlocking = {false};
        Intent intent = getIntent();
        email = intent.getStringExtra("LoggedInEmail");
        uuid = intent.getStringExtra("LoggedInUUID");
        final DatabaseReference blockedRef = dbRef.child("users").child(uuid).child("blocked");
        final DatabaseReference blockingRef = dbRef.child("users").child(uuid).child("blocking");
        ValueEventListener blockedListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                isBlocked[0] = (Boolean) dataSnapshot.getValue();
                if(isBlocked[0] == true)
                {
                    currentMessageElem.setText(R.string.you_blocked_message);
                    currentButtonElem.setText(R.string.call);
                }
                if(isBlocked[0] == false && isBlocking[0]== false)
                {
                    currentMessageElem.setText(R.string.no_updates);
                    currentButtonElem.setText(R.string.find_a_car);
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
                if(isBlocked[0] == false && isBlocking[0] == true)
                {
                    currentMessageElem.setText(R.string.you_blocking_message);
                    currentButtonElem.setText(R.string.un_block);
                }
                if(isBlocked[0] == false && isBlocking[0]== false)
                {
                    currentMessageElem.setText(R.string.no_updates);
                    currentButtonElem.setText(R.string.find_a_car);
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

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }
}
