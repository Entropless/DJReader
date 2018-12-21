package com.wy.djreader.file_manager.view.iview;


import com.wy.djreader.model.Adapter.ReadFilesArrayAdapter;
import com.wy.djreader.model.entity.HaveReadFilesSerializable;

public interface IDocFragment {

    //配置ListAdapter
    void setFilesListAdapter(ReadFilesArrayAdapter readFilesArrayAdapter);
    //双窗口展示文件
    void displayDoc(int index, String filePath);
    //启动新activity展示文件
    void toDisplayDocActivity(String filePath);
}
