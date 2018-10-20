package com.example.excalibur.gameoflife;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by excalibur on 1/20/2018.
 */

public class CellsAdapter extends ArrayAdapter<Cell>{

    private Cell[][] cells;
    private LayoutInflater inflater;
    private int resourceId;
    private int dimension;

    CellsAdapter(@NonNull Context context, int resource, Cell[][] cells, int dimension) {
        super(context, resource);
        this.dimension = dimension;
        this.cells = cells;
        this.inflater = LayoutInflater.from(context);
        this.resourceId = resource;
    }

    @Override
    public int getCount() {
        return dimension*dimension;
    }

    @Nullable
    @Override
    public Cell getItem(int position) {
        return cells[getMatrixRow(position)][getMatrixColumn(position)];
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = inflater.inflate(resourceId, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Cell currentCell = cells[getMatrixRow(position)][getMatrixColumn(position)];
        if(currentCell.isAlive()){
            viewHolder.cell.setBackgroundColor(Color.BLACK);
        }
        else {
            viewHolder.cell.setBackgroundColor(Color.WHITE);
        }

        return convertView;

    }

//    private int getLinearIndex(int rowIndex, int columnIndex){
//        return (rowIndex * dimension + columnIndex);
//    }

    private int getMatrixColumn(int position){
        return position % dimension;
    }

    private int getMatrixRow(int position){
        return position / dimension;
    }

    private class ViewHolder{
        final TextView cell;

        ViewHolder(View view) {
            this.cell = view.findViewById(R.id.cell);
        }
    }
}
