package com.e9ab98e991ab.libcommon.library.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.e9ab98e991ab.libcommon.R;
import com.kongzue.dialog.v2.MessageDialog;

import java.util.concurrent.TimeUnit;

/**
 * @author gaoxin 18-12-4 上午11:44
 * @version V1.0.0
 * @name DialogUtils
 */
public class DialogUtils {

    /***
     * 版本判断
     */
    public static void versionJudge(Context mContext,String msgTitle, String message,String buttonCaption){
        int version = android.os.Build.VERSION.SDK_INT;
        if (version < 23) {
            MessageDialog.show(mContext, msgTitle, message,buttonCaption, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    System.exit(0);
                }
            });
            return;
        }
    }

}
