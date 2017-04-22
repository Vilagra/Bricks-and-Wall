package com.example.wallandbricks;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Vilagra on 20.04.2017.
 */

public class AdapterOfBricks extends RecyclerView.Adapter<AdapterOfBricks.ViewHolder> {

    Map<Integer, Integer> mapOfBricks;
    List<Integer> keys;
    DataIsEmptyListener dataIsEmptyListener;

    interface DataIsEmptyListener{
        void notifyDataIsEmpty(boolean b);
    }


    public void setDataIsEmptyListener(DataIsEmptyListener dataIsEmptyListener) {
        this.dataIsEmptyListener = dataIsEmptyListener;
    }

    public AdapterOfBricks() {
        mapOfBricks= new HashMap<>();
        keys=new ArrayList<>();
    }

    public void setKeys(ArrayList<Integer> keys) {
        this.keys = keys;
    }

    public void setMapOfBricks(Map<Integer, Integer> mapOfBricks) {
        this.mapOfBricks = mapOfBricks;
    }

    public List<Integer> getKeys() {
        return keys;
    }

    public Map<Integer, Integer> getMapOfBricks() {
        return mapOfBricks;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Button buttonDelete;
        TextView sizeAndAmountOfBricks;

        public ViewHolder(CardView cardView) {
            super(cardView);
            sizeAndAmountOfBricks = (TextView) cardView.findViewById(R.id.amountAndSize);
            buttonDelete = (Button) cardView.findViewById(R.id.delete);
            buttonDelete.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Integer key = keys.get(position);
            keys.remove(position);
            mapOfBricks.remove(key);
            AdapterOfBricks.this.notifyDataSetChanged();
            if(mapOfBricks.isEmpty()){
                dataIsEmptyListener.notifyDataIsEmpty(true);
            }
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.bricks_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(cardView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Integer key = keys.get(position);
        holder.sizeAndAmountOfBricks.setText("Size:"+key+"   Amount: "+mapOfBricks.get(key));
    }

    @Override
    public int getItemCount() {
        return keys.size();
    }

    public void updateData(Integer size, Integer amount) {
        keys.remove(size);
        keys.add(size);
        mapOfBricks.put(size,amount);
        notifyDataSetChanged();
    }


}
