package com.example.controlvoce;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class myAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] name;
    private final String[] address;

    public myAdapter(Activity context, String[] name,String[] address) {
        super(context, R.layout.list, name);
        this.context=context;
        this.name=name;
        this.address=address;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.list, null,true);

        TextView titleText = (TextView) rowView.findViewById(R.id.name);
        TextView addressText = (TextView) rowView.findViewById(R.id.address);

        titleText.setText(name[position]);
        addressText.setText(address[position]);

        return rowView;

    };
}