package com.teinproductions.tein.smartcalc.chemistry.molu;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.teinproductions.tein.smartcalc.R;

import java.text.DecimalFormat;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private Element[] data;
    private LayoutInflater inflater;
    private Context context;
    private OnRecyclerItemClickListener clickListener;

    public RecyclerAdapter(Element[] data, Activity activity) {
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

    public Element getElementAtPosition(int i) {
        return data[i];
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, final int i) {
        myViewHolder.name.setText(data[i].getName(context));
        myViewHolder.mass.setText(new DecimalFormat().format(data[i].getMass()) + " u");

        String number = data[i].getAtomicNumber().toString();
        if (number.length() == 1) {
            myViewHolder.atomicNumber.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 22);
        } else {
            myViewHolder.atomicNumber.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 44 / number.length());
        }

        myViewHolder.atomicNumber.setText(number);

        myViewHolder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(RecyclerAdapter.this, i);
            }
        });
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, mass, atomicNumber;
        RelativeLayout root;

        public MyViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);
            mass = (TextView) itemView.findViewById(R.id.number_and_mass);
            root = (RelativeLayout) itemView;
            atomicNumber = (TextView) itemView.findViewById(R.id.atomic_number);
        }
    }

    public static interface OnRecyclerItemClickListener {
        public void onItemClick(RecyclerAdapter recyclerAdapter, int i);
    }
}
