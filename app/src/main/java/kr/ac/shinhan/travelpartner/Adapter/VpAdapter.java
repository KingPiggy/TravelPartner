package kr.ac.shinhan.travelpartner.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import kr.ac.shinhan.travelpartner.PlaceInfoActivity;
import kr.ac.shinhan.travelpartner.R;

public class VpAdapter extends PagerAdapter {
    private int[] images = {R.drawable.vlotte, R.drawable.vaqua, R.drawable.vplaza
            , R.drawable.vsky};
    private int[] pages;
    private LayoutInflater inflater;
    private Context context;
    private int mCount;
    private String contentId, image, contentTypeId, uiContentType, title, tel, addr;
    private double lat, lon;



    public VpAdapter(Context context) {
        this.context = context;
        int actualNoOfIDs = images.length;
        mCount = actualNoOfIDs + 2;
        pages = new int[mCount];

        for (int i = 0; i < actualNoOfIDs; i++) {
            pages[i + 1] = images[i];
        }
        pages[0] = images[actualNoOfIDs - 1];
        pages[mCount - 1] = images[0];
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
                Intent intent;
                switch (position) {
                    case 0:
                        contentTypeId ="12";
                        uiContentType = "#관광지";
                        lat= 127.0979006014;
                        lon = 37.5113516917;
                        contentId = "126498";
                        image ="http://tong.visitkorea.or.kr/cms/resource/77/2553577_image2_1.jpg";
                        title ="롯데월드";
                        tel ="1661-2000";
                        addr ="서울특별시 송파구 올림픽로 240";

                        intent = new Intent(context, PlaceInfoActivity.class);
                        intent.putExtra("latitude", lat);
                        intent.putExtra("longitude", lon);
                        intent.putExtra("contentid", contentId);
                        intent.putExtra("contentTypeId", contentTypeId);
                        intent.putExtra("uiContentTypeId", uiContentType);
                        intent.putExtra("image", image);
                        intent.putExtra("title", title);
                        intent.putExtra("tel", tel);
                        intent.putExtra("addr", addr);
                        context.startActivity(intent);

                        break;
                    case 1:
                        contentTypeId ="14";
                        uiContentType = "#문화시설";
                        lat= 127.0591318945;
                        lon = 37.5118092746;
                        contentId = "130284";
                        image ="http://tong.visitkorea.or.kr/cms/resource/86/2433886_image2_1.JPG";
                        title ="코엑스 아쿠아리움";
                        tel ="";
                        addr ="서울특별시 강남구 영동대로 513";

                        intent = new Intent(context, PlaceInfoActivity.class);
                        intent.putExtra("latitude", lat);
                        intent.putExtra("longitude", lon);
                        intent.putExtra("contentid", contentId);
                        intent.putExtra("contentTypeId", contentTypeId);
                        intent.putExtra("uiContentTypeId", uiContentType);
                        intent.putExtra("image", image);
                        intent.putExtra("title", title);
                        intent.putExtra("tel", tel);
                        intent.putExtra("addr", addr);
                        context.startActivity(intent);
                        break;
                    case 2:
                        contentTypeId ="12";
                        uiContentType = "#관광지";
                        lat= 126.9769709861;
                        lon = 37.5727035021;
                        contentId = "775394";
                        image ="http://tong.visitkorea.or.kr/cms/resource/11/1945811_image2_1.jpg";
                        title ="광화문광장";
                        tel ="";
                        addr ="서울특별시 종로구 세종대로 172";

                        intent = new Intent(context, PlaceInfoActivity.class);
                        intent.putExtra("latitude", lat);
                        intent.putExtra("longitude", lon);
                        intent.putExtra("contentid", contentId);
                        intent.putExtra("contentTypeId", contentTypeId);
                        intent.putExtra("uiContentTypeId", uiContentType);
                        intent.putExtra("image", image);
                        intent.putExtra("title", title);
                        intent.putExtra("tel", tel);
                        intent.putExtra("addr", addr);
                        context.startActivity(intent);
                        break;
                    case 3:
                        contentTypeId ="12";
                        uiContentType = "#관광지";
                        lat= 127.1042832896;
                        lon = 37.5136209882;
                        contentId = "2492348";
                        image ="http://tong.visitkorea.or.kr/cms/resource/91/2492291_image2_1.jpg";
                        title ="롯데월드타워 서울스카이";
                        tel ="";
                        addr ="서울특별시 송파구 올림픽로 300";

                        intent = new Intent(context, PlaceInfoActivity.class);
                        intent.putExtra("latitude", lat);
                        intent.putExtra("longitude", lon);
                        intent.putExtra("contentid", contentId);
                        intent.putExtra("contentTypeId", contentTypeId);
                        intent.putExtra("uiContentTypeId", uiContentType);
                        intent.putExtra("image", image);
                        intent.putExtra("title", title);
                        intent.putExtra("tel", tel);
                        intent.putExtra("addr", addr);
                        context.startActivity(intent);
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
