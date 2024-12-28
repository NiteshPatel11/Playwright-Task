package RentomaticUI;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class HomePageObject {
    private  final Page page;
    private final Locator searchInput;
    private final Locator searchButton;
    private final Locator propertyCards;
    private final Locator loginButton;
    public HomePageObject(Page page) {
        this.page = page;
        this.searchInput = page.locator("input[placeholder='Search for properties']");
        this.searchButton = page.locator("button:has-text('Search')");
        this.propertyCards = page.locator(".property-card"); // Replace with actual class or selector
        this.loginButton = page.locator("a[href='/login']");
    }

    public void enterSearchQuery(String query) {
        searchInput.fill(query);
    }

    public void clickSearchButton() {
        searchButton.click();
    }

    public boolean arePropertyCardsDisplayed() {
        return propertyCards.isVisible();
    }

    public void clickLoginButton() {
        loginButton.click();
    }


}
