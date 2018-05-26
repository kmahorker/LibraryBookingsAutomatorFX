package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.mindrot.jbcrypt.BCrypt;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import classes.AES;
import classes.Cell;
import classes.WebDriverUtility;
import net.thegreshams.firebase4j.error.FirebaseException;
import net.thegreshams.firebase4j.error.JacksonUtilityException;
import net.thegreshams.firebase4j.model.FirebaseResponse;
import net.thegreshams.firebase4j.service.Firebase;

public class Runner{
	
	public static ArrayList<ArrayList<Cell>> cells = new ArrayList<ArrayList<Cell>>();
	public static ArrayList<String> rooms = new ArrayList<String>();
	public static ArrayList<String> times = new ArrayList<String>();
	static int workload = 12;
	
	public static void main(String[] args) {
		
		
		//GO TO Specific Date
//		WebElement date = WebDriverUtility.driver.findElement(By.xpath("/html/body[@id='s-lc-rm-12405']/div[@class='container']/div[@id='s-lc-public-main']/section/div[@id='s-lc-public-page-content']/div[@id='col1']/div[@class='row'][2]/div[@id='s-lc-rm-left']/div[@id='s-lc-rm-cal-cont']/div[@id='s-lc-rm-cal']/div[@class='ui-datepicker-inline ui-datepicker ui-widget ui-widget-content ui-helper-clearfix ui-corner-all']/table[@class='ui-datepicker-calendar']/tbody/tr[5]/td[5]"));
//		date.click();
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(2018, 5, 4);
		
		Scanner in = new Scanner(System.in);
		
		System.out.print("Username: ");
		
		String user = in.nextLine();
		System.out.print("Password: ");
		String pass = in.nextLine();
		
		boolean dateChanged = changeDate(calendar);
		System.out.println(dateChanged);
		
		
		try {
			System.out.println("Added user: " + addUserToFirebase(user, AES.encrypt(pass)));
			for (Map<String, Object> userMap : getUserInfoFromFirebase()) {
				System.out.println("SALT: " + ((String) userMap.get("passwordSalt")));
				System.out.println("USERNAME: " + (String) (userMap.get("username")) + " PASSWORD: "
						+ AES.decrypt((String) userMap.get("passwordSalt")));

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//Pause to wait for table to load
		init();
		displayCells();
		
		
	}
	
	//XPATH: /html/body[@id='s-lc-rm-12405']/div[@class='container']/div[@id='s-lc-public-main']/section/div[@id='s-lc-public-page-content']/div[@id='col1']/div[@class='row'][2]/div[@id='s-lc-rm-right']/div[@id='s-lc-rm-time-grid']/div[@id='s-lc-rm-tg-cont']/table[@id='s-lc-rm-tg-tb']/tbody/tr[2]/td[2]/div[@id='s-lc-rm-tg-scroll']/table/tbody/tr[2]/td
	
	public static void init() {
		WebDriverUtility.init();
		WebDriverUtility.openURL("http://libcal.library.ucsb.edu/rooms.php?i=12405");
		
		rooms = parseRooms();
		rooms.remove(0); //First one in the results is empty
		times = new ArrayList<String>(Arrays.asList("12:00am","12:30am","1:00am","1:30am","2:00am","2:30am","3:00am","3:30am","4:00am","4:30am","5:00am","5:30am","6:00am","6:30am","7:00am","7:30am","8:00am","8:30am","9:00am","9:30am","10:00am","10:30am","11:00am","11:30am","12:00pm","12:30pm","1:00pm","1:30pm","2:00pm","2:30pm","3:00pm","3:30pm","4:00pm","4:30pm","5:00pm","5:30pm","6:00pm","6:30pm","7:00pm","7:30pm","8:00pm","8:30pm","9:00pm","9:30pm","10:00pm","10:30pm","11:00pm","11:30pm"));
		instantiateCells();
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		updateCellsWithAvailability();
		
		
	}
	public static ArrayList<String> parseRooms() {
		ArrayList<String> rooms = new ArrayList<String>();
		WebElement roomName = null;
		int pos = 1;
		do {
			try {
				roomName = WebDriverUtility.driver.findElement(By.xpath("/html/body[@id='s-lc-rm-12405']/div[@class='container']/div[@id='s-lc-public-main']/section/div[@id='s-lc-public-page-content']/div[@id='col1']/div[@class='row'][2]/div[@id='s-lc-rm-right']/div[@id='s-lc-rm-time-grid']/div[@id='s-lc-rm-tg-cont']/table[@id='s-lc-rm-tg-tb']/tbody/tr[2]/td[1]/table[@id='s-lc-rm-tg-rnames']/tbody/tr[" + pos + "]"));
				String roomNameString = roomName.getText();
				rooms.add(roomNameString.trim());
//				System.out.print(roomNameString.trim());
				pos++;
			}catch(Exception e) {
				roomName = null;
			}
		}while(roomName != null);
		
		return rooms;
	}
	
	public static ArrayList<String> parseTimes(){
		WebElement timesList = WebDriverUtility.driver.findElement(By.xpath("/html/body[@id='s-lc-rm-12405']/div[@class='container']/div[@id='s-lc-public-main']/section/div[@id='s-lc-public-page-content']/div[@id='col1']/div[@class='row'][2]/div[@id='s-lc-rm-right']/div[@id='s-lc-rm-time-grid']/div[@id='s-lc-rm-tg-cont']/table[@id='s-lc-rm-tg-tb']/tbody/tr[2]/td[2]/div[@id='s-lc-rm-tg-scroll']/table[@id='s-lc-rm-scrolltb']"));
		
		String timeListString = timesList.getText();
		
		ArrayList<String> timeStrings = new ArrayList<String>(Arrays.asList(timeListString.split("\n")));
		
		timeStrings.remove(timeStrings.size() - 1);
		
		return timeStrings;
		
	}
	
	public static String parseRoomFromTitle(String titleAttr) {
		return titleAttr.substring(0, titleAttr.indexOf(','));
		
	}
	
	public static String parseTimeFromTitle(String titleAttr) {
		return titleAttr.substring(titleAttr.indexOf(' ') + 1, titleAttr.indexOf('m') + 1);
	}
	
	public static void displayCells() {
		System.out.println();
		System.out.printf("%6s", " ");
		for(String s : times) {
			System.out.printf("%9s", s );
		}
		System.out.println();
		
		for(ArrayList<Cell> rowCells : cells) {
			System.out.printf("%-6s", rowCells.get(0).getRoom());
			for(Cell c : rowCells) {
				if(c.isAvailable()) {
					System.out.printf("%4s%s%4s" , " ", "*", " ");
				}
				else {
					System.out.printf("%4s%s%4s", " ", "X", " ");
				}
			}
			System.out.println();
		}
	}
	
	public static void instantiateCells() {
		
		//Instantiate all cells with false availability
		for(int r = 0; r < rooms.size(); r++) {
			ArrayList<Cell> rowCells = new ArrayList<Cell>();
			for(int t = 0; t < times.size(); t++) {
				Cell newCell = new Cell(rooms.get(r), times.get(t), r, t, false);
				rowCells.add(newCell);
			}
			cells.add(rowCells);
		}
	
	}
	
	public static void updateCellsWithAvailability() {
		//Parse Available Cells and Update Cells list with the correct availability
		List<WebElement> availableCells = WebDriverUtility.driver.findElements(By.xpath("/html/body/div/div/section/div/div/div/div/div/div/table/tbody/tr/td/div/table/tbody/tr/td/a"));
		
		for(WebElement e : availableCells) {
			String titleAttr = e.getAttribute("title");
			String roomParsedFromTitle = parseRoomFromTitle(titleAttr);
			String timeParsedFromTitle =  parseTimeFromTitle(titleAttr);
			
			int roomIndex = rooms.indexOf(roomParsedFromTitle.trim());
			int timeIndex = times.indexOf(timeParsedFromTitle);
			
			cells.get(roomIndex).get(timeIndex).setAvailable(true);
		}
	}
	
	//January is 0, December is 11
	public static boolean changeDate(Calendar cal) {
		WebElement monthSelect = WebDriverUtility.driver.findElement(By.xpath("/html/body[@id='s-lc-rm-12405']/div[@class='container']/div[@id='s-lc-public-main']/section/div[@id='s-lc-public-page-content']/div[@id='col1']/div[@class='row'][2]/div[@id='s-lc-rm-left']/div[@id='s-lc-rm-cal-cont']/div[@id='s-lc-rm-cal']/div[@class='ui-datepicker-inline ui-datepicker ui-widget ui-widget-content ui-helper-clearfix ui-corner-all']/div[@class='ui-datepicker-header ui-widget-header ui-helper-clearfix ui-corner-all']/div[@class='ui-datepicker-title']/select[@class='ui-datepicker-month']"));
		Select monthDropdown = new Select(monthSelect);
		monthDropdown.selectByValue(cal.get(Calendar.MONTH)+"");
		String dateCellXpath = "/html/body[@id='s-lc-rm-12405']/div[@class='container']/div[@id='s-lc-public-main']/section/div[@id='s-lc-public-page-content']/div[@id='col1']/div[@class='row'][2]/div[@id='s-lc-rm-left']/div[@id='s-lc-rm-cal-cont']/div[@id='s-lc-rm-cal']/div[@class='ui-datepicker-inline ui-datepicker ui-widget ui-widget-content ui-helper-clearfix ui-corner-all']/table[@class='ui-datepicker-calendar']/tbody/tr[" + cal.get(Calendar.WEEK_OF_MONTH) + "]/td[" + cal.get(Calendar.DAY_OF_WEEK) + "]";
		
		System.out.println(dateCellXpath);
		WebDriverWait wait = new WebDriverWait(WebDriverUtility.driver, 15);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(dateCellXpath)));
		
		WebElement dateCellElement = WebDriverUtility.driver.findElement(By.xpath(dateCellXpath));
		String dateCellClass = dateCellElement.getAttribute("class");
		if(dateCellClass.contains("ui-state-disabled")) {
			return false;
		}
		else {
			dateCellElement.click();
			return true;
		}
		
	}
	
