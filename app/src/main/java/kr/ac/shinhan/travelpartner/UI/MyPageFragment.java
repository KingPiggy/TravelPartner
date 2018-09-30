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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import kr.ac.shinhan.travelpartner.AccountActivity;
import kr.ac.shinhan.travelpartner.Firebase.GoogleSignInActivity;
import kr.ac.shinhan.travelpartner.PlaceInfoActivity;
import kr.ac.shinhan.travelpartner.ProfileNotice;
import kr.ac.shinhan.travelpartner.ProfileUse;
import kr.ac.shinhan.travelpartner.R;

public class MyPageFragment extends Fragment {
    private LinearLayout profileLayout;
    View view;
    private TextView mName, mEmail;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private Button mProfile, mUse;

    public MyPageFragment() {

    }

    @SuppressLint("ClickableViewAccessibility")
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_my_page, container, false);

        mProfile = (Button) view.findViewById(R.id.btn_profile_btn1);
        mUse = (Button) view.findViewById(R.id.btn_profile_btn2);
        setProfile();
        mProfile.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent in = new Intent(view.getContext(), ProfileNotice.class);
                                            startActivity(in);
                                        }
                                    }

        );
        mUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(view.getContext(), ProfileUse.class);
                startActivity(in);
            }
        });

        profileLayout = (LinearLayout) view.findViewById(R.id.layout_profile_profile);
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
                switch (event.getAction()) {
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

    @Override
    public void onResume() {
        super.onResume();
        setProfile();
    }

    public void setProfile() {
        mName = (TextView) view.findViewById(R.id.tv_profile_name);
        mEmail = (TextView) view.findViewById(R.id.tv_profile_email);
        de.hdodenhof.circleimageview.CircleImageView userPhoto = view.findViewById(R.id.iv_profile_image);

        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(view.getContext(), gso);
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            mName.setText(user.getDisplayName());
            mEmail.setText(user.getEmail());

        } else {
            mName.setText("로그인이 필요합니다.");
            mEmail.setText("");
        }
    }

}
