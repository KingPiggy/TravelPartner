package kr.ac.shinhan.travelpartner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import kr.ac.shinhan.travelpartner.Item.PlaceItem;

import static kr.ac.shinhan.travelpartner.XMLparsing.ServiceDefinition.APPNAME;
import static kr.ac.shinhan.travelpartner.XMLparsing.ServiceDefinition.KEY;
import static kr.ac.shinhan.travelpartner.XMLparsing.ServiceDefinition.OS;
import static kr.ac.shinhan.travelpartner.XMLparsing.ServiceDefinition.SERVICE_DETAIL_COMMON;
import static kr.ac.shinhan.travelpartner.XMLparsing.ServiceDefinition.SERVICE_DETAIL_INTRO;
import static kr.ac.shinhan.travelpartner.XMLparsing.ServiceDefinition.SERVICE_URL;

public class PlaceInfoActivity extends AppCompatActivity {
    private PlaceItem placeItem;
    private TextView mTel, mTitle, mAddr, mOverview, mContentTypeId;
    private ImageView image;
    private String contentId;
    private String tel, title, addr1, overview, firstimage, contentTypeId;
    private double lat, lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_info);

        Intent intent = getIntent();
        contentId = intent.getStringExtra("contentid");
        lat = intent.getDoubleExtra("latitude", 0);
        lon = intent.getDoubleExtra("longitude", 0);

        mTel = (TextView)findViewById(R.id.tv_info_tel);
        mTitle = (TextView)findViewById(R.id.tv_info_title);
        mAddr = (TextView)findViewById(R.id.tv_info_addr);
        mOverview = (TextView)findViewById(R.id.tv_info_overview);
        image = (ImageView)findViewById(R.id.iv_info_image);

    }

    class PlaceInfoParsing extends AsyncTask<String, String, PlaceItem> {

        @Override
        protected PlaceItem doInBackground(String... strings) {
            try {
                String contentId = strings[0];

                URL url = new URL(SERVICE_URL + SERVICE_DETAIL_COMMON + "ServiceKey=" + KEY + "&MobileOS=" + OS + "&MobileApp=" + APPNAME +
                        "&contentId="+ contentId + "&defaultYN=Y" + "&firstImageYN=Y" + "&addrinfoYN=Y" + "&overviewYN=Y" );
                XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
                XmlPullParser parser = parserCreator.newPullParser();

                // 지역기반 관광 정보 조회 파싱
                //parser.setInput(areaBasedListURL.openStream(), "UTF-8");
                int parserEvent = parser.getEventType();

                PlaceItem placeItem = null;
                while (parserEvent != XmlPullParser.END_DOCUMENT) {
                    switch (parserEvent) {
                        case XmlPullParser.START_TAG:
                            String tag = parser.getName();
                            if (tag.equals("item")) {
                                placeItem = new PlaceItem();
                            } else if (tag.equals("addr1")) {
                                parser.next();
                                addr1 = parser.getText();
                                placeItem.setAddr(addr1);
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            String endTag = parser.getName();
                            if (endTag.equals("item")) {

                            }
                            break;
                    }
                    parserEvent = parser.next();
                }
            } catch (XmlPullParserException | IOException e) {
                e.printStackTrace();
            }
            return placeItem;
        }

        @Override
        protected void onPostExecute(PlaceItem placeItem) {
            super.onPostExecute(placeItem);

        }
    }
}