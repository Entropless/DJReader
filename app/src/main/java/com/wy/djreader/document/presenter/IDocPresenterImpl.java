package com.wy.djreader.document.presenter;

import android.content.Context;
import android.view.View;

import com.wy.djreader.R;
import com.wy.djreader.model.Adapter.ReadFilesArrayAdapter;
import com.wy.djreader.model.dao.IHaveReadFiles;
import com.wy.djreader.model.dao.impl.HaveReadFilesImpl;
import com.wy.djreader.model.entity.HaveReadFilesSerializable;
import com.wy.djreader.document.presenter.ipresenter.IDocPresenter;
import com.wy.djreader.document.view.iview.IDocFragment;

import java.util.List;

public class IDocPresenterImpl implements IDocPresenter {

    private HaveReadFilesSerializable haveReadFiles = HaveReadFilesSerializable.getInstance();
    private IHaveReadFiles iHaveReadFiles = null;
    private List<HaveReadFilesSerializable> haveReadFilesList = null;
    private IDocFragment iDocFragment;
    private boolean mDualPune = false;

    //构造
    public IDocPresenterImpl(IDocFragment iDocFragment){
        this.iDocFragment = iDocFragment;
    }
    @Override
    public void getFileListInfo(Context context) {
        iHaveReadFiles = new HaveReadFilesImpl(context);
        haveReadFilesList = iHaveReadFiles.queryHaveReadFiles();
        if (haveReadFilesList.size() == 0 ){
            iDocFragment.showNullText();
        }
        ReadFilesArrayAdapter readFilesArrayAdapter = new ReadFilesArrayAdapter(context,R.layout.file_info,haveReadFilesList);
        iDocFragment.setFilesListAdapter(readFilesArrayAdapter);
    }

    @Override
    public boolean dualPuneJudge(Context context, View docFragment) {
        mDualPune = docFragment != null && docFragment.getVisibility() == View.VISIBLE;

        return mDualPune;
    }

    @Override
    public void display(int index) {
        //获取点击的文件路径
        String filePath = haveReadFilesList.get(index).getFile_path();
        if (mDualPune){
            iDocFragment.displayDoc(index,filePath);
        }else{
            iDocFragment.toDisplayDocActivity(filePath);
        }
    }
}
