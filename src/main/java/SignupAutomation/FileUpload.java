package SignupAutomation;

import com.microsoft.playwright.*;

public class FileUpload
{
    public static void main(String[] args) {
        // Set up Playwright
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            BrowserContext context = browser.newContext();
            Page page = context.newPage();

            // Navigate to the file upload page
            page.navigate("https://the-internet.herokuapp.com/upload");

            // Select the file input element and set the file path
            String filePath = "path/to/your/file.txt"; // Replace with the actual file path
           // page.setInputFiles("input#file-upload", filePath);

            // Click the upload button
            page.click("input#file-submit");

            // Wait for confirmation
            page.waitForSelector("div#upload-files", new Page.WaitForSelectorOptions().setTimeout(60000));

            // Verify file upload success
            String uploadedFileName = page.locator("div#upload-files").textContent();
            if (uploadedFileName == null || !uploadedFileName.contains("file.txt")) {
                throw new AssertionError("File upload failed! Expected 'file.txt' but got: " + uploadedFileName);
            }

            System.out.println("File uploaded successfully: " + uploadedFileName);

            // Close the browser
            browser.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1); // Exit with error
        }
    }
}
