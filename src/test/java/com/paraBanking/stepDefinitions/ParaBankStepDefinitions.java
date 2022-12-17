package com.paraBanking.stepDefinitions;

import org.testng.AssertJUnit;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import paraBanking.Constants;

public class ParaBankStepDefinitions {

	public String baseUrl = Constants.BASE_URL;
	public String userName = Constants.USER_NAME;
	public String password = Constants.PASSWORD;
	public static WebDriver driver;
	public static Logger logger;

	public static String Checking_Account_Number = null;
	public static String Savings_Account_Number = null;

	By adminPage = By.xpath("//*[@id=\"headerPanel\"]/ul[1]/li[6]/a");
	By adminPageTitle = By.xpath("//*[@id=\"rightPanel\"]/h1");
	By cleanButton = By.xpath("//*[@id=\"rightPanel\"]/table/tbody/tr/td[1]/form/table/tbody/tr/td[2]/button");
	By initializeButton = By.xpath("//*[@id=\"rightPanel\"]/table/tbody/tr/td[1]/form/table/tbody/tr/td[1]/button");
	By statusMsg = By.xpath("//*[@id=\"rightPanel\"]/p/b");
	By jsonDataAccessMode = By.xpath("//*[@id=\"accessMode3\"]");
	By soapEndPoint = By.xpath("//*[@id=\"soapEndpoint\"]");
	By restEndPoint = By.xpath("//*[@id=\"restEndpoint\"]");
	By endPoint = By.xpath("//*[@id=\"endpoint\"]");
	By submit = By.xpath("//*[@id=\"adminForm\"]/input");
	By loginUserName = By.xpath("//*[@id=\"loginPanel\"]/form/div[1]/input");
	By loginPassword = By.xpath("//*[@id=\"loginPanel\"]/form/div[2]/input");
	By logInButton = By.xpath("//*[@id=\"loginPanel\"]/form/div[3]/input");

	By openNewAccount = By.xpath("//*[@id=\"leftPanel\"]/ul/li[1]/a");
	By openNewAccountMsg = By.xpath("//*[@id=\"rightPanel\"]/div/div/h1");
	By checkingAccount = By.xpath("//*[@id=\"type\"]/option[1]");
	By savingsAccount = By.xpath("//*[@id=\"type\"]/option[2]");

	By selectAccountNumber = By.xpath("//*[@id=\"fromAccountId\"]/option[2]");
	By openNewAccountButton = By.xpath("//*[@id=\"rightPanel\"]/div/div/form/div/input");
	By accountOpenedMsg = By.xpath("//*[@id=\"rightPanel\"]/div/div/h1");
	By accountOpenedSubMsg = By.xpath("//*[@id=\"rightPanel\"]/div/div/p[1]");
	By newAccountId = By.xpath("//*[@id=\"newAccountId\"]");

	By accountDetailsTitle = By.xpath("//*[@id=\"rightPanel\"]/div/div[1]/h1");
	By accountNumber = By.xpath("//*[@id=\"accountId\"]");
	By AccountType = By.xpath("//*[@id=\"accountType\"]");
	By balance = By.xpath("//*[@id=\"balance\"]");
	By availableBalance = By.xpath("//*[@id=\"availableBalance\"]");
	String transactionMsg = "//*[@id=\"transactionTable\"]/tbody/tr[%s]/td[2]/a";
	String credit = "//*[@id=\"transactionTable\"]/tbody/tr[%s]/td[4]";
	String debit = "//*[@id=\"transactionTable\"]/tbody/tr[%s]/td[3]";

	By billPay = By.xpath("//*[@id=\"leftPanel\"]/ul/li[4]/a");
	By billPayMsg = By.xpath("//*[@id=\"rightPanel\"]/div/div[1]/h1");
	By PayeeName = By.xpath("//*[@id=\"rightPanel\"]/div/div[1]/form/table/tbody/tr[1]/td[2]/input");
	By Address = By.xpath("//*[@id=\"rightPanel\"]/div/div[1]/form/table/tbody/tr[2]/td[2]/input");
	By City = By.xpath("//*[@id=\"rightPanel\"]/div/div[1]/form/table/tbody/tr[3]/td[2]/input");
	By State = By.xpath("//*[@id=\"rightPanel\"]/div/div[1]/form/table/tbody/tr[4]/td[2]/input");
	By ZipCode = By.xpath("//*[@id=\"rightPanel\"]/div/div[1]/form/table/tbody/tr[5]/td[2]/input");
	By Phone = By.name("payee.phoneNumber");
	By Account = By.xpath("//*[@id=\"rightPanel\"]/div/div[1]/form/table/tbody/tr[8]/td[2]/input");
	By VerifyAccount = By.xpath("//*[@id=\"rightPanel\"]/div/div[1]/form/table/tbody/tr[9]/td[2]/input");
	By Amount = By.xpath("//*[@id=\"rightPanel\"]/div/div[1]/form/table/tbody/tr[11]/td[2]/input");
	By submitPayment = By.xpath("//*[@id=\"rightPanel\"]/div/div[1]/form/table/tbody/tr[14]/td[2]/input");

	By billPayementMsg = By.xpath("//*[@id=\"rightPanel\"]/div/div[2]/h1");
	By billPayementSubMsg = By.xpath("//*[@id=\"rightPanel\"]/div/div[2]/p[1]");

	By accountsOverview = By.xpath("//*[@id=\"leftPanel\"]/ul/li[2]/a");
	String accountIDs = "//*[@id=\"accountTable\"]/tbody/tr[%s]/td[1]/a";
	By accountsOverviewTableRows = By.xpath("//*[@id=\"accountTable\"]/tbody/tr");

