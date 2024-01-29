package org.dam.common.utils.comgress;

import org.dam.common.utils.FileUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @Author dam
 * @create 2024/1/28 18:00
 */
public class Bzip2CompressUtil {

    public static boolean compressFile(File sourceFile, String targetPath) {
        try {
            byte[] fileBytes = FileUtils.getFileBytes(sourceFile);
            byte[] compressedBytes = compress(fileBytes);
            FileUtils.outputFileBytes(compressedBytes, targetPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public static byte[] compress(byte srcBytes[]) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip;
        try {
            gzip = new GZIPOutputStream(out);
            gzip.write(srcBytes);
            gzip.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

    public static byte[] uncompress(byte[] bytes) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        try {
            GZIPInputStream ungzip = new GZIPInputStream(in);
            byte[] buffer = new byte[2048];
            int n;
            while ((n = ungzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return out.toByteArray();
    }

}
