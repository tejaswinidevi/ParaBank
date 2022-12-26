package com.paraBanking.stepDefinitions;

import org.testng.AssertJUnit;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.percy.selenium.Percy;
import paraBanking.Constants;

public class ParaBankStepDefinitions {

	public String baseUrl = Constants.BASE_URL;
	public static WebDriver driver;
	public static Logger logger;

	public static String Checking_Account_Number = null;
	public static String Savings_Account_Number = null;

	By adminPage = By.xpath("//a[text()=\"Admin Page\"]");
	By adminPageTitle = By.xpath("//h1[contains(@class,\"title\")]");
	By cleanButton = By.xpath("//button[@value=\"CLEAN\"]");
	By initializeButton = By.xpath("//button[@value=\"INIT\"]");
	By statusMsg = By.xpath("//*[@id=\"rightPanel\"]/p/b");
	By jsonDataAccessMode = By.xpath("//*[@id=\"accessMode3\"]");
	By soapEndPoint = By.xpath("//*[@id=\"soapEndpoint\"]");
	By restEndPoint = By.xpath("//*[@id=\"restEndpoint\"]");
	By endPoint = By.xpath("//*[@id=\"endpoint\"]");
	By submit = By.xpath("//input[@value=\"Submit\"]");
	By loginUserName = By.xpath("//input[contains(@name,\"username\")]");
	By loginPassword = By.xpath("//input[contains(@name,\"password\")]");
	By logInButton = By.xpath("//input[contains(@value,\"Log In\")]");

	By openNewAccount = By.xpath("//a[text()=\"Open New Account\"]");
	By openNewAccountMsg = By.xpath("//h1[text()=\"Open New Account\"]");
	By checkingAccount = By.xpath("//option[text()=\"CHECKING\"]");
	By savingsAccount = By.xpath("//option[text()=\"SAVINGS\"]");

	By openNewAccountButton = By.xpath("//input[@value=\"Open New Account\"]");
	By newAccountId = By.xpath("//*[@id=\"newAccountId\"]");

	By accountDetailsTitle = By.xpath("//h1[text()=\"Account Details\"]");
	By minimumBalanceToOpenAccount = By.xpath("//b[contains(text(),\"A minimum\")]");
	By accountNumber = By.xpath("//*[@id=\"accountId\"]");
	By AccountType = By.xpath("//*[@id=\"accountType\"]");
	By balance = By.xpath("//*[@id=\"balance\"]");
	By availableBalance = By.xpath("//*[@id=\"availableBalance\"]");
	String transactionMsg = "//*[@id=\"transactionTable\"]/tbody/tr[%s]/td[2]/a";
	String credit = "//*[@id=\"transactionTable\"]/tbody/tr[%s]/td[4]";
	String debit = "//*[@id=\"transactionTable\"]/tbody/tr[%s]/td[3]";

	By billPay = By.xpath("//a[text()=\"Bill Pay\"]");
	By billPayMsg = By.xpath("//h1[text()=\"Bill Payment Service\"]");
	By PayeeName = By.xpath("//input[@name=\"payee.name\"]");
	By Address = By.xpath("//input[@name=\"payee.address.street\"]");
	By City = By.xpath("//input[@name=\"payee.address.city\"]");
	By State = By.xpath("//input[@name=\"payee.address.state\"]");
	By ZipCode = By.xpath("//input[@name=\"payee.address.zipCode\"]");
	By Phone = By.xpath("//input[@name=\"payee.phoneNumber\"]");
	By Account = By.xpath("//input[@name=\"payee.accountNumber\"]");
	By VerifyAccount = By.xpath("//input[@name=\"verifyAccount\"]");
	By Amount = By.xpath("//input[@name=\"amount\"]");
	By submitPayment = By.xpath("//input[@value=\"Send Payment\"]");

	By billPayementMsg = By.xpath("//h1[contains(text(),\"Bill Payment Complete\")]");
	By billPayementSubMsg = By.xpath("//p[contains(text(),\"Bill Payment to\")]");

	By accountsOverview = By.xpath("//a[text()=\"Accounts Overview\"]");
	String accountIDs = "//*[@id=\"accountTable\"]/tbody/tr[%s]/td[1]/a";
	By accountsOverviewTableRows = By.xpath("//*[@id=\"accountTable\"]/tbody/tr");

