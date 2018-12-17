package com.wy.djreader.model.dao;

import com.wy.djreader.model.entity.HaveReadFilesSerializable;

import java.util.List;

public interface IHaveReadFiles {

	void addOpenedFile(HaveReadFilesSerializable haveReadFiles);//增
	void deleteOpenedFile();//删
	void updateOpenedFile(HaveReadFilesSerializable haveReadFiles);//改
	List<HaveReadFilesSerializable> queryHaveReadFiles();//查
}
