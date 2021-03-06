package org.tianjyan.pwd.application;

import org.apache.http.util.EncodingUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class IOHelper {

    public static String readSDFile(String fileName) throws IOException {

        File file = new File(fileName);

        FileInputStream fis = new FileInputStream(file);

        int length = fis.available();

        byte [] buffer = new byte[length];
        fis.read(buffer);

        String res = EncodingUtils.getString(buffer, "UTF-8");

        fis.close();
        return res;
    }


    public static void writeSDFile(String fileName, String writeStr) throws IOException{

        File file = new File(fileName);

        FileOutputStream fos = new FileOutputStream(file);

        byte [] bytes = writeStr.getBytes();

        fos.write(bytes);

        fos.close();
    }

    public static  void deleteSDFile(String fileName){
        File file = new File(fileName);
        file.delete();
    }
}
