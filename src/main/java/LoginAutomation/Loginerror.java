package LoginAutomation;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;

public class Loginerror {
        public static void main(String[] args) {
            try (Playwright playwright = Playwright.create()) {
                // Launch browser with timeout settings
                Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                        .setHeadless(false)
                        .setTimeout(60000));

                BrowserContext context = browser.newContext(new Browser.NewContextOptions()
                        .setViewportSize(1280, 720));

                Page page = context.newPage();

                // Navigate to Facebook login page
                page.navigate("https://www.facebook.com/login");

                page.waitForLoadState(LoadState.NETWORKIDLE);

                // Login process
                Locator emailField = page.locator("input[name='email']");
                emailField.waitFor(new Locator.WaitForOptions()
                        .setState(WaitForSelectorState.VISIBLE)
                        .setTimeout(10000));
                emailField.fill("your_email@example.com");

                Locator passwordField = page.locator("input[name='pass']");
                passwordField.waitFor(new Locator.WaitForOptions()
                        .setState(WaitForSelectorState.VISIBLE)
                        .setTimeout(10000));
                passwordField.fill("your_password");

                Locator loginButton = page.locator("button[name='login']");
                loginButton.waitFor(new Locator.WaitForOptions()
                        .setState(WaitForSelectorState.VISIBLE)
                        .setTimeout(10000));

                // Click login and wait for response
                page.waitForNavigation(() -> {
                    loginButton.click();
                });

                page.waitForLoadState(LoadState.NETWORKIDLE);

                // Check for error messages
                boolean hasError = false;

                // Common error selectors for Facebook
                String[] errorSelectors = {
                        "div[class*='error']",
                        "#error_box",
                        "div[data-testid='error_message']",
                        "#error_message"
                };

                for (String selector : errorSelectors) {
                    if (page.locator(selector).isVisible()) {
                        String errorText = page.locator(selector).textContent();
                        System.out.println("Login Error: " + errorText);
                        hasError = true;
                        break;
                    }
                }

                if (!hasError)
                    if (page.url().contains("/login")) {
                        System.out.println("Login failed: Still on login page");
                    } else {
                        System.out.println("Login successful!");
                        System.out.println("Current URL: " + page.url());
                        System.out.println("Page Title: " + page.title());
                    }


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
