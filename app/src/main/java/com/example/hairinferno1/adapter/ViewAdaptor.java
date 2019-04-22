package com.example.hairinferno1.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.hairinferno1.R;
import com.example.hairinferno1.Modal.RESULTHOME;

import java.util.List;

public class ViewAdaptor extends RecyclerView.Adapter<ViewAdaptor.Viewviewholder> {


    List<RESULTHOME> datalist;
    Context context;

    public ViewAdaptor(List datalist, Context context)
    {
        this.datalist=datalist;
        this.context=context;
    }
    @NonNull
    @Override
    public ViewAdaptor.Viewviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater =LayoutInflater.from(viewGroup.getContext());
        View view=inflater.inflate(R.layout.list,viewGroup,false);
        return new Viewviewholder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewAdaptor.Viewviewholder viewviewholder, int i) {

        RESULTHOME resulthome= datalist.get(i);
        // Glide.with(context).load(resulthome.getUserImage()).apply(RequestOptions.circleCropTransform()).into(Viewviewholder.img);
        Viewviewholder.text.setText("Barber");
        Glide.with(context).load(R.drawable.barber).apply(RequestOptions.circleCropTransform()).into(Viewviewholder.img);

    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }
    public static class Viewviewholder extends RecyclerView.ViewHolder
    {

        static ImageView img;
        static TextView text;
        public Viewviewholder(@NonNull View itemView) {
            super(itemView);

            img=itemView.findViewById(R.id.img);
            text=itemView.findViewById(R.id.texttitle);
        }

    }

}
