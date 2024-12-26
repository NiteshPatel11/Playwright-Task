package SignupAutomation;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;

public class FbSingnup {
    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                    .setHeadless(false)
                    .setTimeout(30000));

            BrowserContext context = browser.newContext(new Browser.NewContextOptions()
                    .setViewportSize(1280, 720));

            Page page = context.newPage();

            // Navigate to Facebook signup page
            page.navigate("https://www.facebook.com/r.php");
            page.waitForLoadState(LoadState.NETWORKIDLE);

            // Fill in the signup form with proper waits
            fillField(page, "input[name='firstname']", "John");
            fillField(page, "input[name='lastname']", "Doe");
            fillField(page, "input[name='reg_email__']", "john.doe@gmail.com");

            // Confirm email if the confirmation field appears
            if (page.isVisible("input[name='reg_email_confirmation__']")) {
                fillField(page, "input[name='reg_email_confirmation__']", "john.doe@.com");
            }

            fillField(page, "input[name='reg_passwd__']", "SecurePassword123!");

            // Select birthday
            selectDropdownOption(page, "select[name='birthday_day']", "14");
            selectDropdownOption(page, "select[name='birthday_month']", "6");
            selectDropdownOption(page, "select[name='birthday_year']", "1990");

            // Select gender
            Locator genderRadio = page.locator("input[name='sex'][value='2']");
            if (genderRadio.isVisible()) {
                genderRadio.click();
            }


            Locator signupButton = page.locator("button[name='websubmit']");
            if (signupButton.isVisible()) {
                // Wait for navigation after clicking signup
                page.waitForNavigation(() -> {
                    signupButton.click();
                });
            }


            page.waitForLoadState(LoadState.NETWORKIDLE);


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

    // Helper method to fill form fields with proper waiting
    private static void fillField(Page page, String selector, String value) {
        Locator field = page.locator(selector);
        field.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(5000));
        field.fill(value);
    }

    // Helper method to select dropdown options
    private static void selectDropdownOption(Page page, String selector, String value) {
        Locator dropdown = page.locator(selector);
        dropdown.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(5000));
        dropdown.selectOption(value);
    }
}