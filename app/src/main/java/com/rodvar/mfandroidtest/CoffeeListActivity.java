package com.rodvar.mfandroidtest;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.rodvar.mfandroidtest.adapter.CoffeeShopAdapter;
import com.rodvar.mfandroidtest.model.Venue;

import java.util.LinkedList;
import java.util.List;


public class CoffeeListActivity extends ActionBarActivity {

    private CoffeeShopAdapter coffeeShopAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee_list);
        ListView listView = (ListView) this.findViewById(R.id.shopList);
        this.coffeeShopAdapter = new CoffeeShopAdapter(this, fakeList());
        listView.setAdapter(this.coffeeShopAdapter);
    }

    private List<Venue> fakeList() {
        List<Venue> list = new LinkedList<Venue>();
        list.add(new Venue("Pepe", "91 Goulburn St", Long.valueOf(23l)));
        list.add(new Venue("Pregfa", "231 Tuvieja en tanga", Long.valueOf(233l)));
        list.add(new Venue("Largartoide", "91 Marriot St", Long.valueOf(354l)));
        list.add(new Venue("Coffeee puto", "343 Marquick St", Long.valueOf(53l)));
        return list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_coffee_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
