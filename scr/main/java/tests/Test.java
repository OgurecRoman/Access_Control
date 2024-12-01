package tests;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;

public class Test {
    public static void main(String[] args) {
        String url = "http://localhost:3030/model/add_face";
        File file = new File("/Users/licette./test_pic/pic.png");

        if (!file.exists()) {
            System.out.println("Файл не найден: " + file.getAbsolutePath());
            return;
        }

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(url);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addTextBody("data", "{\"uuid\": \"IVAN IVANOV\"}");
            builder.addBinaryBody("file", file);

            HttpEntity multipart = builder.build();
            post.setEntity(multipart);

            HttpResponse response = client.execute(post);
            System.out.println("Response Code: " + response.getStatusLine().getStatusCode());

            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                String responseString = EntityUtils.toString(responseEntity);
                System.out.println("Response Body: " + responseString);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


/*
Можно через cURL, но у меня аналогичная ошибка.

curl -X POST http://localhost:3030/model/add_face \
-F "data={\"uuid\": \"IVAN IVANOV\"}" \
-F "file=@/Users/licette/Desktop/test_pic/pic.png"
*/