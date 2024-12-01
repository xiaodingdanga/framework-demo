package com.lx.framework.demo1.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author xin.liu
 * @description TODO
 * @date 2024-11-22  17:37
 * @Version 1.0
 */
public class GzipUtil {
    public static byte[] compress(byte[] data) throws IOException {
        if (data == null || data.length == 0) {
            return new byte[0];
        }
        System.out.println("压缩前data大小："+ data.length);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzOut = new GZIPOutputStream(out);
        gzOut.write(data);
        gzOut.close();
        System.out.println("压缩后data大小："+ out.toByteArray().length);
        return out.toByteArray();
    }

    public static byte[] decompress(byte[] gzip) throws IOException {
        if (gzip == null || gzip.length == 0) {
            return new byte[0];
        }
        System.out.println("解压前data大小："+ gzip.length);
        ByteArrayInputStream in = new ByteArrayInputStream(gzip);
        GZIPInputStream gzIn = new GZIPInputStream(in);
        System.out.println("解压后data大小："+ gzIn.readAllBytes().length);
        return gzIn.readAllBytes();
    }
}