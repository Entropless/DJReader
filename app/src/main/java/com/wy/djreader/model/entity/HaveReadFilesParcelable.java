package com.wy.djreader.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @ClassN HaveReadFilesParcelable
 * @desc 
 * @author wy
 * @date 2018/12/3 17:40
 */
public class HaveReadFilesParcelable implements Parcelable {

    private String id;
    private String file_name;
    private String file_path;
    private String display_time;
    private String file_size;
    private byte[] file_thum;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getFile_name() {
        return file_name;
    }
    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }
    public String getFile_path() {
        return file_path;
    }
    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }
    public String getDisplay_time() {
        return display_time;
    }
    public void setDisplay_time(String display_time) {
        this.display_time = display_time;
    }
    public String getFile_size() {
        return file_size;
    }
    public void setFile_size(String file_size) {
        this.file_size = file_size;
    }
    public byte[] getFile_thum() {
        return file_thum;
    }
    public void setFile_thum(byte[] file_thum) {
        this.file_thum = file_thum;
    }

    public HaveReadFilesParcelable() {
        super();
    }
    public HaveReadFilesParcelable(String id, String file_name, String file_path, String display_time, String file_size, byte[] file_thum) {
        super();
        this.id = id;
        this.file_name = file_name;
        this.file_path = file_path;
        this.display_time = display_time;
        this.file_size = file_size;
        this.file_thum = file_thum;
    }

    public static final Creator<HaveReadFilesParcelable> CREATOR = new Creator<HaveReadFilesParcelable>() {
        @Override
        public HaveReadFilesParcelable createFromParcel(Parcel in) {
            return new HaveReadFilesParcelable(in);
        }

        @Override
        public HaveReadFilesParcelable[] newArray(int size) {
            return new HaveReadFilesParcelable[size];
        }
    };

    /**
     * @desc 当前对象的内容描述,一般返回0即可
     * @date 2018/12/3 18:11
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * @desc 将数据对象序列化到Parcel对象中
     * @author think
     * @date 2018/12/3 18:11
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(file_name);
        dest.writeString(file_path);
        dest.writeString(file_size);
        dest.writeString(display_time);
        dest.writeByteArray(file_thum);
    }

    /**
     * @desc 将数据从Parcel对象中反序列化出来   Note:序列化与反序列化的顺序必须一致
     * @author think
     * @date 2018/12/3 18:13
     */
    protected HaveReadFilesParcelable(Parcel in) {
        id = in.readString();
        file_name = in.readString();
        file_path = in.readString();
        file_size = in.readString();
        display_time = in.readString();
//        如果数据类型为数组或集合类型，需要先进行实例化  List<Student> list = studentList;
        file_thum = new byte[in.readInt()];
        in.readByteArray(file_thum);
        /*list = new ArrayList<Student>();
        in.readList(list,getClass().getClassLoader());*/
    }
}
