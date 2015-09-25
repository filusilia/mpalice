package com.alice.util;

import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Filus on 2015/9/23 0023.
 */
public class HttpClientUtil {

    private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    private HttpClientUtil() {
    }

    /**
     * 发送HTTP_GET请求
     *
     * @param requestURL    请求地址(含参数)
     * @param decodeCharset 解码字符集,解析响应数据时用之,其为null时默认采用UTF-8解码
     * @return 远程主机响应正文
     */
    public static String sendGetRequest(String requestURL, Charset decodeCharset) {
        // 设置可关闭的httpclient
        CloseableHttpClient httpClient = HttpClients.createDefault();
        long responseLength = 0;       //响应长度
        String responseContent = null; //响应内容
        HttpGet httpGet = new HttpGet(requestURL);           //创建org.apache.http.client.methods.HttpGet
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet); //执行GET请求
            HttpEntity entity = response.getEntity();            //获取响应实体
            if (null != entity) {
                responseLength = entity.getContentLength();
                responseContent = EntityUtils.toString(entity, decodeCharset == null ? Consts.UTF_8 : decodeCharset);
                EntityUtils.consume(entity); //Consume response content
            }
            logger.debug("请求地址: " + httpGet.getURI());
            logger.debug("响应状态: " + response.getStatusLine());
            logger.debug("响应长度: " + responseLength);
            logger.debug("响应内容: " + responseContent);
        } catch (ClientProtocolException e) {
            logger.error("该异常通常是协议错误导致,比如构造HttpGet对象时传入的协议不对(将'http'写成'htp')或者服务器端返回的内容不符合HTTP协议要求等,堆栈信息如下", e);
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error("该异常通常是网络原因引起的,如HTTP服务器未启动等,堆栈信息如下", e);
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseContent;
    }

    /**
     * 发送HTTP_POST请求
     * <p>
     * 该方法为<code>sendPostRequest(String,String,boolean,String,String)</code>的简化方法
     * 该方法在对请求数据的编码和响应数据的解码时,所采用的字符集均为UTF-8
     * 当<code>isEncoder=true</code>时,其会自动对<code>sendData</code>中的[中文][|][]等特殊字符进行<code>URLEncoder.encode(string,"UTF-8")</code>
     *
     * @param isEncoder 用于指明请求数据是否需要UTF-8编码,true为需要
     */
    public static String sendPostRequest(String reqURL, String sendData, boolean isEncoder) {
        return sendPostRequest(reqURL, sendData, isEncoder, null, null);
    }

    /**
     * 发送HTTP_POST请求
     * <p>
     * 当 isEncoder=true</code>时,其会自动对<code>sendData</code>中的[中文][|][]等特殊字符进行<code>URLEncoder.encode(string,encodeCharset)</code>
     *
     * @param reqURL        请求地址
     * @param sendData      请求参数,若有多个参数则应拼接成param11=value11?m22=value22?m33=value33的形式后,
     *                      传入该参数中
     * @param isEncoder     请求数据是否需要encodeCharset编码,true为需要
     * @param encodeCharset 编码字符集,编码请求数据时用之,其为null时默认采用UTF-8解码
     * @param decodeCharset 解码字符集,解析响应数据时用之,其为null时默认采用UTF-8解码
     * @return 远程主机响应正文
     */
    public static String sendPostRequest(String reqURL, String sendData, boolean isEncoder, Charset encodeCharset, Charset decodeCharset) {
        String responseContent = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpPost httpPost = new HttpPost(reqURL);
        httpPost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded");
        try {
            if (isEncoder) {
                List<NameValuePair> formParams = new ArrayList<>();
                for (String str : sendData.split("&")) {
                    formParams.add(new BasicNameValuePair(str.substring(0, str.indexOf("=")), str
                            .substring(str.indexOf("=") + 1)));
                }
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, encodeCharset == null ? Consts.UTF_8 : encodeCharset);
                httpPost.setEntity(entity);
            } else {
                httpPost.setEntity(new StringEntity(sendData));
            }

            CloseableHttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (null != entity) {
                responseContent = EntityUtils.toString(entity, decodeCharset == null ? Consts.UTF_8 : decodeCharset);
                EntityUtils.consume(entity);
            }
        } catch (Exception e) {
            logger.error("与[" + reqURL + "]通信过程中发生异常,堆栈信息如下", e);
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseContent;
    }

    /**
     * 发送HTTP_POST请求
     * <p>
     * 该方法会自动对<code>params</code>中的[中文][|][ ]等特殊字符进行<code>URLEncoder.encode(string,encodeCharset)</code>
     *
     * @param reqURL        请求地址
     * @param params        请求参数
     * @param encodeCharset 编码字符集,编码请求数据时用之,其为null时默认采用UTF-8解码
     * @param decodeCharset 解码字符集,解析响应数据时用之,其为null时默认采用UTF-8解码
     * @return 远程主机响应正文
     */
    public static String sendPostRequest(String reqURL, Map<String, String> params, Charset encodeCharset, Charset decodeCharset) {
        String responseContent = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpPost httpPost = new HttpPost(reqURL);
        List<NameValuePair> formParams = new ArrayList<>(); // 创建参数队列
        for (Map.Entry<String, String> entry : params.entrySet()) {
            formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        try {

            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, encodeCharset == null ? Consts.UTF_8 : encodeCharset);
            httpPost.setEntity(entity);
            httpClient.execute(httpPost);
            if (null != entity) {
                responseContent = EntityUtils.toString(entity, decodeCharset == null ? Consts.UTF_8 : decodeCharset);
                EntityUtils.consume(entity);
            }
        } catch (Exception e) {
            logger.error("与[" + reqURL + "]通信过程中发生异常,堆栈信息如下", e);
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseContent;
    }

    /**
     * 发送HTTPS_POST请求
     * <p>
     * 该方法为<code>sendPostSSLRequest(String,Map<String,String>,String,String)</code>方法的简化方法
     * 该方法在对请求数据的编码和响应数据的解码时,所采用的字符集均为UTF-8
     * 该方法会自动对<code>params</code>中的[中文][|][ ]等特殊字符进行
     * <code>URLEncoder.encode(string,"UTF-8")</code>
     */
    public static String sendPostSSLRequest(String reqURL, Map<String, String> params) {
        return sendPostSSLRequest(reqURL, params, null, null);
    }

    /**
     * 发送HTTPS_POST请求
     * <p>
     * 该方法会自动对<code>params</code>中的[中文][|][ ]等特殊字符进行<code>URLEncoder.encode(string,encodeCharset)</code>
     *
     * @param reqURL        请求地址
     * @param params        请求参数
     * @param encodeCharset 编码字符集,编码请求数据时用之,其为null时默认采用UTF-8解码
     * @param decodeCharset 解码字符集,解析响应数据时用之,其为null时默认采用UTF-8解码
     * @return 远程主机响应正文
     */
    public static String sendPostSSLRequest(String reqURL, Map<String, String> params,
                                            String encodeCharset, String decodeCharset) {
        String responseContent = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        X509TrustManager xtm = new WechatX509TrustManager();
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            ctx.init(null, new TrustManager[]{xtm}, null);
            SSLSocketFactory socketFactory = new SSLSocketFactory(ctx);
            httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", 443, socketFactory));

            HttpPost httpPost = new HttpPost(reqURL);
            List<NameValuePair> formParams = new ArrayList<>();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(formParams, encodeCharset == null ? "UTF-8"
                    : encodeCharset));

            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (null != entity) {
                responseContent = EntityUtils.toString(entity, decodeCharset == null ? "UTF-8"
                        : decodeCharset);
                EntityUtils.consume(entity);
            }
        } catch (Exception e) {
            logger.error("与[" + reqURL + "]通信过程中发生异常,堆栈信息为", e);
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
        return responseContent;
    }

    /**
     * 发送HTTPS_POST,类型为JSON的请求
     * <p>
     * 该方法为<code>sendPostSSLRequest(String,String,String,String)</code>方法的简化方法
     * 该方法在对请求数据的编码和响应数据的解码时,所采用的字符集均为UTF-8
     * 该方法会自动对<code>params</code>中的[中文][|][ ]等特殊字符进行
     * <code>URLEncoder.encode(string,"UTF-8")</code>
     */
    public static String sendPostSSLRequest(String reqURL, String params) {
        return sendPostSSLRequest(reqURL, params, null, null);
    }

    /**
     * 发送HTTPS_POST，类型为JSON的请求
     * <p>
     * 该方法会自动对<code>params</code>中的[中文][|][ ]等特殊字符进行<code>URLEncoder.encode(string,encodeCharset)</code>
     *
     * @param reqURL        请求地址
     * @param params        请求参数
     * @param encodeCharset 编码字符集,编码请求数据时用之,其为null时默认采用UTF-8解码
     * @param decodeCharset 解码字符集,解析响应数据时用之,其为null时默认采用UTF-8解码
     * @return 远程主机响应正文
     */
    public static String sendPostSSLRequest(String reqURL, String params, String encodeCharset,
                                            String decodeCharset) {
        String responseContent = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        X509TrustManager xtm = new WechatX509TrustManager();
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            ctx.init(null, new TrustManager[]{xtm}, null);
            SSLSocketFactory socketFactory = new SSLSocketFactory(ctx);
            httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", 443, socketFactory));

            HttpPost httpPost = new HttpPost(reqURL);

            httpPost.setEntity(new StringEntity(params, encodeCharset == null ? "UTF-8" : encodeCharset));
            httpPost.addHeader("content-type", "application/json");
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (null != entity) {
                responseContent = EntityUtils.toString(entity, decodeCharset == null ? "UTF-8"
                        : decodeCharset);
                EntityUtils.consume(entity);
            }
        } catch (Exception e) {
            logger.error("与[" + reqURL + "]通信过程中发生异常,堆栈信息为", e);
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
        return responseContent;
    }

    /**
     * 发送HTTPS_GET请求
     *
     * @param reqURL        请求地址(含参数)
     * @param decodeCharset 解码字符集,解析响应数据时用之,其为null时默认采用UTF-8解码
     * @return 远程主机响应正文
     */
    public static String sendGetSSLRequest(String reqURL, String decodeCharset) {
        long responseLength = 0; // 响应长度
        String responseContent = null; // 响应内容
        CloseableHttpClient httpClient = HttpClients.createDefault(); // 创建默认的httpClient实例
        X509TrustManager xtm = new WechatX509TrustManager();
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            ctx.init(null, new TrustManager[]{xtm}, null);
            SSLSocketFactory socketFactory = new SSLSocketFactory(ctx);
            httpClient.getConnectionManager().getSchemeRegistry()
                    .register(new Scheme("https", 443, socketFactory));

            HttpGet httpGet = new HttpGet(reqURL); // 创建org.apache.http.client.methods.HttpGet
            HttpResponse response = httpClient.execute(httpGet); // 执行GET请求
            HttpEntity entity = response.getEntity(); // 获取响应实体
            if (null != entity) {
                responseLength = entity.getContentLength();
                responseContent = EntityUtils.toString(entity, decodeCharset == null ? "UTF-8"
                        : decodeCharset);
                EntityUtils.consume(entity); // Consume response content
            }
            logger.debug("请求地址: " + httpGet.getURI());
            logger.debug("响应状态: " + response.getStatusLine());
            logger.debug("响应长度: " + responseLength);
            logger.debug("响应内容: " + responseContent);
        } catch (Exception e) {
            logger.error("与[" + reqURL + "]通信过程中发生异常,堆栈信息为", e);
        } finally {
            httpClient.getConnectionManager().shutdown(); // 关闭连接,释放资源
        }
        return responseContent;
    }

    /**
     * 发送HTTP_POST请求
     * <p>
     * 本方法默认的连接超时时间为30秒,默认的读取超时时间为30秒
     *
     * @param reqURL 请求地址
     * @param params 发送到远程主机的正文数据,其数据类型为<code>java.util.Map<String, String></code>
     * @return 远程主机响应正文`HTTP状态码,如<code>"SUCCESS`200"</code><br>
     * 若通信过程中发生异常则返回"Failed`HTTP状态码",如<code>"Failed`500"</code>
     * 若发送的<code>params</code>中含有中文,记得按照双方约定的字符集将中文<code>URLEncoder.encode(string,encodeCharset)</code>
     */
    public static String sendPostRequestByJava(String reqURL, Map<String, String> params) {
        StringBuilder sendData = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            sendData.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        if (sendData.length() > 0) {
            sendData.setLength(sendData.length() - 1); // 删除最后一个&符号
        }
        return sendPostRequestByJava(reqURL, sendData.toString());
    }

    /**
     * 发送HTTP_POST请求
     * <p>
     * 本方法默认的连接超时时间为30秒,默认的读取超时时间为30秒
     *
     * @param reqURL   请求地址
     * @param sendData 发送到远程主机的正文数据
     * @return 远程主机响应正文`HTTP状态码,如<code>"SUCCESS`200"</code><br>
     * 若通信过程中发生异常则返回"Failed`HTTP状态码",如<code>"Failed`500"</code>
     * 若发送的<code>sendData</code>中含有中文,记得按照双方约定的字符集将中文URLEncoder.encode(string,encodeCharset)</code>
     */
    public static String sendPostRequestByJava(String reqURL, String sendData) {
        HttpURLConnection httpURLConnection = null;
        OutputStream out = null; // 写
        InputStream in = null; // 读
        int httpStatusCode = 0; // 远程主机响应的HTTP状态码
        try {
            URL sendUrl = new URL(reqURL);
            httpURLConnection = (HttpURLConnection) sendUrl.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true); // 指示应用程序要将数据写入URL连接,其值默认为false
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setConnectTimeout(30000); // 30秒连接超时
            httpURLConnection.setReadTimeout(30000); // 30秒读取超时

            out = httpURLConnection.getOutputStream();
            out.write(sendData.toString().getBytes());

            // 清空缓冲区,发送数据
            out.flush();

            // 获取HTTP状态码
            httpStatusCode = httpURLConnection.getResponseCode();

            in = httpURLConnection.getInputStream();
            byte[] byteDatas = new byte[in.available()];
            in.read(byteDatas);
            return new String(byteDatas) + "`" + httpStatusCode;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return "Failed`" + httpStatusCode;
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    logger.error("关闭输出流时发生异常,堆栈信息如下", e);
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                    logger.error("关闭输入流时发生异常,堆栈信息如下", e);
                }
            }
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
    }

}
