package org.dam;

import org.dam.common.utils.comgress.Lz4CompressUtil;
import org.dam.common.utils.comgress.SnappyCompressUtil;
import org.junit.Test;

import java.io.File;

/**
 * @Author dam
 * @create 2024/1/28 19:51
 */
public class CompressTest {

    @Test
    public void bigFileTest() {
        try {
            String sourceFilePath = "/Users/dam/File/备份测试/source/Counting Stars.mp4";
            String targetFilePath = "/Users/dam/File/备份测试/target/Counting Stars.zip";

            long start = System.currentTimeMillis();

            /*
             Java heap space
             */
//            DeflateCompressUtil.compressFile(new File(sourceFilePath), targetFilePath);

            /*
                压缩前：1022 MB
                压缩后：1018 MB
                压缩程度：0.3384 %
                压缩时间：25728ms
             */
//            ZipUtil.compressToFile(new File(sourceFilePath), targetFilePath);

            /*
             Java heap space
             */
//            GzipCompressUtil.compressFile(new File(sourceFilePath), targetFilePath);

            /**
             * Java heap space
             */
            SnappyCompressUtil.compressFile(new File(sourceFilePath), targetFilePath);

            File sourceFile = new File(sourceFilePath);
            File targetFile = new File(targetFilePath);
            System.out.println("压缩前：" + sourceFile.length() / (1024 * 1024) + " MB");
            System.out.println("压缩后：" + targetFile.length() / (1024 * 1024) + " MB");
            System.out.println("压缩程度：" + String.format("%.4f", (1 - (targetFile.length() * 1.0 / sourceFile.length())) * 100) + " %");
            System.out.println("压缩时间：" + (System.currentTimeMillis() - start) + "ms");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void smallFileTest() {
        try {
            String sourceFilePath = "/Users/dam/File/备份测试/source/word文档.doc";
            String targetFilePath = "/Users/dam/File/备份测试/target/word文档.zip";

            long start = System.currentTimeMillis();

            /**
             * 压缩前：2195 kb
             * 压缩后：1155 kb
             * 压缩程度：47.3604 %
             * 压缩时间：26ms
             */
//            DeflateCompressUtil.compressFile(new File(sourceFilePath), targetFilePath);

            /**
             * 压缩前：2195 kb
             * 压缩后：1130 kb
             * 压缩程度：48.5075 %
             * 压缩时间：50ms
             */
//            ZipUtil.compressToFile(new File(sourceFilePath), targetFilePath);

            /**
             * 压缩前：2195 kb
             * 压缩后：1130 kb
             * 压缩程度：48.5130 %
             * 压缩时间：42ms
             */
//            GzipCompressUtil.compressFile(new File(sourceFilePath), targetFilePath);

            /**
             * 压缩前：2195 kb
             * 压缩后：1287 kb
             * 压缩程度：41.3663 %
             * 压缩时间：109ms
             */
//            SnappyCompressUtil.compressFile(new File(sourceFilePath), targetFilePath);

            /**
             * 压缩前：2195 kb
             * 压缩后：1130 kb
             * 压缩程度：48.5130 %
             * 压缩时间：42ms
             */
//            Bzip2CompressUtil.compressFile(new File(sourceFilePath), targetFilePath);

            /**
             * 压缩前：2195 kb
             * 压缩后：1315 kb
             * 压缩程度：40.0924 %
             * 压缩时间：57ms
             */
//            LzoCompressUtil.compressFile(new File(sourceFilePath), targetFilePath);

            /**
             * 压缩前：2195 kb
             * 压缩后：1509 kb
             * 压缩程度：31.2273 %
             * 压缩时间：60ms
             */
            Lz4CompressUtil.compressFile(new File(sourceFilePath), targetFilePath);

            File sourceFile = new File(sourceFilePath);
            File targetFile = new File(targetFilePath);
            System.out.println("压缩前：" + sourceFile.length() / (1024) + " kb");
            System.out.println("压缩后：" + targetFile.length() / (1024) + " kb");
            System.out.println("压缩程度：" + String.format("%.4f", (1 - (targetFile.length() * 1.0 / sourceFile.length())) * 100) + " %");
            System.out.println("压缩时间：" + (System.currentTimeMillis() - start) + "ms");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
