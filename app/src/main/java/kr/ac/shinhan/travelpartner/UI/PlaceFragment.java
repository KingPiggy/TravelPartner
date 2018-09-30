package kr.ac.shinhan.travelpartner.UI;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import kr.ac.shinhan.travelpartner.Adapter.AreaSpinnerAdapter;
import kr.ac.shinhan.travelpartner.Adapter.ContentTypeSpinnerAdapter;
import kr.ac.shinhan.travelpartner.Adapter.RecyclerAdapter;
import kr.ac.shinhan.travelpartner.Item.PlaceItem;
import kr.ac.shinhan.travelpartner.R;

import static kr.ac.shinhan.travelpartner.XMLparsing.ServiceDefinition.APPNAME;
import static kr.ac.shinhan.travelpartner.XMLparsing.ServiceDefinition.AREA_CODE;
import static kr.ac.shinhan.travelpartner.XMLparsing.ServiceDefinition.KEY;
import static kr.ac.shinhan.travelpartner.XMLparsing.ServiceDefinition.NUM_OF_ITEM;
import static kr.ac.shinhan.travelpartner.XMLparsing.ServiceDefinition.NUM_OF_ROWS;
import static kr.ac.shinhan.travelpartner.XMLparsing.ServiceDefinition.OS;
import static kr.ac.shinhan.travelpartner.XMLparsing.ServiceDefinition.SERVICE_AREACODE;
import static kr.ac.shinhan.travelpartner.XMLparsing.ServiceDefinition.SERVICE_AREA_BASED_LIST;
import static kr.ac.shinhan.travelpartner.XMLparsing.ServiceDefinition.SERVICE_SEARCH_KEYWORD;
import static kr.ac.shinhan.travelpartner.XMLparsing.ServiceDefinition.SERVICE_URL;

public class PlaceFragment extends Fragment {
    View view;
    private String guCode, contentType, arrange, contentId;
    private String title, tel, addr1, image;
    private double latitude, longitude;
    private Spinner mAreaSpinner, mContentTypeSpinner;
    private ProgressBar mProgressBar;
    private Button mSearchBtn;
    private FloatingActionButton mScrollBtn;
    private TextView mTitleArrange, mViewArrange;
    private EditText mSearchEditText;
    private ArrayList<String> guNameList;
    private ArrayList<PlaceItem> firstItems = new ArrayList<PlaceItem>();
    private HashMap<String, String> guCodeMap;
    private UISetting uiSetting = new UISetting();
    private RecyclerAdapter recyclerAdapter = new RecyclerAdapter(this.getActivity(), firstItems, R.layout.activity_main);

    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;

