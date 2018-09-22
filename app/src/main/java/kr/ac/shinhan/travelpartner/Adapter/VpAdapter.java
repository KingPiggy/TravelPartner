package kr.ac.shinhan.travelpartner.Adapter;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import kr.ac.shinhan.travelpartner.R;

public class VpAdapter extends FragmentPagerAdapter {
    private int[] images = {R.drawable.brown, R.drawable.cony, R.drawable.edward
            , R.drawable.sally, R.drawable.yoon};
    private int[] pages;
    private LayoutInflater inflater;
    private Context context;
    private int mCount;

    public VpAdapter(android.support.v4.app.FragmentManager fm) {
        super(fm);
    }


//    this.context = context;
//    int actualNoOfIDs = images.length;
//    mCount = actualNoOfIDs + 2;
//    pages = new int[mCount];
//
//        for (int i = 0; i < actualNoOfIDs; i++) {
//        pages[i + 1] = images[i];
//    }
//    pages[0] = images[actualNoOfIDs - 1];
//    pages[mCount - 1] = images[0];

    @Override
    public Fragment getItem(int position) {

        return null;
    }

    public int getCount() {
        if (images == null)
            return 0;
        return images.length;
    }

    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((View) object);
    }


    public Object instantiateItem(ViewGroup container, final int position) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.item_notice_slider, container, false);
        ImageView imageView = (ImageView) v.findViewById(R.id.iv_noticeslider_img);
        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String itemTitle;
                Intent intentInfo;
                switch (position) {
                    case 0:
//                        itemTitle = "남산타워";
//                        intentInfo = new Intent(context, InfoActivity.class);
//                        intentInfo.putExtra("ItemTitle", itemTitle);
//                        context.startActivity(intentInfo);
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                }
            }
        });
        imageView.setImageResource(images[position]);
        container.addView(v);
        return v;
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
//        container.invalidate();
        container.removeView((View) object);
    }

    public int getItemPosition(@NonNull Object object) {
        return super.getItemPosition(object);
    }
}
