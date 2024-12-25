package LoginAutomation;

import com.microsoft.playwright.*;

public class FbLoginAutomation {
    public static void main(String[] args) {
        try(Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            BrowserContext context = browser.newContext();
            Page page = context.newPage();
            page.navigate("https://www.facebook.com/login");

            String username = "your_email_or_phone";
            String password = "your_password";

            page.fill("input[name= 'email']", username);
            page.fill("input[name= 'password']", password);
            page.click("button[name = 'login']");
            page.waitForTimeout(5000);

            boolean isProfileVisible = page.isVisible("[aria-label='Profile']");
            System.out.println("isProfileVisible: " + isProfileVisible);
            browser.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
