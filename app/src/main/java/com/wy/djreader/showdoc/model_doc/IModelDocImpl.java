package com.wy.djreader.showdoc.model_doc;

import android.content.Context;
import android.graphics.Bitmap;

import com.dianju.showpdf.DJSOInterface;
import com.wy.djreader.model.dao.IHaveReadFiles;
import com.wy.djreader.model.dao.impl.HaveReadFilesImpl;
import com.wy.djreader.model.entity.HaveReadFilesSerializable;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IModelDocImpl implements IModelDoc{

    private IHaveReadFiles iHaveReadFiles;
    private HaveReadFilesSerializable haveReadFiles;
    @Override
    public void addFileInfosToDb(Context context, String filePath) {
        // 将数据存储到数据库
        iHaveReadFiles = new HaveReadFilesImpl(context);
        haveReadFiles = HaveReadFilesSerializable.getInstance();
        String filename = filePath.substring(filePath.lastIndexOf("/") + 1);
        File opendFile = new File(filePath);
        String filesize = opendFile.length()/1000 + "Kb";
        if (opendFile.length()/1000 >= 1000) {
            filesize = opendFile.length()/1000/1000 + "Mb";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//大写代表24小时制
        Date date = new Date(System.currentTimeMillis());
        String currTime = dateFormat.format(date);
        //API24以上使用此方法
//		Calendar ca = Calendar.getInstance();
//		currTime = ca.get(Calendar.HOUR_OF_DAY) + "";
        String opentime = currTime;
        haveReadFiles.setFile_name(filename);
        haveReadFiles.setFile_path(filePath);
        haveReadFiles.setDisplay_time(opentime);
        haveReadFiles.setFile_size(filesize);
        //获取文件缩略图
        byte[] filethumb = getFileThumb(filePath);
        haveReadFiles.setFile_thum(filethumb);

    }

    @Override
    public byte[] getFileThumb(String filePath) {
        byte[] thumb = null;
        DJSOInterface djsoInterface = new DJSOInterface();
        if(filePath.endsWith(".aip")) {
            djsoInterface.openTempFile(filePath);
        } else if(filePath.endsWith(".pdf")) {
            djsoInterface.openFile(filePath);
        } else if(filePath.endsWith(".ofd")) {
            djsoInterface.openTempFile(filePath);
        }
        Bitmap bitmap = djsoInterface.getBitmap(0, 150, 150, 0);
        djsoInterface.saveFile("");
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 10, bout);
        thumb = bout.toByteArray();
        return thumb;
    }
}
