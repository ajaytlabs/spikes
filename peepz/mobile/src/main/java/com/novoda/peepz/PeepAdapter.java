package com.novoda.peepz;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseUser;

import java.util.List;

class PeepAdapter extends RecyclerView.Adapter {

    private final FirebaseUser signedInUser;

    private List<Peep> peepz;

    public PeepAdapter(FirebaseUser signedInUser, List<Peep> peepz) {
        this.signedInUser = signedInUser;
        this.peepz = peepz;
        super.setHasStableIds(true);
    }

    public void update(List<Peep> peepz) {
        this.peepz = peepz;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == R.id.view_type_selfie) {
            return SelfieViewHolder.inflateView(parent);
        } else {
            return PeepViewHolder.inflateView(parent);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Peep peep = peepz.get(position);
        if (getItemViewType(position) == R.id.view_type_selfie) {
            ((SelfieViewHolder) holder).bind(peep);
        } else {
            ((PeepViewHolder) holder).bind(peep);
        }
    }

    @Override
    public int getItemCount() {
        return peepz.size();
    }

    @Override
    public long getItemId(int position) {
        return peepz.get(position).id().hashCode();
    }

    @Override
    public int getItemViewType(int position) {
        Peep peep = peepz.get(position);
        if (signedInUser.getUid().equals(peep.id())) {
            return R.id.view_type_selfie;
        } else {
            return R.id.view_type_peep;
        }
    }

}
