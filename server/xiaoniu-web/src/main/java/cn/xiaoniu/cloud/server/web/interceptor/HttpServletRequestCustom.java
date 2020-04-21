package cn.xiaoniu.cloud.server.web.interceptor;

import cn.xiaoniu.cloud.server.util.JsonUtil;
import cn.xiaoniu.cloud.server.util.constant.CommonConstant;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * Request包装类 解决InputStream只能读取一次问题
 */
public class HttpServletRequestCustom extends HttpServletRequestWrapper {

    private byte[] body;

    private Map<String, String[]> params = new HashMap<>();

    private BufferedReader reader;

    private ServletInputStream inputStream;

    public HttpServletRequestCustom(HttpServletRequest request) throws IOException {
        super(request);
        loadBody(request);
        initParams();
    }

    private void loadBody(HttpServletRequest request) throws IOException {
        body = IOUtils.toByteArray(request.getInputStream());
        inputStream = new RequestCachingInputStream(body);
    }

    public byte[] getBody() {
        return body;
    }

    @Override
    public String getParameter(String name) {
        return this.params.get(name) != null && this.params.get(name).length > 0 ? this.params.get(name)[0] : null;
    }

    @Override
    public String[] getParameterValues(String name) {
        return this.params.get(name);
    }

    /**
     * 初始化所有参数
     */
    private void initParams() {
        this.params.putAll(super.getRequest().getParameterMap());

        String bodyStr = new String(body, Charset.forName(super.getCharacterEncoding()));
        if (bodyStr.contains(CommonConstant.CHAR_EQUAL)) {
            convertParamStr(bodyStr, this.params);
        }

        if (JsonUtil.isJson(bodyStr)) {
            Map paramsMap = JsonUtil.fromJson(bodyStr, Map.class);
            if (!CollectionUtils.isEmpty(paramsMap)) {
                paramsMap.forEach((key, value) ->
                        this.params.put(key.toString(), new String[]{value == null ? null : value.toString()})
                );
            }
        }
    }

    /**
     * 将 a=a&b=b 的参数格式转Map
     *
     * @param paramStr
     * @return
     */
    public void convertParamStr(String paramStr, Map<String, String[]> result) {
        if (StringUtils.isBlank(paramStr)) {
            return;
        }
        for (String param : paramStr.split(CommonConstant.CHAR_AND)) {
            String[] keyValue = param.split(CommonConstant.CHAR_EQUAL);
            String value = keyValue.length > 1 ? keyValue[1] : null;
            result.put(keyValue[0], new String[]{value});
        }
    }


    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (inputStream != null) {
            return inputStream;
        }
        return super.getInputStream();
    }

    @Override
    public BufferedReader getReader() throws IOException {
        if (reader == null) {
            reader = new BufferedReader(new InputStreamReader(inputStream, getCharacterEncoding()));
        }
        return reader;
    }

    private static class RequestCachingInputStream extends ServletInputStream {

        private final ByteArrayInputStream inputStream;

        public RequestCachingInputStream(byte[] bytes) {
            inputStream = new ByteArrayInputStream(bytes);
        }

        @Override
        public int read() throws IOException {
            return inputStream.read();
        }

        @Override
        public boolean isFinished() {
            return inputStream.available() == 0;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener readlistener) {
        }

    }

}
