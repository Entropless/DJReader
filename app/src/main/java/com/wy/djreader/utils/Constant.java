package com.wy.djreader.utils;

import android.graphics.Color;
import android.os.Environment;

import java.io.File;

public interface Constant {
	String USER_ID = "USER_ID";
	String USER_ID_DEFV = "HWSEALDEMO";
	String ROOT_PATH = Environment.getExternalStorageDirectory() + File.separator;
	String DOWNLOAD_PATH = Constant.ROOT_PATH + "Download/com.dianju.djreader/apk/";
	String OPENED_FILE = "opened_file";
	
	public static final String PROJECT_PATH = Environment.getExternalStorageDirectory()+File.separator+"dianju";
	public static final String TEMP_DIR = PROJECT_PATH + File.separator +"temp_files";
	public static final String OPEN_PATH = "OPEN_PATH";//打开文件列表路径
	public static final String OPEN_PATH_DEFAULT = PROJECT_PATH;//文件列表路径
	public static final String HAVEREADFILE_PATH_KEY = "read_key";
	
	public static final String SELECT_SEL_PATH = "SELECT_SEL_PATH";//选择印章路径
	public static final String SELECT_SEL_PATH_DEF = PROJECT_PATH;//印章列表路径
	
	//证书
	public static final String PFX_PATH = PROJECT_PATH+File.separator+"123456.pfx";
	public static final String PFX_PWD = "123456";
	
	public static final String FONTS_PATH = PROJECT_PATH+File.separator+"fonts"+File.separator;
	
	public static final String SET_WORDSIZE = "SET_WORDSIZE";//文字批注-文字大小设置
	public static final int SET_WORDSIZE_DEF = 8;//文字批注-文字大小默认8
	public static final String SET_WORDCOLOR = "SET_WORDCOLOR";//文字批注-文字颜色设置
	public static final int SET_WORDCOLOR_DEF = Color.BLACK;//文字批注-文字颜色默认黑色
	public static final String SET_HANDSIZE = "SET_WORDSIZE";//文字批注-文字大小设置
	public static final int SET_HANDSIZE_DEF = 8;//文字批注-文字大小默认8
	public static final String SET_HANDCOLOR = "SET_WORDCOLOR";//文字批注-文字颜色设置
	public static final int SET_HANDCOLOR_DEF = Color.BLACK;//文字批注-文字颜色默认黑色
	public static final String SET_HAND = "SET_HAND";//手触设置
	public static final int SET_HAND_DEF = 1;//默认支持手写
	public static final String READ_TYPE = "READ_TYPE";//阅读方式
	public static final int READ_TYPE_DEF = 1;//上下连续
	
	public static final int MAXWORDSIZE = 100;//默认支持手写
	public static final int MAXHANDSIZE = 100;//默认支持手写
	
	interface RequestCode {
		int PERMISSIONS_RESULT = 1;//权限检查结果
		int NEW_OPEN = 3;//打开文件
		int FRESH_LIST = 4;//刷新阅读记录
		int SELECT_SEL = 5;//选择印章
		int SET_HAND_ATTR = 6;//设置手写属性
	}
	interface Flag{
		int DOWN_ST = 1;
		int DOWN_ING = 99;
		int DOWN_OK = 100;
		int DOWN_ERROR = -100;
		int UPDATE_CLIENT = 1;
		int NOT_UPDATE = -1;
	}
	interface DB{
		String DB_NAME = "dj_reader.db";
		int DB_VERSION = 1;
	}
	interface AutoHandleType {
		String TEXT_ANNOTATION = "TEXT_ANNOTATION";//文字批注
	}
	interface CountDown{
		int BACKGROUND_COLOR = 0x50555555;
	    float BORDER_WIDTH = 15f;
	    int BORDER_COLOR = 0xFF6ADBFE;
	    float TEXT_SIZE = 50f;
	    int TEXT_COLOR = 0xFFFFFFFF;
	    int countDownTime = 5000;//倒计时时间
		int freshPadding = 100;//刷新间隔
	}
	interface TopMenu {
		int READ = 1;
		int TEXT = 2;
		int HADN = 3;
		int SEAL = 4;
	}
	//权限常量值
	interface PermissionConstant {
		String READ_EXTERNAL_STORAGE = "android.permission.READ_EXTERNAL_STORAGE";
		String WRITE_EXTERNAL_STORAGE = "android.permission.WRITE_EXTERNAL_STORAGE";
		String READ_PHONE_STATE = "android.permission.READ_PHONE_STATE";
	}
}
