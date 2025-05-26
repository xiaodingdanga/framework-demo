package com.lx.framework.demo1.ocr;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.json.JSONException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Base64;

/**
 * @author xin.liu
 * @description TODO
 * @date 2025-04-29  11:12
 * @Version 1.0
 */
@RestController
@RequestMapping("/ocr")
@CrossOrigin(origins = {"https://zxgk.court.gov.cn","192.168.124.45"})
public class baidu {

//    public static final String API_KEY = "rRWgdc53sqRZXwD9V3gFzTgu";
    public static final String API_KEY = "Nfe5g10mNZyAmR7EkHR23e1o";
//    public static final String SECRET_KEY = "VNLIMa1vqQy88q1E768BkuKj5EbkQafN";
    public static final String SECRET_KEY = "VKLwVOUuR0TRZl8nkUkrUyZAgnM49WWo";

    private static final String API_URL = "https://aip.baidubce.com/rest/2.0/ocr/v1/accurate_basic"; // 根据文档替换实际URL

    private static final String imageStr = "/9j/4AAQSkZJRgABAQAAAQABAAD/4gHYSUNDX1BST0ZJTEUAAQEAAAHIAAAAAAQwAABtbnRyUkdCIFhZWiAH4AABAAEAAAAAAABhY3NwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAQAA9tYAAQAAAADTLQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAlkZXNjAAAA8AAAACRyWFlaAAABFAAAABRnWFlaAAABKAAAABRiWFlaAAABPAAAABR3dHB0AAABUAAAABRyVFJDAAABZAAAAChnVFJDAAABZAAAAChiVFJDAAABZAAAAChjcHJ0AAABjAAAADxtbHVjAAAAAAAAAAEAAAAMZW5VUwAAAAgAAAAcAHMAUgBHAEJYWVogAAAAAAAAb6IAADj1AAADkFhZWiAAAAAAAABimQAAt4UAABjaWFlaIAAAAAAAACSgAAAPhAAAts9YWVogAAAAAAAA9tYAAQAAAADTLXBhcmEAAAAAAAQAAAACZmYAAPKnAAANWQAAE9AAAApbAAAAAAAAAABtbHVjAAAAAAAAAAEAAAAMZW5VUwAAACAAAAAcAEcAbwBvAGcAbABlACAASQBuAGMALgAgADIAMAAxADb/2wBDAAMCAgICAgMCAgIDAwMDBAYEBAQEBAgGBgUGCQgKCgkICQkKDA8MCgsOCwkJDRENDg8QEBEQCgwSExIQEw8QEBD/2wBDAQMDAwQDBAgEBAgQCwkLEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBD/wAARCAA+ALgDASIAAhEBAxEB/8QAHQABAAMAAwADAAAAAAAAAAAAAAYHCAQFCQECA//EADwQAAECBgEDAQYEAggHAAAAAAECAwAEBQYHEQgSITFBEyIyUWFxCRSBoRgjFRYlQlJigpEXQ1OiscHR/8QAGwEAAQUBAQAAAAAAAAAAAAAAAAIDBAUGAQf/xAAxEQABAwMCBQIFAwUBAAAAAAABAAIDBAUREiEGMUFRYRNxFCKBkaFSYrEjMpLC0fD/2gAMAwEAAhEDEQA/APVOEIQISEQ3LmRaZi2w6pdtReSlUsyoS6Ce7jxGkJA9e+v0ih+H9dy3kyp1PIV73PUXqKlSm5KUWshpa1eoT6gD94FXzXGOKqZRgEudvt0HcrVUIQgVgkIR1ly3JRLQoU7ctxz7clTqe0Xph9w6CEj/AN70APUmFMY6RwYwZJ2AHUrjnBoyeS7OEVla/JfBN4FKKJkyiqdV/wAt58MqH369RYsjUafU2RM02el5to+HGHUuJP6gkRIqaGqonaamNzD+4Efym454phmNwPscrkQhCIqdSEIQISEIQISEIQISEI6O872tbH1AmrnvCssU2myaCt150nwPQJGyo/QAmBcJA3K7yEY8rPPap3pPvUDjnh+4Lzm0q6ROqlnBLo/zKCRtI+qiI7KwrL5sXxeVKurKF60+1qHKTSJlyiyCgVOoB37NRR5+WlExZG2SxN1VDgzwTv8AYbpzQRzWsYQhFakL4KkjyoD7mOMuqUxt0sOVGWS6PKC6kKH6b3GaF8R79rLinbmzbVnvaHbiGyvpUfU66tftHHyZhOlYjxfVrqXX5yfXSpcFpKxrqWToFXz7mHL5GKCJptrhUPJ3G7AB3yQc+wCy814urGGQUWGjcl0jeQ8AFVByjyinLuTJey26mqStGjPhL82QoocIPvLAHxeugO8Wzb1651mLWkrd4/4ol6Pb8kyG5efrikoXMdvjDexrfnuIhvFfGdEzpTaldV7oe9nJPCXYZlHi0N+dnXmLzrXELE1clTJzKq60gkElmqOpUdem9xTUs1xkwaiFrfZ+f9VGsdDPcR8fUSFnq89PMDsCentjPdZ3/i35C4xyE3a+V6dTnEpeQmZl/YJQoNqOutC0nWtdxuN206dRUZCXn2wQmYaS4AfIBG48q8v4fpj/ACHGMrJr1bW2J5qmIVPVBc0WlFQBO1HwPOvpF6VbipzAsWlq/wCFmdnZ1xKQG5Z+YW2B9uslMaSko4qp7YzMG5OMuyAPJO+ytrUGMdO2OpdM1pwNTSCCOmcnK3THSXhZds39Qn7au2lN1GmzOi7LuEhKiDsb0e+iAfuIwjJZO/Exw+yF3ji5q9JBr4nGEtTDnSPrLqKv+2LItL8QimNJDOVcaV2330AB1UvLqWltR9FBfSRGpdwPeISKi3PZPjcGGQOIPtkOB+itaY/H6o2sO3MEf+ypVc/4fuAq71rpsjUaO4rv1S0wSAfoFdoyjl/GdGwtccvbGIs2V+tXS8+GG6NTepxxo/JakK0D/l1uLNyryxu3NtwSmN8TVJu1LfqTgRM16eeTLrLf973if5Y/cnQjQfH3DGGcTyCXKJWqPXLjmB7Sbqy5pt59xSv8J2dJ8+PMaWmv1x4apxLeqkyuP9sGQ7x/UcQ7SP2j5vZU1dw9LLL6cdK6IDm8tc3/ABG2ffkuPxMs3ONCtd6r5uuiozc7OBIladNvBxcsj1Kz/iPbtvtF+x8BSVDaVA7+RiO33ftu46oLlwXJN+yYSrobQkbW6sjslI9T2jy283UVk8lwqA2MHcgANaB4CsgYLXS6pX4YwZLnH8kqRwjNkhyXyLflQdk8YYzVPNtnRceWdJHp1K7JT+pjtl5D5Q01SHajh+TeZJAV+XmEOqA+zayf2jJs4mopRrha97f1NjcR98LOx8b22ob6lOyWRn6mxPLfuGq/YRG52+KFbNuMVy+qvIUPqYS6+mZeCOgkbKQD3JHy8xk/kJzDTVae0nBuQk0uUk3lJqNWckkltQ0OzZc/8lP23Gohpppo/Wa06O+Djfpnv4Wiq7hBRU4qZyQ046HOTyGOefC2mpSUJKlqCQO5JOgIpvLXL3j7hlh3+tmRKY/Ptg/2ZTX0Tc4T8i2hXu/6tRQdlWbmDl9jOnT0xnefp9sOOraemqYv2U1O9CylaVhHT07IPkeNHWjuM78hMH2NJ5IlOK/Hy3EzVwy0oZ+5K5NuF6bc9wOdJWTpKQhSSQAPii9tdHadPqXCR5cM/IwY2HVz3bAHw0n2TNLXS1jPUbGWDpqGCfp/1bGxRy4mOSdSdo2KJFqnezT7Z5c2Qt+WZJIStxI2lJVo6AJMWlWcOWRPUqZqWWJ5dwMMoL0yuovFEuhKRs+6CAEj5HtGEPwl52Rp9/Xvbkx7s+GUKShwdKulKlJOh53sdx6RaPN3KFz5VyrROG+NZ11mYqzjCrgmGSQWmlgL6SR/dCFJUfvGYquH6Wa6OncXEA6mhzstYOYwBgZHRxBd5TTbXE+X4mpcZHA5AJ+VvbDRgbdyCfKsPB3I+fy3kWdsvAGMKTJY4t6YMrN3EtJabcKfRhtAAJUNEb9CCY1WPHeIXh/FFrYYsGlWFaci2xLU+XQ264lOlTDoSAt1Z9VKOyfvE1iRM9r35YNvz9VbjPVIQhDS6kQLOtovXzie47blklT0xJrU2kDupSR1AD7kaiewgTc0TZ43RO5EEfdeefEzO05iRmtWpUbIrFWlEvF+ZcpzftXJbp7FSkeSPrF+17nZhynUp5+ms1ubqCUENyipEtHr12Cio9huLromOLFturTFdoVq06SqE1v20yywlLi9+QTHOftK1pp8TUzblMdeB2FrlUFW/nvUcVDRWy40VMKeOdu3LLc4HjcfnKxHxUx1dWTszTmb7npL8tINvuzbTr6CA++sEDp38Wgd7+kbyj6ttttIDbSEoSkaCUjQAj7R1WFrtzbZCYwdRJJJ7kpGKPxIL2qlAtmkWlSkCXl644VTzraQkupb7pQo+SN6OvpG14rjM+BMf53pMpSr5lZpX5Bwuyz8q8W3GyQQRv1B34+gjRcKXKltF3hrK1pMbTk43PLY48HBSrpTy1VI+GA4cVQPCjBeO7gwq1W7rtyTq8zU5hZKpgFSm0gDSQQdjz4ESm+eAWIbnqiq5b1SrdszxT0hchNqCU/LQPf94t3DGIKThO1V2dQqvOz9PD6nmfzfSVtA+UggDY7RP4j8S17Ltc56hji5j3EjO23TbphXdivN2s9NHFDO5paMEaiR9jkLG38HXIK0Fbx1yarAbR8LVR617Hy3sxBs24u5RUejUitZHyBJV+mSTykLbZaILfUB3Udeuh3+kegUcGt0SmXFSpmi1iVRMSk2gtutrGwR/wDYx1ztba6mfEw4JG2dxkbjIOxSeJrlXcRW+akmEZc9uAXRsO/TIx368xzG4WWMEWtlOuWK2uxshU6kyntFhxkS/W62v5q7dzE6kMH54mPaIuHk5Wg2tWwmn0yXbIHy6lJMRedxjk7j9cExdeMUOV2gPnczThtTiEee6R3Ou+lJ8eoibW/y2xdPsdNyLqFAnEDTjMxKOOp6vUJLaVH/AHAiJw/xKLGz4St0wygY+drSwgdWFwLR9MLySw1tBa4m0F8LqeZgxhz3NjcB1YchuD26clEZjgtaNfub+nr+yBc91NDShL1CZ2Cr18dgPoAInV+8XsV3TiSrYro1p0uky8+x0tPsSyUuIeHwuKXrqUfqTH6Uzk7jauXPJW5RXZ6bTOL9n+aEqtDbZ9NhQCtfpFuxqhxTNfW/LUeo1hxgEaQfYYC9Bt91obtGRRSiRrDjY5AOO/X3Xl9xEyxcHD3LVb445iS5TqRUJ5x2nzj/AGaDh9xDqT/01hAP336xMODgYu/l7mS76sUzE4iZeZZcWerbZdWn3T6ApSnsfTQ9I1Byl4r2byVtP8lUUokLikUf2ZVkJ/mNEEkIUfJQSSdRRfBzhtm3jtkat3ZftZoExTqnLqllIlJtx190pUelxW0ADe96JJ7+hiaZ4pYnvzh5GCO/spukggdFVHIbHV1cKORctyNsGnPTFqVuZdcn0MpPQyt5ZLrSvQA72n7x+/AWvyeX+X2QcszIcW9MJfmJNT3xpbdWoBI+XuBI16ACPRy77Ptu/benLVu2ksVKlzzZbfl3k7SoH1+hHziscIcR8L8e6lMVjHFHnpeem2vYvPzM6t5S0edaPb1+UN/GNdCWvHzYxnwu6d9uSueEIRXJaQhCBCQhCBCQhCBCQhCBCQhCBCQhCBCQhCBCR0dYsazbgcL1atelzjp8uOyqCs/6tb/eO8hCJImSjTI0EeRlNTQRVDdErQ4diMj8rpaRZVo0HRo9t06UI8FuXSD/AL63HdQhBHGyIaYwAPGyIoYoG6Imho7AY/hIQhC06kIQgQkIQgQkIQgQv//Z";

