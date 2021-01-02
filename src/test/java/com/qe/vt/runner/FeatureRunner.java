package com.qe.vt.runner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = { "pretty", "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"},
        glue = "com.qe.vt.steps",
        features = "classpath:features",
        //tags = "@Improvement", //Remove this comment to run only defect scenarios
        monochrome=true
)
public class FeatureRunner {

}
