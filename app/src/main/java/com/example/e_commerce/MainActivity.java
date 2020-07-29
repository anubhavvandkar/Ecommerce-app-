package com.example.e_commerce;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.e_commerce.helper.SQLiteHandler;
import com.example.e_commerce.helper.SessionManager;

import java.util.HashMap;

public class MainActivity extends Activity {

    public void logout(View view){

        session.setLogin(false);
        db.deleteUsers();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private SQLiteHandler db;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Intent intent = new Intent(this, LoginActivity.class);
        //startActivity(intent);

        TextView txtName = findViewById(R.id.txtName);
        TextView txtEmail = findViewById(R.id.email);
        Button start = findViewById(R.id.Button);
        txtName.setText("");

        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        if(!session.isLoggedIn()) {
            logoutUser();
        }

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();

        String name = user.get("name");
        String email = user.get("email");

        // Displaying the user details on the screen
        if(name!=null) {
            txtName.setText(name);
            txtEmail.setText(email);
        }
        else {
            txtName.setText("User");
            txtEmail.setText("You haven't logged in yet!");
        }
        // Logout button click event
        start.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startShopping();
            }
        });
    }

    private void startShopping(){

        Intent intent = new Intent(this, ShopActivity.class);
        startActivity(intent);
    }

    /**
     * Logging out the user. Will set isLoggedIn flag to false in shared
     * preferences Clears the user data from sqlite users table
     * */
    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


}