    @PostMapping(value = "/baiduOcr")
    public String baiduOcr(@org.springframework.web.bind.annotation.RequestBody ModelDto modelDto) throws Exception {
        System.out.println(modelDto.getImageParam());
        String reslut;
        String[] split = modelDto.getImageParam().split(",");
//        String encode = URLEncoder.encode(split[1], StandardCharsets.UTF_8.toString());
        String encode = URLEncoder.encode(imageStr, StandardCharsets.UTF_8.toString());
        String param = "image="+encode;
        // 发送 POST 请求
        HttpResponse response = HttpRequest.post(API_URL +"?access_token=" + getAccessToken())
                .body(param)
                .execute();

        // 输出响应内容
        String string = response.body();
        System.out.println("Response Body: " + string);
        JSONObject jsonObject1 = JSONObject.parseObject(string);
        String string1 = jsonObject1.get("words_result").toString();
        JSONArray jsonArray = JSONArray.parseArray(string1);
        JSONObject jsonObject11 = jsonArray.getJSONObject(0);
        reslut = jsonObject11.get("words").toString();
        System.out.println("result:"+ reslut);
        return reslut;
    }

    /**
     * 将图片文件转换为Base64编码
     */
    private static String imageToBase64(String imagePath) throws IOException {
        byte[] imageBytes = Files.readAllBytes(new File(imagePath).toPath());
        String s = Base64.getEncoder().encodeToString(imageBytes);
        return URLEncoder.encode(s, StandardCharsets.UTF_8.toString());
    }

    /**
     * 从用户的AK，SK生成鉴权签名（Access Token）
     *
     * @return 鉴权签名（Access Token）
     */
    static String getAccessToken() {
        String param = "grant_type=client_credentials&client_id=" + API_KEY + "&client_secret=" + SECRET_KEY;
        // 发送 POST 请求
        HttpResponse response = HttpRequest.post("https://aip.baidubce.com/oauth/2.0/token")
                .body(param)
                .execute();

        return JSONObject.parseObject(response.body()).getString("access_token");
    }

    public static void main(String []args) throws IOException, JSONException {
        String param = "image="+imageToBase64("D:\\2.jpg");
        // 发送 POST 请求
        HttpResponse response = HttpRequest.post(API_URL +"?access_token=" + getAccessToken())
                .body(param)
                .execute();

        System.out.println(response.body());
    }

}