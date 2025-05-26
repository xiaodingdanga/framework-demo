package com.lx.framework.demo1.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author xin.liu
 * @description TODO
 * @date 2025-05-12  16:37
 * @Version 1.0
 */
public class CompressionUtils {

    /**
     * 压缩字节数组
     * @param data 原始字节数组
     * @return 压缩后的字节数组
     * @throws IOException 压缩过程中的IO异常
     */
    public static byte[] compress(byte[] data) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(bos);
        gzip.write(data);
        gzip.close();
        return bos.toByteArray();
    }

    /**
     * 解压缩字节数组
     * @param compressedData 压缩后的字节数组
     * @return 原始字节数组
     * @throws IOException 解压缩过程中的IO异常
     */
    public static byte[] decompress(byte[] compressedData) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(compressedData);
        GZIPInputStream gzip = new GZIPInputStream(bis);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = gzip.read(buffer)) != -1) {
            bos.write(buffer, 0, bytesRead);
        }

        gzip.close();
        bos.close();
        return bos.toByteArray();
    }
}