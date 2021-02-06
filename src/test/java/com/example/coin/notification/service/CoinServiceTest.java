package com.example.coin.notification.service;

import com.example.coin.notification.common.CoinEnum;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoinServiceTest {

    @Test
    void getHtmlWithUrl() {
        CoinService coinService = new CoinService();
        coinService.getCoinInformation(CoinEnum.CHROME, "C:\\WebDriver\\bin\\chromedriver.exe", "http://www.pbc.gov.cn/goutongjiaoliu/113456/113469/11040/index1.html");
    }

}