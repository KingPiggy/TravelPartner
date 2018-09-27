package kr.ac.shinhan.travelpartner;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.net.URL;

import kr.ac.shinhan.travelpartner.Item.DetailWithTourItem;
import kr.ac.shinhan.travelpartner.Item.PlaceInfoItem;
import kr.ac.shinhan.travelpartner.Item.PlaceItem;

import static kr.ac.shinhan.travelpartner.XMLparsing.ServiceDefinition.APPNAME;
import static kr.ac.shinhan.travelpartner.XMLparsing.ServiceDefinition.KEY;
import static kr.ac.shinhan.travelpartner.XMLparsing.ServiceDefinition.OS;
import static kr.ac.shinhan.travelpartner.XMLparsing.ServiceDefinition.SERVICE_DETAIL_INTRO;
import static kr.ac.shinhan.travelpartner.XMLparsing.ServiceDefinition.SERVICE_DETAIL_WITH_TOUR;
import static kr.ac.shinhan.travelpartner.XMLparsing.ServiceDefinition.SERVICE_URL;

public class PlaceInfoActivity extends AppCompatActivity implements OnMapReadyCallback{
    private PlaceInfoItem placeInfoItem = new PlaceInfoItem();
    private TextView mContentTypeId, mTitle, mTel, mAddr, mOpenTime;
    private String chkbabycarriage, chkpet, restdate, parking, usetime, opentime;
    private ImageView mImage;
    private Button mTelBtn, mAddrBtn, mWriteReviewBtn, mFavoriteBtn;
    private String contentId, image, contentTypeId, title, tel, addr;
    private double lat, lon;
    private GoogleMap mMap;
    boolean isParking, isPet, isBabycarriage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_info);

        Intent intent = getIntent();
        contentId = intent.getStringExtra("contentid");
        image = intent.getStringExtra("image");
        contentTypeId = intent.getStringExtra("contentTypeId");
        title = intent.getStringExtra("title");
        tel = intent.getStringExtra("tel");
        addr = intent.getStringExtra("addr");

        Log.d("hoon", "콘텐트 ID : " + contentId);
        Log.d("hoon", "콘텐트 타입 : " + contentTypeId);

        initUI();
        new PlaceInfoParsing().execute(contentId, contentTypeId);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_info);
        mapFragment.getMapAsync(this);
