package PlaywrightLocatorTest;

import com.microsoft.playwright.*;

public class PlaywrightLocator {
    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) {


            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
         Page page = browser.newPage();


            page.navigate("https://www.amazon.com/");


            Locator searchInput = page.locator("input[id='twotabsearchtextbox']");


            searchInput.fill("Laptop");


            searchInput.press("Enter");


            page.waitForSelector(".s-main-slot"); // Wait for search results to appear


            String title = page.title();
            System.out.println("Page Title: " + title);
            String url = page.url();
            System.out.println("Page URL: " + url);


            Locator firstResult = page.locator(".s-main-slot .s-result-item").first();
            firstResult.click();


            page.waitForNavigation();

            System.out.println("After Clicking First Result:");
            System.out.println("New Page Title: " + page.title());
            System.out.println("New Page URL: " + page.url());


            browser.close();

            playwright.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
