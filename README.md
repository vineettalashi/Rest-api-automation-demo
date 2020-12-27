# Rest-api-automation-demo

#This is an Rest API Automation project in which we have automated 2 Rest Apis which are validating the fields in jSON request body.
#Tech Used :
#BDD: Cucumber
#Programmimg Language: Java
#Reporting: ExtentSpark (HTML+PDF)
#Build Tool: Maven
#Rest Automation : Rest Assured library.
#Assertion Library : JUnit

#Runner File (JunitRunner)
#FeatureRunner.java

#Reports
#All Scenarios : \Reports-FullSuite\
#Defect Scenarios : \Reports-DefectsOnly\

#Maven Run Configuration
#mvn clean install -Dcucumber.options="classpath:features" -Dcucumber.options="--glue com.bdd.steps" -Dcucumber.options="--plugin com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
