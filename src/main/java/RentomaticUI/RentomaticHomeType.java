package RentomaticUI;

import com.microsoft.playwright.*;
import org.junit.Test;

public class RentomaticHomeType {


    public static void main(String[]args){
        try(Playwright playwright = Playwright.create()){
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            BrowserContext context = browser.newContext();
            Page page = context.newPage();

            page.navigate("https://rentomaticui.vercel.app/homeType");
            System.out.println("Page loaded: " + page.title());
            Locator sectionHeader = page.locator("text=Let’s get started! Pick the type of home you live in");
            if (sectionHeader.isVisible()) {
                System.out.println("Section header is visible.");
            } else {
                System.err.println("Section header not found.");
            }

            Locator singleFamilyOption = page.locator("text=Single family");
            if (singleFamilyOption.isVisible()) {
                singleFamilyOption.click();
                System.out.println("'Single family' option clicked.");
            } else {
                System.err.println("'Single family' option not found.");
            }

            Locator selectedIndicator = page.locator("selector-for-selected-home-type"); // Adjust this to the actual selector if needed
            if (selectedIndicator.isVisible()) {
                System.out.println("Home type selection successful.");
            } else {
                System.err.println("Home type selection not reflected.");
            }

            page.waitForTimeout( 10000);
            browser.close();

        }
    }
}
