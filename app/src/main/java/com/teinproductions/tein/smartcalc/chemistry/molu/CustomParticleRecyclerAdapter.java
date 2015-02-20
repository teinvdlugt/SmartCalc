package com.teinproductions.tein.smartcalc.chemistry.molu;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.teinproductions.tein.smartcalc.R;

public class CustomParticleRecyclerAdapter extends RecyclerView.Adapter<CustomParticleRecyclerAdapter.ViewHolder> {

    CustomParticle[] customParticles;
    LayoutInflater inflater;
    Activity activity;
    OnClickListener listener;

    public CustomParticleRecyclerAdapter(CustomParticle[] customParticles, Activity activity) {
        super();
        this.customParticles = customParticles;
        this.activity = activity;
        this.inflater = LayoutInflater.from(activity);
        this.activity = activity;
        try {
            this.listener = (OnClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() +
                    " must implement CustomParticleRecyclerAdapter.OnClickListener");
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item_custom_particles, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (customParticles[position].getName() != null) {
            holder.name.setText(customParticles[position].getName());
        }

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickEdit(position);
            }
        });

        holder.goTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickGoTo(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return customParticles.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageButton edit, goTo;

        public ViewHolder(View itemView) {
            super(itemView);

            this.name = (TextView) itemView.findViewById(R.id.particle_name_text_view);
            this.edit = (ImageButton) itemView.findViewById(R.id.img_edit);
            this.goTo = (ImageButton) itemView.findViewById(R.id.img_go);
        }
    }

    public interface OnClickListener {
        public void onClickEdit(int i);

        public void onClickGoTo(int i);
    }
}
