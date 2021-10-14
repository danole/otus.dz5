import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

public class Homework {

    private Logger logger = LogManager.getLogger(Homework.class.getName());
    protected static WebDriver driver;

    @Before
    public void StartUp() {
        WebDriverManager.chromedriver().setup();
        logger.info("Драйвер поднят");
    }

    @After
    public void End() {
        if (driver != null) {
            driver.quit();
        }
    }

    /* Открыть Chrome в headless режиме
    Перейти на https://duckduckgo.com/
    В поисковую строку ввести ОТУС
    Проверить что в поисковой выдаче первый результат Онлайн‑курсы для профессионалов, дистанционное обучение. */

    @Test
    public void findOtusTest() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
        driver.get("https://duckduckgo.com/");
        logger.info("Зашел на сайт");
        WebElement input = driver.findElement(By.xpath("//input[@placeholder='Онлайн-поиск без слежки']"));
        input.sendKeys("Отус");
        input.submit();
        logger.info("Введено отус в поисковую строку");
        WebElement otusLink = driver.findElement(By.xpath("//div[contains(@class,'results_links_deep')][1]/div/h2/a"));
        String textFromOtusLink = otusLink.getText();
        Assert.assertEquals("Онлайн‑курсы для профессионалов, дистанционное обучение...", textFromOtusLink);
        logger.info("Текст 1ой ссылки совпадает с ожидаемым");
    }

    /*Открыть Chrome в режиме киоска
    Перейти на https://demo.w3layouts.com/demos_new/template_demo/03-10-2020/photoflash-liberty-demo_Free/685659620/web/index.html?_ga=2.181802926.889871791.1632394818-2083132868.1632394818
    Нажать на любую картинку
    Проверить что картинка открылась в модальном окне*/

    @Test
    public void modalWindowTest() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
        String link = "https://demo.w3layouts.com/demos_new/template_demo/03-10-2020/photoflash-liberty-demo_Free/" +
                "685659620/web/index.html?_ga=2.181802926.889871791.1632394818-2083132868.1632394818";
        driver.manage().window().fullscreen();
        logger.info("Браузер открылся в режиме киоска");
        driver.get(link);
        logger.info("Перешел на ссылку");
        WebElement image = driver.findElement(By.xpath("//div[@class='content-overlay'][1]"));
        image.click();
        logger.info("Нажал на 1ую картинку");
        WebElement modalWindow = driver.findElement(By.xpath("//div[@class='pp_hoverContainer']"));
        Assert.assertEquals("pp_hoverContainer", modalWindow.getAttribute("class"));
        logger.info("Появилось модальное окно");
    }

    /*Открыть Chrome в режиме полного экрана
    Перейти на https://otus.ru
    Авторизоваться под каким-нибудь тестовым пользователем(можно создать нового)
    Вывести в лог все cookie*/

    @Test
    public void outputCookieTest() throws InterruptedException {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
        driver.get("https://otus.ru");
        logger.info("Перешел на сайт Отус");
        WebElement button = driver.findElement(By.xpath("//button[contains(text(),'Вход')]"));
        button.click();
        WebElement email = driver.findElement(By.xpath("//input[@type='text' and @placeholder='Электронная почта']"));
        email.sendKeys("email");
        WebElement password = driver.findElement(By.name("password"));
        password.sendKeys("password");
        WebElement submit = driver.findElement(By.xpath("//button[contains(text(),'Войти')]"));
        submit.submit();
        logger.info("Авторизовался");
        logger.info(driver.manage().getCookies());
        logger.info("Получил все куки");
    }

}