	public static boolean addUserToFirebase(String user, String string) {
		
		FirebaseResponse responseRoot = null;
		FirebaseResponse responseUsernames = null;
		try {
			
			
//			String addedName = "";
//			if(responseRoot.getSuccess()) {
//				addedName = (String) responseRoot.getBody().get("name");
//			}
			Firebase firebaseRoot = new Firebase("https://librarybookingsautomator.firebaseio.com/Users");
			Firebase firebaseUsernames = new Firebase("https://librarybookingsautomator.firebaseio.com/usernames");
			FirebaseResponse allUsernames = firebaseUsernames.get();
			
			Map<String, Object> allMap = allUsernames.getBody();
			boolean repeated = false;
			for(String key : allMap.keySet()) {
				Map<String, Object> oneUser = (Map<String, Object>)allMap.get(key);
				if(oneUser.containsKey("username") && ((String)(oneUser.get("username"))).equals(user)) {
					repeated = true;
				}
			}
			
			System.out.println("Was repeated?: " + repeated);
			if(!repeated) {
				Map<String, Object> usernameObject = new LinkedHashMap<String, Object>();
				usernameObject.put("username", user);
				responseUsernames = firebaseUsernames.post(usernameObject);
				System.out.println("USERNAME POST RESPONSE: " + responseUsernames);
				
				
				Map<String, Object> userObject = new LinkedHashMap<String, Object>();
				userObject.put("username", user);
				userObject.put("passwordSalt", string);
				responseRoot = firebaseRoot.post(userObject);
				System.out.println("ROOT POST Response" + responseRoot);
			}
			
			
		
		} catch (FirebaseException | JacksonUtilityException | Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(responseRoot != null && responseUsernames != null) {
			return responseRoot.getSuccess() && responseUsernames.getSuccess();
		}
		
		return false;
		
	}
	
	public static List<Map<String, Object>> getUserInfoFromFirebase(){
		List<Map<String, Object>> userInfo = new ArrayList<Map<String, Object>>();
		try {
			Firebase firebaseRoot = new Firebase("https://librarybookingsautomator.firebaseio.com/Users");
			FirebaseResponse response = firebaseRoot.get();
			System.out.println("GETUSERINFO: " + response.toString());
			if(response.getSuccess()) {
				Map<String, Object> responseMap = response.getBody();
				for(String responseMapKey : responseMap.keySet()) {
					Map<String, Object> userResponseMap = (Map<String, Object>) responseMap.get(responseMapKey);
					if(userResponseMap.containsKey("username") && userResponseMap.containsKey("passwordSalt")) {
						Map<String, Object> userMap = new HashMap<String, Object>();
						userMap.put("username", (String) userResponseMap.get("username"));
						userMap.put("passwordSalt", userResponseMap.get("passwordSalt"));
						userInfo.add(userMap);
					}
					System.out.println("ADDEDMAP: " + responseMap.toString());
				}
				
			}
		} catch (FirebaseException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return userInfo;
		
		
	}
	
	
	

}
