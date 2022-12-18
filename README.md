
# ParaBank

## Java JDK Setup
Java is a scripting language widely used for writing frontend annd backend applications. Inorder to setup java jdk onto your system follow the instructions given in the link [**Java JDK Setup**](https://docs.oracle.com/en/java/javase/15/install/installation-jdk-macos.html#GUID-F575EB4A-70D3-4AB4-A20E-DBE95171AB5F)

## Eclipse IDE Setup
Eclipse is an IDE(Integrated Development Environment) for writing and running java applications. Inorder to setup Eclipse IDE follow the instructions given in the link [**Eclipse IDE Setup**](https://beginnersbook.com/2016/04/how-to-install-eclipse-on-mac-os-x/)

## Cucumber
- Cucumber is the most popular BDD(Behavior Driven Development) framework present to write the scenarios using Gherkin Language. 
- For every feature developed we maintain a seperate feature file covering all the test cases in the forms of scenarios. 
- Cucumber is available as a plugin in Eclipse IDE's MarketPlace
- Follow the instructions to install Cucumber plugin on to your IDE 
- [Cucumber Plugin](https://www.javatpoint.com/install-cucumber-eclipse-plugin)


## Create Maven Project
- Maven is the build tool used to configure the java project 
- Inorder to create a maven project first we need to install Maven onto the machine
- First install home brew onto the machine using terminal 
- Here is the command 
- `/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"`
- Now to install maven follow the steps [**Maven Install**](https://mkyong.com/maven/install-maven-on-mac-osx/)
- To create a maven project follow the steps 
- [Maven Project in Eclipse](https://www.simplilearn.com/tutorials/maven-tutorial/maven-project-in-eclipse)

## Git Setup
- Git and GitHub is Version Control Management Tool used to create and maintain numerous versions of the application as the project proceeds and grows
- It is very helpful to keep in sync among various developers working on the same project
- Inorder to create or clone any github projects first we need to install git on to the system. Use the following homebrew command to do so.
- `brew install git`

## Executing the Automation Suite
- Click on the Code button present on the top right corner of the current page
- Copy the https url  
- Go to any of the folder/directory inside your file system (Documents is preferred) from terminal
- Now give the following command
- `git clone [copied https url]`
- Now the project is available in your machine
- Go to Eclipse and give import **project -> Existing Maven Projects -> Browse the location of your cloned project** and select it
- Now the project is onto the Eclipse
- To run the automation suite go to testng.xml present and give **Run As -> TestNG Suite**
- The tests will be run as per the sequence of the scenarios present in the paraBanking feature file under src/test/resources
- You can track the status of the running scenarios from the console
- After the successful completion of your automation execution go to **test-output -> paraBankTest-Report-[currentDateTime].html**
- Here you can see the status of the scenarios and testcases run








