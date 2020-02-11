/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.melonsoft.proxycheckerextractor;

import com.google.common.net.HttpHeaders;
import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author ArbuzovA
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    final static Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        logger.debug("");
        logger.debug("Start");

        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {

            //HttpGet request = new HttpGet("https://httpbin.org/get");
            HttpGet request = new HttpGet("https://checkerproxy.net/archive/2020-02-11");
            //HttpGet request = new HttpGet("http://proxydb.net/anon");
            //HttpGet request = new HttpGet("https://google.com");

            // add request headers
            //request.addHeader("custom-key", "mkyong");
            request.addHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.87 Safari/537.36");

            // 5 seconds timeout
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectionRequestTimeout(5000)
                    .setConnectTimeout(5000)
                    .setSocketTimeout(5000)
                    .build();

            request.setConfig(requestConfig);

            CloseableHttpResponse response = httpClient.execute(request);

            try {

                // Get HttpResponse Status
                System.out.println(response.getProtocolVersion());              // HTTP/1.1
                System.out.println(response.getStatusLine().getStatusCode());   // 200
                System.out.println(response.getStatusLine().getReasonPhrase()); // OK
                System.out.println(response.getStatusLine().toString());        // HTTP/1.1 200 OK

                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    // return it as a String
                    String result = EntityUtils.toString(entity);
                    System.out.println(result);
                }

            } catch (Exception e) {
                System.out.println("==============================>/r/n" + e.getLocalizedMessage() + "/r/n <===================================");
            } finally {
                response.close();
            }
        } catch (Exception e) {
            System.out.println("==============================>/r/n" + e.getLocalizedMessage() + "/r/n <===================================");
        } finally {
            httpClient.close();
        }

    }

}
