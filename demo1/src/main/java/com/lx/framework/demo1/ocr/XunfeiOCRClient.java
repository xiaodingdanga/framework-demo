package com.lx.framework.demo1.ocr;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author xin.liu
 * @description TODO
 * @date 2025-04-28  20:55
 * @Version 1.0
 */
@RestController
@RequestMapping("/ocr")
public class XunfeiOCRClient {

    // 从讯飞控制台获取（需替换成你的实际值）
    private static final String APP_ID = "09711c4b";
    private static final String API_KEY = "a0fbcd0def4fa1e6dbf0c6ada6d55b1e";
    private static final String API_SECRET = "MWU0ZjNmMTE5YWQxMjZmYTAzMTkzMzA1";
    private static final String API_URL = "https://cbm01.cn-huabei-1.xf-yun.com/v1/private/se75ocrbm"; // 根据文档替换实际URL
    private static final String HOSTS = "218.19.1.194"; // 根据文档替换实际URL

    private static final String imageStr = "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";


    // 生成 date 参数，格式为 RFC1123
    private static String generateDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", java.util.Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf.format(new Date());
    }

    // 生成 signature
    private static String generateSignature(String date) throws Exception {
        // 构造 signature_origin 字符串
        String requestLine = "POST /v1/private/se75ocrbm HTTP/1.1";
        String signatureOrigin = String.format("host: %s\ndate: %s\n%s", HOSTS, date, requestLine);

        // 使用 hmac-sha256 算法签名
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(API_SECRET.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        mac.init(secretKeySpec);
        byte[] signatureBytes = mac.doFinal(signatureOrigin.getBytes(StandardCharsets.UTF_8));

        // 将签名结果进行 base64 编码
        return Base64.getEncoder().encodeToString(signatureBytes);
    }

    // 生成 authorization_origin
    private static String generateAuthorizationOrigin(String signature) {
        return String.format("api_key=\"%s\",algorithm=\"hmac-sha256\",headers=\"host date request-line\",signature=\"%s\"",
                API_KEY, signature);
    }


    /**
     * 将图片文件转换为Base64编码
     */
    private static String imageToBase64(String imagePath) throws IOException {
        byte[] imageBytes = Files.readAllBytes(new File(imagePath).toPath());
        return Base64.getEncoder().encodeToString(imageBytes);
    }


    // 构造请求体 JSON 数据
    private static String constructRequestBody(String imageParam) {

        JSONObject header = new JSONObject();
        header.set("app_id", APP_ID);
        header.set("status", 0);

        JSONObject result = new JSONObject();
        result.set("encoding", "utf8");
        result.set("compress", "raw");
        result.set("format", "plain");

        JSONObject ocr = new JSONObject();
        ocr.set("result_option", "normal");
        ocr.set("result_format", "json,sed");
        ocr.set("output_type", "one_shot");
        ocr.set("exif_option", "0");
        ocr.set("json_element_option", "");
        ocr.set("markdown_element_option", "watermark=0,page_header=0,page_footer=0,page_number=0,graph=0");
        ocr.set("sed_element_option", "watermark=0,page_header=0,page_footer=0,page_number=0,graph=0");
        ocr.set("alpha_option", "0");
        ocr.set("rotation_min_angle", 5);
        ocr.set("result", result);

        JSONObject parameter = new JSONObject();
        parameter.set("ocr", ocr);

        JSONObject image = new JSONObject();
//        image.set("image", imageToBase64("D:\\captchaNew.jpg")); // 替换为 Base64 编码后的图片字符串
        image.set("image", imageParam); // 替换为 Base64 编码后的图片字符串
        image.set("status", 0);

        JSONObject payload = new JSONObject();
        payload.set("image", image);

        JSONObject requestBodyJson = new JSONObject();
        requestBodyJson.set("header", header);
        requestBodyJson.set("parameter", parameter);
        requestBodyJson.set("payload", payload);

        return JSONUtil.toJsonStr(requestBodyJson);
    }

    @GetMapping(value = "/model")
    public String model(@RequestParam("image") String imageParam) throws Exception {

            // 构造请求体 JSON 数据
            String requestBody = constructRequestBody(imageParam);

            System.out.println("requestBody: " + requestBody);
            // 生成 date 参数
            String date = generateDate();

            // 生成 signature
            String signature = generateSignature(date);

            // 生成 authorization_origin
            String authorizationOrigin = generateAuthorizationOrigin(signature);

            // 对 authorization_origin 进行 base64 编码
            String authorization = Base64.getEncoder().encodeToString(authorizationOrigin.getBytes(StandardCharsets.UTF_8));

            String param = "authorization="+authorization+"&host="+HOSTS+"&date="+date;

            // 发送 POST 请求
            HttpResponse response = HttpRequest.post(API_URL+"?"+param)
                    .body(requestBody)
                    .execute();

            // 输出响应内容
            System.out.println("Response Code: " + response.getStatus());
            System.out.println("Response Body: " + response.body());
            com.alibaba.fastjson2.JSONObject jsonObject1 = com.alibaba.fastjson2.JSONObject.parseObject(response.body());
            String string = jsonObject1.get("payload").toString();
            com.alibaba.fastjson2.JSONObject jsonObject = com.alibaba.fastjson2.JSONObject.parseObject(string);
            String string1 = jsonObject.get("result").toString();
            String string2 = com.alibaba.fastjson2.JSONObject.parseObject(string1).get("text").toString();
            // 解码为字节数组
            byte[] decodedBytes = Base64.getDecoder().decode(string2);

            // 转换为字符串
            String decodedString = new String(decodedBytes, "UTF-8");
            System.out.println("识别结果为: " + decodedString);
            String[] split = decodedString.split(",\"text\":\\[\"");
            String result = null;
            for (String s : split) {
                if (s.substring(0,6).endsWith("\"]")){
                    result = s.substring(0,4);
                    break;
                }
            }
            return result;
    }

    public static void main(String[] args) throws Exception {
//        try {
//            // 构造请求体 JSON 数据
//            String requestBody = constructRequestBody();
//
//            System.out.println("requestBody: " + requestBody);
//            // 生成 date 参数
//            String date = generateDate();
//
//            // 生成 signature
//            String signature = generateSignature(date);
//
//            // 生成 authorization_origin
//            String authorizationOrigin = generateAuthorizationOrigin(signature);
//
//            // 对 authorization_origin 进行 base64 编码
//            String authorization = Base64.getEncoder().encodeToString(authorizationOrigin.getBytes(StandardCharsets.UTF_8));
//
//            String param = "authorization="+authorization+"&host="+HOSTS+"&date="+date;
//
//            // 发送 POST 请求
//            HttpResponse response = HttpRequest.post(API_URL+"?"+param)
//                    .body(requestBody)
//                    .execute();
//
//            // 输出响应内容
//            System.out.println("Response Code: " + response.getStatus());
//            System.out.println("Response Body: " + response.body());
//            com.alibaba.fastjson2.JSONObject jsonObject1 = com.alibaba.fastjson2.JSONObject.parseObject(response.body());
//            String string = jsonObject1.get("payload").toString();
//            com.alibaba.fastjson2.JSONObject jsonObject = com.alibaba.fastjson2.JSONObject.parseObject(string);
//            String string1 = jsonObject.get("result").toString();
//            String string2 = com.alibaba.fastjson2.JSONObject.parseObject(string1).get("text").toString();
//            // 解码为字节数组
//            byte[] decodedBytes = Base64.getDecoder().decode(string2);
//
//            // 转换为字符串
//            String decodedString = new String(decodedBytes, "UTF-8");
//            System.out.println("识别结果为: " + decodedString);
//            String[] split = decodedString.split(",\"text\":\\[\"");
//            for (String s : split) {
//                if (s.substring(0,6).endsWith("\"]")){
//                    System.out.println(s.substring(0,4));
//                    break;
//                }
//            }
//
//            System.out.println(imageToBase64("D:\\captchaNew.jpg"));
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

}