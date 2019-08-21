package com.e9ab98e991ab.libcommon.utils;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

import java.io.File;


public class FileUtil {

    public static File getCacheRootFile(Context context) {
        File cacheRootDir = null;
        // 判断sd卡是否存在
        String folder = "Recorder";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            // 获取根目录
            cacheRootDir = new File(Environment.getExternalStorageDirectory() + "/" + context.getPackageName(), folder);
        } else {
            cacheRootDir = new File(context.getPackageName(), folder);
        }
        if (!cacheRootDir.exists()) {
            // 如果路径不存在就先创建路径
            cacheRootDir.mkdirs();
        }
        return cacheRootDir;
    }

    public static void deleteDirWihtFile(File dir) {
        if (dir == null || !dir.exists() || !dir.isDirectory()) {
            return;
        }
        for (File file : dir.listFiles()) {
            if (file.isFile())
            {
                // 删除所有文件
                file.delete();
            } else if (file.isDirectory())
            {
                // 递规的方式删除文件夹
                deleteDirWihtFile(file);
            }
        }
        // 删除目录本身
        dir.delete();
    }

    // 生成文件
    public static File makeFilePath(String filePath, String fileName) {
        File file = null;
        makeRootDirectory(filePath);
        try {
            file = new File(filePath, fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    // 生成文件夹
    public static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {
            Log.i("error:", e + "");
        }
    }

    /**
     * 检测录音设备是否被占用
     * @return s
     */
    public static boolean validateMicAvailability() {
        Boolean available = true;
        AudioRecord recorder = new AudioRecord(MediaRecorder.AudioSource.MIC, 44100,
                        AudioFormat.CHANNEL_IN_MONO,
                        AudioFormat.ENCODING_DEFAULT, 44100);
        try {
            if (recorder.getRecordingState() != AudioRecord.RECORDSTATE_STOPPED) {
                available = false;
            }
            recorder.startRecording();
            if (recorder.getRecordingState() != AudioRecord.RECORDSTATE_RECORDING) {
                recorder.stop();
                available = false;

            }
            recorder.stop();
        } finally {
            recorder.release();
            recorder = null;
        }

        return available;
    }
}
