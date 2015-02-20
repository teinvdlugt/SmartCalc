package com.teinproductions.tein.smartcalc.chemistry.molu;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.teinproductions.tein.smartcalc.R;

import java.text.DecimalFormat;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private Particle[] data;
    private LayoutInflater inflater;
    private Context context;
    private OnRecyclerItemClickListener clickListener;

    public RecyclerAdapter(Particle[] data, Activity activity) {
        super();
        this.data = data;
        this.context = activity;
        this.inflater = LayoutInflater.from(context);
        try {
            this.clickListener = (OnRecyclerItemClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() +
                    " must implement RecyclerAdapter.OnRecyclerItemClickListener");
        }
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
    public void onBindViewHolder(MyViewHolder holder, final int i) {
        String name = data[i].getName();
        if (name == null) name = context.getString(R.string.unknown);

        String mass;
        Double massD = data[i].getMass();
        if (massD == null) {
            mass = "";
        } else {
            mass = new DecimalFormat().format(massD) + " u";
        }

        String atomicNumber;
        try {
            atomicNumber = data[i].getAtomicNumber().toString();
        } catch (UnsupportedOperationException e) {
            atomicNumber = null;
        }

        holder.name.setText(name);
        holder.mass.setText(mass);

        if (atomicNumber != null) {
            holder.atomicNumber.setVisibility(View.VISIBLE);
            if (atomicNumber.length() == 1) {
                holder.atomicNumber.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 22);
            } else {
                holder.atomicNumber.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 44 / atomicNumber.length());
            }
            holder.atomicNumber.setText(atomicNumber);
        } else {
            holder.atomicNumber.setVisibility(View.GONE);
        }

        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(RecyclerAdapter.this, i);
            }
        });
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, mass, atomicNumber;
        LinearLayout root;

        public MyViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);
            mass = (TextView) itemView.findViewById(R.id.number_and_mass);
            root = (LinearLayout) itemView;
            atomicNumber = (TextView) itemView.findViewById(R.id.atomic_number);
        }
    }

    public static interface OnRecyclerItemClickListener {
        public void onItemClick(RecyclerAdapter recyclerAdapter, int i);
    }
}
