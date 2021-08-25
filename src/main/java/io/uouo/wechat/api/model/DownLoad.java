package io.uouo.wechat.api.model;

import io.uouo.wechat.WeChatBot;
import io.uouo.wechat.api.enums.ApiURL;
import lombok.Data;

/**
 * 下载请求Model
 *
 * @author biezhi
 * @since 2018/1/21
 */
@Data
public class DownLoad {

    private ApiURL   apiURL;
    private String   suffix;
    private String   msgId;
    private Object[] params;
    private boolean  saveByDay;

    public DownLoad(ApiURL apiURL, String... params) {
        this.apiURL = apiURL;
        this.suffix = apiURL.getSuffix();
        this.params = params;
    }

    public DownLoad msgId(String msgId) {
        this.msgId = msgId;
        return this;
    }

    public DownLoad suffix(String suffix) {
        this.suffix = suffix;
        return this;
    }

    public DownLoad saveByDay() {
        this.saveByDay = true;
        return this;
    }

    public String getFileName() {
        return this.msgId + this.suffix;
    }

    public String getDir(WeChatBot bot) {
        return bot.config().assetsDir() + "/" + apiURL.getDir();
    }

}
