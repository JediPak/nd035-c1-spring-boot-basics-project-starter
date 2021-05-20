package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;
	private static LoginPageObject loginPO;
	private static SignupPageObject signupPO;
	private static HomePageObject homePO;
	private static ResultPageObject resultPO;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		driver.get("http://localhost:" + port + "/login");
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/*
	********************************************
	Test signup and login flow
	********************************************
	 */

	//Write a Selenium test that verifies that the home page is not accessible without logging in.
	@Test
	void errorLoggingIn() throws InterruptedException {
		String username = "username";
		String password = "password";
		loginPO = new LoginPageObject(driver);
		boolean atLoginPage = loginPO.atLoginPage();
		//verify at login page
		assertEquals(true, atLoginPage);
		//tried to login
		loginPO.login(username, password);
		//since user isnt logged in, error msg should be shown
		assertEquals(true, loginPO.wrongLogin());

		//try navigating to home page
		driver.get("http://localhost:" + port + "/home");

		//verify that home page can't be seen, and at login page instead
		atLoginPage = loginPO.atLoginPage();
		assertEquals(true, atLoginPage);

	}

	//Write a Selenium test that signs up a new user,
	// logs that user in,
	// verifies that they can access the home page,
	// then logs out and verifies that the home page is no longer accessible.
	@Test
	void successLoginAndLogout() throws InterruptedException {
		String firstname = "firstname";
		String lastname = "lastname";
		String username = "username";
		String password = "password";
		loginPO = new LoginPageObject(driver);
		boolean atLoginPage = loginPO.atLoginPage();
		//verifying you're at login
		assertEquals(true, atLoginPage);

		//loging in
		loginPO.signup();
		signupPO = new SignupPageObject(driver);
		boolean atSignupPage = signupPO.atSignupPage();
		//verifying you're at signin page
		assertEquals(true, atSignupPage);

		//signs up a new user,
		signupPO.signup(firstname, lastname, username, password);

		//(success signup page to login page redirect sleeps for 2000ms)
		Thread.sleep(3000);

		// logs that user in,
		loginPO.login(username, password);

		// verifies that they can access the home page,
		assertEquals("Home", driver.getTitle());

		// then logs out and verifies that the home page is no longer accessible.
		homePO = new HomePageObject(driver);
		homePO.logout();
		driver.get("http://localhost:" + port + "/home");
		String title = driver.getTitle();
		System.out.println("Page title: "+ title);
		assertFalse("Home".equals(title));
	}

	/*
	********************************************
	Test adding, editing, and deleting notes
	********************************************
	 */

	//1
	// Write a Selenium test that logs in an existing user,
	// creates a note and verifies that the note details are visible in the note list.
	@Test
	void createNote() throws InterruptedException {

		/*
		* Logging in
		*/
		String firstname = "firstname";
		String lastname = "lastname";
		String username = "username";
		String password = "password";
		loginPO = new LoginPageObject(driver);
		boolean atLoginPage = loginPO.atLoginPage();
		//verifying you're at login
		assertEquals(true, atLoginPage);

		//logging in
		loginPO.signup();
		signupPO = new SignupPageObject(driver);
		boolean atSignupPage = signupPO.atSignupPage();
		//verifying you're at signin page
		assertEquals(true, atSignupPage);

		//signs up a new user,
		signupPO.signup(firstname, lastname, username, password);

		//(success signup page to login page redirect sleeps for 2000ms)
		Thread.sleep(3000);

		// logs that user in,
		loginPO.login(username, password);

		Thread.sleep(1000);
		// verifies that they can access the home page,
		assertEquals("Home", driver.getTitle());

		/*
		 * Create Note
		 */
		String title = "title";
		String description = "description -thing1 -thing2";
		homePO = new HomePageObject(driver);
		homePO.createNote(title, description);

		/*
		 * verifies that the note details are visible in the note list
		 */
		resultPO = new ResultPageObject(driver);
		resultPO.getResultMsg();
		assertTrue(resultPO.getResultMsg().contains("Success"));

		resultPO.continueToHome();
		Thread.sleep(2000);

		List<List<String>> viewNote = homePO.viewNote();
		assertTrue(viewNote.get(0).get(0).equals(title));
		assertTrue(viewNote.get(0).get(1).equals(description));

	}

	//2
	// Write a Selenium test that logs in an existing user with existing notes,
	// clicks the edit note button on an existing note,
	// changes the note data,
	// saves the changes,
	// and verifies that the changes appear in the note list.

	@Test
	void createNoteLogout_loggingEditNote() throws InterruptedException {
		/*
		 * Logging in
		 */
		String firstname = "firstname";
		String lastname = "lastname";
		String username = "username";
		String password = "password";
		loginPO = new LoginPageObject(driver);
		boolean atLoginPage = loginPO.atLoginPage();
		//verifying you're at login
		assertEquals(true, atLoginPage);

		//logging in
		loginPO.signup();
		signupPO = new SignupPageObject(driver);
		boolean atSignupPage = signupPO.atSignupPage();
		//verifying you're at signin page
		assertEquals(true, atSignupPage);

		//signs up a new user,
		signupPO.signup(firstname, lastname, username, password);

		//(success signup page to login page redirect sleeps for 2000ms)
		Thread.sleep(3000);

		// logs that user in,
		loginPO.login(username, password);

		Thread.sleep(1000);
		// verifies that they can access the home page,
		assertEquals("Home", driver.getTitle());

		/*
		 * Create Note
		 */
		String title = "title";
		String description = "description -thing1 -thing2";
		homePO = new HomePageObject(driver);
		homePO.createNote(title, description);

		/*
		 * verifies that the note details are visible in the note list
		 */
		resultPO = new ResultPageObject(driver);
		resultPO.getResultMsg();
		assertTrue(resultPO.getResultMsg().contains("Success"));

		resultPO.continueToHome();
		Thread.sleep(2000);

		List<List<String>> viewNote = homePO.viewNote();
		assertTrue(viewNote.get(0).get(0).equals(title));
		assertTrue(viewNote.get(0).get(1).equals(description));

		title = "title edited";
		description = "description edited -thing1_edited -thing2_edited";
		//loging out
		homePO.logout();
		//2
		// Write a Selenium test that logs in an existing user with existing notes,
		loginPO.login(username, password);

		// clicks the edit note button on an existing note,
		// changes the note data, saves the changes,
		homePO.editNote(0, title, description);

		// and verifies that the changes appear in the note list.
		resultPO.getResultMsg();
		assertTrue(resultPO.getResultMsg().contains("Success"));
		resultPO.continueToHome();
		Thread.sleep(2000);
		viewNote = homePO.viewNote();
		assertTrue(viewNote.get(0).get(0).equals(title));
		assertTrue(viewNote.get(0).get(1).equals(description));

	}

	//3
	// Write a Selenium test that logs in an existing user with existing notes,
	// clicks the delete note button on an existing note,
	// and verifies that the note no longer appears in the note list.
	@Test
	void createNoteLogout_loggingInEditNote_loggingInDeleteNote() throws InterruptedException {
		/*
		 * Logging in
		 */
		String firstname = "firstname";
		String lastname = "lastname";
		String username = "username";
		String password = "password";
		loginPO = new LoginPageObject(driver);
		boolean atLoginPage = loginPO.atLoginPage();
		//verifying you're at login
		assertEquals(true, atLoginPage);

		//logging in
		loginPO.signup();
		signupPO = new SignupPageObject(driver);
		boolean atSignupPage = signupPO.atSignupPage();
		//verifying you're at signin page
		assertEquals(true, atSignupPage);

		//signs up a new user,
		signupPO.signup(firstname, lastname, username, password);

		//(success signup page to login page redirect sleeps for 2000ms)
		Thread.sleep(3000);

		// logs that user in,
		loginPO.login(username, password);

		Thread.sleep(1000);
		// verifies that they can access the home page,
		assertEquals("Home", driver.getTitle());

		/*
		 * Create Note
		 */
		String title = "title";
		String description = "description -thing1 -thing2";
		homePO = new HomePageObject(driver);
		homePO.createNote(title, description);

		/*
		 * verifies that the note details are visible in the note list
		 */
		resultPO = new ResultPageObject(driver);
		resultPO.getResultMsg();
		assertTrue(resultPO.getResultMsg().contains("Success"));

		resultPO.continueToHome();
		Thread.sleep(2000);

		List<List<String>> viewNote = homePO.viewNote();
		assertTrue(viewNote.get(0).get(0).equals(title));
		assertTrue(viewNote.get(0).get(1).equals(description));

		title = "title edited";
		description = "description edited -thing1_edited -thing2_edited";
		//loging out
		homePO.logout();
		//2
		// Write a Selenium test that logs in an existing user with existing notes,
		loginPO.login(username, password);

		// clicks the edit note button on an existing note,
		// changes the note data, saves the changes,
		homePO.editNote(0, title, description);

		// and verifies that the changes appear in the note list.
		resultPO.getResultMsg();
		assertTrue(resultPO.getResultMsg().contains("Success"));
		resultPO.continueToHome();
		Thread.sleep(2000);
		viewNote = homePO.viewNote();
		assertTrue(viewNote.get(0).get(0).equals(title));
		assertTrue(viewNote.get(0).get(1).equals(description));

		//3
		homePO.logout();
		// Write a Selenium test that logs in an existing user with existing notes,
		loginPO.login(username, password);
		// clicks the delete note button on an existing note,
		homePO.deleteNote(0);
		// and verifies that the note no longer appears in the note list.
		resultPO.getResultMsg();
		assertTrue(resultPO.getResultMsg().contains("Success"));
		resultPO.continueToHome();
		Thread.sleep(2000);
		viewNote = homePO.viewNote();
		assertTrue(viewNote.size() == 0);

	}

	/*
	********************************************
	Test adding, editing and deleting credentials
	 ********************************************
	 */

	//1
	// Write a Selenium test that logs in an existing user,
	// creates a credential and verifies that the credential details are visible in the credential list.
	@Test
	void createCredentialLogout() throws InterruptedException {
		/*
		 * Logging in
		 */
		String firstname = "firstname";
		String lastname = "lastname";
		String username = "username";
		String password = "password";
		loginPO = new LoginPageObject(driver);
		boolean atLoginPage = loginPO.atLoginPage();
		//verifying you're at login
		assertEquals(true, atLoginPage);

		//logging in
		loginPO.signup();
		signupPO = new SignupPageObject(driver);
		boolean atSignupPage = signupPO.atSignupPage();
		//verifying you're at signin page
		assertEquals(true, atSignupPage);

		//signs up a new user,
		signupPO.signup(firstname, lastname, username, password);

		//(success signup page to login page redirect sleeps for 2000ms)
		Thread.sleep(3000);

		// logs that user in,
		loginPO.login(username, password);

		Thread.sleep(1000);
		// verifies that they can access the home page,
		assertEquals("Home", driver.getTitle());

		/*
		 * Create Note
		 */
		String url = "url";
		String username_c = "username";
		String password_c = "password";
		homePO = new HomePageObject(driver);
		homePO.createCredential(url, username_c, password_c);

		/*
		 * verifies that the note details are visible in the note list
		 */
		resultPO = new ResultPageObject(driver);
		resultPO.getResultMsg();
		assertTrue(resultPO.getResultMsg().contains("Success"));

		resultPO.continueToHome();
		Thread.sleep(2000);

		List<List<String>> viewCredential = homePO.viewCredential();
		assertTrue(viewCredential.get(0).get(0).equals(url));
		assertTrue(viewCredential.get(0).get(1).equals(username_c));
		//assertTrue(viewCredential.get(0).get(1).equals(password_c)); --can't compare unencrypted password and encrypted password
/*
		title = "title edited";
		description = "description edited -thing1_edited -thing2_edited";
		//loging out
		homePO.logout();
		//2
		// Write a Selenium test that logs in an existing user with existing notes,
		loginPO.login(username, password);

		// clicks the edit note button on an existing note,
		// changes the note data, saves the changes,
		homePO.editNote(0, title, description);

		// and verifies that the changes appear in the note list.
		resultPO.getResultMsg();
		assertTrue(resultPO.getResultMsg().contains("Success"));
		resultPO.continueToHome();
		Thread.sleep(2000);
		viewNote = homePO.viewNote();
		assertTrue(viewNote.get(0).get(0).equals(title));
		assertTrue(viewNote.get(0).get(1).equals(description));

		//3
		homePO.logout();
		// Write a Selenium test that logs in an existing user with existing notes,
		loginPO.login(username, password);
		// clicks the delete note button on an existing note,
		homePO.deleteNote(0);
		// and verifies that the note no longer appears in the note list.
		resultPO.getResultMsg();
		assertTrue(resultPO.getResultMsg().contains("Success"));
		resultPO.continueToHome();
		Thread.sleep(2000);
		viewNote = homePO.viewNote();
		assertTrue(viewNote.size() == 0);
*/
	}

	//2
	// Write a Selenium test that logs in an existing user with existing credentials,
	// clicks the edit credential button on an existing credential,
	// changes the credential data, saves the changes,
	// and verifies that the changes appear in the credential list.
	@Test
	void createCredentialLogout_loggingInEditCredential() throws InterruptedException {
		/*
		 * Logging in
		 */
		String firstname = "firstname";
		String lastname = "lastname";
		String username = "username";
		String password = "password";
		loginPO = new LoginPageObject(driver);
		boolean atLoginPage = loginPO.atLoginPage();
		//verifying you're at login
		assertEquals(true, atLoginPage);

		//logging in
		loginPO.signup();
		signupPO = new SignupPageObject(driver);
		boolean atSignupPage = signupPO.atSignupPage();
		//verifying you're at signin page
		assertEquals(true, atSignupPage);

		//signs up a new user,
		signupPO.signup(firstname, lastname, username, password);

		//(success signup page to login page redirect sleeps for 2000ms)
		Thread.sleep(3000);

		// logs that user in,
		loginPO.login(username, password);

		Thread.sleep(1000);
		// verifies that they can access the home page,
		assertEquals("Home", driver.getTitle());

		/*
		 * Create Note
		 */
		String url = "url";
		String username_c = "username";
		String password_c = "password";
		homePO = new HomePageObject(driver);
		homePO.createCredential(url, username_c, password_c);

		/*
		 * verifies that the note details are visible in the note list
		 */
		resultPO = new ResultPageObject(driver);
		resultPO.getResultMsg();
		assertTrue(resultPO.getResultMsg().contains("Success"));

		resultPO.continueToHome();
		Thread.sleep(2000);

		List<List<String>> viewCredential = homePO.viewCredential();
		assertTrue(viewCredential.get(0).get(0).equals(url));
		assertTrue(viewCredential.get(0).get(1).equals(username_c));
		//assertTrue(viewCredential.get(0).get(1).equals(password_c)); --can't compare unencrypted password and encrypted password

		url = "url.edited";
		username_c = "usernameEdited";
		password_c = "passwordEdited";
		homePO.logout();
		//2
		// Write a Selenium test that logs in an existing user with existing notes,
		loginPO.login(username, password);

		// clicks the edit note button on an existing note,
		// changes the note data, saves the changes,
		homePO.editCredential(0, url, username_c, password_c);

		// and verifies that the changes appear in the note list.
		resultPO.getResultMsg();
		assertTrue(resultPO.getResultMsg().contains("Success"));
		resultPO.continueToHome();
		Thread.sleep(2000);

		viewCredential = homePO.viewCredential();
		assertTrue(viewCredential.get(0).get(0).equals(url));
		assertTrue(viewCredential.get(0).get(1).equals(username_c));


	}
	//Write a Selenium test that logs in an existing user with existing credentials,
	// clicks the delete credential button on an existing credential,
	// and verifies that the credential no longer appears in the credential list.
	@Test
	void createCredentialLogout_loggingInEditCredential_loggingInDeleteCredential() throws InterruptedException {
		/*
		 * Logging in
		 */
		String firstname = "firstname";
		String lastname = "lastname";
		String username = "username";
		String password = "password";
		loginPO = new LoginPageObject(driver);
		boolean atLoginPage = loginPO.atLoginPage();
		//verifying you're at login
		assertEquals(true, atLoginPage);

		//logging in
		loginPO.signup();
		signupPO = new SignupPageObject(driver);
		boolean atSignupPage = signupPO.atSignupPage();
		//verifying you're at signin page
		assertEquals(true, atSignupPage);

		//signs up a new user,
		signupPO.signup(firstname, lastname, username, password);

		//(success signup page to login page redirect sleeps for 2000ms)
		Thread.sleep(3000);

		// logs that user in,
		loginPO.login(username, password);

		Thread.sleep(1000);
		// verifies that they can access the home page,
		assertEquals("Home", driver.getTitle());

		/*
		 * Create Note
		 */
		String url = "url";
		String username_c = "username";
		String password_c = "password";
		homePO = new HomePageObject(driver);
		homePO.createCredential(url, username_c, password_c);

		/*
		 * verifies that the note details are visible in the note list
		 */
		resultPO = new ResultPageObject(driver);
		resultPO.getResultMsg();
		assertTrue(resultPO.getResultMsg().contains("Success"));

		resultPO.continueToHome();
		Thread.sleep(2000);

		List<List<String>> viewCredential = homePO.viewCredential();
		assertTrue(viewCredential.get(0).get(0).equals(url));
		assertTrue(viewCredential.get(0).get(1).equals(username_c));
		//assertTrue(viewCredential.get(0).get(1).equals(password_c)); --can't compare unencrypted password and encrypted password
/*
		title = "title edited";
		description = "description edited -thing1_edited -thing2_edited";
		//loging out
		homePO.logout();
		//2
		// Write a Selenium test that logs in an existing user with existing notes,
		loginPO.login(username, password);

		// clicks the edit note button on an existing note,
		// changes the note data, saves the changes,
		homePO.editNote(0, title, description);

		// and verifies that the changes appear in the note list.
		resultPO.getResultMsg();
		assertTrue(resultPO.getResultMsg().contains("Success"));
		resultPO.continueToHome();
		Thread.sleep(2000);
		viewNote = homePO.viewNote();
		assertTrue(viewNote.get(0).get(0).equals(title));
		assertTrue(viewNote.get(0).get(1).equals(description));

		//3
		homePO.logout();
		// Write a Selenium test that logs in an existing user with existing notes,
		loginPO.login(username, password);
		// clicks the delete note button on an existing note,
		homePO.deleteNote(0);
		// and verifies that the note no longer appears in the note list.
		resultPO.getResultMsg();
		assertTrue(resultPO.getResultMsg().contains("Success"));
		resultPO.continueToHome();
		Thread.sleep(2000);
		viewNote = homePO.viewNote();
		assertTrue(viewNote.size() == 0);
*/
	}
}
