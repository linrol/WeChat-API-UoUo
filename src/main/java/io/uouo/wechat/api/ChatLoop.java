package io.uouo.wechat.api;

import io.uouo.wechat.WeChatBot;
import io.uouo.wechat.api.model.Message;
import io.uouo.wechat.api.model.SyncCheckRet;
import io.uouo.wechat.api.response.WebSyncResponse;
import io.uouo.wechat.utils.DateUtils;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

import static io.uouo.wechat.api.enums.RetCode.*;

/**
 * 轮训监听消息
 *
 * @author biezhi
 * @since 2018/1/21
 */
@Slf4j
public class ChatLoop implements Runnable {

    private WeChatBot bot;
    private WeChatApi api;
    private int retryCount = 0;

    ChatLoop(WeChatBot bot) {
        this.bot = bot;
        this.api = bot.api();
    }

    @Override
    public void run() {
        while (bot.isRunning()) {
            try {
                SyncCheckRet syncCheckRet = api.syncCheck();
                if (syncCheckRet.getRetCode() == UNKNOWN) {
                    log.info("未知状态");
                    continue;
                } else if (syncCheckRet.getRetCode() == MOBILE_LOGIN_OUT) {
                    log.info("你在手机上登出了微信，再见");
                    api.logout();
                    break;
                } else if (syncCheckRet.getRetCode() == LOGIN_OTHERWISE) {
                    log.info("你在其他地方登录了 WEB 版微信，再见");
                    api.logout();
                    break;
                } else if (syncCheckRet.getRetCode() == NORMAL) {
                    // 更新最后一次正常检查时间
                    bot.updateLastCheck();
                    switch (syncCheckRet.getSelector()) {
                        case 2:
                        case 6:
                            WebSyncResponse webSyncResponse = api.webSync();
                            bot.writeLoginSession();
                            if (null == webSyncResponse) {
                                break;
                            }
                            List<Message> messages = webSyncResponse.getAddMessageList();
                            if(6 == syncCheckRet.getSelector() && null !=  messages && messages.size() > 0) {
                                messages.forEach(message -> {
                                    message.setType(51);
                                });
                            }
                            bot.addMessages(api.handleMsg(messages));
                            break;
                        default:
                            break;
                    }
                }
                if (System.currentTimeMillis() - bot.getLastCheckTs() <= 30) {
                    DateUtils.sleep(System.currentTimeMillis() - bot.getLastCheckTs());
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                retryCount += 1;
                if (bot.getReceiveRetryCount() < retryCount) {
                    api.logout();
                } else {
                    DateUtils.sleep(1000);
                }
            }
        }
    }
}
