import cn.hutool.http.HttpRequest;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadHttpTest {

    // HTTP POST 请求方法
//    private void sendPostRequest() {
//        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
//            HttpPost post = new HttpPost("http://localhost:8080/synchronizedBuy");
//            post.setHeader("Content-Type", "application/json");
//            try (CloseableHttpResponse response = httpClient.execute(post)) {
//                System.out.println("Response Code: " + response.getStatusLine().getStatusCode());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    @Test
    public void testMultiThreadSendRequest() throws InterruptedException {
        int threadCount = 100; // 线程数量
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
//                String url = "http://localhost:8080/synchronizedBuy";
//                String url = "http://localhost:8080/buy";
//                String url = "http://localhost:8080/staticBuy";
//                String url = "http://localhost:8080/cacheMap";
                String url = "http://localhost:8080/reentrantLockBuy";
                String jsonBody = "{\"key\":"+System.currentTimeMillis()+"}";
                String response = HttpRequest.
                        post(url).
                        body(jsonBody).
                        header("Content-Type", "application/json").
                        timeout(3000).
                        execute().
                        body();
                System.out.println("Response: " + response);
            });
        }
        Thread.sleep(5000);
        System.out.println("所有线程执行完毕");
        executorService.shutdown();
    }
}