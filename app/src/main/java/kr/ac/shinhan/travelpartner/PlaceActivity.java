package kr.ac.shinhan.travelpartner;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import kr.ac.shinhan.travelpartner.Adapter.AreaSpinnerAdapter;
import kr.ac.shinhan.travelpartner.Adapter.ContentTypeSpinnerAdapter;
import kr.ac.shinhan.travelpartner.Adapter.RecyclerAdapter;
import kr.ac.shinhan.travelpartner.Item.PlaceItem;
import kr.ac.shinhan.travelpartner.Listener.EndlessRecyclerViewScrollListener;

import static kr.ac.shinhan.travelpartner.XMLparsing.ServiceDefinition.APPNAME;
import static kr.ac.shinhan.travelpartner.XMLparsing.ServiceDefinition.AREA_CODE;
import static kr.ac.shinhan.travelpartner.XMLparsing.ServiceDefinition.KEY;
import static kr.ac.shinhan.travelpartner.XMLparsing.ServiceDefinition.NUM_OF_ITEM;
import static kr.ac.shinhan.travelpartner.XMLparsing.ServiceDefinition.NUM_OF_ROWS;
import static kr.ac.shinhan.travelpartner.XMLparsing.ServiceDefinition.OS;
import static kr.ac.shinhan.travelpartner.XMLparsing.ServiceDefinition.SERVICE_AREACODE;
import static kr.ac.shinhan.travelpartner.XMLparsing.ServiceDefinition.SERVICE_AREA_BASED_LIST;
import static kr.ac.shinhan.travelpartner.XMLparsing.ServiceDefinition.SERVICE_URL;

public class PlaceActivity extends AppCompatActivity {
    private String guCode, contentType, arrange, contentId;
    private int page;
    private String title, tel, addr1, thumbnail;
    private Spinner mAreaSpinner, mContentTypeSpinner;
    private Button mScrollBtn;
    private TextView mTitleArrange, mViewArrange; //버튼 셀렉터로 대체 가능
    private ArrayList<String> guNameList;
    private ArrayList<PlaceItem> items;
    private HashMap<String, String> guCodeMap;
    private EndlessRecyclerViewScrollListener scrollListener;
    private RecyclerAdapter recyclerAdapter;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        arrange = "O";
        contentType = "";
        guCode = "";
        page = 1;

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_place);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mTitleArrange = (TextView) findViewById(R.id.tv_place_arrange_title);
        mViewArrange = (TextView) findViewById(R.id.tv_place_arrange_view);
        mTitleArrange.setOnClickListener(sortListener);
        mViewArrange.setOnClickListener(sortListener);

        mScrollBtn = (Button) findViewById(R.id.btn_place_scrollup);
        mScrollBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerView.smoothScrollToPosition(0);
            }
        });

        new AreaCode().execute();
        PlaceItemParsing placeItemParsing = new PlaceItemParsing();
        placeItemParsing.execute(guCode, contentType, arrange, Integer.toString(page));


//        scrollListener = new EndlessRecyclerViewScrollListener(mLayoutManager) {
//            @Override
//            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
//                //1번
//                new PlaceItemParsing().execute(guCode, contentType, arrange, Integer.toString(page));
//                recyclerAdapter.notifyDataSetChanged();
//            }
//        };

