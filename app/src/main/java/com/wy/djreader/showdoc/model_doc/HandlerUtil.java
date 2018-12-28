package com.wy.djreader.showdoc.model_doc;

import android.os.Handler;
import android.os.Message;

import com.dianju.showpdf.DJContentView;
import com.wy.djreader.model.dao.IHaveReadFiles;
import com.wy.djreader.model.dao.impl.HaveReadFilesImpl;
import com.wy.djreader.model.entity.HaveReadFilesSerializable;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HandlerUtil {
    private HaveReadFilesSerializable haveReadFiles = null;
    private IHaveReadFiles iHaveReadFiles = null;
    public static Handler fileHandler = new FileHandler();

    static class FileHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case DJContentView.DJCode.OPEN_FILE://打开文件
                    if (msg.arg1 == 1) {//加载成功
                        //打开文件后，记录传递过来的文件信息，当做最近打开的文件
//                        recordOpenedFiles(filePath);
                    }
                    break;
                case DJContentView.DJCode.SEAL://盖章返回
//                    cleanSelectExBtn();
                    break;
                default:
                    break;
            }
        }
    }

    /*private void recordOpenedFiles(String filePath) {
        // 将数据存储到数据库
        iHaveReadFiles = new HaveReadFilesImpl()
        openedFile = new OpenedFiles();
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
        openedFile.setFile_name(filename);
        openedFile.setFile_path(filePath);
        openedFile.setOpen_time(opentime);
        openedFile.setFile_size(filesize);
        //获取文件缩略图
        byte[] filethum = getFileThum(filePath);
        openedFile.setFile_thum(filethum);
        //检测数据库中是否有相同的文件，有就覆盖
        boolean isRepeat = checkRepeatFile(filePath);
        if (!isRepeat) {
            openedFilesDao.addOpenedFile(openedFile);
        }
    }*/
}
