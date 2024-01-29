package org.dam.common.utils.comgress;

import org.anarres.lzo.*;
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
public class LzoCompressUtil {

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

    public static byte[] compress(byte srcBytes[]) throws IOException {
        LzoCompressor compressor = LzoLibrary.getInstance().newCompressor(
                LzoAlgorithm.LZO1X, null);

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        LzoOutputStream cs = new LzoOutputStream(os, compressor);
        cs.write(srcBytes);
        cs.close();

        return os.toByteArray();
    }

    public static byte[] uncompress(byte[] bytes) throws IOException {
        LzoDecompressor decompressor = LzoLibrary.getInstance()
                .newDecompressor(LzoAlgorithm.LZO1X, null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ByteArrayInputStream is = new ByteArrayInputStream(bytes);
        LzoInputStream us = new LzoInputStream(is, decompressor);
        int count;
        byte[] buffer = new byte[2048];
        while ((count = us.read(buffer)) != -1) {
            baos.write(buffer, 0, count);
        }
        return baos.toByteArray();
    }


}
