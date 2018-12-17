package com.wy.djreader.model.dao.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.wy.djreader.utils.Constant;


public class MySQLiteOpenHelper extends SQLiteOpenHelper{

	private Context context;
	
	public MySQLiteOpenHelper(Context context) {
		super(context, Constant.DB.DB_NAME, null, Constant.DB.DB_VERSION);
		this.context = context;
	}

	/**
	 * 创建数据库
	 * @param db
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// 创建表
		db.execSQL("create table tbl_opened_file(id integer primary key autoincrement,file_name,"
				+ "file_path,open_time,file_size,file_thum BLOB)");
	}

	/**
	 * 数据库升级
	 * @param db
	 * @param oldVersion
	 * @param newVersion
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
