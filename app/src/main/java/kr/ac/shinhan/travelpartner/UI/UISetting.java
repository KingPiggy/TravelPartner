package kr.ac.shinhan.travelpartner.UI;

public class UISetting {
    public String setContentTypeId(String contentTypeId){
        String result = "#";
        //{"장소 유형", "관광지", "문화시설", "레포츠", "숙박", "쇼핑", "음식점"};
        //={"", "12", "14", "28", "32", "38", "39"};
        if(contentTypeId.equals("12")){
            result = result + "관광지";
        }
        else if(contentTypeId.equals("14")){
            result = result + "문화시설";
        }
        else if(contentTypeId.equals("28")){
            result = result + "레포츠";
        }
        else if(contentTypeId.equals("32")){
            result = result + "숙박";
        }
        else if(contentTypeId.equals("38")){
            result = result + "쇼핑";
        }
        else if(contentTypeId.equals("39")){
            result = result + "음식점";
        }
        else{
            result = result + "기타";
        }
        return result;
    }
}
