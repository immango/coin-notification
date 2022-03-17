package com.ldh.coin.notification.service;

import com.ldh.coin.notification.alarm.event.CoinEvent;
import com.ldh.coin.notification.common.CoinEnum;
import com.ldh.coin.notification.entity.AnnouncementEntity;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @author mango
 * @description 有关纪念币的相关服务
 * @date 2021/1/31 11:57
 */
@Service
public class CoinService {

    /**
     * 匹配次数，如果超过该数量代表这个公告是有关纪念币
     */
    private int matchCoinCount = 3;
    private ApplicationEventPublisher publisher;
    private final Logger log = LoggerFactory.getLogger(CoinService.class);

    public void getCoinInformation(CoinEnum browserType, String driverPath, String url) {
        WebDriver webDriver = getPageDetail(browserType, driverPath, url);
        try {
            // 获取原始html数据
            webDriver.get(url);
            // 对html数据进行处理，获取到对应的公告板块
            WebElement element = webDriver.findElement(By.xpath("//*[@id=\"11040\"]/div[2]/div[1]/table/tbody/tr[2]/td"));
            List<WebElement> table = element.findElements(By.cssSelector("table"));
            // 针对每一条公告进行处理
            for (WebElement e : table) {
                coinHelper(browserType, driverPath, e);
            }
            log.info("当前页面分析结束: {}", url);
        }catch (Exception e) {
            log.error("获取原始页面数据发生异常: ", e);
        }finally {
            webDriver.quit();
        }

    }
    /**
     * 针对 中国人民银行-新闻发布一栏的内容
     * 查找有关纪念币的公告
     * @param browserType 浏览器类型
     * @param driverPath 驱动路径
     * @param element element
     */
    public void coinHelper(CoinEnum browserType, String driverPath, WebElement element) {
        WebElement a = element.findElement(By.cssSelector("a"));
        WebElement span = element.findElement(By.cssSelector("span"));

        // 公告标题
        String detailTitle = a.getText();
        // 公告地址
        String detailUrl = a.getAttribute("href");
        // 公告发出时间
        String detailDate = span.getText();

        // 有关纪念币公告的标题有固定样式: 中国人民银行公告〔2021〕第1号
        // 于是先根据公告标题进行一轮筛选,筛选规则即为标题是否匹配到 '中国人民银行公告'
        String pattern = ".*中国人民银行公告.*";
        boolean matches = Pattern.matches(pattern, detailTitle);

        if (matches) {
            // 抓取公告内容, 并进一步判断是否为纪念币公告
            WebDriver webDriver = getPageDetail(browserType, driverPath, detailUrl);
            AnnouncementEntity announcementEntity = new AnnouncementEntity();
            coinAnnouncement(webDriver, announcementEntity);

            // 发送事件
            if (announcementEntity.isCoin()) {
                announcementEntity.setUrl(detailUrl);
                announcementEntity.setTitle(detailTitle);
                announcementEntity.setDate(detailDate);
                publisher.publishEvent(new CoinEvent(announcementEntity));
                log.info("已检测到纪念币相关信息，已发送对应事件。简略信息为: {}", announcementEntity.getTitle());
            }
        }
    }

    protected void coinAnnouncement(WebDriver webDriver, AnnouncementEntity announcementEntity) {
        StringBuilder announcementDetail = new StringBuilder();
        int countOfCoin = 0;
        announcementEntity.setCoin(false);
        try {
            WebElement zoom = webDriver.findElement(By.id("zoom"));
            List<WebElement> pList = zoom.findElements(By.cssSelector("p"));

            for (WebElement element : pList) {
                String attribute = "";
                try {
                    attribute = element.findElement(By.cssSelector("a")).getAttribute("href");
                }catch (Exception e2) {
                    // p标签有些有超链接有些没有，如果此处有异常，说明当前p标签没有超链接
                }

                // 统计公告内每个p标签是否含有'纪念币'字样
                if (element.getText().contains("纪念币")) {
                    countOfCoin++;
                }
                announcementDetail.append(element.getText()).append(" ").append(attribute).append("\n");
            }

            announcementEntity.setDetail(announcementDetail.toString());
            // 数量超过3代表是纪念币文章
            announcementEntity.setCoin(countOfCoin >= matchCoinCount);

        }catch (Exception e) {
            log.error("解析HTML数据发生异常: ", e);
        }finally {
            webDriver.quit();
        }
    }


    protected WebDriver getPageDetail(CoinEnum browserType, String driverPath, String url) {
        WebDriver webDriver = getWebDriver(browserType, driverPath);
        webDriver.get(url);
        return webDriver;
    }

    protected WebDriver getWebDriver(CoinEnum browserType, String driverPath) {
        System.setProperty("webdriver.chrome.driver", driverPath);
        WebDriver webDriver = null;
        try {
            switch (browserType) {
                case CHROME: {
                    ChromeOptions chromeOptions = new ChromeOptions();
                    // 不开启界面
                    chromeOptions.addArguments("--headless");
                    // 图形化界面下启用关闭沙箱模式 解决DevToolsActivePort文件不存在报错问题
                    chromeOptions.addArguments("--no-sandbox");
                    // 禁用GPU硬件加速。如果软件渲染器没有就位，则GPU进程将不会启动
                    chromeOptions.addArguments("--disable-gpu");
                    // 不加载图片 获得速度优化
                    chromeOptions.addArguments("blink-settings=imagesEnabled=false");
                    webDriver = new ChromeDriver(chromeOptions);
                    break;
                }
                case FIREFOX: {
                    webDriver = new FirefoxDriver();
                    break;
                }
                case Edge: {
                    webDriver = new EdgeDriver();
                    break;
                }
                case OPERA: {
                    webDriver = new OperaDriver();
                    break;
                }
                case SAFARI: {
                    webDriver = new SafariDriver();
                    break;
                }
                case IE: {
                    webDriver = new InternetExplorerDriver();
                    break;
                }
                default: {
                    break;
                }
            }
        }catch (Exception e) {
            // 创建对应浏览器发生异常
            log.error("构建对应的浏览器发生异常, 当前驱动版本为: {}, 当前驱动路径为: {}",browserType, driverPath, e);
        }
        return webDriver;
    }

    @Autowired
    public void setPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }
}
