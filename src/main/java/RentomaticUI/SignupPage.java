package RentomaticUI;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;

public class SignupPage {
    public static void main(String[]args){
        try(Playwright playwright = Playwright.create()){
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            BrowserContext context = browser.newContext();
            Page page= context.newPage();
            page.navigate("https://rentomaticui.vercel.app/");
            page.waitForLoadState(LoadState.NETWORKIDLE);

            page.fill("input[name= 'name']","nitesh");
            page.fill("input[name = 'email']","nitesh@gmail.com");
            page.fill("input[name = 'contactNumber']","6525564622");
            page.fill("input[name = 'companyName']","Liseinfotech");
            page.fill("input[name = 'companyAddress']","Indore");

            Locator button = page.locator("span:has-text(\"let's get started\")");
            button.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
          button.click();
            page.waitForTimeout(10000);
        }



    }
}
