package com.kok.sport.utils.mockdata;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import com.kok.sport.utils.Timeutil;
//import org.openqa.selenium.remote.DesiredCapabilities;
import com.kok.sport.utils.Util;

public class WebdriverFirefox {
	static org.apache.logging.log4j.Logger logger = LogManager.getLogger(WebdriverFirefox.class);

	public static void main(String[] args) throws Exception {
		
		int timetout = Integer.parseInt(args[0].trim());
		Util.timeOutExitRuntime(timetout*1000);
		// declaration and instantiation of objects/variables
		String gkdv = "D:\\geckodriver.exe";
		if (new File(gkdv).exists())
			System.setProperty("webdriver.gecko.driver", gkdv);
		else
			System.setProperty("webdriver.gecko.driver", "/geckodriver");

		FirefoxOptions chromeOptions = new FirefoxOptions();
		// 设置 chrome 的无头模式
		chromeOptions.setHeadless(Boolean.TRUE);
		// 启动一个 chrome 实例
		// webDriver = new ChromeDriver(chromeOptions);
		WebDriver driver = new FirefoxDriver(chromeOptions);
		// org.openqa.selenium.remote.s

		// DesiredCapabilities IEcaps = DesiredCapabilities.htmlUnit();
//INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS
		// IEcaps .setCapability(HtmlUnitDriver.INVALIDSELECTIONERROR,true);

		// WebDriver driver = new HtmlUnitDriver(BrowserVersion.FIREFOX);
		// Set implicit wait
		driver.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
		// comment the above 2 lines and uncomment below 2 lines to use Chrome
		System.setProperty("webdriver.chrome.driver",
				"D:\\prj\\spdjs\\node_modules\\chromedriver\\lib\\chromedriver\\chromedriver.exe");
//	 WebDriver driver =new ChromeDriver();
		// InternetExplorerDriver lang.UnsatisfiedLinkError:
		// C:\Users\Administrator\AppData\Local\Temp\webdriver3771754343790130835.tmp:
		// Can't load IA 32-bit .dll on a AMD 64-bit platform
		// org.openqa.selenium.ie.InternetExplorerDriver.class
		// new ChromeDriver();

		String baseUrl = "https://live.leisu.com/";
		String expectedTitle = "Welcome: Mercury Tours";
		String actualTitle = "";

		// launch Fire fox and direct it to the Base URL
		driver.get(baseUrl);

		// get the actual value of the title
		actualTitle = driver.getTitle();

		/*
		 * compare the actual title of the page with the expected one and print the
		 * result as "Passed" or "Failed"
		 */
		if (actualTitle.contentEquals(expectedTitle)) {
			System.out.println("Test Passed!");
		} else {
			System.out.println("Test Failed");
		}
		Thread.sleep(12000);

		//
		// Timer timer=new Timer();
		// timer.

		int n = 0;

		while (true) {
			n++;
			if (n > 3) {
				// driver.close();
			}
			int hours = new Date().getHours();
			if (hours >= 5 && hours < 8)
			{
				logger.info("hours >= 5 && hours < 9 exit");
				driver.close();
				Runtime.getRuntime().exit(0);
			}
			
			String pageSource = driver.getPageSource();
			try {
				Leisu.processTime(pageSource);
			} catch (Exception e) {
				logger.error(e);
			}

			try {

				String now = Timeutil.now();
				now = now.replaceAll(":", "_");
				FileUtils.write(new File("d:\\cache\\leisu" + now + ".htm"), pageSource, false);
			} catch (Exception e) {
				logger.warn(e);
			}

			try {
				FileUtils.write(new File("/leisu.htm"), pageSource, false);
			} catch (Exception e) {
				logger.warn(e);

				Thread.sleep(1000);
				logger.info(new Date());
				System.out.println(new Date());
			}

			// close Fire fox
			// driver.close();
			// System.out.println("f");
		}

	}
}
