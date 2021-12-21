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
import com.example.travelapp.model.ModelKuliner;

import java.util.List;

public class KulinerAdapter extends RecyclerView.Adapter<KulinerAdapter.ViewHolder> {

    final List<ModelKuliner> items;
    final KulinerAdapter.onSelectData onSelectData;
    final Context mContext;

    public interface onSelectData {
        void onSelected(ModelKuliner modelNews);
    }

    public KulinerAdapter(Context context, List<ModelKuliner> items, KulinerAdapter.onSelectData xSelectData) {
        this.mContext = context;
        this.items = items;
        this.onSelectData = xSelectData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_kuliner, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ModelKuliner data = items.get(position);

        //Get Image
        Glide.with(mContext)
                .load(data.getGambarKuliner())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imgKuliner);

        holder.tvNamaKuliner.setText(data.getTxtNamaKuliner());
        holder.rlListKuliner.setOnClickListener(new View.OnClickListener() {
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

        public TextView tvNamaKuliner;
        public RelativeLayout rlListKuliner;
        public ImageView imgKuliner;

        public ViewHolder(View itemView) {
            super(itemView);
            rlListKuliner = itemView.findViewById(R.id.rlListKuliner);
            tvNamaKuliner = itemView.findViewById(R.id.tvNamaKuliner);
            imgKuliner = itemView.findViewById(R.id.imgKuliner);
        }
    }
}
