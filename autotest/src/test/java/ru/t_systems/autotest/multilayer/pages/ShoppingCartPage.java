package ru.t_systems.autotest.multilayer.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.stalenessOf;

public class ShoppingCartPage extends Page {
    @FindBy(css = "[name='remove_cart_item']")
    private WebElement removeButton;

    @FindBy(css = "[name='confirm_order']")
    private List<WebElement> confirmButton;

    public ShoppingCartPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public ShoppingCartPage removeAllItemsFromShoppingCart(){
        while (confirmButton.size() != 0) {
            List<WebElement> tr = driver.findElements(By.cssSelector("#box-checkout-summary tr"));
            removeButton.click();
            if (tr.size() > 0) {
                wait.until(stalenessOf(tr.get(0)));
            }
        }
        return this;
    }
}
