package com.yaoc.inclassassignment08_yaoc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Main2Activity extends AppCompatActivity {
    private String TAG = "Main2Activity";
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent intent = getIntent();
        String email = intent.getStringExtra("email").replace('.', 'd');
        myRef = database.getReference("username").child(email);


        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);

                TextView nameDisplay = (TextView) findViewById(R.id.nameDisplay);
                if (value != null) {
                    nameDisplay.setText(value);
                } else {
                    nameDisplay.setText("Not set");
                }

                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    public void update(View view) {
        EditText inputName = (EditText)findViewById(R.id.nameField);
        String username = inputName.getText().toString();

        myRef.setValue(username);
    }

    public void logout(View view) {
        finish();
    }
}
