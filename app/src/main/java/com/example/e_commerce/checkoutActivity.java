package com.example.e_commerce;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class checkoutActivity extends ShopActivity {

    public checkoutActivity(){

    }

    public checkoutActivity(SQLiteDatabase db) {
        super(db);
    }

    public void goToMain(){
        db.execSQL("delete from cart");
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    public void continueShopping(View view){
        finish();
    }

    public void emptyCart(View view){

        new AlertDialog.Builder(this).setIcon(android.R.drawable.alert_light_frame)
                .setTitle("Confirm empty")
                .setMessage("Are you sure you want to empty your cart and continue shopping?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "Emptied cart successfully!", Toast.LENGTH_SHORT).show();
                        //context.deleteDatabase("cart");
                        //final SQLiteDatabase db = context.openOrCreateDatabase("cart", MODE_PRIVATE, null);
                        db.execSQL("delete from cart");
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }

    public void checkOut(View view){

        new AlertDialog.Builder(this).setIcon(android.R.drawable.alert_light_frame)
                .setTitle("Confirm checkout")
                .setMessage("Are you sure you want to checkout with these items?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "Checkout successful!", Toast.LENGTH_SHORT).show();
                        goToMain();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        Cursor c = db.rawQuery("SELECT * FROM cart", null);
        ArrayList<String> arrayList = new ArrayList<>();
        int index = c.getColumnIndex("cart");
        c.moveToFirst();
        //int i = c.getColumnCount();
        //Log.i("Something", String.valueOf(i));
        while(!c.isAfterLast()){
            Log.i("Something", String.valueOf(index));
            String cartItem = c.getString(index);
            arrayList.add(cartItem);
            c.moveToNext();
        }

        ListView listView = findViewById(R.id.listView2);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList){

            @Override
            public View getView(int position, View convertView, ViewGroup parent){

                View view = super.getView(position,convertView,parent);
                ViewGroup.LayoutParams layoutparams = view.getLayoutParams();
                layoutparams.height = 200;
                view.setLayoutParams(layoutparams);
                return view;
            }
        };
        listView.setAdapter(arrayAdapter);
    }
}
