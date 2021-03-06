package net.serenitybdd.samples.etsy.pages;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.annotations.findby.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static net.serenitybdd.samples.etsy.pages.Spinners.noSpinnerToBeVisible;

public class ItemDetailsPage extends PageObject {

    @FindBys({@FindBy(id="listing-page-cart"), @FindBy(tagName = "h1")})
    WebElement itemName;

    public String getItemName() {
        return itemName.getText();
    }

    public String getItemDescription() {
        return $("#description-text").getText();
    }

    public void addToCart() {
        withAction().moveToElement($("#item-tabs")).perform();
        $(".buy-button button").click();
    }

    public List<String> getProductVariationIds() {
        return findAll(".variation")
                .stream()
                .map(elt -> elt.getAttribute("id"))
                .filter(id -> !id.isEmpty())
                .collect(toList());
    }

    public void selectVariation(String variationId, int optionIndex) {
        find(By.id(variationId)).selectByIndex(optionIndex);
        if (spinnerIsVisible()) {
            waitFor(noSpinnerToBeVisible());
        }
    }

    private boolean spinnerIsVisible() {
        return containsElements(".spinner-small");
    }
}
