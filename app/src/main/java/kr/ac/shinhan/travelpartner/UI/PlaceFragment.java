package kr.ac.shinhan.travelpartner.UI;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

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
    private String title, tel, addr1, thumbnail;

    private int page;
    private Spinner mAreaSpinner, mContentTypeSpinner;
    private Button mScrollBtn, mSearchBtn;
    private TextView mTitleArrange, mViewArrange; //버튼 셀렉터로 대체 가능
    private EditText mSearchEditText;
    private ArrayList<String> guNameList;
    private ArrayList<PlaceItem> items = new ArrayList<PlaceItem>();
    private HashMap<String, String> guCodeMap;
    private RecyclerAdapter recyclerAdapter = new RecyclerAdapter(this.getActivity(), items, R.layout.activity_main);


    public PlaceFragment(){

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_place, container, false);

        return view;
    }
}