//        mRecyclerView.addOnScrollListener(scrollListener);
        mRecyclerView.setNestedScrollingEnabled(false);
    }

    public void initSpinner() {
        String[] contentTypeList = {"전체 타입", "관광지", "문화시설", "행사/공연/축제", "레포츠", "숙박", "쇼핑", "음식점"};

        mAreaSpinner = findViewById(R.id.spinner_place_area);
        AreaSpinnerAdapter mAreaSpinnerAdapter = new AreaSpinnerAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, guNameList);
        mAreaSpinner.setAdapter(mAreaSpinnerAdapter);
        mAreaSpinner.setOnItemSelectedListener(areaSpinnerListener);

        mContentTypeSpinner = findViewById(R.id.spinner_place_contenttype);
        ContentTypeSpinnerAdapter mContentTypeAdapter = new ContentTypeSpinnerAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, contentTypeList);
        mContentTypeSpinner.setAdapter(mContentTypeAdapter);
        mContentTypeSpinner.setOnItemSelectedListener(contentTypeSpinnerListener);
    }


    View.OnClickListener sortListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_place_arrange_title:
                    arrange = "O";
                    new PlaceItemParsing().execute(guCode, contentType, arrange, Integer.toString(page));
                    break;
                case R.id.tv_place_arrange_view:
                    arrange = "P";
                    new PlaceItemParsing().execute(guCode, contentType, arrange, Integer.toString(page));
                    break;
            }
        }
    };

    AdapterView.OnItemSelectedListener areaSpinnerListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String guName = guNameList.get(position);
            guCode = guCodeMap.get(guName);
            Toast.makeText(getApplicationContext(), "guCode : " + guCode, Toast.LENGTH_SHORT).show();
            new PlaceItemParsing().execute(guCode, contentType, arrange, Integer.toString(page));
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    AdapterView.OnItemSelectedListener contentTypeSpinnerListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String[] contentTypeList = {"", "12", "14", "15", "28", "32", "38", "39"};
            contentType = contentTypeList[position];
            Toast.makeText(getApplicationContext(), "contentType : " + contentType, Toast.LENGTH_SHORT).show();
            new PlaceItemParsing().execute(guCode, contentType, arrange, Integer.toString(page));
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    class PlaceItemParsing extends AsyncTask<String, String, ArrayList<PlaceItem>> {
        @Override
        protected ArrayList<PlaceItem> doInBackground(String... strings) {
            try {
                String guCode, contentType, arrange, page;
                guCode = strings[0];
                contentType = strings[1];
                arrange = strings[2];
                page = strings[3];
                Log.d("hoon", "doInBackground의 guCode : " + guCode);
                Log.d("hoon", "doInBackground의 contentType : " + contentType);
                Log.d("hoon", "doInBackground의 arrange : " + arrange);
                Log.d("hoon", "doInBackground의 page : " + page);

                URL areaBasedListURL = new URL(SERVICE_URL + SERVICE_AREA_BASED_LIST + "ServiceKey=" + KEY + "&MobileOS=" + OS + "&MobileApp=" + APPNAME + "&areaCode=" + AREA_CODE
                        + "&numOfRows=" + NUM_OF_ITEM + "&pageNo=" + page + "&arrange=" + arrange + "&contentTypeId=" + contentType + "&sigunguCode=" + guCode);
                XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
                XmlPullParser parser = parserCreator.newPullParser();

                // 지역기반 관광 정보 조회 파싱
                parser.setInput(areaBasedListURL.openStream(), "UTF-8");
                int parserEvent = parser.getEventType();
                items = new ArrayList<PlaceItem>();
                PlaceItem placeItem = null;
                while (parserEvent != XmlPullParser.END_DOCUMENT) {
                    switch (parserEvent) {
                        case XmlPullParser.START_TAG:
                            String tag = parser.getName();
                            if (tag.equals("item")){
                                placeItem = new PlaceItem();
                            } else if (tag.equals("addr1")) {
                                parser.next();
                                addr1 = parser.getText();
                                placeItem.setAddr(addr1);
                            } else if (tag.equals("contentid")) {
                                parser.next();
                                contentId = parser.getText();
                                placeItem.setContentId(contentId);
                            } else if (tag.equals("firstimage2")) {
                                parser.next();
                                thumbnail = parser.getText();
                                placeItem.setImage(thumbnail);
                            } else if (tag.equals("tel")) {
                                parser.next();
                                tel = parser.getText();
                                placeItem.setTel(tel);
                            } else if (tag.equals("title")) {
                                parser.next();
                                title = parser.getText();
                                placeItem.setTitle(title);
                            }
                            Log.d("hoon", "파싱 값 : " + title + tel + contentId);
                            break;
                        case XmlPullParser.END_TAG:
                            String endTag = parser.getName();
                            if(endTag.equals("item")) {
                                items.add(placeItem);
                            }
                            break;
                    }
                    parserEvent = parser.next();
                }
            } catch (XmlPullParserException | IOException e) {
                e.printStackTrace();
            }
            return items;
        }

        @Override
        protected void onPostExecute(ArrayList<PlaceItem> placeItems) {
            super.onPostExecute(placeItems);
            if(placeItems.isEmpty()){
                //아이템 없다고 띄우기
                items.add(new PlaceItem());
            }
            recyclerAdapter = new RecyclerAdapter(getApplicationContext(), placeItems, R.layout.activity_main);
            mRecyclerView.setAdapter(recyclerAdapter);
        }
    }

    class AreaCode extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(SERVICE_URL + SERVICE_AREACODE + "ServiceKey=" + KEY + "&MobileOS=" + OS + "&MobileApp=" + APPNAME + "&areaCode=" + AREA_CODE
                        + "&numOfRows=" + NUM_OF_ROWS);
                XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
                XmlPullParser parser = parserCreator.newPullParser();
                parser.setInput(url.openStream(), "UTF-8");
                int parserEvent = parser.getEventType();
                guCodeMap = new HashMap<String, String>();
                String key = "", value = "";
                while (parserEvent != XmlPullParser.END_DOCUMENT) {
                    switch (parserEvent) {
                        case XmlPullParser.START_TAG:
                            String tag = parser.getName();
                            if (tag.equals("code")) {
                                parser.next();
                                value = parser.getText();

                            } else if (tag.equals("name")) {
                                parser.next();
                                key = parser.getText();
                            }
                            if (!guCodeMap.containsKey(key))
                                guCodeMap.put(key, value);
                            break;
                    }
                    parserEvent = parser.next();
                    guNameList = new ArrayList<String>();
                    guCodeMap.remove("");
                    for (Map.Entry<String, String> entry : guCodeMap.entrySet()) {
                        guNameList.add(entry.getKey());
                    }
                }
                guCodeMap.put("서울 전체", "");
                Collections.sort(guNameList);
                guNameList.add(0, "서울 전체");
            } catch (XmlPullParserException | IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            initSpinner();
        }
    }
}
