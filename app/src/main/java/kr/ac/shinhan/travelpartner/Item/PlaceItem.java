package kr.ac.shinhan.travelpartner.Item;

public class PlaceItem {
    String contentType, title, tel, addr, contentId, image;

    public PlaceItem() {
    }

    public PlaceItem(String contentType, String title, String tel, String addr, String image) {
        this.contentType = contentType;
        this.title = title;
        this.tel = tel;
        this.addr = addr;
        this.image = image;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }


    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
