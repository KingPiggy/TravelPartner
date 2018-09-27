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
import com.squareup.picasso.Picasso;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.net.URL;

import kr.ac.shinhan.travelpartner.Item.PlaceItem;

import static kr.ac.shinhan.travelpartner.XMLparsing.ServiceDefinition.APPNAME;
import static kr.ac.shinhan.travelpartner.XMLparsing.ServiceDefinition.KEY;
import static kr.ac.shinhan.travelpartner.XMLparsing.ServiceDefinition.OS;
import static kr.ac.shinhan.travelpartner.XMLparsing.ServiceDefinition.SERVICE_DETAIL_INTRO;
import static kr.ac.shinhan.travelpartner.XMLparsing.ServiceDefinition.SERVICE_URL;

public class PlaceInfoActivity extends AppCompatActivity{
    private PlaceItem placeItem;
    private TextView mContentTypeId, mTitle, mTel, mAddr, mOpenTime;
    private ImageView mImage;
    private Button mTelBtn, mAddrBtn;
    private String contentId, image, contentTypeId, title, tel, addr;

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

        mTelBtn = (Button)findViewById(R.id.btn_info_tel);
        mAddrBtn = (Button)findViewById(R.id.btn_info_addr);
        mTelBtn.setOnClickListener(btnListener);

        Log.d("hoon", "콘텐트 ID : " + contentId);
        Log.d("hoon", "콘텐트 타입 : " + contentTypeId);
        initUI();
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

        Picasso.get().load(image).into(mImage);
        mContentTypeId.setText(contentTypeId);
        mTitle.setText(title);
        mTel.setText(tel);
        mAddr.setText(addr);
    }

//    class PlaceInfoParsing extends AsyncTask<String, String, PlaceItem> {
//
//        @Override
//        protected PlaceItem doInBackground(String... strings) {
//            try {
//                String contentId = strings[0];
//
//                URL detailIntroUrl = new URL(SERVICE_URL + SERVICE_DETAIL_INTRO + "ServiceKey=" + KEY + "&MobileOS=" + OS + "&MobileApp=" + APPNAME +
//                        "&contentId="+ contentId + "&defaultYN=Y" + "&firstImageYN=Y" + "&addrinfoYN=Y" + "&overviewYN=Y" );
//                XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
//                XmlPullParser parser = parserCreator.newPullParser();
//
//                parser.setInput(detailIntroUrl.openStream(), "UTF-8");
//                int parserEvent = parser.getEventType();
//
//                PlaceItem placeItem = null;
//                while (parserEvent != XmlPullParser.END_DOCUMENT) {
//                    switch (parserEvent) {
//                        case XmlPullParser.START_TAG:
//                            String tag = parser.getName();
//                            if (tag.equals("item")) {
//                                placeItem = new PlaceItem();
//                            } else if (tag.equals("addr1")) {
//                                parser.next();
//                                addr = parser.getText();
//                                placeItem.setAddr(addr);
//                            }
//                            break;
//                        case XmlPullParser.END_TAG:
//                            String endTag = parser.getName();
//                            if (endTag.equals("item")) {
//
//                            }
//                            break;
//                    }
//                    parserEvent = parser.next();
//                }
//            } catch (XmlPullParserException | IOException e) {
//                e.printStackTrace();
//            }
//            return placeItem;
//        }
//
//        @Override
//        protected void onPostExecute(PlaceItem placeItem) {
//            super.onPostExecute(placeItem);
//
//        }
//    }
}