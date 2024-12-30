package RentomaticUI;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitForSelectorState;

public class LotSize {
    public static void main(String[]args){
        try(Playwright playwright= Playwright.create() ){
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            BrowserContext context = browser.newContext();
            Page page = context.newPage();
            page.navigate("https://rentomaticui.vercel.app/lotSize");
            page.locator("input[placeholder='Eg. 0.1 Acre']").fill("1000");
            Locator inputField = page.locator("#react-select-2-input"); // Replace with your input field's selector

            inputField.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(5000));
            inputField.click();


             page.waitForTimeout(20000);

        }
    }
}
