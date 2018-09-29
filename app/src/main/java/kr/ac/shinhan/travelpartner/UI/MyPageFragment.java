package kr.ac.shinhan.travelpartner.UI;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import kr.ac.shinhan.travelpartner.AccountActivity;
import kr.ac.shinhan.travelpartner.Firebase.GoogleSignInActivity;
import kr.ac.shinhan.travelpartner.R;

public class MyPageFragment extends Fragment {
    private LinearLayout profileLayout;
    View view;
    private FirebaseAuth mAuth;
    private Bitmap photoBitmap;
    public MyPageFragment(){

    }

    @SuppressLint("ClickableViewAccessibility")
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_my_page, container, false);

        setProfile();

        profileLayout = (LinearLayout)view.findViewById(R.id.layout_profile_profile);
        profileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), AccountActivity.class);
                startActivity(intent);
            }
        });
        profileLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_UP:
                        profileLayout.setBackgroundColor(Color.parseColor("#ffffff"));
                        break;
                    case MotionEvent.ACTION_DOWN:
                        profileLayout.setBackgroundColor(Color.parseColor("#f1f1f1"));
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return false;
            }
        });

        return view;
    }

    public void setProfile(){
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        de.hdodenhof.circleimageview.CircleImageView userPhoto = view.findViewById(R.id.iv_profile_image);
//        Thread mThread= new Thread(){
//            @Override
//            public void run() {
//                try{
//                    //현재로그인한 사용자 정보를 통해 PhotoUrl 가져오기
//                    URL url = new URL(user.getPhotoUrl().toString());
//                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                    conn.setDoInput(true);
//                    conn.connect();
//                    InputStream is = conn.getInputStream();
//                    photoBitmap = BitmapFactory.decodeStream(is);
//                } catch (MalformedURLException ee) {
//                    ee.printStackTrace();
//                }catch (IOException e){
//                    e.printStackTrace();
//                }
//            }
//        };
//        mThread.start();
//
//        try{
//            mThread.join();
//            //변환한 bitmap적용
//            userPhoto.setImageBitmap(photoBitmap);
//
//        }catch (InterruptedException e){
//            e.printStackTrace();
//        }
    }
}
