package com.ldh.coin.notification.service;

import com.ldh.coin.notification.common.CoinEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CoinServiceTest {
    private CoinService coinService;

    @Test
    public void getCoinInformationTest() {
        coinService.getCoinInformation(CoinEnum.CHROME, "C:\\WebDriver\\bin\\chromedriver.exe", "http://www.pbc.gov.cn/goutongjiaoliu/113456/113469/11040/index1.html");
    }

    @Autowired
    public void setCoinService(CoinService coinService) {
        this.coinService = coinService;
    }
}