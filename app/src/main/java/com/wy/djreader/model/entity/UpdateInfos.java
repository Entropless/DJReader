package com.wy.djreader.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class UpdateInfos implements Parcelable {
    private String versionCode;
    private String versionName;
    private String appUpdateUrl;
    private String description;

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getAppUpdateUrl() {
        return appUpdateUrl;
    }

    public void setAppUpdateUrl(String appUpdateUrl) {
        this.appUpdateUrl = appUpdateUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UpdateInfos() {
    }

    protected UpdateInfos(Parcel in) {
        versionCode = in.readString();
        versionName = in.readString();
        appUpdateUrl = in.readString();
        description = in.readString();
    }

    public static final Creator<UpdateInfos> CREATOR = new Creator<UpdateInfos>() {
        @Override
        public UpdateInfos createFromParcel(Parcel in) {
            return new UpdateInfos(in);
        }

        @Override
        public UpdateInfos[] newArray(int size) {
            return new UpdateInfos[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(versionCode);
        dest.writeString(versionName);
        dest.writeString(appUpdateUrl);
        dest.writeString(description);
    }


}
