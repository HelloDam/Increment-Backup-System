package org.dam.common.utils.comgress;

import net.jpountz.lz4.*;
import org.anarres.lzo.*;
import org.dam.common.utils.FileUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * @Author dam
 * @create 2024/1/28 18:00
 */
public class Lz4CompressUtil {

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
        LZ4Factory factory = LZ4Factory.fastestInstance();
        ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
        LZ4Compressor compressor = factory.fastCompressor();
        LZ4BlockOutputStream compressedOutput = new LZ4BlockOutputStream(
                byteOutput, 2048, compressor);
        compressedOutput.write(srcBytes);
        compressedOutput.close();
        return byteOutput.toByteArray();
    }

    public static byte[] uncompress(byte[] bytes) throws IOException {
        LZ4Factory factory = LZ4Factory.fastestInstance();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        LZ4FastDecompressor decompresser = factory.fastDecompressor();
        LZ4BlockInputStream lzis = new LZ4BlockInputStream(
                new ByteArrayInputStream(bytes), decompresser);
        int count;
        byte[] buffer = new byte[2048];
        while ((count = lzis.read(buffer)) != -1) {
            baos.write(buffer, 0, count);
        }
        lzis.close();
        return baos.toByteArray();
    }


}