    public PlaceFragment() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_place, container, false);

        arrange = "O";
        contentType = "";
        guCode = "";

        mProgressBar = (ProgressBar) view.findViewById(R.id.progressbar_place_progress);
        mProgressBar.setIndeterminate(true);
        mProgressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#00498c"), PorterDuff.Mode.MULTIPLY);
        mSearchEditText = (EditText) view.findViewById(R.id.edittext_place_search);

        mSearchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId) {
                    case EditorInfo.IME_ACTION_SEARCH:
                        mSearchBtn.performClick();
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });

        mSearchBtn = (Button) view.findViewById(R.id.btn_place_search);
        mSearchBtn.setOnClickListener(searchListener);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_place);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mTitleArrange = (TextView) view.findViewById(R.id.tv_place_arrange_title);
        mViewArrange = (TextView) view.findViewById(R.id.tv_place_arrange_view);
        mTitleArrange.setPaintFlags(mTitleArrange.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        mViewArrange.setPaintFlags(mViewArrange.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        mTitleArrange.setOnClickListener(sortListener);
        mViewArrange.setOnClickListener(sortListener);

        mScrollBtn = (FloatingActionButton) view.findViewById(R.id.btn_place_scrollup);
        mScrollBtn.bringToFront();
        mScrollBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerView.smoothScrollToPosition(0);
            }
        });

        mRecyclerView.setAdapter(recyclerAdapter);
        mRecyclerView.setNestedScrollingEnabled(false);

        new AreaCode().execute();
        PlaceItemParsing placeItemParsing = new PlaceItemParsing();
        placeItemParsing.execute(guCode, contentType, arrange);

        return view;
    }

    public void initSpinner() {
        String[] contentTypeList = {"장소 유형", "관광지", "문화시설", "레포츠", "숙박", "쇼핑", "음식점"};

        mAreaSpinner = view.findViewById(R.id.spinner_place_area);
        AreaSpinnerAdapter mAreaSpinnerAdapter = new AreaSpinnerAdapter(this.getActivity(), android.R.layout.simple_spinner_item, guNameList);
        mAreaSpinner.setAdapter(mAreaSpinnerAdapter);
        mAreaSpinner.setOnItemSelectedListener(areaSpinnerListener);

        mContentTypeSpinner = view.findViewById(R.id.spinner_place_contenttype);
        ContentTypeSpinnerAdapter mContentTypeAdapter = new ContentTypeSpinnerAdapter(this.getActivity(), android.R.layout.simple_spinner_item, contentTypeList);
        mContentTypeSpinner.setAdapter(mContentTypeAdapter);
        mContentTypeSpinner.setOnItemSelectedListener(contentTypeSpinnerListener);
    }

    View.OnClickListener searchListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mSearchEditText.clearFocus();
            InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            in.hideSoftInputFromWindow(mSearchEditText.getWindowToken(), 0);
            String keyword = mSearchEditText.getText().toString();
            new SerachKeyword().execute(keyword);
        }
    };

    View.OnClickListener sortListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_place_arrange_title:
                    arrange = "O";
                    mTitleArrange.setTextSize(13);
                    mTitleArrange.setTextColor(Color.parseColor("#00498c"));
                    mViewArrange.setTextSize(12);
                    mViewArrange.setTextColor(Color.parseColor("#000000"));
                    new PlaceItemParsing().execute(guCode, contentType, arrange);
                    break;
                case R.id.tv_place_arrange_view:
                    arrange = "P";
                    mViewArrange.setTextSize(13);
                    mViewArrange.setTextColor(Color.parseColor("#00498c"));
                    mTitleArrange.setTextSize(12);
                    mTitleArrange.setTextColor(Color.parseColor("#000000"));
                    new PlaceItemParsing().execute(guCode, contentType, arrange);
                    break;
            }
        }
    };

    AdapterView.OnItemSelectedListener areaSpinnerListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String guName = guNameList.get(position);
            guCode = guCodeMap.get(guName);
            new PlaceItemParsing().execute(guCode, contentType, arrange);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    AdapterView.OnItemSelectedListener contentTypeSpinnerListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String[] contentTypeList = {"", "12", "14", "28", "32", "38", "39"};
            contentType = contentTypeList[position];
            new PlaceItemParsing().execute(guCode, contentType, arrange);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    // 지역기반 관광 정보 파싱
    class PlaceItemParsing extends AsyncTask<String, String, ArrayList<PlaceItem>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<PlaceItem> doInBackground(String... strings) {
            ArrayList<PlaceItem> newItems = new ArrayList<PlaceItem>();

            try {
                String guCode, contentType, arrange, page;
                guCode = strings[0];
                contentType = strings[1];
                arrange = strings[2];

                URL areaBasedListURL = new URL(SERVICE_URL + SERVICE_AREA_BASED_LIST + "ServiceKey=" + KEY + "&MobileOS=" + OS + "&MobileApp=" + APPNAME + "&areaCode=" + AREA_CODE
                        + "&numOfRows=" + NUM_OF_ITEM + "&arrange=" + arrange + "&contentTypeId=" + contentType + "&sigunguCode=" + guCode);
                XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
                XmlPullParser parser = parserCreator.newPullParser();

                parser.setInput(areaBasedListURL.openStream(), "UTF-8");
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
                            } else if (tag.equals("contentid")) {
                                parser.next();
                                contentId = parser.getText();
                                placeItem.setContentId(contentId);
                            } else if (tag.equals("contenttypeid")) {
                                parser.next();
                                contentType = parser.getText();
                                placeItem.setContentTypeId(contentType);
                                contentType = uiSetting.setContentTypeId(contentType);
                                placeItem.setUiContentTypeId(contentType);
                            } else if (tag.equals("firstimage")) {
                                parser.next();
                                image = parser.getText();
                                placeItem.setImage(image);
                            } else if (tag.equals("mapx")) {
                                parser.next();
                                latitude = Double.parseDouble(parser.getText());
                                placeItem.setLatitude(latitude);
                            } else if (tag.equals("mapy")) {
                                parser.next();
                                longitude = Double.parseDouble(parser.getText());
                                placeItem.setLongitude(longitude);
                            } else if (tag.equals("tel")) {
                                parser.next();
                                tel = parser.getText();
                                tel = tel.replace("<br />", "");
                                tel = tel.replace("<br/>", "");
                                tel = tel.replace("<br>", "");
                                placeItem.setTel(tel);
                            } else if (tag.equals("title")) {
                                parser.next();
                                title = parser.getText();
                                placeItem.setTitle(title);
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            String endTag = parser.getName();
                            if (endTag.equals("item")) {
                                newItems.add(placeItem);
                            }
                            break;
                    }
                    parserEvent = parser.next();
                }
            } catch (XmlPullParserException | IOException e) {
                e.printStackTrace();
            }
            return newItems;
        }

        @Override
        protected void onPostExecute(ArrayList<PlaceItem> newItems) {
            super.onPostExecute(newItems);
            if (newItems.isEmpty()) {
                Toast.makeText(getActivity(), "아이템이 없습니다.", Toast.LENGTH_SHORT).show();
            }
            firstItems.clear();
            firstItems.addAll(newItems);
            recyclerAdapter.notifyDataSetChanged();
            mProgressBar.setVisibility(View.INVISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    // 키워드 검색
    class SerachKeyword extends AsyncTask<String, String, ArrayList<PlaceItem>> {
        ArrayList<PlaceItem> newItems = new ArrayList<PlaceItem>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<PlaceItem> doInBackground(String... strings) {
            try {
                String keyword = strings[0];

                URL url = new URL(SERVICE_URL + SERVICE_SEARCH_KEYWORD + "ServiceKey=" + KEY + "&MobileOS=" + OS + "&MobileApp=" + APPNAME
                        + "&areaCode=" + AREA_CODE + "&keyword=" + URLEncoder.encode(keyword, "UTF-8") + "&numOfRows=" + NUM_OF_ITEM);

                XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
                XmlPullParser parser = parserCreator.newPullParser();
                parser.setInput(url.openStream(), "UTF-8");
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
                                Log.d("Bae","addr1");


                            } else if (tag.equals("contentid")) {
                                parser.next();
                                contentId = parser.getText();
                                placeItem.setContentId(contentId);
                                Log.i("Bae","contentId");

                            } else if (tag.equals("contenttypeid")) {
                                parser.next();
                                contentType = parser.getText();
                                placeItem.setContentTypeId(contentType);

                                contentType = uiSetting.setContentTypeId(contentType);
                                placeItem.setUiContentTypeId(contentType);
                            } else if (tag.equals("firstimage")) {
                                parser.next();
                                image = parser.getText();
                                placeItem.setImage(image);

                            } else if (tag.equals("mapx")) {
                                parser.next();
                                latitude = Double.parseDouble(parser.getText());
                                placeItem.setLatitude(latitude);
                            } else if (tag.equals("mapy")) {
                                parser.next();
                                longitude = Double.parseDouble(parser.getText());
                                placeItem.setLongitude(longitude);
                            } else if (tag.equals("tel")) {
                                parser.next();
                                tel = parser.getText();
                                if(tel != null){
                                    tel = tel.replace("<br />", "");
                                    tel = tel.replace("<br/>", "");
                                    tel = tel.replace("<br>", "");
                                }
                                placeItem.setTel(tel);

                            } else if (tag.equals("title")) {
                                parser.next();
                                title = parser.getText();
                                placeItem.setTitle(title);
                            }

                            break;
                        case XmlPullParser.END_TAG:
                            String endTag = parser.getName();
                            if (endTag.equals("item")) {
                                newItems.add(placeItem);
                            }
                            break;
                    }
                    parserEvent = parser.next();
                }
            } catch (XmlPullParserException | IOException e) {
                e.printStackTrace();
            }
            return newItems;
        }

        @Override
        protected void onPostExecute(ArrayList<PlaceItem> newItems) {
            super.onPostExecute(newItems);
            if (newItems.isEmpty()) {
                Toast.makeText(getActivity(), "아이템이 없습니다.", Toast.LENGTH_SHORT).show();
            }
            firstItems.clear();
            firstItems.addAll(newItems);
            recyclerAdapter.notifyDataSetChanged();
            mProgressBar.setVisibility(View.INVISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    // 지역 코드 조회
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
