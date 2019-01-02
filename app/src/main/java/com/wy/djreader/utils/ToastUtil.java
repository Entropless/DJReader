package com.wy.djreader.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {

    public static int LOGN = Toast.LENGTH_LONG;
    public static int SHORT = Toast.LENGTH_LONG;
    public static void toastMessage(Context context,String message, int showTime){
        Toast.makeText(context,message,showTime).show();
    }
}
