package RentomaticUI;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitForSelectorState;

public class PropertyYear {

    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            BrowserContext context = browser.newContext();
            Page page = context.newPage();

            page.navigate("https://rentomaticui.vercel.app/PropertyYear");
            System.out.println("Page loaded: " + page.title());
            Locator inputField = page.locator("#react-select-2-input"); // Replace with your input field's selector

            inputField.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(5000));

            // Click the input field to open the dropdown
            inputField.click();

            // Select the year from the dropdown by visible text (e.g., "2025")
            page.locator("text=2025").click(); // Replace with the appropriate year option text

            Locator button = page.locator("span:has-text(\"Continue\")");
            button.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            button.click();

            // Now, you can interact with the new page or verify the navigation
            System.out.println("Navigated to the next page!");
            // Optionally wait for the page to reflect the selection
            page.waitForTimeout(2000);

            browser.close();

        }catch (Exception e){
            e.printStackTrace();
         }
    }
}
