package PlaywrightLocatorTest;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;

public class PlaywrightLocator {
    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) {
            // Launch browser with timeout and viewport settings
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(false)
                .setTimeout(30000));
            
            BrowserContext context = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(1920, 1080));
            
            Page page = context.newPage();

            // Navigate with timeout
            page.navigate("https://www.amazon.com/", 
                new Page.NavigateOptions().setTimeout(30000));

            // Add explicit wait for page load
            page.waitForLoadState(LoadState.NETWORKIDLE);

            // Search for laptop
            Locator searchInput = page.locator("input[id='twotabsearchtextbox']");
            searchInput.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            searchInput.fill("Laptop");
            searchInput.press("Enter");

            // Wait for search results with better selector
            page.waitForSelector(".s-main-slot", 
                new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE));

            // Log page info
            System.out.println("Page Title: " + page.title());
            System.out.println("Page URL: " + page.url());

            // Click first result with proper wait
            Locator firstResult = page.locator(".s-main-slot .s-result-item:not(.AdHolder)").first();
            firstResult.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            
            // Click with Promise.all to handle navigation
            page.waitForNavigation(() -> {
                firstResult.click();
            });

            System.out.println("After Clicking First Result:");
            System.out.println("New Page Title: " + page.title());
            System.out.println("New Page URL: " + page.url());

            // Close in reverse order
            context.close();
            browser.close();

        } catch (PlaywrightException e) {
            System.err.println("Playwright error: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
