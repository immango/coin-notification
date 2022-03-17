### 纪念币通知程序 coin-notification

##### 1. 背景

​	**Coin-Notification**是一个纪念币通知程序，简单来说是一个爬虫，定时爬取中国人民银行发布的纪念币预约通知，并将相关信息整合通知到订阅人员。

​	做这个的起因是因为我本人喜欢收集各种纪念币，于是做了这个爬虫抓取纪念币信息。

##### 2.设计

​	项目是一个Springboot的工程，技术上使用[Selenium](https://www.selenium.dev/zh-cn/documentation/)来实现模拟浏览器访问行为。

​	使用Selenium来做爬虫有一个好处是可以避免网站反爬虫，因为selenium是真实的模拟浏览器访问，同时不可避免带来一些性能上的损耗，由于Coin-Notification对实时性要求不高，性能上的部分损耗可以接受，并且可以很好配合Java语言使用。

​	爬虫设计思路也比较简单，首先获取对应页面的html，然后遍历DOM树，获取关键信息。程序上也是按照这个思路设计。

##### 3. 如何使用

###### 3.0 基本

​	项目是Springboot工程，可以直接将该项目clone至你的本地，再导入Idea中。项目默认使用Chrome作为浏览器，其余浏览器类似。

​	其中配置文件`application.properties`中的信息解释如下：

​	`coin.browser.type` 定义了浏览器类型，目前有可选的浏览器 Chrome/IE等等

​	`coin.driver.path` 定义了浏览器驱动的路径，其中*驱动版本需要和你本地安装的浏览器版本一致*。Chrome的[驱动下载地址](https://chromedriver.storage.googleapis.com/index.html )，如果访问不了可以通过国内的源来下载对应的驱动。注意：如果驱动版本和浏览器版本不一致则启动会报错。

​	`coin.pbc.baseUrl` 定义了默认访问的URL路径

​	`spring.mail.*` 则定义了邮件相关内容，邮件部分的疑问参考spring的邮件解决方案。

​	同时，程序预留了一个人工干预接口 `/api/v1/manual_intervention`通过访问这个接口可以手动触发爬虫程序。当然程序也预留一个**<u>单机</u>**定时任务，修改`@Scheduled`注解中的cron表达式以达到你想要的目的。



###### 3.1 Window环境下使用

​	在windows环境中需要安装Google Chrome浏览器, 并下载对应的驱动程序放在对应的目录下。

​	配置`application.properties`中的配置项后，正常启动项目，统计人工干预接口即可观察程序运行状态。

###### 3.2 Linux环境下使用

​	在Linux中的使用和Windows环境下类似,但是安装Chrome稍有不同。

​	3.2.1 Centos7安装Chrome

```
1. 配置yum下载源：
在目录 /etc/yum.repos.d/ 下新建文件 google-chrome.repo

2. 编辑google-chrome.repo，内容如下，编辑后保存退出(：wq)
[google-chrome]
name=google-chrome
baseurl=http://dl.google.com/linux/chrome/rpm/stable/$basearch
enabled=1
gpgcheck=1
gpgkey=https://dl-ssl.google.com/linux/linux_signing_key.pub

3. 安装google chrome浏览器
yum -y install google-chrome-stable --nogpgcheck

4. 查看浏览器版本
google-chrome --version

5. 启动google-chrome
默认安装好google-chrome后需要修改/usr/bin/google-chrome
vim /usr/bin/google-chrome
并在exec -a "$0" "$HERE/chrome" "$@" 这一句后加上 --no-sandbox
即exec -a "$0" "$HERE/chrome" "$@" --no-sandbox 这样可以保证你在root用户下可以正常启动chrome

最后执行/usr/bin/google-chrome

```

3.2.2 下载对应的chrome driver并放置在指定路径，并且在配置文件中修改对应的参数

3.2.3 启动项目

​	使用本地打出来的jar包，`nohup java -jar coin-notification-0.0.1-SNAPSHOT.jar > coin-notification.log 2>&1 &`启动该jar包，访问人工干预接口。

此时你如果遇到这个问题：`[org.openqa.selenium.WebDriverException: unknown error: DevToolsActivePort file doesn't exist]`，请将你本地的chrome driver进程全部杀出，重新执行/usr/bin/google-chrome，重新启动项目即可。



启动成结果：

```
...
2022-03-17 17:52:46.897  INFO 3214 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
...
2022-03-17 17:55:30.356  INFO 3214 --- [nio-8080-exec-1] com.ldh.coin.notification.api.Api        : 接口主动触发纪念币程序
2022-03-17 17:55:30.357  INFO 3214 --- [nio-8080-exec-1] c.l.c.n.s.CoinNotificationServiceSync    : 纪念币定时任务已启动: 2022-03-17T17:55:30.357178
Starting ChromeDriver 99.0.4844.51 (d537ec02474b5afe23684e7963d538896c63ac77-refs/branch-heads/4844@{#875}) on port 20441
Only local connections are allowed.
Please see https://chromedriver.chromium.org/security-considerations for suggestions on keeping ChromeDriver safe.
ChromeDriver was started successfully.
2022-03-17 17:55:31.593  INFO 3214 --- [ null to remote] o.o.selenium.remote.ProtocolHandshake    : Detected dialect: W3C
Starting ChromeDriver 99.0.4844.51 (d537ec02474b5afe23684e7963d538896c63ac77-refs/branch-heads/4844@{#875}) on port 30430
Only local connections are allowed.
Please see https://chromedriver.chromium.org/security-considerations for suggestions on keeping ChromeDriver safe.
ChromeDriver was started successfully.
2022-03-17 17:55:34.023  INFO 3214 --- [ null to remote] o.o.selenium.remote.ProtocolHandshake    : Detected dialect: W3C
2022-03-17 17:55:38.230  INFO 3214 --- [nio-8080-exec-1] c.l.c.n.alarm.email.EmailAlarmImpl       : 通知邮件已发送，通知人员为: [xxx@qq.com;]
2022-03-17 17:55:38.230  INFO 3214 --- [nio-8080-exec-1] c.l.c.notification.service.CoinService   : 已检测到纪念币相关信息，已发送对应事件。简略信息为: 中国人民银行公告〔2021〕第23号
2022-03-17 17:55:38.807  INFO 3214 --- [nio-8080-exec-1] c.l.c.notification.service.CoinService   : 当前页面分析结束: http://www.pbc.gov.cn/goutongjiaoliu/113456/113469/11040/index7.html
```



##### 4. 其他

请注意本项目为学习使用，但你可以随意修改项目代码并且新增其他功能。

License ： GPL