	String billPaymentErrorMessage = "//*[@id=\"rightPanel\"]/div/div[1]/form/table/tbody/tr[%s]/td[3]/span";

	@Before
	public void setup() {

		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/Drivers/chromedriver");
		driver = new ChromeDriver();
		logger = Logger.getLogger("paraBanking");
		PropertyConfigurator.configure("Log4j.properties");

		driver.get(baseUrl);
		driver.manage().window().maximize();
		logger.info("URL is opened");

		driver.findElement(adminPage).click();
		AssertJUnit.assertEquals("Administration", driver.findElement(adminPageTitle).getText());

		driver.findElement(cleanButton).click();
		AssertJUnit.assertEquals("Database Cleaned", driver.findElement(statusMsg).getText());

		driver.findElement(initializeButton).click();
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

		AssertJUnit.assertEquals("Open New Account", driver.findElement(openNewAccountMsg).getText());

		if (accountType.equalsIgnoreCase("Checking")) {
			driver.findElement(checkingAccount).click();
		} else if (accountType.equalsIgnoreCase("Savings")) {
			driver.findElement(savingsAccount).click();
		}

		TimeUnit.SECONDS.sleep(5);
		driver.findElement(openNewAccountButton).click();
		TimeUnit.SECONDS.sleep(10);

		if (accountType.equalsIgnoreCase("Checking")) {
			Checking_Account_Number = driver.findElement(newAccountId).getText();
		} else if (accountType.equalsIgnoreCase("Savings")) {
			Savings_Account_Number = driver.findElement(newAccountId).getText();
		}
	}

	@And("^verify the details of the (.*) Account created in Account Details page$")
	public void verifyAccountDetailsPage(String accountType) throws InterruptedException {
		driver.findElement(newAccountId).click();
		TimeUnit.SECONDS.sleep(10);

		AssertJUnit.assertEquals("Account Details", driver.findElement(accountDetailsTitle).getText());
		if (accountType.equalsIgnoreCase("Checking")) {
			assertEquals(Checking_Account_Number, driver.findElement(accountNumber).getText());
		} else if (accountType.equalsIgnoreCase("Savings")) {
			assertEquals(Savings_Account_Number, driver.findElement(accountNumber).getText());
		}
		assertEquals("$100.00", driver.findElement(balance).getText());
		assertEquals("$100.00", driver.findElement(availableBalance).getText());
		assertEquals("Funds Transfer Received",
				driver.findElement(By.xpath(String.format(transactionMsg, 1))).getText());
		assertEquals("$100.00", driver.findElement(By.xpath(String.format(credit, 1))).getText());
	}

	@Then("^Transfer an amount of (.*) from (.*) account to the (.*) account for the payeeName (.*) address (.*) city (.*) state (.*) zipCode (.*) phone (.*) account (.*) verifyAccount (.*)$")
	public void transferAmount(String amount, String fromAccount, String toAccount, String payeeName, String address,
			String city, String state, String zipCode, String phone, String account, String verifyAccount)
			throws InterruptedException {
		driver.findElement(billPay).click();
		TimeUnit.SECONDS.sleep(5);

		assertEquals("Bill Payment Service", driver.findElement(billPayMsg).getText());
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
			if (account.equalsIgnoreCase("Savings")) {
				driver.findElement(VerifyAccount).sendKeys(Savings_Account_Number);
			} else if (account.equalsIgnoreCase("Checking")) {
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
		TimeUnit.SECONDS.sleep(10);

		assertEquals("Bill Payment Complete", driver.findElement(billPayementMsg).getText());
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
		TimeUnit.SECONDS.sleep(10);
		assertEquals("Account Details", driver.findElement(accountDetailsTitle).getText());
		if (accountType.equalsIgnoreCase("Savings")) {
			assertEquals(Savings_Account_Number, driver.findElement(accountNumber).getText());
			assertEquals("SAVINGS", driver.findElement(AccountType).getText());
		} else if (accountType.equalsIgnoreCase("Checking")) {
			assertEquals(Checking_Account_Number, driver.findElement(accountNumber).getText());
			assertEquals("CHECKING", driver.findElement(AccountType).getText());
		}

		if (transactionType.equalsIgnoreCase("transferring")) {
			assertEquals("$" + (100 - Integer.parseInt(amount)) + ".00", driver.findElement(balance).getText());
			assertEquals("$" + (100 - Integer.parseInt(amount)) + ".00",
					driver.findElement(availableBalance).getText());
			assertEquals("Bill Payment to " + payeeName,
					driver.findElement(By.xpath(String.format(transactionMsg, 2))).getText());
			assertEquals("$" + (100 - Integer.parseInt(amount)) + ".00",
					driver.findElement(By.xpath(String.format(debit, 2))).getText());

		} else if (transactionType.equalsIgnoreCase("receiving")) {
			try {
				if (!driver.findElement(balance).getText().equalsIgnoreCase("$" + (100 + amount) + ".00")) {
					fail("Amount didn't get credited to " + payeeName);
				}
				assertEquals("$" + (100 + Integer.parseInt(amount)) + ".00",
						driver.findElement(availableBalance).getText());
				assertEquals("Funds Transfer Received",
						driver.findElement(By.xpath(String.format(transactionMsg, 2))).getText());
				assertEquals("$" + (100 + Integer.parseInt(amount)) + ".00",
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
				assertEquals(messages.get(i),
						driver.findElement(By.xpath(String.format(billPaymentErrorMessage, 9))).getText());
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

}
