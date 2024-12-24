package PlayWriteSession;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class PlayWrite1 {
    public static void main(String[] args) {
       try ( Playwright playwright = Playwright.create()) {


           Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
           Page page = browser.newPage();
           page.navigate("https://amazon.com/");
           String title = page.title();
           System.out.println(title);
           String url = page.url();
           System.out.println(url);
           browser.close();
           playwright.close();
       }
       catch ( Exception e ) {
           e.printStackTrace();
       }
    }
}