//
//        CameraUpdate update = CameraUpdateFactory.newLatLng(new LatLng(37,126));
//
//        MarkerOptions makerOptions = new MarkerOptions();
//        LatLng seoul = new LatLng(lat, lon);
//        makerOptions
//                .position(seoul)
//                .title(title);
//        mMap.addMarker(makerOptions).showInfoWindow();
//
//        //카메라를 여의도 위치로 옮긴다.
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(seoul));
    }
    View.OnClickListener btnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent;
            switch (v.getId()){
                case R.id.btn_info_tel:
                    intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel));
                    startActivity(intent);
                    break;
                case R.id.btn_info_addr:
                    String geoCode = "geo:" + lat + "," + lon + "?q=" + title;
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoCode));
                    startActivity(intent);
                    break;
                case R.id.btn_info_write_review:
                    break;
                case R.id.btn_info_favorite:
                    break;
            }
        }
    };

    public void initUI(){
        mImage = (ImageView)findViewById(R.id.iv_info_image);
        mContentTypeId = (TextView)findViewById(R.id.tv_info_contenttypeid);
        mTitle = (TextView)findViewById(R.id.tv_info_title);
        mTel = (TextView)findViewById(R.id.tv_info_tel);
        mAddr = (TextView)findViewById(R.id.tv_info_addr);
        mOpenTime = (TextView)findViewById(R.id.tv_info_opentime);
        mTelBtn = (Button)findViewById(R.id.btn_info_tel);
        mAddrBtn = (Button)findViewById(R.id.btn_info_addr);

        mTelBtn = (Button)findViewById(R.id.btn_info_tel);
        mAddrBtn = (Button)findViewById(R.id.btn_info_addr);
        mWriteReviewBtn = (Button)findViewById(R.id.btn_info_write_review);
        mFavoriteBtn = (Button)findViewById(R.id.btn_info_favorite);

        mTelBtn.setOnClickListener(btnListener);
        mAddrBtn.setOnClickListener(btnListener);
        mWriteReviewBtn.setOnClickListener(btnListener);
        mFavoriteBtn.setOnClickListener(btnListener);

        Picasso.get().load(image).into(mImage);
        mContentTypeId.setText(contentTypeId);
        mTitle.setText(title);
        mTel.setText(tel);
        mAddr.setText(addr);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Intent intent = getIntent();
        lat = intent.getDoubleExtra("latitude", 0);  //위도
        lon = intent.getDoubleExtra("longitude", 0); //경도
        title = intent.getStringExtra("title");
        Log.d("Bae","title : " + title);
        Log.d("Bae","lat : " + lat);
        Log.d("Bae","lon : " + lon);

        MarkerOptions makerOptions = new MarkerOptions();
        LatLng seoul = new LatLng(lat, lon);

        Log.d("Bae","LatLng 객체 멤버값 latitude : " + seoul.latitude);
        Log.d("Bae","LatLng 객체 멤버값 longitude : " + seoul.longitude);

        makerOptions
                .position(seoul)
                .title(title);
        mMap.addMarker(makerOptions).showInfoWindow();

        //카메라를 여의도 위치로 옮긴다.
        mMap.moveCamera(CameraUpdateFactory.newLatLng(seoul));
    }

    class PlaceInfoParsing extends AsyncTask<String, String, PlaceInfoItem> {

        @Override
        protected PlaceInfoItem doInBackground(String... strings) {
            try {
                contentId = strings[0];
                contentTypeId = strings[1];

                Log.d("hoon", "콘텐트 ID : " + contentId);
                Log.d("hoon", "콘텐트타입 : " + contentTypeId);

                URL detailIntroUrl = new URL(SERVICE_URL + SERVICE_DETAIL_INTRO + "ServiceKey=" + KEY + "&MobileOS=" + OS + "&MobileApp=" + APPNAME +
                        "&contentId="+ contentId + "&contentTypeId=" + contentTypeId  );
                XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
                XmlPullParser parser = parserCreator.newPullParser();

                parser.setInput(detailIntroUrl.openStream(), "UTF-8");
                int parserEvent = parser.getEventType();

                while (parserEvent != XmlPullParser.END_DOCUMENT) {
                    switch (parserEvent) {
                        case XmlPullParser.START_TAG:
                            String tag = parser.getName();
                            if (tag.equals("item")) {
                                placeInfoItem = new PlaceInfoItem();
                            } else if (tag.contains("chkbabycarriage")) {
                                parser.next();
                                chkbabycarriage = parser.getText();
                                placeInfoItem.setChkbabycarriage(chkbabycarriage);
                                Log.d("hoon", "placeInfoItem 값 들어오니?? " + placeInfoItem.getChkbabycarriage());
                                Log.d("hoon", "placeInfoItem 값 들어오니?? " + parser.getText());
                            } else if (tag.contains("chkpet")) {
                                parser.next();
                                chkpet = parser.getText();
                                placeInfoItem.setChkpet(chkpet);
                                Log.d("hoon", "placeInfoItem 값 들어오니?? " + placeInfoItem.getChkpet());
                                Log.d("hoon", "placeInfoItem 값 들어오니?? " + parser.getText());
                            } else if (tag.contains("restdate")) {
                                parser.next();
                                restdate = parser.getText();
                                placeInfoItem.setRestdate(restdate);
                                Log.d("hoon", "placeInfoItem 값 들어오니?? " + placeInfoItem.getRestdate());
                                Log.d("hoon", "placeInfoItem 값 들어오니?? " + parser.getText());
                            } else if (tag.contains("parking")) {
                                parser.next();
                                parking = parser.getText();
                                placeInfoItem.setParking(parking);
                                Log.d("hoon", "placeInfoItem 값 들어오니?? " + placeInfoItem.getParking());
                                Log.d("hoon", "placeInfoItem 값 들어오니?? " + parser.getText());
                            } else if (tag.contains("opentime")) {
                                parser.next();
                                opentime = parser.getText();
                                placeInfoItem.setOpentime(opentime);
                                Log.d("hoon", "placeInfoItem 값 들어오니?? " + placeInfoItem.getOpentime());
                                Log.d("hoon", "placeInfoItem 값 들어오니?? " + parser.getText());
                            } else if (tag.contains("usetime")) {
                                parser.next();
                                usetime = parser.getText();
                                placeInfoItem.setUsetime(usetime);
                                Log.d("hoon", "placeInfoItem 값 들어오니?? " + placeInfoItem.getUsetime());
                                Log.d("hoon", "placeInfoItem 값 들어오니?? " + parser.getText());
                            }
                            break;
                    }
                    parserEvent = parser.next();
                }
            } catch (XmlPullParserException | IOException e) {
                e.printStackTrace();
            }
            return placeInfoItem;
        }

        @Override
        protected void onPostExecute(PlaceInfoItem placeInfoItem) {
            super.onPostExecute(placeInfoItem);
            Log.d("hoon", "placeInfoItem 값 확인 " + placeInfoItem.getParking() + placeInfoItem.getChkpet() + placeInfoItem.getChkbabycarriage()
                   + placeInfoItem.getOpentime() + placeInfoItem.getUsetime() + placeInfoItem.getRestdate());
            checkBooleans(placeInfoItem);
            Log.d("hoon", "boolean 값 확인" + "유모차 : " + isBabycarriage + "주차 : " + isParking + "펫 : " + isPet);
            setIcons();
        }

        public void checkBooleans(PlaceInfoItem placeInfoItem){
            if(placeInfoItem.getChkbabycarriage().contains("없음")|placeInfoItem.getChkbabycarriage().contains("불가")){
                isBabycarriage = false;
            }
            else if(placeInfoItem.getChkbabycarriage().contains("있음")|placeInfoItem.getChkbabycarriage().contains("가능")){
                isBabycarriage = true;
            }
            if(placeInfoItem.getChkpet().contains("없음")|placeInfoItem.getChkpet().contains("불가")){
                isPet = false;
            }
            else if(placeInfoItem.getChkpet().contains("있음")|placeInfoItem.getChkpet().contains("가능")){
                isPet = true;
            }
            if(placeInfoItem.getParking().contains("없음")|placeInfoItem.getParking().contains("불가")){
                isParking = false;
            }
            else if(placeInfoItem.getParking().contains("있음")|placeInfoItem.getParking().contains("가능")){
                isParking = true;
            }
        }

        public void setIcons(){
            ImageView mStroller = (ImageView)findViewById(R.id.iv_info_stroller);
            ImageView mPet = (ImageView)findViewById(R.id.iv_info_pet);
            ImageView mParking = (ImageView)findViewById(R.id.iv_info_parking);
            if(isBabycarriage){
                //가능할 때 아이콘 이미지 설정
            }
            else{
                //불가능할 때 아이콘 이미지 설정, xml에는 아이콘이랑 사진 둘 다 모르는 상태로 정의할 것(정보 없는 것)
            }
            if(isPet){

            }
            else{

            }
            if(isParking){

            }
            else{

            }
        }
    }

    class DetailWithTourParsing extends AsyncTask<String, String, DetailWithTourItem> {

        @Override
        protected DetailWithTourItem doInBackground(String... strings) {
            DetailWithTourItem detailWithTourItem = null;
            try {
                String contentId = strings[0];
                String contentTypeId = strings[1];

                URL detailIntroUrl = new URL(SERVICE_URL + SERVICE_DETAIL_WITH_TOUR + "ServiceKey=" + KEY + "&MobileOS=" + OS + "&MobileApp=" + APPNAME +
                        "&contentId="+ contentId);
                XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
                XmlPullParser parser = parserCreator.newPullParser();

                parser.setInput(detailIntroUrl.openStream(), "UTF-8");
                int parserEvent = parser.getEventType();

                while (parserEvent != XmlPullParser.END_DOCUMENT) {
                    switch (parserEvent) {
                        case XmlPullParser.START_TAG:
                            String tag = parser.getName();
                            if (tag.contains("item")) {
                                detailWithTourItem = new DetailWithTourItem();
                            } else if (tag.equals("chkbabycarriage")) {
                                parser.next();
                                //chkbabycarriage = parser.getText();
                                //placeInfoItem.setChkbabycarriage(chkbabycarriage);
                            }
                            break;
                    }
                    parserEvent = parser.next();
                }
            } catch (XmlPullParserException | IOException e) {
                e.printStackTrace();
            }
            return detailWithTourItem;
        }

        @Override
        protected void onPostExecute(DetailWithTourItem detailWithTourItem) {
            super.onPostExecute(detailWithTourItem);

        }

    }

}