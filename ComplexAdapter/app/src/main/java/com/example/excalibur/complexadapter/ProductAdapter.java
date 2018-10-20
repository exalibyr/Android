package com.example.excalibur.complexadapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by excalibur on 1/17/2018.
 */

public class ProductAdapter extends ArrayAdapter<String>{
    private List<String> products = new ArrayList<>();
    private LayoutInflater inflater;
    private int layoutId;

    ProductAdapter(Context context, int recourse, List<String> products){
        super(context, recourse, products);
        this.layoutId = recourse;
        this.products = products;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder viewHolder;
        if(convertView==null){
            convertView = inflater.inflate(layoutId, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final String product = products.get(position);
        viewHolder.nameTextView.setText(product);

        viewHolder.switcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    viewHolder.nameTextView.setAlpha(0);
                }
                else {
                    viewHolder.nameTextView.setAlpha(1);
                }
            }
        });

        return convertView;
    }

    private class ViewHolder{
        final ToggleButton switcher;
        final TextView nameTextView;

        ViewHolder(View view) {
            this.switcher = view.findViewById(R.id.button);
            this.nameTextView = view.findViewById(R.id.name);
        }
    }
}
