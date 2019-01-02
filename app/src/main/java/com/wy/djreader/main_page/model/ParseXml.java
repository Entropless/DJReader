package com.wy.djreader.main_page.model;

import android.util.Xml;

import com.wy.djreader.model.entity.UpdateInfos;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

public class ParseXml {
    public static UpdateInfos getUpdateInfos(InputStream inputStream) {
        UpdateInfos updateInfos = new UpdateInfos();
        XmlPullParser parser = Xml.newPullParser();
        try {
            parser.setInput(inputStream,"utf-8");
            int type = parser.getEventType();
            while (XmlPullParser.END_DOCUMENT == type){
                switch (type){
                    case XmlPullParser.START_TAG:
                        String nodeName = parser.getName();
                        if ("versionCode".equals(nodeName)) {
                            updateInfos.setVersionCode(parser.nextText());// 获取版本号
                        } else if ("versionName".equals(nodeName)) {
                            updateInfos.setVersionName(parser.nextText());//获取版本名称
                        } else if ("url".equals(nodeName)) {
                            updateInfos.setAppUpdateUrl(parser.nextText());// 获取apk下载地址
                        } else if ("description".equals(nodeName)) {
                            updateInfos.setDescription(parser.nextText());//获取描述信息
                        }
                        break;
                }
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return updateInfos;
    }
}
