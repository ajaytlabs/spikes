package com.novoda.peepz;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

final class SelfieViewHolder extends RecyclerView.ViewHolder {

    public static SelfieViewHolder inflateView(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_selfie, parent, false);
        return new SelfieViewHolder(itemView);
    }

    private SelfieViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(Peep peep) {
        ((SelfieView) itemView).bind(peep);
    }

}
