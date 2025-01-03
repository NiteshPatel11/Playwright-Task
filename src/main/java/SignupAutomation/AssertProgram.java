package SignupAutomation;

import com.microsoft.playwright.*;

public class AssertProgram {
    public static void main(String[] args) {


        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            BrowserContext context = browser.newContext();
            Page page = context.newPage();

            // Navigate to a website
            page.navigate("https://example.com");

            // Assert the page title
            String title = page.title();
            if (!"Example Domain".equals(title)) {
                throw new AssertionError("Expected title 'Example Domain', but got: " + title);
            }

            // Assert a specific element's text content
            String heading = page.locator("h1").textContent();
            if (!"Example Domain".equals(heading)) {
                throw new AssertionError("Expected heading 'Example Domain', but got: " + heading);
            }

            System.out.println("All assertions passed!");
            browser.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1); // Exit with error
        }
    }
}

