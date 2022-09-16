
package io.uouo.wechat.api.request;

import io.uouo.wechat.api.constant.Constant;
import io.uouo.wechat.api.response.ApiResponse;
import lombok.Getter;
import okhttp3.Headers;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * 基础请求
 *
 * @author biezhi
 */
@Getter
public abstract class ApiRequest<T extends ApiRequest, R extends ApiResponse> {

    protected int timeout = 10;
    protected boolean noRedirect;
    protected boolean jsonBody;
    protected boolean multipart;

    protected String url;
    protected String method = "GET";
    protected String fileName;
    protected String contentType = "application/x-www-form-urlencoded";
    protected Headers headers;
    @SuppressWarnings("unchecked")
    protected final T thisAsT = (T) this;
    private final Class<? extends R>  responseClass;
    private final Map<String, Object> parameters;
    private final String UOS_PATCH_EXTSPAM = "Go8FCIkFEokFCggwMDAwMDAwMRAGGvAESySibk50w5Wb3uTl2c2h64jVVrV7gNs06GFlWplHQbY/5FfiO++1yH4ykCyNPWKXmco+wfQzK5R98D3so7rJ5LmGFvBLjGceleySrc3SOf2Pc1gVehzJgODeS0lDL3/I/0S2SSE98YgKleq6Uqx6ndTy9yaL9qFxJL7eiA/R3SEfTaW1SBoSITIu+EEkXff+Pv8NHOk7N57rcGk1w0ZzRrQDkXTOXFN2iHYIzAAZPIOY45Lsh+A4slpgnDiaOvRtlQYCt97nmPLuTipOJ8Qc5pM7ZsOsAPPrCQL7nK0I7aPrFDF0q4ziUUKettzW8MrAaiVfmbD1/VkmLNVqqZVvBCtRblXb5FHmtS8FxnqCzYP4WFvz3T0TcrOqwLX1M/DQvcHaGGw0B0y4bZMs7lVScGBFxMj3vbFi2SRKbKhaitxHfYHAOAa0X7/MSS0RNAjdwoyGHeOepXOKY+h3iHeqCvgOH6LOifdHf/1aaZNwSkGotYnYScW8Yx63LnSwba7+hESrtPa/huRmB9KWvMCKbDThL/nne14hnL277EDCSocPu3rOSYjuB9gKSOdVmWsj9Dxb/iZIe+S6AiG29Esm+/eUacSba0k8wn5HhHg9d4tIcixrxveflc8vi2/wNQGVFNsGO6tB5WF0xf/plngOvQ1/ivGV/C1Qpdhzznh0ExAVJ6dwzNg7qIEBaw+BzTJTUuRcPk92Sn6QDn2Pu3mpONaEumacjW4w6ipPnPw+g2TfywJjeEcpSZaP4Q3YV5HG8D6UjWA4GSkBKculWpdCMadx0usMomsSS/74QgpYqcPkmamB4nVv1JxczYITIqItIKjD35IGKAUwAA==";
    public ApiRequest(String url, Class<? extends R> responseClass) {
        this.url = url;
        this.responseClass = responseClass;
        this.parameters = new HashMap<>();
        this.headers = Headers.of(
                "User-Agent", Constant.USER_AGENT,
                "Content-Type", this.contentType,
                "client-version", "2.0.0",
                "extspam", UOS_PATCH_EXTSPAM,
                "referer", "https://wx.qq.com/?&lang=zh_CN&target=t"
        );
    }

    public T header(String name, String value) {
        this.headers = this.headers.newBuilder().set(name, value).build();
        return thisAsT;
    }

    public T add(String name, Object val) {
        parameters.put(name, val);
        return thisAsT;
    }

    public T noRedirect() {
        this.noRedirect = true;
        return thisAsT;
    }

    public T multipart() {
        this.multipart = true;
        return thisAsT;
    }

    public Type getResponseType() {
        return responseClass;
    }

    public T url(String url) {
        this.url = url;
        return thisAsT;
    }

    public T timeout(int seconds) {
        this.timeout = seconds;
        return thisAsT;
    }

    public T fileName(String fileName) {
        this.fileName = fileName;
        return thisAsT;
    }

    public T post() {
        this.method = "POST";
        return thisAsT;
    }

    public T jsonBody() {
        this.jsonBody = true;
        this.contentType = "application/json; charset=UTF-8";
        this.header("Content-Type", this.contentType);
        return thisAsT;
    }

}