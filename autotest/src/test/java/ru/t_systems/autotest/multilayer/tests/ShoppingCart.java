package ru.t_systems.autotest.multilayer.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.t_systems.autotest.multilayer.app.Application;

public class ShoppingCart {
    private Application app;

    @Before
    public void start() {
        app = new Application();
    }

    @After
    public void stop() {
        app.quit();
        app = null;
    }

    @Test
    public void AddAndDeleteProduct() {
        app.mainPage.openMainPage();
        for (int i = 0; i < 3; i++) {
            app.mainPage.goToProductPage();
            app.productPage.selectSizeIfExist()
                    .addProduct()
                    .backToMainPage();
        }

        app.mainPage.goToShoppingCartPage();
        app.shoppingCartPage.removeAllItemsFromShoppingCart();
    }
}
