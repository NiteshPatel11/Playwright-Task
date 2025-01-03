package RentomaticUI;

import com.microsoft.playwright.*;

public class RentomaticTest {
    public static void main(String[]args){
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
            BrowserContext context = browser.newContext();
            Page page = context.newPage();

            // Navigate to Rentomatic UI
            page.navigate("https://rentomaticui.vercel.app/");

            // Create an instance of RentomaticHomePage
            HomePageObject homePage = new HomePageObject(page);

            // Interact with the page
            homePage.enterSearchQuery("New York");
            homePage.clickSearchButton();

            if (homePage.arePropertyCardsDisplayed()) {
                System.out.println("Properties are displayed successfully.");
            } else {
                System.out.println("No properties displayed.");
            }

            // Navigate to Login
            homePage.clickLoginButton();
        }
    }
}
