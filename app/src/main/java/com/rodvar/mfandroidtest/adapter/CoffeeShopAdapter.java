package com.rodvar.mfandroidtest.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.rodvar.mfandroidtest.MFMapActivity;
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
                ((TextView) convertView.findViewById(R.id.shopDistance)).setText(venue.getLocation().getDistance().toString() + "m away");
                ((Button) convertView.findViewById(R.id.call)).setOnClickListener(this.callHandler(venue));
                ((Button) convertView.findViewById(R.id.map)).setOnClickListener(this.mapHandler(venue));
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

    public View.OnClickListener callHandler(final Venue venue) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + venue.getContact().getPhone()));
                context.startActivity(intent);
            }
        };
    }

    private View.OnClickListener mapHandler(final Venue venue) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context
                        , MFMapActivity.class);
                Bundle b = new Bundle();
                b.putString(MFMapActivity.LAT_PARAM,venue.getLocation().getLat());
                b.putString(MFMapActivity.LON_PARAM,venue.getLocation().getLng());
                intent.putExtras(b);
                context.startActivity(intent);
            }
        };
    }

}
