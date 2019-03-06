package com.dianju_wy.showpdf.util;

//翻页模式，单页和多页
public enum PageMode {
	SinglePage(0),MultiPage(1);
	private int value;
	
	private PageMode(int pagemode){
		value=pagemode;
	}
	
	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	} 
	
}