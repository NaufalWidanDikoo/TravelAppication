package com.example.travelapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.travelapp.R;
import com.example.travelapp.model.ModelWisata;

import java.util.List;

public class WisataAdapter extends RecyclerView.Adapter<WisataAdapter.ViewHolder> {

    final List<ModelWisata> items;
    final WisataAdapter.onSelectData onSelectData;
    final Context mContext;

    public interface onSelectData {
        void onSelected(ModelWisata modelNews);
    }

    public WisataAdapter(Context context, List<ModelWisata> items, WisataAdapter.onSelectData xSelectData) {
        this.mContext = context;
        this.items = items;
        this.onSelectData = xSelectData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_wisata, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ModelWisata data = items.get(position);

        //Get Image
        Glide.with(mContext)
                .load(data.getGambarWisata())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imgWisata);

        holder.tvNamaWisata.setText(data.getTxtNamaWisata());
        holder.rlListWisata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectData.onSelected(data);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    //Class Holder
    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvNamaWisata;
        public RelativeLayout rlListWisata;
        public ImageView imgWisata;

        public ViewHolder(View itemView) {
            super(itemView);
            rlListWisata = itemView.findViewById(R.id.rlListWisata);
            tvNamaWisata = itemView.findViewById(R.id.tvNamaWisata);
            imgWisata = itemView.findViewById(R.id.imgWisata);
        }
    }
}
