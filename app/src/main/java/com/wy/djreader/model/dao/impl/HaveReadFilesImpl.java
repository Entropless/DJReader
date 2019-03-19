package com.wy.djreader.model.dao.impl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wy.djreader.model.dao.IHaveReadFiles;
import com.wy.djreader.model.dao.db.MySQLiteOpenHelper;
import com.wy.djreader.model.entity.HaveReadFilesSerializable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HaveReadFilesImpl implements IHaveReadFiles {

	private SQLiteDatabase writableDatabase;
	private SQLiteDatabase readableDatabase;
	private Context context;
	private MySQLiteOpenHelper mSqlite = null;
	private String id,file_name,file_path,open_time,file_size;
	private byte[] file_thum;
	private String currTime = "";//当前时间
	private String beforeofHebdomad = "";//一周之前的时间
	
	public HaveReadFilesImpl(Context context) {
		super();
		this.context = context;
		mSqlite = new MySQLiteOpenHelper(context);
		writableDatabase = mSqlite.getWritableDatabase();//磁盘没有空间之后，使用此对象会报错
		readableDatabase = mSqlite.getReadableDatabase();//磁盘没有空间之后，可以做查询，不会报错(磁盘未满增删改查都可以)
	}


	@Override
	public void addOpenedFile(HaveReadFilesSerializable haveReadFiles) {
		// 执行增加sql语句
		String addSql = "insert into tbl_opened_file (file_name,file_path,open_time,file_size,file_thum) values (?,?,?,?,?)";
		writableDatabase.execSQL(addSql, new Object[] {haveReadFiles.getFile_name(),haveReadFiles.getFile_path(),
				haveReadFiles.getDisplay_time(),haveReadFiles.getFile_size(),haveReadFiles.getFile_thum()});
	}


	/**
	 * 删除超过一周的文件
	 */
	@SuppressLint("NewApi")
	@Override
	public void deleteOpenedFile() {
		//获取当前系统时间和七天前的时间
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = new Date(System.currentTimeMillis());
		currTime = dateFormat.format(date);
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -7);
		beforeofHebdomad = dateFormat.format(calendar.getTime());
		//获取每个文件的时间，判断是否符合时间要求
		List<HaveReadFilesSerializable> filesInfos = new ArrayList<HaveReadFilesSerializable>();
		IHaveReadFiles iHaveReadFiles = new HaveReadFilesImpl(context);
		filesInfos = iHaveReadFiles.queryHaveReadFiles();
		StringBuffer staleData = new StringBuffer();
		for (int i = 0; i < filesInfos.size(); i++) {
			String filetime = filesInfos.get(i).getDisplay_time();
			Date fTime = null;
			Date limitTime = null;
			try {
				fTime = (Date) dateFormat.parse(filetime);
				limitTime = (Date) dateFormat.parse(beforeofHebdomad);
				if (fTime.getTime() < limitTime.getTime()) {//删除超过一周的文件
					//获取符合条件的数据主键
					String id = filesInfos.get(i).getId();
					staleData.append(id).append(",");
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (staleData.length() > 0) {
			//计算占位符个数
			staleData.deleteCharAt(staleData.length() - 1);
			String [] staleDatas = staleData.toString().split(",");
			StringBuffer placeholer = new StringBuffer();
			for (int i = 0; i < staleDatas.length; i++) {
				placeholer.append("?").append(",");
			}
			placeholer.deleteCharAt(placeholer.length() - 1);
			String deleteSql = "delete from tbl_opened_file where id in (" + placeholer +")";
			writableDatabase.execSQL(deleteSql,staleDatas);
		}
	}


	@Override
	public void updateOpenedFile(HaveReadFilesSerializable haveReadFiles) {
		// 修改数据
		String updateSql = "update tbl_opened_file set open_time = ?,file_size = ?,file_thum = ? where id = ?";
		writableDatabase.execSQL(updateSql, new Object[]{haveReadFiles.getDisplay_time(),haveReadFiles.getFile_size(),haveReadFiles.getFile_thum(),haveReadFiles.getId()});
	}


	/**
	 * 读取最近打开过得文件
	 */
	@Override
	public List<HaveReadFilesSerializable> queryHaveReadFiles() {
		// 读取数据库中文件信息
		String readSql = "select * from tbl_opened_file";
		Cursor cursor = readableDatabase.rawQuery(readSql, null);
		HaveReadFilesSerializable haveReadFiles = HaveReadFilesSerializable.getInstance();
		List<HaveReadFilesSerializable> filesInfos = new ArrayList<HaveReadFilesSerializable>();
		while (cursor.moveToNext()) {
			id = cursor.getString(cursor.getColumnIndex("id"));
			file_name = cursor.getString(cursor.getColumnIndex("file_name"));
			file_path = cursor.getString(cursor.getColumnIndex("file_path"));
			open_time = cursor.getString(cursor.getColumnIndex("open_time"));
			file_size = cursor.getString(cursor.getColumnIndex("file_size"));
			file_thum = cursor.getBlob(cursor.getColumnIndex("file_thum"));
			haveReadFiles = new HaveReadFilesSerializable(id,file_name, file_path, open_time, file_size, file_thum);
//			haveReadFiles.setId(id);
//			haveReadFiles.setFile_name(file_name);
//			haveReadFiles.setFile_path(file_path);
//			haveReadFiles.setDisplay_time(open_time);
//			haveReadFiles.setFile_size(file_size);
//			haveReadFiles.setFile_thum(file_thum);
			filesInfos.add(haveReadFiles);
		}
		return filesInfos;
	}
}
