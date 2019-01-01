package com.wy.djreader.showdoc.presenter;

import android.content.Context;
import android.util.Log;

import com.dianju.showpdf.DJContentView;
import com.wy.djreader.model.dao.IHaveReadFiles;
import com.wy.djreader.model.dao.impl.HaveReadFilesImpl;
import com.wy.djreader.model.entity.HaveReadFilesSerializable;
import com.wy.djreader.showdoc.ShowDocContract;
import com.wy.djreader.showdoc.model_doc.IModelDoc;
import com.wy.djreader.showdoc.model_doc.IModelDocImpl;
import com.wy.djreader.utils.singleton.SingleDJContentView;

import java.util.ArrayList;
import java.util.List;

public class DisplayDocPresenter implements ShowDocContract.Presenter {

    private ShowDocContract.View view;
    private Context context;
    private String filePath = "";
    private int openRes = -1;
    private IModelDoc iModelDoc;
    private IHaveReadFiles iHaveReadFiles;
    private HaveReadFilesSerializable haveReadFiles;

    //Presenter构造方法，M V P三层的连接点
    public DisplayDocPresenter(ShowDocContract.View view,Context context) {
        this.view = view;
        this.iModelDoc = new IModelDocImpl();
        this.context = context;
    }

    @Override
    public int loadingDoc(String filePath, DJContentView contentView) {
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
    public void recordReadFilesInfos(String filePath) {
        iModelDoc.addFileInfosToDb(context,filePath);
        iHaveReadFiles = new HaveReadFilesImpl(context);
        haveReadFiles = HaveReadFilesSerializable.getInstance();
        //检查数据库中是否有相同的文件,有就覆盖
        boolean isRepeat = checkRepeatFile(filePath);
        if (!isRepeat) {
            iHaveReadFiles.addOpenedFile(haveReadFiles);
        }
    }

    private boolean checkRepeatFile(String filePath) {
        //
        List<HaveReadFilesSerializable> filesInfos = new ArrayList<>();
        filesInfos = iHaveReadFiles.queryHaveReadFiles();
        for (int i = 0; i < filesInfos.size(); i++) {
            String fpath = filesInfos.get(i).getFile_path();
            String id = filesInfos.get(i).getId();
            haveReadFiles.setId(id);
            if (filePath.equals(fpath)) {
                //修改重复文件的文件信息
                iHaveReadFiles.updateOpenedFile(haveReadFiles);
                return true;
            }
        }
        return false;
    }
}
