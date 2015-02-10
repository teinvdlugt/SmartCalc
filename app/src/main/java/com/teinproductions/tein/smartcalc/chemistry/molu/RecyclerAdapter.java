package com.teinproductions.tein.smartcalc.chemistry.molu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.teinproductions.tein.smartcalc.R;

import java.text.DecimalFormat;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    Element[] data;
    LayoutInflater inflater;
    Context context;

    public RecyclerAdapter(Element[] data, Context context) {
        super();
        this.data = data;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.element_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        myViewHolder.setData(context, data[i]);
    }


    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, number;
        ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);
            image = (ImageView) itemView.findViewById(R.id.image);
            number = (TextView) itemView.findViewById(R.id.number_and_mass);
        }

        public void setData(Context context, Element element) {
            this.name.setText(element.getName(context));
            this.number.setText(element.getAtomicNumber().toString());
            this.number.append(" -- " + new DecimalFormat().format(element.getMass()) + " u");
            //this.image.setImageResource(element.getImageId());
        }
    }
}
