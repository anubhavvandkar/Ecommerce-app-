package com.example.e_commerce;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ShopActivity extends Activity {

    Context context;
    int count;

    public ShopActivity(){

    }

    public ShopActivity(SQLiteDatabase db) {
        this.db = db;
    }


    public void checkout(View view){

        Intent intent = new Intent(context, checkoutActivity.class);
        startActivity(intent);
    }

    SQLiteDatabase db;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);
        db = this.openOrCreateDatabase("cart", MODE_PRIVATE, null);
        //db.execSQL("delete from cart");
        count = 0;

        context = this;

        listView = findViewById(R.id.listViewShop);
        final ArrayList<String> arr = new ArrayList<>();
        arr.add("Product1");
        arr.add("Product2");
        arr.add("Product3");
        arr.add("Product4");
        arr.add("Product5");
        arr.add("Product6");
        arr.add("Product7");
        arr.add("Product8");
        arr.add("Product9");
        arr.add("Product10");
        arr.add("Product11");
        arr.add("Product12");
        arr.add("Product13");
        arr.add("Product14");
        arr.add("Product15");
        arr.add("Product16");
        arr.add("Product17");
        arr.add("Product18");
        arr.add("Product19");
        arr.add("Product20");
        arr.add("Product21");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arr){

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

        db.execSQL("CREATE TABLE IF NOT EXISTS cart (cart VARCHAR, num INT(2))");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                new AlertDialog.Builder(context).setIcon(android.R.drawable.alert_light_frame)
                        .setTitle("Add to cart")
                        .setMessage("Do you want to add "+arr.get(position)+" to your cart?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                count++;
                                Toast.makeText(context, "Added to cart", Toast.LENGTH_SHORT).show();
                                //inserting to cart sql
                                db.execSQL("INSERT INTO cart (cart, num) VALUES ('"+ arr.get(position)+"', "+position+")");
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
        });
    }
}