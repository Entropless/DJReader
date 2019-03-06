package com.dianju_wy.showpdf;

public class DJNativeInterface {
    public int objID;
    public int pageNums;
    private static final String libName = "AutoSrvSealUtil";
    static{
        System.loadLibrary(libName);
    }
    public int openFile(String path){
        if (objID != 0){
            saveFile(objID,"");
        }
        objID = this.openObj(path,1);
        pageNums = this.getPageCount(objID);
        return objID;
    }

    /**
     * 跳转页码
     * @param page
     * @return
     */
    public int gotoPage(int page) {
        if (page > pageNums - 1)
            page = pageNums - 1;
        else if (page < 0) {
            page = 0;
        }
        int res=gotoPage(objID, page);
        return res;
    }

    /**
     * 打开对象
     *
     * @param openPath
     *            打开路径，可为""
     * @param isPdf
     *            1为pdf类型,0为aip类型
     * @return 对象ID <=0为失败，>0为成功
     */
    public native int openObj(String path,int isPdf);

    /**
     * 保存文档
     *
     * @param objID
     *            打开后返回的objID
     * @param filePath
     *            文档保存路径
     * @return 对象ID <=0为失败，>0为成功
     */
    public native int saveFile(int objID, String filePath);

    /**
     * 获取文档页数
     *
     * @param objID
     *            打开后返回的objID
     * @return
     */
    public native int getPageCount(int objID);

    /**
     * 页码跳转
     *
     * @param objID
     *            打开后返回的objID
     * @param page
     *            转到的页码，从0开始
     * @return
     */
    public native int gotoPage(int objID, int page);

    /**
     * 获取页宽
     *
     * @param objID
     *            打开后返回的objID
     * @return
     */
    public native float getPageWidth(int objID);

    /**
     * 获取页高
     *
     * @param objID
     *            打开后返回的objID
     * @return
     */
    public native float getPageHeight(int objID);

    /**
     * 设置page属性
     *
     * @param objID
     *            打开后返回的objID，横缩放，纵缩放，x坐标，y坐标
     * @return
     */
    public native int setAndroidPageInfo(int objID, float fScaleW, float fScaleH, int patchX, int patchY, int patchW, int patchH);
    public native int setAndroidPageInfoEx(int nObjID, float fScaleW, float fScaleH, int patchX, int patchY, int patchW, int patchH, int nToBmpX, int nToBmpY);

    public native int setValue(int nObjID, String name, String value);

}
