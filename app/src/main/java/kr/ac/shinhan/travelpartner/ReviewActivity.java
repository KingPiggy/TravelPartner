package kr.ac.shinhan.travelpartner;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class ReviewActivity extends AppCompatActivity {

    private static final String TAG = "ReviewActivity";
    private static final String REQUIRED = "Required";

    private String title, content, image, userid, place, contentId;
    private EditText mTitle, mContent;
    private Context mContext;
    private Button mBtnSend;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mFirebaseDatabase;
    PlaceInfoActivity.PlaceInfoParsing contentParsing ;

    @Override
    protected void onCreate(@Nullable Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_review);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.parseColor("#FAD956"));
        }
        mContext = ReviewActivity.this;
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();

        mTitle = (EditText) findViewById(R.id.et_title);
        mContent = (EditText) findViewById(R.id.et_content);
        mBtnSend = (Button) findViewById(R.id.btn_send_review);

        userid =user.getUid();

    mBtnSend.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            title = mTitle.getText().toString();
            content = mContent.getText().toString();
            Intent intent = getIntent();
            contentId = intent.getStringExtra("contentId");
            writeNewPost(userid, title, content, contentId);
        }
    });
        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = mTitle.getText().toString();
                content = mContent.getText().toString();
                writeNewPost(userid, title, content, contentId);
            }
        });
    }
    private void writeNewPost(String userId, String title, String content, String contentId) {
        User user = new User(userId, title, content, contentId);

        mFirebaseDatabase.child("users").push().setValue(user);
    }
}

