package com.wy.djreader.showdoc.presenter;

import android.content.Context;
import android.util.Log;

import com.wy.djreader.model.dao.IHaveReadFiles;
import com.wy.djreader.model.dao.impl.HaveReadFilesImpl;
import com.wy.djreader.model.entity.HaveReadFilesSerializable;
import com.wy.djreader.showdoc.ShowDocContract;
import com.wy.djreader.showdoc.model_doc.IModelDoc;
import com.wy.djreader.showdoc.model_doc.IModelDocImpl;
import com.wy.djreader.utils.Singleton.SingleDJContentView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DisplayDocPresenter implements ShowDocContract.Presenter {

    private ShowDocContract.View view;
    private Context context;
    private String filePath = "";
    private int openRes = -1;
    private IModelDoc iModelDoc;
    private IHaveReadFiles iHaveReadFiles;
    private HaveReadFilesSerializable haveReadFiles;

    //Presenter构造方法，M V P三层的连接点
    public DisplayDocPresenter(ShowDocContract.View view) {
        this.view = view;
        this.iModelDoc = new IModelDocImpl();
    }

    @Override
    public int loadingDoc(String filePath, SingleDJContentView contentView) {
        if (filePath.endsWith(".pdf")){
            openRes = contentView.openFile(filePath);
        }else if (filePath.endsWith(".aip")){
            openRes = contentView.openTempFile(filePath);
        }
        Log.d("wy_openRes",openRes+"");
        //登录

        return openRes;
    }

    @Override
    public void recordReadFilesInfos(Context context, String filePath) {
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
//        byte[] filethumb = getFileThumb(filePath);
//        openedFile.setFile_thum(filethum);
//        //检测数据库中是否有相同的文件，有就覆盖
//        boolean isRepeat = checkRepeatFile(filePath);
//        if (!isRepeat) {
//            openedFilesDao.addOpenedFile(openedFile);
//        }
    }

    @Override
    public byte[] getFileThumb(String filePath) {
        byte[] thumb = null;
//        DJSOInterface djsoInterface = new DJSOInterface();
//        if(filePath.endsWith(".aip")) {
//            djsoInterface.openTempFile(filePath);
//        } else if(filePath.endsWith(".pdf")) {
//            djsoInterface.openFile(filePath);
//        } else if(filePath.endsWith(".ofd")) {
//            djsoInterface.openTempFile(filePath);
//        }
//        Bitmap bitmap = util.getBitmap(0, 150, 150, 0);
//        util.saveFile("");
//        ByteArrayOutputStream bout = new ByteArrayOutputStream();
//        bitmap.compress(CompressFormat.PNG, 10, bout);
//        thum = bout.toByteArray();
        return thumb;
    }
}
