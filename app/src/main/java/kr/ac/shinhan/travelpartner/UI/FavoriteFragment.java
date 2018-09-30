package kr.ac.shinhan.travelpartner.UI;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.places.Place;

import java.util.ArrayList;

import kr.ac.shinhan.travelpartner.Adapter.RecyclerAdapter;
import kr.ac.shinhan.travelpartner.Item.PlaceItem;
import kr.ac.shinhan.travelpartner.R;

import static android.content.Context.MODE_PRIVATE;
import static kr.ac.shinhan.travelpartner.MainActivity.PREFNAME;

public class FavoriteFragment extends Fragment {
    private ArrayList<PlaceItem> firstItems = new ArrayList<PlaceItem>();
    private RecyclerAdapter recyclerAdapter = new RecyclerAdapter(this.getActivity(), firstItems, R.layout.activity_main);
    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;

    View view;

    public FavoriteFragment(){

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_favorite, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_favorite);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);



        mRecyclerView.setAdapter(recyclerAdapter);
        mRecyclerView.setNestedScrollingEnabled(false);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ArrayList<PlaceItem> newItems = new ArrayList<PlaceItem>();
        SharedPreferences favoriteItems = view.getContext().getSharedPreferences(PREFNAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = favoriteItems.edit();
        int itemCount =  favoriteItems.getInt("itemCount", 0);
        Log.d("hoon", "널값이니" + itemCount);
        while(itemCount != 0 && itemCount >0) {
            double lat, lon;
            Log.d("hoon", "널값이니" + itemCount);
            String contentId, image, contentTypeId, uiContentTypeId, title, tel, addr;
            lat = Double.parseDouble(favoriteItems.getString(itemCount + "latitude", ""));
            lon = Double.parseDouble(favoriteItems.getString(itemCount + "longitude", ""));
            contentId = favoriteItems.getString(itemCount + "contentid", "");
            contentTypeId = favoriteItems.getString(itemCount + "contentTypeId", "");
            uiContentTypeId = favoriteItems.getString(itemCount + "uiContentTypeId", "");
            image = favoriteItems.getString(itemCount + "image", "");
            title = favoriteItems.getString(itemCount + "title", "");
            tel = favoriteItems.getString(itemCount + "tel", "");
            addr = favoriteItems.getString(itemCount + "addr", "");
            PlaceItem placeItem = new PlaceItem();
            placeItem.setContentTypeId(contentTypeId);
            placeItem.setLatitude(lat);
            placeItem.setLongitude(lon);
            placeItem.setContentId(contentId);
            placeItem.setUiContentTypeId(uiContentTypeId);
            placeItem.setImage(image);
            placeItem.setTitle(title);
            placeItem.setTel(tel);
            placeItem.setAddr(addr);
            newItems.add(placeItem);
            itemCount--;
        }
        firstItems.clear();
        firstItems.addAll(newItems);
        recyclerAdapter.notifyDataSetChanged();
    }
}