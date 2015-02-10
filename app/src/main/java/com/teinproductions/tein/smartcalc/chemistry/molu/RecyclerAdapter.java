package com.teinproductions.tein.smartcalc.chemistry.molu;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.teinproductions.tein.smartcalc.R;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>{

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {

    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, number;
        ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);
            image = (ImageView) itemView.findViewById(R.id.image);
            number = (TextView) itemView.findViewById(R.id.atomic_number);
        }
    }
}
