package kr.ac.shinhan.travelpartner.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import kr.ac.shinhan.travelpartner.Item.PlaceItem;
import kr.ac.shinhan.travelpartner.R;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    Context context;
    ArrayList<PlaceItem> items;
    int itemLayout;

    public RecyclerAdapter(Context context, ArrayList<PlaceItem> items, int item_layout) {
        this.context = context;
        this.items = items;
        this.itemLayout = item_layout;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_place, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (getItemCount() <= 0 && position >= getItemCount()) {
            return;
        }
        PlaceItem item = items.get(holder.getAdapterPosition());

        holder.mTitle.setText(item.getTitle());
        holder.mContentType.setText(item.getContentType());
        holder.mTel.setText(item.getTel());
        holder.mAddr.setText(item.getAddr());
//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        if (items == null){
            return 0;
        }
        return this.items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mContentType, mTitle, mTel, mAddr;
        ImageView mImage;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            mContentType = (TextView) itemView.findViewById(R.id.tv_placeitem_contenttype);
            mTitle = (TextView) itemView.findViewById(R.id.tv_placeitem_title);
            mTel = (TextView) itemView.findViewById(R.id.tv_placeitem_tel);
            mAddr = (TextView) itemView.findViewById(R.id.tv_placeitem_addr);
            mImage = (ImageView) itemView.findViewById(R.id.iv_placeitem_thumbnail);
            cardView = (CardView) itemView.findViewById(R.id.cardview_placeitem);
        }
    }
}
