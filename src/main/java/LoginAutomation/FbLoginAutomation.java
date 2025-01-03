package LoginAutomation;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;

public class FbLoginAutomation {
    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(false)
                .setTimeout(30000));
            
            BrowserContext context = browser.newContext();
            Page page = context.newPage();
            
            // Navigate and wait for page load
            page.navigate("https://www.facebook.com/login");
            page.waitForLoadState(LoadState.NETWORKIDLE);

            String username = "your_email_or_phone";
            String password = "your_password";

            // Fill login form with proper waits
            Locator emailInput = page.locator("input[name='email']");
            emailInput.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE));
            emailInput.fill(username);

            Locator passwordInput = page.locator("input[name='pass']");
            passwordInput.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE));
            passwordInput.fill(password);

            // Click login with proper wait
            Locator loginButton = page.locator("button[name='login']");
            loginButton.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE));
            
            // Handle navigation and check for errors
            page.waitForNavigation(() -> {
                loginButton.click();
            });

            // Wait for page load after login attempt
            page.waitForLoadState(LoadState.NETWORKIDLE);

            // Check for login errors
            if (page.url().contains("/login")) {
                // Check for error messages
                Locator errorMessage = page.locator("div[class*='error'], #error_box");

                    System.out.println("Login failed: Still on login page");

            } else {
                // Check for successful login
                boolean isProfileVisible = page.isVisible("[aria-label='Profile']");
                if (isProfileVisible) {
                    System.out.println("Login successful! Profile is visible");
                } else {
                    System.out.println("Login status unclear. Current URL: " + page.url());
                }
            }

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
}
