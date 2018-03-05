package com.sky.app.ui.activity.ask;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by Administrator on 2018/1/16 0016.
 */

public class RWFile {
    /**

     * 判断sdcard是否存在

     *

     * @return

     */

    public static boolean isSdcard() {

        String status = Environment.getExternalStorageState();

        if (status.equals(Environment.MEDIA_MOUNTED)) {

            return true;

        } else {

            return false;

        }

    }

    /**

     * 读取文件内容

     *

     * @param filePathAndName

     * @return

     */

    public static String readFile(String filePathAndName) {

        String fileContent = null;

        try {

            File f = new File(filePathAndName);

            if (f.isFile() && f.exists()) {

                fileContent = "";

                InputStreamReader read = new InputStreamReader(

                        new FileInputStream(f), "UTF-8");

                BufferedReader reader = new BufferedReader(read);

                String line;

                while ((line = reader.readLine()) != null) {

                    fileContent += line;

                }

                read.close();

            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        }

        return fileContent;

    }

    /**

     * 写入文件内容

     *

     * @param filePathAndName

     * @param fileContent

     */

    public static boolean writeFile(String filePathAndName, String fileContent) {

        try {

            File f = new File(filePathAndName);

            if (!f.exists()) {

                f.createNewFile();

            }

// 覆盖文件

            OutputStreamWriter write = new OutputStreamWriter(

                    new FileOutputStream(f), "UTF-8");// 覆盖文件

// 追加文件

// OutputStreamWriter write = new OutputStreamWriter(

// new FileOutputStream(f, true), "UTF-8"); // 追加文件

            BufferedWriter writer = new BufferedWriter(write);

            writer.write(fileContent);

            writer.close();

        } catch (Exception e) {

            e.printStackTrace();

            return false;

        }

        return true;

    }

}