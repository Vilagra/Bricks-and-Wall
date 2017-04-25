package com.example.wallandbricks;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.wallandbricks.entity.Brick;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Vilagra on 20.04.2017.
 */

public class AdapterOfBricks extends RecyclerView.Adapter<AdapterOfBricks.ViewHolder> {

    private Map<Brick, Integer> mapOfBricks;
    private List<Brick> keys;
    private DataIsEmptyListener dataIsEmptyListener;

    interface DataIsEmptyListener{
        void notifyDataIsEmpty(boolean b); //listener watches when data becomes empty
    }


    public void setDataIsEmptyListener(DataIsEmptyListener dataIsEmptyListener) {
        this.dataIsEmptyListener = dataIsEmptyListener;
    }

    public AdapterOfBricks() {
        mapOfBricks= new HashMap<>();
        keys=new ArrayList<>();
    }

    public void setKeys(ArrayList<Brick> keys) {
        this.keys = keys;
    }

    public void setMapOfBricks(Map<Brick, Integer> mapOfBricks) {
        this.mapOfBricks = mapOfBricks;
    }

    public List<Brick> getKeys() {
        return keys;
    }

    public Map<Brick, Integer> getMapOfBricks() {
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
        public void onClick(View v) {               //delete bricks by this position
            int position = getAdapterPosition();
            Brick key = keys.get(position);
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
        Brick key = keys.get(position);
        holder.sizeAndAmountOfBricks.setText("Height:"+key.getHeight()+" Width:"+ key.getWidth()+" Amount: "+mapOfBricks.get(key));
    }

    @Override
    public int getItemCount() {
        return keys.size();
    }

    public void updateData(Brick brick, Integer amount) {  //input new data, if data already exists it is replaced
        keys.remove(brick);
        keys.add(brick);
        mapOfBricks.put(brick,amount);
        notifyDataSetChanged();
    }


}
