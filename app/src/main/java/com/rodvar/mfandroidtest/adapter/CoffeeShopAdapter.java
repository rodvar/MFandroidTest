package com.rodvar.mfandroidtest.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rodvar.mfandroidtest.R;
import com.rodvar.mfandroidtest.model.Venue;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by rodrigo on 18/07/15.
 */
public class CoffeeShopAdapter extends BaseAdapter {

    private final Context context;
    private List<Venue> venues;

    public CoffeeShopAdapter(Context context) {
        this.context = context;
        this.venues = new LinkedList<Venue>();
    }

    public CoffeeShopAdapter(Context context, List<Venue> venues) {
        this(context);
        this.venues = venues;
    }

    @Override
    public int getCount() {
        return this.venues.size();
    }

    @Override
    public Object getItem(int position) {
        return this.venues.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            Venue venue = this.venues.get(position);
            LayoutInflater li = LayoutInflater.from(this.context);
            if (convertView == null) {
                convertView = li.inflate(R.layout.coffee_element, null);
                ((TextView) convertView.findViewById(R.id.shopName)).setText(venue.getName());
                ((TextView) convertView.findViewById(R.id.shopAddress)).setText(venue.getLocation().getAddress());
                ((TextView) convertView.findViewById(R.id.shopDistance)).setText(venue.getLocation().getDistance().toString());

                // TODO add magic functionality :P
            }
        } catch (Exception e) {
            Log.e("ELEM_CREATE", "Failed", e);
        }
        return convertView;
    }

    public void reset(List<Venue> venues) {
        this.venues = venues;
        this.notifyDataSetChanged();
    }
}
