/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 *
 * @author duyetpt
 */
public class DCAHttpRequest {

    private DCAHttpRequest() {
        client = HttpClientBuilder.create().build();
    }

    CloseableHttpClient client;

    private static final DCAHttpRequest instance = new DCAHttpRequest();

    public static final DCAHttpRequest getInstance() {
        return instance;
    }

    public URI buildUrl(String host, int port, String path, Map<String, String> parameters) throws URISyntaxException {
        URIBuilder builder = new URIBuilder();
        builder.setScheme("http").setHost(host).setPort(port).setPath(path);
        if (parameters != null) {
            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                builder.addParameter(entry.getKey(), entry.getValue());
            }
        }

        return builder.build();
    }

    public int put(URI url, String entity) throws UnsupportedEncodingException, IOException {
        HttpPut put = new HttpPut(url);
        put.setEntity(new StringEntity(entity));
        int result = getResponse(put);
        return result;
    }

    public int get(URI url) throws IOException {
        HttpGet get = new HttpGet(url);
        int result = getResponse(get);
        return result;
    }

    public int post(URI url, String entity) throws IOException {
        HttpPost post = new HttpPost(url);
        post.setEntity(new StringEntity(entity));
        int result = getResponse(post);

        return result;
    }

    private int getResponse(HttpUriRequest httpRequest) throws IOException {
        HttpResponse httpResponse = client.execute(httpRequest);
        return httpResponse.getStatusLine().getStatusCode();

//        BufferedReader rd = new BufferedReader(
//                new InputStreamReader(httpResponse.getEntity().getContent()));
//
//        StringBuilder result = new StringBuilder();
//        String line = "";
//        while ((line = rd.readLine()) != null) {
//            result.append(line);
//        }
//
//        return result.toString();
    }
}
