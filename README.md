# 重大更新!!   
#   解决个人微信号无法登录网页版本的问题, 现已全面支持所有微信号

**基于微信网页版JAVA项目, 升级为UOS协议, 解决大量用户不能登录网页版的问题**             [原项目地址](https://github.com/biezhi/wechat-api)              [原版本在线文档](https://biezhi.github.io/wechat-api/)



## 本分支 TODO

佛系更新  (公司业务不忙的情况下)

> 1. 解决首次使用需要扫码两次的问题(已有解决思路)
> 2. extspam参数自动生成变换
> 3. 完成原版未开发完成的开发任务
> 4. 完成本分支的开发文档(估计和原版本也不会有太大的差距.. 毕竟接口名什么的我都不会去动,防止之前的用户二次熟悉本项目)
> 5. 重写出一个新的Golang版本



## 原分支 TODO

> 1. 接收位置
> 2. 撤回消息查看
> 3. 发送文件消息
> 4. 多线程处理消息

## 特性

- 使用简单，引入依赖即可

- 支持本地图片和终端输出二维码

- 本地自动登录

- 支持文本、图片、视频、撤回消息等

- 支持发送文本、图片、附件

- 注解绑定消息监听

- 群聊、单聊支持

- 添加好友验证

- 撤回消息获取

- JDK7+

## 使用

本地开发的同学请先安装 [lombok](https://projectlombok.org/) 插件并确保你的JDK环境是1.7+

引入 `maven` 依赖 

```xml
<dependency>
  <groupId>io.uouo</groupId>
  <artifactId>wechat-api-uouo</artifactId>
  <version>1.0.0</version>
</dependency>
```

构建自己的小机器人

```java
public class HelloBot extends WeChatBot {
    
    public HelloBot(Config config) {
        super(config);
    }
    
    @Bind(msgType = MsgType.TEXT)
    public void handleText(WeChatMessage message) {
        if (StringUtils.isNotEmpty(message.getName())) {
            log.info("接收到 [{}] 的消息: {}", message.getName(), message.getText());
            this.sendMsg(message.getFromUserName(), "自动回复: " + message.getText());
        }
    }
    
    public static void main(String[] args) {
        new HelloBot(Config.me().autoLogin(true).showTerminal(true)).start();
    }
    
}
```

## Bot API

```java
/**
 * 给文件助手发送消息
 *
 * @param msg 消息内容
 * @return 发送是否成功
 */
boolean sendMsgToFileHelper(String msg);

/**
 * 给某个用户发送消息
 *
 * @param name 用户UserName
 * @param msg  消息内容
 * @return 发送是否成功
 */
boolean sendMsg(String name, String msg);

/**
 * 根据名称发送消息
 *
 * @param name 备注或昵称，精确匹配
 * @param msg  消息内容
 * @return 发送是否成功
 */
boolean sendMsgByName(String name, String msg);

/**
 * 给某个用户发送图片消息
 *
 * @param name    用户UserName
 * @param imgPath 图片路径
 * @return 发送是否成功
 */
boolean sendImg(String name, String imgPath);

/**
 * 根据名称发送图片消息
 *
 * @param name    备注或昵称，精确匹配
 * @param imgPath 图片路径
 * @return 发送是否成功
 */
boolean sendImgName(String name, String imgPath);

/**
 * 给用户发送文件
 *
 * @param name     用户UserName
 * @param filePath 文件路径
 * @return 发送是否成功
 */
boolean sendFile(String name, String filePath);

/**
 * 根据名称发送文件消息
 *
 * @param name     备注或昵称，精确匹配
 * @param filePath 文件路径
 * @return 发送是否成功
 */
boolean sendFileName(String name, String filePath);
```

[更多API见文档](https://biezhi.github.io/wechat-api/#/?id=api%e5%88%97%e8%a1%a8)

## deploy jar
```shell script
mvn clean deploy -P sonatype-oss-release -Darguments="gpg.passphrase=19950120" -s /Users/linrol/work/ide/maven/apache-maven-3.6.0/conf/settings_aliyun.xml
```

## 开源协议

原项目开源协议[MIT](https://github.com/biezhi/wechat-api/blob/master/LICENSE) 

当前分支开源协议[Mozilla ](https://github.com/UoUoio/WeChat-API-UoUo/blob/master/LICENSE)   "使用时请注意声明作者名称:  **青衫 / UoUoio**"