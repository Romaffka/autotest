package ru.t_systems.autotest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ProductDetails {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
        driver.get("http://localhost:9080/litecart/");
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }

    @Test
    public void checkProductDetails() {
        List<WebElement> products = driver.findElements(By.cssSelector("#box-campaigns .product"));
        assertFalse("no products", products.isEmpty());

        for(int i = 0; i < products.size(); i++) {
            WebElement titleOnMainPage = products.get(i).findElement(By.cssSelector(".name"));
            String textTitleOnMainPage = titleOnMainPage.getText();

            WebElement regularPriceOnMainPage = products.get(i).findElement(By.cssSelector(".regular-price"));
            String textRegularPriceOnMainPage = regularPriceOnMainPage.getText();

            WebElement discountPriceOnMainPage = products.get(i).findElement(By.cssSelector(".campaign-price"));
            String textDiscountPriceOnMainPage = discountPriceOnMainPage.getText();

            checkRegularPrice(regularPriceOnMainPage);
            checkDiscountPrice(discountPriceOnMainPage);
            comparePriceFontSize(regularPriceOnMainPage, discountPriceOnMainPage);

            products.get(i).click();

            WebElement titleOnDetailsPage = driver.findElement(By.cssSelector("#box-product .title"));
            String textTitleOnDetailsPage = titleOnDetailsPage.getText();

            WebElement regularPriceOnDetailsPage = driver.findElement(By.cssSelector((".regular-price")));
            String textRegularPriceOnDetailsPage = regularPriceOnDetailsPage.getText();

            WebElement discountPriceOnDetailsPage = driver.findElement(By.cssSelector(".campaign-price"));
            String textDiscountPriceOnDetailsPage = discountPriceOnDetailsPage.getText();

            checkRegularPrice(regularPriceOnDetailsPage);
            checkDiscountPrice(discountPriceOnDetailsPage);
            comparePriceFontSize(regularPriceOnDetailsPage, discountPriceOnDetailsPage);

            assertEquals("Titles are not equal ", textTitleOnMainPage, textTitleOnDetailsPage);
            assertEquals("Usual prices are not equal ", textRegularPriceOnMainPage, textRegularPriceOnDetailsPage);
            assertEquals("Discount prices are not equal ", textDiscountPriceOnMainPage, textDiscountPriceOnDetailsPage);

            driver.navigate().back();

            products = driver.findElements(By.cssSelector("#box-campaigns .product"));
        }
    }

    private void checkRegularPrice(WebElement regularPrice) {
        String colorRegularPrice = regularPrice.getCssValue("color");
        checkGreyColor(colorRegularPrice);

        String decorationRegularPrice = regularPrice.getCssValue("text-decoration-line");
        assertEquals("decoration is not line-through", "line-through", decorationRegularPrice);
    }

    private void checkDiscountPrice(WebElement discountPrice) {
        String colorDiscountPrice = discountPrice.getCssValue("color");
        checkRedColor(colorDiscountPrice);

        String fontWeight = discountPrice.getCssValue("font-weight");
        checkBold(fontWeight);
    }

    private void comparePriceFontSize(WebElement regularPriceOnMainPage, WebElement discountPriceOnMainPage) {
        String fontSizeRegularPrice = regularPriceOnMainPage.getCssValue("font-size");
        String fontSizeDiscountPrice = discountPriceOnMainPage.getCssValue("font-size");
        fontSizeRegularPrice = fontSizeRegularPrice.substring(0, fontSizeRegularPrice.length() - 2);
        fontSizeDiscountPrice = fontSizeDiscountPrice.substring(0, fontSizeDiscountPrice.length() - 2);
        double regularPrice = Double.parseDouble(fontSizeRegularPrice);
        double discountPrice = Double.parseDouble(fontSizeDiscountPrice);
        assertTrue(regularPrice < discountPrice);
    }

    private void checkBold(String fontWeight) {
        int weight = Integer.parseInt(fontWeight);
        assertTrue("font is not bold ", weight > 400);
    }


    private void checkRedColor(String color) {
        List<Integer> rgb = getRGB(color);
        assertTrue(rgb.get(0) != 0 && rgb.get(1) == 0 && rgb.get(2) == 0);
    }

    private void checkGreyColor(String color) {
        List<Integer> rgb = getRGB(color);
        assertTrue(rgb.get(0).equals(rgb.get(1)) && rgb.get(1).equals(rgb.get(2)));
    }

    private List<Integer> getRGB(String rgb) {
        List<Integer> colors = new ArrayList<>();
        Pattern regexp = Pattern.compile("(\\d+)");
        Matcher m = regexp.matcher(rgb);
        while(m.find())
        {
            colors.add(Integer.parseInt(m.group(1)));
        }
        return colors;
    }
}

