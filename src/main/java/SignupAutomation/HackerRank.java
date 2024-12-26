package SignupAutomation;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;

public class HackerRank {
    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(false)
                .setTimeout(30000));
            
            BrowserContext context = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(1280, 720));
            
            Page page = context.newPage();

            // Navigate to HackerRank signup page
            page.navigate("https://www.hackerrank.com/auth/signup");
            

            page.waitForLoadState(LoadState.DOMCONTENTLOADED);
            page.waitForLoadState(LoadState.NETWORKIDLE);
            

            System.out.println("Current URL: " + page.url());
            
            // Wait for the signup form container
            page.waitForSelector(".auth-form", new Page.WaitForSelectorOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(10000));

            // Fill signup form with correct selectors
            fillField(page, "[name='name']", "Nitesh patel"); // fullname
            fillField(page, "[name='email']", "nitesh@gmail.com"); // email
            fillField(page, "[name='password']", "nit@1231"); // password

            // Find and click the signup button
            Locator signupButton = page.locator("button.auth-button");
            if (signupButton.isVisible()) {
                System.out.println("Clicking signup button...");
                signupButton.click();
            } else {
                System.out.println("Signup button not visible!");
                // Try alternative button selector if the first one fails
                Locator altButton = page.locator("[data-analytics='SignupPassword']");
                if (altButton.isVisible()) {
                    System.out.println("Clicking alternative signup button...");
                    altButton.click();
                }
            }

            // Wait for navigation/confirmation
            page.waitForLoadState(LoadState.NETWORKIDLE);

            // Check for successful signup or errors
            if (page.url().contains("/onboarding")) {
                System.out.println("Signup successful!");
            } else {
                System.out.println("Signup might have failed. Current URL: " + page.url());
                
                // Check for error messages with multiple possible selectors
                String[] errorSelectors = {
                    ".error-message",
                    ".alert-danger",
                    "[data-automation='auth-error']",
                    ".error-text"
                };
                
                for (String selector : errorSelectors) {
                    Locator errorMessage = page.locator(selector);
                    if (errorMessage.isVisible()) {
                        System.out.println("Error message: " + errorMessage.textContent());
                        break;
                    }
                }
            }

            // Give some time to see the result
            page.waitForTimeout(10000);

            // Clean up
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

    private static void fillField(Page page, String selector, String value) {
        try {
            System.out.println("Filling field: " + selector);
            
            // Wait for element with increased timeout
            page.waitForSelector(selector, new Page.WaitForSelectorOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(15000));
            
            Locator field = page.locator(selector);
            
            // Make sure element is ready for interaction
            field.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(5000));
                
            field.clear(); // Clear any existing value
            field.fill(value);
            
            System.out.println("Successfully filled " + selector);
            
        } catch (PlaywrightException e) {
            System.err.println("Error filling field " + selector + ": " + e.getMessage());
            throw e;
        }
    }
}
