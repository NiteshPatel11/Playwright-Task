package SignupAutomation;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;

public class DemoQa {
    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(false));
            
            BrowserContext context = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(1280, 720));  // Set viewport size
            
            Page page = context.newPage();
            
            // Navigate to DemoQA text box page
            page.navigate("https://demoqa.com/text-box");
            
            // Wait for page load
            page.waitForLoadState(LoadState.DOMCONTENTLOADED);
            page.waitForLoadState(LoadState.NETWORKIDLE);

            // Wait for and fill userName field
            Locator userNameField = page.locator("#userName");
            userNameField.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE));
            userNameField.fill("nitesh patel");

            // Wait for and fill email field
            Locator emailField = page.locator("#userEmail");
            emailField.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE));
            emailField.fill("niteshpatel@gmail.com");

            // Wait for and fill current address field
            Locator currentAddressField = page.locator("#currentAddress");
            currentAddressField.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE));
            currentAddressField.fill("indore");

            // Wait for and fill permanent address field
            Locator permanentAddressField = page.locator("#permanentAddress");
            permanentAddressField.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE));
            permanentAddressField.fill("indore MP");

            // Scroll to and click submit button
            Locator submitButton = page.locator("#submit");
            submitButton.scrollIntoViewIfNeeded();
            submitButton.click();

            // Wait to see the results
            page.waitForTimeout(3000);

            // Verify submission
            Locator output = page.locator(".border");
            if (output.isVisible()) {
                System.out.println("Form submitted successfully!");
                System.out.println("Output text: " + output.textContent());
            }

            // Close browser
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
