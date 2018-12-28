package com.wy.djreader.model.entity;

import java.io.Serializable;

public class HaveReadFilesSerializable implements Serializable {

    private static HaveReadFilesSerializable haveReadFiles = null;
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

    /**
     * @desc 构造
     * @author think
     * @date 2018/12/4 14:09
     */
    private HaveReadFilesSerializable() {
    }
    private static class SingletonHaveReadFiles {
        private static final HaveReadFilesSerializable haveReadFiles = new HaveReadFilesSerializable();
    }
    public static HaveReadFilesSerializable getInstance(){
        return SingletonHaveReadFiles.haveReadFiles;
    }
    private Object readResolve(){
        return haveReadFiles;
    }

}
