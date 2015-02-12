package com.teinproductions.tein.smartcalc.chemistry.molu;

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
        myViewHolder.fillData(context, data, i);
    }


    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, mass, atomicNumber;
        RelativeLayout root;

        public MyViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);
            mass = (TextView) itemView.findViewById(R.id.number_and_mass);
            root = (RelativeLayout) itemView;
            atomicNumber = (TextView) itemView.findViewById(R.id.atomic_number);
        }

        public void fillData(Context context, Element[] data, int i) {
            this.name.setText(data[i].getName(context));
            this.mass.setText(new DecimalFormat().format(data[i].getMass()) + " u");

            String number = data[i].getAtomicNumber().toString();
            if (number.length() == 1) {
                this.atomicNumber.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 22);
            } else {
                this.atomicNumber.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 44 / number.length());
            }

            this.atomicNumber.setText(number);
        }
    }
}
