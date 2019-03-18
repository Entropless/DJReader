package com.wy.djreader.document.view;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.wy.djreader.R;
import com.wy.djreader.document.model.adapter.MyAdapter;
import com.wy.djreader.utils.DialogUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MyFileManager extends ListActivity {
	
	/*public static final String SET_PATH = "SET_PATH";//设置路径(只显示目录)
	public static final String OPEN_FILE = "OPEN_FILE";//(显示目录和(aip/pdf/all)，默认显示all)
	public static final String SAVE_AS = "SAVE_AS";*/
	public static final String BASIC_PATH = Environment.getExternalStorageDirectory()+"";
	private static final String rootPath = BASIC_PATH;
	private String type = "0";//0-打开文件/1-打开目录/2-保存
	private String dir = Environment.getExternalStorageDirectory()+"";//操作的目录
	private String showType = "0";//0-all;1-pdf;2-aip;3-ofd(如果operateType是打开文件loadFileType有效)
	/*
	
	
	
	private String type = "";
	private int showType = 0;//显示文件的类型 0-所有文件;1-pdf文件;2-aip文件;3-ofd
*/	private List<String> items = null;
	private List<String> paths = null;
	/*private static final String rootPath = BASIC_PATH;//= "/";
	private String oldRootPath;*/
	private TextView dirT,fileNameT,typeT;
	private EditText fileNameE;
	private Spinner typeS;
	private Button btnConfirm,btnCancel;
	private String currPath;
	List<String> data_list;
	private Context context;
	//private SharedPreferences myPrefs;
	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.myfileselect);
		this.context = this;
		type = getIntent().getStringExtra("type");
		dir = getIntent().getStringExtra("dir");
		if(TextUtils.isEmpty(type)) {
			type = "0";
		}
		if(TextUtils.isEmpty(dir)) {
			dir = Environment.getExternalStorageDirectory()+"";
		}
		showType = "0";
		dirT = (TextView) findViewById(R.id.dirT);
		fileNameT=(TextView)findViewById(R.id.fileNameT);
		typeT=(TextView)findViewById(R.id.typeT);
		fileNameE=(EditText)findViewById(R.id.fileNameE);
		typeS = (Spinner) findViewById(R.id.typeS);
		btnConfirm = (Button) findViewById(R.id.btnConfirm);
		btnCancel = (Button) findViewById(R.id.btnCancel);
		btnCancel.setText("取消");
		data_list = new ArrayList<String>();
	    data_list.add("All Files(*.*)");
	    data_list.add("PDF(*.pdf)");
	    data_list.add("AIP(*.aip)");
	    data_list.add("OFD(*.ofd)");
	    ArrayAdapter<String> arr_adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data_list);
	    //加载适配器
	    typeS.setAdapter(arr_adapter);
		if("0".equals(type)) {//打开文件
			btnConfirm.setText("打开");
			fileNameT.setText("文件名(N):");
			typeS.setSelection(0,true);
			typeS.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
					String con = data_list.get(position);
					if(con.startsWith("All")) {
						showType = "0";
						getFileDir(currPath,showType);
					} else if(con.startsWith("PDF")) {
						showType = "1";
						getFileDir(currPath,showType);
					} else if(con.startsWith("AIP")) {
						showType = "2";
						getFileDir(currPath,showType);
					} else if(con.startsWith("OFD")) {
						showType = "3";
						getFileDir(currPath,showType);
					} else {};
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					// TODO Auto-generated method stub
					
				}
			});
		} else if("1".equals(type)) {//设置目录
			btnConfirm.setText("确定");
			fileNameT.setText("选定目录:");
			fileNameE.setEnabled(false);
			typeS.setSelection(0,true);
			typeS.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
					String con = data_list.get(position);
					if(con.startsWith("All")) {
						showType = "0";
						getFileDir(currPath,showType);
					} else if(con.startsWith("PDF")) {
						showType = "1";
						getFileDir(currPath,showType);
					} else if(con.startsWith("AIP")) {
						showType = "2";
						getFileDir(currPath,showType);
					} else if(con.startsWith("OFD")) {
						showType = "3";
						getFileDir(currPath,showType);
					} else {};
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					// TODO Auto-generated method stub
					
				}
			});
		} else {//保存
			btnConfirm.setText("保存");
			fileNameT.setText("文件名(N):");
			typeS.setSelection(2,true);
			fileNameE.setText("newfile.aip");
			typeS.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
					String con = data_list.get(position);
					String fn = fileNameE.getText().toString();
					if(con.startsWith("All")) {
						
					} else if(con.startsWith("PDF")) {
						fileNameE.setText(addSuffix(fn, ".pdf"));
					} else if(con.startsWith("AIP")) {
						fileNameE.setText(addSuffix(fn, ".aip"));
					} else if(con.startsWith("OFD")) {
						fileNameE.setText(addSuffix(fn, ".ofd"));
					} else {};
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					// TODO Auto-generated method stub
					
				}
			});
		}
		getFileDir(dir,showType);
		
		btnConfirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String fileName = fileNameE.getText().toString();
				if("0".equals(type) || "2".equals(type)) {
					if(TextUtils.isEmpty(fileName)) {
						Toast.makeText(MyFileManager.this, "文件名不能为空", Toast.LENGTH_LONG).show();
						return;
					}
				}
				if("2".equals(type)) {
					if(!fileName.contains(".")) {
						int i = typeS.getSelectedItemPosition();
						if(i==1) {
							fileName += ".pdf";
						} else if(i==2) {
							fileName += ".aip";
						} else if(i==3) {
							fileName += ".ofd";
						}
					}
					//判断文件是否已存在
					File file = new File(currPath+File.separator+fileName);
					if(file.exists() && file.isFile()) {//文件已存在，确认覆盖
						final String tempFileName = fileName;
						Bundle msg = new Bundle();
						msg.putString("title","提示");
						msg.putString("message","同名文件已存在,确认覆盖?");
						msg.putString("positive","确定");
						msg.putString("negative","取消");
						DialogInterface.OnClickListener positiveListener = (dialog, which) -> {
							Intent data = new Intent();
							Bundle bundle = new Bundle();
							bundle.putString("savePath", currPath);
							bundle.putString("fileName", tempFileName);
							data.putExtras(bundle);
							setResult(1, data);
							dialog.dismiss();
							finish();
						};
						//lambda表达式
						DialogInterface.OnClickListener negativeListener = (dialog, which) -> dialog.dismiss();
						DialogUtil.showDialog(context, msg, false, null, positiveListener, negativeListener);
					} else {
						Intent data = new Intent();
						Bundle bundle = new Bundle();
						bundle.putString("savePath", currPath);
						bundle.putString("fileName", fileName);
						data.putExtras(bundle);
						setResult(1, data);
						finish();
					}
				}
				Intent data = new Intent();
				Bundle bundle = new Bundle();
				bundle.putString("savePath", currPath);
				bundle.putString("fileName", fileName);
				data.putExtras(bundle);
				setResult(1, data);
				finish();
			}
		});
		
		btnCancel.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				finish();
			}
		});
	}

	private void getFileDir(String filePath, String showType) {
		String suffix = "";
		if("1".equals(showType)) {
			suffix = ".pdf";
		} else if("2".equals(showType)) {
			suffix = ".aip";
		} else if("3".equals(showType)) {
			suffix = ".ofd";
		} else {}
		try {
			currPath = filePath;
			String path = filePath.replace(BASIC_PATH, "存储设备");
			dirT.setText(path);
			if("1".equals(type)) {
				fileNameE.setText(path);
			}
			items = new ArrayList<String>();
			paths = new ArrayList<String>();
			File f = new File(filePath);
			File[] files = f.listFiles();

			if (!filePath.equals(rootPath)) {
				items.add("b1");
				paths.add(rootPath);
				items.add("b2");
				paths.add(f.getParent());
			}
			for (int i = 0; i < files.length; i++) {
				File file = files[i];
				String filename = file.getName();
				if(TextUtils.isEmpty(suffix)) {
					items.add(file.getName());
					paths.add(file.getPath());
				} else {
					if (filename.endsWith(suffix) || file.isDirectory()) {
						items.add(file.getName());
						paths.add(file.getPath());
					}
				}
			}
		} catch (Exception e) {

		}
		setListAdapter(new MyAdapter(this, items, paths));
	}
	
	private String addSuffix(String con, String suffix) {
		if(TextUtils.isEmpty(con)) con = "newfile.aip";
		if(!con.contains(".")) {
			return (con+suffix);
		}
		if(con.endsWith(suffix)) {
			return con;
		}
		int i = con.lastIndexOf(".");
		if(i<=0) return "";
		return (con.substring(0, i)+suffix);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		File file = new File(paths.get(position));
		if("0".equals(type)) {
			if (file.isDirectory()) {
				fileNameE.setText("");
				getFileDir(paths.get(position),showType);
			} else {
				fileNameE.setText(file.getName());
			}
		} else if("1".equals(type)) {
			if (file.isDirectory()) {
				getFileDir(paths.get(position),showType);
				fileNameE.setText(dirT.getText().toString());
			} else {
				//fileNameE.setText(file.getName());
			}
		} else if("2".equals(type)) {
			if (file.isDirectory()) {
				getFileDir(paths.get(position),showType);
			} else {
				//fileNameE.setText(file.getName());
			}
		} else {}
		
	}

}