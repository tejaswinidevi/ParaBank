Feature: Automate ParaBank Website using cucumber,selenium,testng and java

  Scenario Outline: Create Savings account and Verify Account Details page
    Then Login to the ParaBank using userName <name> and password <pwd>
    Then Open New Savings Account
    And verify the details of the Savings Account created in Account Details page
    Then Open New Checking Account
    And verify the details of the Checking Account created in Account Details page

    Examples: 
      | name | pwd  |
      | john | demo |

  Scenario Outline: Bill Payment
    Then Login to the ParaBank using userName <name> and password <pwd>
    Then Open New Savings Account
    And verify the details of the Savings Account created in Account Details page
    Then Open New Checking Account
    And verify the details of the Checking Account created in Account Details page
    Then Transfer an amount of <amount> from Savings account to the Checking account for the payeeName <payeeName> address <address> city <city> state <state> zipCode <zipCode> phone <phone> account <account> verifyAccount <verifyAccount>
    Then Verify the response msg of bill payment to <payeeName> of amount <amount> from Savings account as successful
    And Verify that balance and transaction table details of Savings account is correct after transferring amount of <amount> <from-to> <payeeName>
    And Verify that balance and transaction table details of Checking account is correct after receiving amount of <amount> <from-to> <payeeName>

    Examples: 
      | name | pwd  | payeeName | address                                      | city     | state     | zipCode | phone      | account  | verifyAccount | amount |
      | john | demo | Riya      | Flat 27, SunnyVale Apartments, EastTree Road | Banglore | Karnataka |  600023 | 9182746732 | Checking | Checking      |     50 |

  Scenario Outline: Bill Pay Form Validations
    Then Login to the ParaBank using userName <name> and password <pwd>
    Then Open New Savings Account
    And verify the details of the Savings Account created in Account Details page
    Then Open New Checking Account
    And verify the details of the Checking Account created in Account Details page
    Then Transfer an amount of <amount> from Savings account to the Checking account for the payeeName <payeeName> address <address> city <city> state <state> zipCode <zipCode> phone <phone> account <account> verifyAccount <verifyAccount>
    And Verify the msg of <field> as <message>

    Examples: 
      | name | pwd  | amount | payeeName | address                                      | city     | state     | zipCode | phone      | account  | verifyAccount | field                                                                                   | message                                                                                                                                                                                                               |
      | john | demo |     40 | Riya      | Flat 27, SunnyVale Apartments, EastTree Road | Banglore | Karnataka |  600023 | empty      | Checking | Checking      | Phone                                                                                   | Phone number is required.                                                                                                                                                                                             |
      | john | demo |     50 | Riya      | Flat 27, SunnyVale Apartments, EastTree Road | Banglore | Karnataka |  600023 | 9182736211 | empty    | Checking      | Account-Verify Account                                                                  | Account number is required.-The account numbers do not match.                                                                                                                                                         |
      | john | demo |     60 | Riya      | Flat 27, SunnyVale Apartments, EastTree Road | Banglore | Karnataka |  600023 | 9182736211 | Checking | empty         | Verify Account                                                                          | Account number is required.                                                                                                                                                                                           |
      | john | demo |     70 | Riya      | Flat 27, SunnyVale Apartments, EastTree Road | Banglore | Karnataka |  600023 | empty      | empty    | empty         | Phone-Account-Verify Account                                                            | Phone number is required.-Account number is required.-Account number is required.                                                                                                                                     |
      | john | demo |     42 | Riya      | Flat 27, SunnyVale Apartments, EastTree Road | Banglore | Karnataka |  600023 | empty      | empty    | Checking      | Phone-Account-Verify Account                                                            | Phone number is required.-Account number is required.-The account numbers do not match.                                                                                                                               |
      | john | demo |      4 | Riya      | Flat 27, SunnyVale Apartments, EastTree Road | Banglore | Karnataka |  600023 | empty      | Checking | empty         | Phone-Verify Account                                                                    | Phone number is required.-Account number is required.                                                                                                                                                                 |
      | john | demo |     18 | Riya      | Flat 27, SunnyVale Apartments, EastTree Road | Banglore | Karnataka |  600023 | 9182736211 | empty    | empty         | Account-Verify Account                                                                  | Account number is required.-Account number is required.                                                                                                                                                               |
      | john | demo | empty  | empty     | empty                                        | empty    | empty     | empty   | empty      | empty    | empty         | Payee Name-Address-City-State-Zip Code-Phone-Account-Verify Account-Amount-From account | Payee name is required.-Address is required.-	City is required.-State is required-Zip Code is required.-Phone number is required.-Account number is required.-Account number is required.-The amount cannot be empty. |
