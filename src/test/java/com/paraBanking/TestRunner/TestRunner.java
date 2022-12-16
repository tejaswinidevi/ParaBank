package com.paraBanking.TestRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(tags = "", features = { "src/test/resources/Login.feature" }, glue = {
		"com.paraBanking.stepDefinitions" }, plugin = {})

public class TestRunner extends AbstractTestNGCucumberTests {

}
