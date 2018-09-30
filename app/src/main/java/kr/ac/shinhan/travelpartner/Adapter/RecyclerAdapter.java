package kr.ac.shinhan.travelpartner.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import kr.ac.shinhan.travelpartner.AccountActivity;
import kr.ac.shinhan.travelpartner.Item.PlaceItem;
import kr.ac.shinhan.travelpartner.PlaceInfoActivity;
import kr.ac.shinhan.travelpartner.R;
import kr.ac.shinhan.travelpartner.UI.PlaceFragment;

import static android.content.Context.MODE_PRIVATE;
import static com.google.android.gms.flags.impl.SharedPreferencesFactory.getSharedPreferences;
import static kr.ac.shinhan.travelpartner.MainActivity.PREFNAME;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    Context context;
    ArrayList<PlaceItem> items;
    int itemLayout;
    double lat, lon;
    String contentId, image, contentTypeId, uiContentTypeId, title, tel, addr;

    public RecyclerAdapter(Context context, ArrayList<PlaceItem> items, int itemLayout) {
        this.context = context;
        this.items = items;
        this.itemLayout = itemLayout;
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
        holder.cardView.setTag(position);
        holder.mTitle.setText(item.getTitle());
        holder.mContentType.setText(item.getUiContentTypeId());
        holder.mTel.setText(item.getTel());
        holder.mTel.setSelected(true);
        holder.mAddr.setText(item.getAddr());
        holder.mFavorite.setTag(position);
        holder.mFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();
                PlaceItem item = items.get(position);
                MyCoolFragment.this.getActivity().
                SharedPreferences favoriteItems = context.getApplicationContext().getSharedPreferences(PREFNAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = favoriteItems.edit();
                int itemCount =  favoriteItems.getInt("itemCount", 0);
                editor.putString(itemCount + "latitude", String.valueOf(item.getLatitude()));
                editor.putString(itemCount + "longitude", String.valueOf(item.getLongitude()));
                editor.putString(itemCount + "contentid", item.getContentId());
                editor.putString(itemCount + "contentTypeId", item.getContentTypeId());
                editor.putString(itemCount + "uiContentTypeId", item.getUiContentTypeId());
                editor.putString(itemCount + "image", item.getImage());
                editor.putString(itemCount + "title", item.getTitle());
                editor.putString(itemCount + "tel", item.getTel());
                editor.putString(itemCount + "addr", item.getAddr());
                itemCount = itemCount++;
                editor.putInt("itemCount", 0);
                editor.apply();
            }
        });
        Picasso.get().load(item.getImage()).into(holder.mImage);
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return this.items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView mContentType, mTitle, mTel, mAddr;
        Button mFavorite;
        ImageView mImage;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardview_placeitem);
            cardView.setOnClickListener(this);
            mContentType = (TextView) itemView.findViewById(R.id.tv_placeitem_contenttype);
            mTitle = (TextView) itemView.findViewById(R.id.tv_placeitem_title);
            mTel = (TextView) itemView.findViewById(R.id.tv_placeitem_tel);
            mAddr = (TextView) itemView.findViewById(R.id.tv_placeitem_addr);
            mImage = (ImageView) itemView.findViewById(R.id.iv_placeitem_thumbnail);
            cardView = (CardView) itemView.findViewById(R.id.cardview_placeitem);
            mFavorite = (Button) itemView.findViewById(R.id.btn_placeitem_favorite);
        }

        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            PlaceItem item = items.get(position);
            Intent intent = new Intent(v.getContext(), PlaceInfoActivity.class);

            lat = item.getLatitude();
            lon = item.getLongitude();
            contentId = item.getContentId();
            contentTypeId = item.getContentTypeId();
            uiContentTypeId = item.getUiContentTypeId();
            image = item.getImage();
            title = item.getTitle();
            tel = item.getTel();
            addr = item.getAddr();

            intent.putExtra("latitude", lat);
            intent.putExtra("longitude", lon);
            intent.putExtra("contentid", contentId);
            intent.putExtra("contentTypeId", contentTypeId);
            intent.putExtra("uiContentTypeId", uiContentTypeId);
            intent.putExtra("image", image);
            intent.putExtra("title", title);
            intent.putExtra("tel", tel);
            intent.putExtra("addr", addr);

            v.getContext().startActivity(intent);
        }

        @Override
        public boolean onLongClick(View v) {
            int position = (int) v.getTag();
            AlertDialog.Builder alt_bld = new AlertDialog.Builder(context.getApplicationContext());
            alt_bld.setTitle("아이템을 삭제하시겠습니까?").setIcon(R.drawable.ic_photo).setCancelable(
                    false).setPositiveButton("확인",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            SharedPreferences favoriteItems = context.getSharedPreferences(PREFNAME, MODE_PRIVATE);
                            SharedPreferences.Editor editor = favoriteItems.edit();
                            int itemCount =  favoriteItems.getInt("itemCount", 0);
                            editor.remove(itemCount + "latitude");
                            editor.remove(itemCount + "longitude");
                            editor.remove(itemCount + "contentid");
                            editor.remove(itemCount + "contentTypeId");
                            editor.remove(itemCount + "uiContentTypeId");
                            editor.remove(itemCount + "image");
                            editor.remove(itemCount + "title");
                            editor.remove(itemCount + "tel");
                            editor.remove(itemCount + "addr");
                            itemCount = itemCount--;
                            editor.putInt("itemCount", 0);
                            editor.apply();
                        }
                    }).setNegativeButton("취소",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = alt_bld.create();
            alert.show();
            return false;
        }
    }
}