	String billPaymentErrorMessage = "//*[@id=\"rightPanel\"]/div/div[1]/form/table/tbody/tr[%s]/td[3]/span";
	By billPaymentErrorMessageForAccountNumbersMismatch = By
			.xpath("//span[@ng-show=\"validationModel.verifyAccount == 'mismatch'\"]");

	private static Percy percy;
	int minBalance = 0;

	@Before
	public void setup() throws InterruptedException {

		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/Drivers/chromedriver");
		driver = new ChromeDriver();
		logger = Logger.getLogger("paraBanking");
		PropertyConfigurator.configure("Log4j.properties");

		percy = new Percy(driver);
		driver.get(baseUrl);
		percy.snapshot("Home Page");
		driver.manage().window().maximize();
		logger.info("URL is opened");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[text()=\"Admin Page\"]")));

		driver.findElement(adminPage).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1[@class=\"title\"]")));

		AssertJUnit.assertEquals("Administration", driver.findElement(adminPageTitle).getText());

		driver.findElement(cleanButton).click();
		WebElement dataBaseCleaned = driver.findElement(By.xpath("//b[text()=\"Database Cleaned\"]"));
		wait.until(ExpectedConditions.textToBePresentInElement(dataBaseCleaned, "Database Cleaned"));

		AssertJUnit.assertEquals("Database Cleaned", driver.findElement(statusMsg).getText());

		driver.findElement(initializeButton).click();
		wait(driver, By.xpath("//b[text()=\"Database Initialized\"]"));
		WebElement dataBaseInitialized = driver.findElement(By.xpath("//b[text()=\"Database Initialized\"]"));
		wait.until(ExpectedConditions.textToBePresentInElement(dataBaseInitialized, "Database Initialized"));

		AssertJUnit.assertEquals("Database Initialized", driver.findElement(statusMsg).getText());

		driver.findElement(jsonDataAccessMode).click();
		driver.findElement(soapEndPoint).clear();
		driver.findElement(restEndPoint).clear();
		driver.findElement(endPoint).clear();
		driver.findElement(submit).click();

		AssertJUnit.assertEquals("Settings saved successfully.", driver.findElement(statusMsg).getText());
	}

	@Then("^Login to the ParaBank using userName (.*) and password (.*)$")
	public void loginToParaBank(String name, String pwd) {
		driver.findElement(loginUserName).clear();
		driver.findElement(loginUserName).sendKeys(name);
		driver.findElement(loginPassword).clear();
		driver.findElement(loginPassword).sendKeys(pwd);
		driver.findElement(logInButton).click();
		AssertJUnit.assertEquals("ParaBank | Accounts Overview", driver.getTitle());
	}

	@Then("^Open New (.*) Account$")
	public void openAccount(String accountType) throws InterruptedException {
		driver.findElement(openNewAccount).click();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		WebElement openNewAccount = driver.findElement(By.xpath("//h1[text()=\"Open New Account\"]"));
		wait.until(ExpectedConditions.textToBePresentInElement(openNewAccount, "Open New Account"));

		AssertJUnit.assertEquals("Open New Account", driver.findElement(openNewAccountMsg).getText());
		minBalance = Integer
				.parseInt(driver.findElement(minimumBalanceToOpenAccount).getText().split("\\$")[1].split("\\.")[0]
						.replace(",", ""));

		if (accountType.equalsIgnoreCase("Checking")) {
			driver.findElement(checkingAccount).click();
		} else if (accountType.equalsIgnoreCase("Savings")) {
			driver.findElement(savingsAccount).click();
		}

		TimeUnit.SECONDS.sleep(5);
		wait.until(ExpectedConditions.elementToBeClickable(openNewAccountButton));
		driver.findElement(openNewAccountButton).click();

		Wait<WebDriver> fluentWait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(120))
				.pollingEvery(Duration.ofSeconds(5)).ignoring(NoSuchElementException.class);

		fluentWait.until(driver -> {
			return driver.findElement(By.xpath("//h1[text()=\"Account Opened!\"]"));
		});
		WebElement accountOpened = driver.findElement(By.xpath("//h1[text()=\"Account Opened!\"]"));
		wait.until(ExpectedConditions.textToBePresentInElement(accountOpened, "Account Opened!"));

		if (accountType.equalsIgnoreCase("Checking")) {
			Checking_Account_Number = driver.findElement(newAccountId).getText();
		} else if (accountType.equalsIgnoreCase("Savings")) {
			Savings_Account_Number = driver.findElement(newAccountId).getText();
		}
	}

	@And("^verify the details of the (.*) Account created in Account Details page$")
	public void verifyAccountDetailsPage(String accountType) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(90));
		wait.until(ExpectedConditions.elementToBeClickable(newAccountId));
		driver.findElement(newAccountId).click();

		if (accountType.equalsIgnoreCase("Checking")) {
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"accountId\"]")));
			wait.until(ExpectedConditions.textToBePresentInElement(
					driver.findElement(By.xpath("//*[@id=\"accountId\"]")), Checking_Account_Number));

		} else if (accountType.equalsIgnoreCase("Savings")) {
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"accountId\"]")));
			wait.until(ExpectedConditions.textToBePresentInElement(
					driver.findElement(By.xpath("//*[@id=\"accountId\"]")), Savings_Account_Number));

		}

		AssertJUnit.assertEquals("Account Details", driver.findElement(accountDetailsTitle).getText());

		if (accountType.equalsIgnoreCase("Checking")) {
			assertEquals(Checking_Account_Number, driver.findElement(accountNumber).getText());
		} else if (accountType.equalsIgnoreCase("Savings")) {
			assertEquals(Savings_Account_Number, driver.findElement(accountNumber).getText());
		}
		assertEquals("$" + minBalance + ".00", driver.findElement(balance).getText());
		assertEquals("$" + minBalance + ".00", driver.findElement(availableBalance).getText());
		assertEquals("Funds Transfer Received",
				driver.findElement(By.xpath(String.format(transactionMsg, 1))).getText());
		assertEquals("$" + minBalance + ".00", driver.findElement(By.xpath(String.format(credit, 1))).getText());
	}

	@Then("^Transfer an amount of (.*) from (.*) account to the (.*) account for the payeeName (.*) address (.*) city (.*) state (.*) zipCode (.*) phone (.*) account (.*) verifyAccount (.*)$")
	public void transferAmount(String amount, String fromAccount, String toAccount, String payeeName, String address,
			String city, String state, String zipCode, String phone, String account, String verifyAccount)
			throws InterruptedException {
		driver.findElement(billPay).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1[text()=\"Bill Payment Service\"]")));

		WebElement billPaymentService = driver.findElement(By.xpath("//h1[text()=\"Bill Payment Service\"]"));

		wait.until(ExpectedConditions.textToBePresentInElement(billPaymentService, "Bill Payment Service"));

		assertEquals("Bill Payment Service", wait(driver, By.xpath("//h1[text()=\"Bill Payment Service\"]")).getText());
		if (!payeeName.equalsIgnoreCase("empty")) {
			driver.findElement(PayeeName).sendKeys(payeeName);
		}

		if (!address.equalsIgnoreCase("empty")) {
			driver.findElement(Address).sendKeys(address);
		}

		if (!city.equalsIgnoreCase("empty")) {
			driver.findElement(City).sendKeys(city);
		}

		if (!state.equalsIgnoreCase("empty")) {
			driver.findElement(State).sendKeys(state);
		}

		if (!zipCode.equalsIgnoreCase("empty")) {
			driver.findElement(ZipCode).sendKeys(zipCode);
		}

		if (!phone.equalsIgnoreCase("empty")) {
			driver.findElement(Phone).sendKeys(phone);
		}

		if (!account.equalsIgnoreCase("empty")) {
			if (account.equalsIgnoreCase("Savings")) {
				driver.findElement(Account).sendKeys(Savings_Account_Number);
			} else if (account.equalsIgnoreCase("Checking")) {
				driver.findElement(Account).sendKeys(Checking_Account_Number);
			}
		}

		if (!verifyAccount.equalsIgnoreCase("empty")) {
			if (verifyAccount.equalsIgnoreCase("Savings")) {
				driver.findElement(VerifyAccount).sendKeys(Savings_Account_Number);
			} else if (verifyAccount.equalsIgnoreCase("Checking")) {
				driver.findElement(VerifyAccount).sendKeys(Checking_Account_Number);
			}

		}

		if (!amount.equalsIgnoreCase("empty")) {
			driver.findElement(Amount).sendKeys(amount);
		}

		if (fromAccount.equalsIgnoreCase("Savings")) {
			Select fromAccountId = new Select(driver.findElement(By.name("fromAccountId")));
			TimeUnit.SECONDS.sleep(5);
			fromAccountId.selectByVisibleText(Savings_Account_Number);
		} else if (fromAccount.equalsIgnoreCase("Checking")) {
			Select fromAccountId = new Select(driver.findElement(By.name("fromAccountId")));
			fromAccountId.selectByVisibleText(Checking_Account_Number);
		}
		driver.findElement(submitPayment).click();
	}

	@Then("^Verify the response msg of bill payment to (.*) of amount (.*) from (.*) account as successful$")
	public void verifyBillPaymentMsg(String payeeName, String amount, String accountType) throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		WebElement billPaymentComplete = driver.findElement(By.xpath("//h1[text()=\"Bill Payment Complete\"]"));

		wait.until(ExpectedConditions.textToBePresentInElement(billPaymentComplete, "Bill Payment Complete"));

		assertEquals("Bill Payment Complete", billPaymentComplete.getText());
		String accNumber = null;
		if (accountType.equalsIgnoreCase("Savings")) {
			accNumber = Savings_Account_Number;
		} else if (accountType.equalsIgnoreCase("Checking")) {
			accNumber = Checking_Account_Number;
		}

		String paymentMsg = "Bill Payment to " + payeeName + " in the amount of $" + amount + ".00 from account "
				+ accNumber + " was successful.";
		AssertJUnit.assertEquals(paymentMsg, driver.findElement(billPayementSubMsg).getText());
	}

	@And("^Verify that balance and transaction table details of (.*) account is correct after (.*) amount of (.*) (.*) (.*)$")
	public void verifyBalanceAndTransactionTable(String accountType, String transactionType, String amount,
			String from_to, String payeeName) throws InterruptedException {

		driver.findElement(accountsOverview).click();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(90));
		WebElement accountsOverview = driver.findElement(By.xpath("//h1[text()=\"Accounts Overview\"]"));

		wait.until(ExpectedConditions.textToBePresentInElement(accountsOverview, "Accounts Overview"));
		TimeUnit.SECONDS.sleep(10);
		int accountsOverviewTableRowsCount = driver.findElements(accountsOverviewTableRows).size();

		if (accountType.equalsIgnoreCase("Savings")) {
			for (int i = 1; i < accountsOverviewTableRowsCount; i++) {
				String jobId = String.format(accountIDs, i);
				if (driver.findElement(By.xpath(jobId)).getText().equalsIgnoreCase(Savings_Account_Number)) {
					driver.findElement(By.xpath(jobId)).click();
					break;
				}
			}
		} else if (accountType.equalsIgnoreCase("Checking")) {
			for (int i = 1; i < accountsOverviewTableRowsCount; i++) {
				String jobId = String.format(accountIDs, i);
				if (driver.findElement(By.xpath(jobId)).getText().equalsIgnoreCase(Checking_Account_Number)) {
					driver.findElement(By.xpath(jobId)).click();
					break;
				}
			}
		}

		Wait<WebDriver> fluentWait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(90))
				.pollingEvery(Duration.ofSeconds(5)).ignoring(NoSuchElementException.class);

		fluentWait.until(driver -> {
			return driver.findElement(By.xpath("//*[@id=\"accountId\"]"));
		});

		WebElement accountId = driver.findElement(By.xpath("//*[@id=\"accountId\"]"));

		if (accountType.equalsIgnoreCase("Checking")) {
			wait.until(ExpectedConditions.textToBePresentInElement(accountId, Checking_Account_Number));
		} else if (accountType.equalsIgnoreCase("Savings")) {
			wait.until(ExpectedConditions.textToBePresentInElement(accountId, Savings_Account_Number));
		}

		assertEquals("Account Details", driver.findElement(accountDetailsTitle).getText());
		if (accountType.equalsIgnoreCase("Savings")) {
			assertEquals(Savings_Account_Number, driver.findElement(accountNumber).getText());
			assertEquals("SAVINGS", driver.findElement(AccountType).getText());
		} else if (accountType.equalsIgnoreCase("Checking")) {
			assertEquals(Checking_Account_Number, driver.findElement(accountNumber).getText());
			assertEquals("CHECKING", driver.findElement(AccountType).getText());
		}

		if (transactionType.equalsIgnoreCase("transferring")) {
			assertEquals("$" + (minBalance - Integer.parseInt(amount)) + ".00", driver.findElement(balance).getText());
			assertEquals("$" + (minBalance - Integer.parseInt(amount)) + ".00",
					driver.findElement(availableBalance).getText());
			assertEquals("Bill Payment to " + payeeName,
					driver.findElement(By.xpath(String.format(transactionMsg, 2))).getText());
			assertEquals("$" + (Integer.parseInt(amount)) + ".00",
					driver.findElement(By.xpath(String.format(debit, 2))).getText());

		} else if (transactionType.equalsIgnoreCase("receiving")) {
			try {
				if (!driver.findElement(balance).getText().equalsIgnoreCase("$" + (minBalance + amount) + ".00")) {
					fail("Amount didn't get credited to " + payeeName);
				}
				assertEquals("$" + (minBalance + Integer.parseInt(amount)) + ".00",
						driver.findElement(availableBalance).getText());
				assertEquals("Funds Transfer Received",
						driver.findElement(By.xpath(String.format(transactionMsg, 2))).getText());
				assertEquals("$" + (minBalance + Integer.parseInt(amount)) + ".00",
						driver.findElement(By.xpath(String.format(credit, 2))).getText());
			} catch (Exception e) {
				fail(e + "Amount didn't get credited to " + payeeName);
			}

		}

	}

	@And("^Verify the msg of (.*) as (.*)$")
	public void verifyBillPaymentFormErrorMessage(String field, String message) {
		List<String> fields = new ArrayList<String>(Arrays.asList(field.split("\\|")));
		List<String> messages = new ArrayList<String>(Arrays.asList(message.split("\\|")));

		for (int i = 0; i < fields.size(); i++) {
			if (fields.get(i).equalsIgnoreCase("Payee Name")) {
				assertEquals(messages.get(i),
						driver.findElement(By.xpath(String.format(billPaymentErrorMessage, 1))).getText());
			} else if (fields.get(i).equalsIgnoreCase("Address")) {
				assertEquals(messages.get(i),
						driver.findElement(By.xpath(String.format(billPaymentErrorMessage, 2))).getText());
			} else if (fields.get(i).equalsIgnoreCase("City")) {
				assertEquals(messages.get(i),
						driver.findElement(By.xpath(String.format(billPaymentErrorMessage, 3))).getText());
			} else if (fields.get(i).equalsIgnoreCase("State")) {
				assertEquals(messages.get(i),
						driver.findElement(By.xpath(String.format(billPaymentErrorMessage, 4))).getText());
			} else if (fields.get(i).equalsIgnoreCase("Zip Code")) {
				assertEquals(messages.get(i),
						driver.findElement(By.xpath(String.format(billPaymentErrorMessage, 5))).getText());
			} else if (fields.get(i).equalsIgnoreCase("Phone")) {
				assertEquals(messages.get(i),
						driver.findElement(By.xpath(String.format(billPaymentErrorMessage, 6))).getText());
			} else if (fields.get(i).equalsIgnoreCase("Account")) {
				assertEquals(messages.get(i),
						driver.findElement(By.xpath(String.format(billPaymentErrorMessage, 8))).getText());
			} else if (fields.get(i).equalsIgnoreCase("Verify Account")) {
				if (fields.size() == 1 && messages.get(i).equalsIgnoreCase("The account numbers do not match.")) {
					assertEquals(messages.get(i),
							driver.findElement(billPaymentErrorMessageForAccountNumbersMismatch).getText());
				} else {
					assertEquals(messages.get(i),
							driver.findElement(By.xpath(String.format(billPaymentErrorMessage, 9))).getText());
				}

			} else if (fields.get(i).equalsIgnoreCase("Amount")) {
				assertEquals(messages.get(i),
						driver.findElement(By.xpath(String.format(billPaymentErrorMessage, 11))).getText());
			}

		}
	}

	@After
	public void tearDown() {
		driver.quit();
	}

	WebElement wait(WebDriver driver, By elementIdentifier) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(60))
				.pollingEvery(Duration.ofSeconds(5)).ignoring(NoSuchElementException.class);

		return wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				return driver.findElement(elementIdentifier);
			}
		});
	}

}
