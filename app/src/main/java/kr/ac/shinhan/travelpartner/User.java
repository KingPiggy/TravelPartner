package kr.ac.shinhan.travelpartner;

import com.google.firebase.database.IgnoreExtraProperties;

// [START blog_user_class]
@IgnoreExtraProperties
public class User {

    public String userid;
    public String title;
    public String content;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String userid, String title, String content) {
        this.userid = userid;
        this.title = title;
        this.content = content;
    }

}
// [END blog_user_class]