package com.paraBanking.TestRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
 
@CucumberOptions(tags = "", features = { "src/test/resources/paraBanking.feature" }, glue = {
		"com.paraBanking.stepDefinitions" }, plugin = {})

public class TestRunner extends AbstractTestNGCucumberTests {

}
