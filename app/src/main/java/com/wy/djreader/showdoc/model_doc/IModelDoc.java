package com.wy.djreader.showdoc.model_doc;

import android.content.Context;

public interface IModelDoc  {

    //文档数据返回信息处理
    void addFileInfosToDb(Context context, String filePath);
    //获取缩略图
    byte[] getFileThumb(String filePath);
}
