package com.example.pampa;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.DetailViewHolder> {

    ArrayList<TodayModel> list;
    private NumberFormat currformat;

    public DetailAdapter(ArrayList<TodayModel> today){
        this.list = today;
        currformat = NumberFormat.getCurrencyInstance(new Locale("in","ID"));
    }

    @NonNull
    @Override
    public DetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater lf = LayoutInflater.from(parent.getContext());
        View vw = lf.inflate(R.layout.detail_item,parent,false);
        return new DetailAdapter.DetailViewHolder(vw);
    }

    @SuppressLint({"ResourceAsColor"})
    @Override
    public void onBindViewHolder(@NonNull DetailViewHolder holder, int position) {
        TodayModel l = list.get(position);
        holder.tvt.setText(l.getTitle());

        if (l.isStatus()){
            holder.tvb.setText("+"+currformat.format(l.getAmount()));
        }else{
            holder.tvb.setText("-"+currformat.format(l.getAmount()));
            holder.tvt.setTextColor(Color.parseColor("#FD7B9A"));
            holder.tvb.setTextColor(Color.parseColor("#FD7B9A"));
        }


    }

    @Override
    public int getItemCount() {
        return (list.size() != 0) ? list.size() : 0;
    }

    public class DetailViewHolder extends RecyclerView.ViewHolder {
        TextView tvt,tvb;
        public DetailViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tvb = itemView.findViewById(R.id.tvdbalance);
            this.tvt = itemView.findViewById(R.id.tvdtitle);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
    }
}
