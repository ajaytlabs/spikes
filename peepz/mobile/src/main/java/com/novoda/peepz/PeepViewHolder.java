package com.novoda.peepz;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

final class PeepViewHolder extends RecyclerView.ViewHolder {

    public static PeepViewHolder inflateView(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_peep, parent, false);
        return new PeepViewHolder(itemView);
    }

    @BindView(R.id.peep_text_name)
    TextView nameTextView;

    @BindView(R.id.peep_image)
    ImageView imageTextView;

    private PeepViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(Peep peep) {
        if (peep.name().isPresent()) {
            nameTextView.setText(peep.name().get());
            nameTextView.setVisibility(View.VISIBLE);
        } else {
            nameTextView.setVisibility(View.INVISIBLE);
        }

        imageTextView.setImageBitmap(peep.image());
    }

}
