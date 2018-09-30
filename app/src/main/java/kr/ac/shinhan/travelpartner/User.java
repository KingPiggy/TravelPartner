package kr.ac.shinhan.travelpartner;

import com.google.firebase.database.IgnoreExtraProperties;

// [START blog_user_class]
@IgnoreExtraProperties
public class User {

    public String userid;
    public String title;
    public String content;
    public String email;
    public String contentId;
    public String image;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String userid, String title, String content, String contentId) {
        this.userid = userid;
        this.title = title;
        this.content = content;
        this.contentId = contentId;
    }

    public User(String userId, String email, String image) {
        this.userid = userId;
        this.email = email;
        this.image = image;
    }
}
// [END blog_user_class]