package ru.t_systems.autotest.multilayer.app;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.t_systems.autotest.multilayer.pages.MainPage;
import ru.t_systems.autotest.multilayer.pages.ProductPage;
import ru.t_systems.autotest.multilayer.pages.ShoppingCartPage;

public class Application {
    private WebDriver driver;

    public MainPage mainPage;
    public ProductPage productPage;
    public ShoppingCartPage shoppingCartPage;

    public Application() {
        driver = new ChromeDriver();

        mainPage = new MainPage(driver);
        productPage = new ProductPage(driver);
        shoppingCartPage = new ShoppingCartPage(driver);
    }

    public void quit() {
        driver.quit();
        driver = null;
    }
}
