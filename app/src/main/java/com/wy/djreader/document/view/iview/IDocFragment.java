package com.wy.djreader.document.view.iview;


import com.wy.djreader.model.Adapter.ReadFilesArrayAdapter;

public interface IDocFragment {

    //配置ListAdapter
    void setFilesListAdapter(ReadFilesArrayAdapter readFilesArrayAdapter);
    //双窗口展示文件
    void displayDoc(int index, String filePath);
    //启动新activity展示文件
    void toDisplayDocActivity(String filePath);
}
