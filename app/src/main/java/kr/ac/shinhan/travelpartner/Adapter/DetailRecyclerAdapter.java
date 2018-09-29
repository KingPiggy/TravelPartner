package kr.ac.shinhan.travelpartner.Adapter;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import kr.ac.shinhan.travelpartner.Item.DetailWithTourItem;

import kr.ac.shinhan.travelpartner.R;

public class DetailRecyclerAdapter extends RecyclerView.Adapter<DetailRecyclerAdapter.ViewHolder> {
    Context context;
    ArrayList<DetailWithTourItem> items;
    int itemLayout;
    private String parking, publicTransport, exit, wheelchair, elevator, restroom, braileblock, helpdog, audioguide, signguide, videoguide, stroller, lactationroom;

    public DetailRecyclerAdapter(Context context, ArrayList<DetailWithTourItem> items, int itemLayout) {
        this.context = context;
        this.items = items;
        this.itemLayout = itemLayout;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (getItemCount() <= 0 && position >= getItemCount()) {
            return;
        }
        DetailWithTourItem item = items.get(holder.getAdapterPosition());
        String title = item.getTitle();
        setImage(title, holder.mIcon);
        holder.mTitle.setText(title);
        holder.mContents.setText(item.getContents());
    }
    public void setImage(String title, ImageView mIcon){
        switch (title){
            case "장애인 주차 구역":
                mIcon.setImageResource(R.drawable.ic_disabledparking);
                break;
            case "접근로":
                mIcon.setImageResource(R.drawable.ic_entrance);
                break;
            case "휠체어":
                mIcon.setImageResource(R.drawable.ic_wheelchair);
                break;
            case "출입 통로":
                mIcon.setImageResource(R.drawable.ic_exit);
                break;
            case "엘리베이터":
                mIcon.setImageResource(R.drawable.ic_elevator);
                break;
            case "화장실":
                mIcon.setImageResource(R.drawable.ic_toilet);
                break;
            case "점자블록":
                mIcon.setImageResource(R.drawable.ic_braileblock);
                break;
            case "보조견 동반":
                mIcon.setImageResource(R.drawable.ic_dog);
                break;
            case "오디오 가이드":
                mIcon.setImageResource(R.drawable.ic_headphones);
                break;
            case "수화안내":
                mIcon.setImageResource(R.drawable.ic_signlanguage);
                break;
            case "유모차":
                mIcon.setImageResource(R.drawable.ic_stroller_color);
                break;
            case "수유실":
                mIcon.setImageResource(R.drawable.ic_baby_bottle);
                break;
        }
    }
    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return this.items.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView mIcon;
        TextView mTitle, mContents;


        public ViewHolder(View itemView) {
            super(itemView);
            mIcon = itemView.findViewById(R.id.iv_detailitem_icon);
            mTitle = itemView.findViewById(R.id.tv_detailitem_title);
            mContents = itemView.findViewById(R.id.tv_detailitem_contents);
        }
    }

}
