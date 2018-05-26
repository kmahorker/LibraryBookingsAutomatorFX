package classes;

import java.net.URL;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;
public final class WebDriverUtility{
	
	public static WebDriver driver;
	
	private WebDriverUtility() {
		init();
	}
	
	public static void init() {
		URL url = WebDriverUtility.class.getClassLoader().getResource("chromedriver.exe"); //"C:\\Users\\kmaho\\Desktop\\chromedriver_win32\\chromedriver.exe";
		System.out.println(url.toString());
		System.setProperty("webdriver.chrome.driver", url.getPath());
		driver = new ChromeDriver();
	}
	
	public static void openURL(String str){
		driver.get(str);
	}
	
	
	
	
}