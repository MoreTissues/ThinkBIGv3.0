package com.thinkbig.thinkbig.Objects;

public class Upload {
    private String mName;
    private String mImgURL;
    private String mKey;
    private String mDesc;

    public Upload(){

    }

    public Upload(String mName, String mImgURL, String mDesc) {
        if (mName.trim().equals("")){
            mName = "No Name";
        }
        this.mName = mName;
        this.mImgURL = mImgURL;
        this.mDesc = mDesc;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmImgURL() {
        return mImgURL;
    }

    public void setmImgURL(String mImgURL) {
        this.mImgURL = mImgURL;
    }

    public String getmKey() {
        return mKey;
    }

    public void setmKey(String mKey) {
        this.mKey = mKey;
    }

    public String getmDesc() {
        return mDesc;
    }

    public void setmDesc(String mDesc) {
        this.mDesc = mDesc;
    }
}



