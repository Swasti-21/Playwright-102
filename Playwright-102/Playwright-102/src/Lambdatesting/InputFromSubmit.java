package Lambdatesting;

import com.google.gson.JsonObject;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.testng.Assert;
import org.testng.annotations.*;

public class InputFromSubmit {
    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;

    @Test
    public void testInputFormSubmit() throws UnsupportedEncodingException {
    	
    	JsonObject capabilities = new JsonObject();
		JsonObject ltoptions = new JsonObject();
		
		String user = "swastisenapati98";
		
		String accesskey="LT_KQGrSJYjFMfy0Kjv9WVIaZwUZ4xnkBW0U6kOGeacT3nAJE9";
		
		capabilities.addProperty("browsername", "Chrome");

		capabilities.addProperty("browserVersion", "latest");
		
		ltoptions.addProperty("platform", "Windows 11");

		ltoptions.addProperty("name", "InputFromSubmit");

		ltoptions.addProperty("build", "Playwright-102 Test");

		ltoptions.addProperty("user", user);

		ltoptions.addProperty("accessKey", accesskey);

		capabilities.add("LT:Options", ltoptions);
		
		Playwright playwright = Playwright.create();

		BrowserType chromium = playwright.chromium();

		String caps = URLEncoder.encode (capabilities.toString(), "utf-8");

		String cdpUrl = "wss://cdp.lambdatest.com/playwright?capabilities=" + caps;

		Browser browser = chromium.connect(cdpUrl);

		Page page = browser.newPage();  
    	
        page.navigate("https://www.lambdatest.com/selenium-playground");

        page.locator("text=Input Form Submit").click();

        Locator submitBtn = page.locator("(//button[@type='submit'])[2]");
        submitBtn.click();

        Locator nameField = page.locator("#name");

        String validationMsg = (String) nameField.evaluate("el => el.validationMessage");

        Assert.assertEquals(validationMsg, "Please fill out this field.");

        nameField.fill("Swasti Ranjan");
        page.fill("#inputEmail4", "swastiranjan@test.com");
        page.fill("#inputPassword4", "782532589");
        page.fill("#company", "LambdaTest");
        page.fill("input[name='website']", "https://www.linkedin.com/in/swasti98/");

        page.selectOption("select[name='country']", new SelectOption().setLabel("India"));

        page.fill("#inputCity", "Hyderabad");
        page.fill("#inputAddress1", "Secunderabad");
        page.fill("#inputAddress2", "Vihari Prime");
        page.fill("#inputState", "Telangana");
        page.fill("#inputZip", "500003");

        submitBtn.click();

        Locator successText = page.locator("text=Thanks for contacting us, we will get back to you shortly.");

        successText.waitFor(new Locator.WaitForOptions().setTimeout(5000));

        Assert.assertTrue(successText.isVisible(), "Success message should be visible.");
   
        if (context != null) context.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }
}
