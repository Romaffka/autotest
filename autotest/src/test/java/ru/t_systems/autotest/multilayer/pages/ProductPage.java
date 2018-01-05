package ru.t_systems.autotest.multilayer.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class ProductPage extends Page {

    @FindBy(css = "[name='add_cart_product']")
    private WebElement addProduct;

    @FindBy(css = "[title='My Store']")
    private WebElement mainPageLink;

    @FindBy(css = "[name='options[Size]']")
    private List<WebElement> options;

    @FindBy(css = ".quantity")
    private WebElement quantity;

    public ProductPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public ProductPage selectSizeIfExist() {
        if (options.size() > 0) {
            Select select = new Select(options.get(0));
            select.selectByIndex(1);
        }
        return this;
    }

    public ProductPage addProduct() {
        String count = quantity.getText();
        addProduct.click();
        wait.until((d) -> !quantity.getText().equals(count));
        return this;
    }

    public ProductPage backToMainPage() {
        mainPageLink.click();
        return this;
    }
}
