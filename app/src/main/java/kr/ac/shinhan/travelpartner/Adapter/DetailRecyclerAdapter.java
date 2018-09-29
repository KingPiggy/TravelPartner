package kr.ac.shinhan.travelpartner.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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
        holder.mIcon.setImageResource(R.drawable.ic_parking);
        holder.mTitle.setText(item.getTitle());
        holder.mContents.setText(item.getContents());
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
