package com.example.project_mobileprog_aa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<ZeldaGames> values;
    private OnGameListener onGameListener;



    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // each data item is just a string in this case
        TextView txtHeader;
        TextView txtFooter;
        View layout;
        ImageView mImg = (ImageView) itemView.findViewById(R.id.icon);
        OnGameListener onGameListener;

        ViewHolder(View v, OnGameListener onGameListener) {
            super(v);
            layout = v;
            txtHeader = (TextView) v.findViewById(R.id.firstLine);
            txtFooter = (TextView) v.findViewById(R.id.secondLine);
            this.onGameListener = onGameListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onGameListener.onGameClick(getAdapterPosition());
        }
    }

    public void add(int position, ZeldaGames item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ListAdapter(List<ZeldaGames> myDataset, OnGameListener onGameListener) {
        values = myDataset;
        this.onGameListener = onGameListener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.row_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v, onGameListener);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final ZeldaGames currentZeldaGame = values.get(position);
        holder.txtHeader.setText(currentZeldaGame.getName());
        /*holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //remove(position);

                //NavHostFragment.findNavController().navigate();

                //Intent intent = new Intent(new MainActivity(), itemDescription.class);
                //mContext.startActivity(intent);
            }
        });*/


        holder.txtFooter.setText("Release year: " + currentZeldaGame.getRelease_year_in_Europe());
        Picasso.get().load(currentZeldaGame.getImg_Cover_URL()).resize(100,100).into(holder.mImg);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }

    public interface OnGameListener{
        void onGameClick(int position);
    }

}
