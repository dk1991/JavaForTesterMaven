package ru.stqa.pft.mantis.appmanager;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HttpSession {
    private CloseableHttpClient httpClient;
    private ApplicationManager app;

    public HttpSession(ApplicationManager app) {
        this.app = app;
        // создание клиента для отправления запросов
        // setRedirectStrategy(new LaxRedirectStrategy() для перенаправления запросов автоматически
        httpClient = HttpClients.custom().setRedirectStrategy(new LaxRedirectStrategy()).build();
    }

    public boolean login(String userName, String password) throws IOException {
        HttpPost post = new HttpPost(app.getProperty("web.baseUrl") + "/login.php"); // создание post запроса
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("username", userName));
        params.add(new BasicNameValuePair("password", password));
        params.add(new BasicNameValuePair("secure_session", "on"));
        params.add(new BasicNameValuePair("return", "index.php"));
        post.setEntity(new UrlEncodedFormEntity(params)); // параметры упаковать в запрос post
        CloseableHttpResponse response = httpClient.execute(post); // выполнить запрос post
        String body = getTextFrom(response);
        //System.out.println(body);
        // проверяем, содержит ли страница имя залогиненного юзера (по элементу на странице, который содержит логин)
        return body.contains(String.format("<span class=\"user-info\">%s</span>", userName));
    }

    private String getTextFrom(CloseableHttpResponse response) throws IOException {
        try {
            return EntityUtils.toString(response.getEntity());
        } finally {
            response.close();
        }
    }

    // проверяем, залогинен ли пользователь
    public boolean isLoggedInAs(String userName) throws IOException {
        HttpGet get = new HttpGet(app.getProperty("web.baseUrl") + "/index.php");
        CloseableHttpResponse response = httpClient.execute(get); // выполнить запрос get
        String body = getTextFrom(response);
        return body.contains(String.format("<span class=\"user-info\">%s</span>", userName));
    }
}
