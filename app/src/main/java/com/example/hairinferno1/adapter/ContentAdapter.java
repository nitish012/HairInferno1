package com.example.hairinferno1.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.hairinferno1.R;
import com.example.hairinferno1.Modal.RESULTHOME;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentViewHolder> {
    private Context context;
    private List<RESULTHOME> commentModalList;

    public ContentAdapter(Context context, List<RESULTHOME> commentModalList) {
        this.context = context;
        this.commentModalList = commentModalList;
    }

    @NonNull
    @Override
    public ContentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.activity_content, viewGroup, false);
        return new ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ContentViewHolder commentViewHolder, int i) {

        final RESULTHOME resulthome= commentModalList.get(i);
        Glide.with(context).load(resulthome.getUserImage()).apply(RequestOptions.circleCropTransform()).into(commentViewHolder.profile_image);
        commentViewHolder.textname.setText(resulthome.getName());
        commentViewHolder.name.setText(resulthome.getName());
        commentViewHolder.number_likes.setText(String.valueOf(resulthome.getLikeCount()));
        Glide.with(context).load(resulthome.getUserImage()).into(commentViewHolder.fullimage);

        commentViewHolder.expandedmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //creating a popup menu
                PopupMenu popup = new PopupMenu(context, commentViewHolder.expandedmenu);
                //inflating menu from xml resource
                popup.inflate(R.menu.main_menu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.report:
                                //handle menu1 click
                                Toast.makeText(context, "Working progress", Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.block:
                                //handle menu2 click
                                Toast.makeText(context, "Working progress", Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.mute:
                                //handle menu3 click
                                Toast.makeText(context, "Working progress", Toast.LENGTH_SHORT).show();
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                //displaying the popup
                popup.show();


            }
        });

        commentViewHolder.likelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(commentViewHolder.like==false)
                {
                    commentViewHolder.like_flame.setImageResource(R.drawable.ic_red_flame);
                    commentViewHolder.like=true;
                    commentViewHolder.number_likes.setText(String.valueOf(resulthome.getLikeCount()+1));
                }

                else if(commentViewHolder.like)
                {
                    commentViewHolder.like_flame.setImageResource(R.drawable.ic_flame);
                    commentViewHolder.like=false;
                    int like=Integer.parseInt(commentViewHolder.number_likes.getText().toString());
                    commentViewHolder.number_likes.setText(String.valueOf(like-1));
                }


            }
        });



    }

    @Override
    public int getItemCount() {
        return commentModalList.size();
    }

    class ContentViewHolder extends RecyclerView.ViewHolder {

        LinearLayout likelayout;
        CircleImageView profile_image;
        TextView textname,name,number_likes,change_likes;
        ImageView fullimage,expandedmenu,like_flame;
        boolean like=false;
        public ContentViewHolder(@NonNull View itemView) {
            super(itemView);

            profile_image=itemView.findViewById(R.id.profile_image);
            textname=itemView.findViewById(R.id.textname);
            name=itemView.findViewById(R.id.name);
            fullimage=itemView.findViewById(R.id.image_full);
            number_likes=itemView.findViewById(R.id.number_likes);
            change_likes=itemView.findViewById(R.id.change_likes_number);
            expandedmenu=itemView.findViewById(R.id.expanded_menu);
            like_flame=itemView.findViewById(R.id.like_flame);
            likelayout=itemView.findViewById(R.id.like_layout);

        }
    }
}