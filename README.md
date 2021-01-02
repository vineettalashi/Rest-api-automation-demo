# Rest-api-automation-demo

#This is a Rest API Automation project in which we have automated 2 Rest Apis which are validating the fields in jSON request body.
#Tech Used :
#BDD: Cucumber
#Programmimg Language: Java
#Reporting: ExtentSpark (HTML+PDF)
#Build Tool: Maven
#Rest Automation : Rest Assured library.
#Assertion Library : JUnit

#Runner File (JunitRunner)
#FeatureRunner.java

#Maven Run Configuration
mvn clean install -Dcucumber.options="classpath:features" -Dcucumber.options="--glue com.qe.vt.steps" -Dcucumber.options="--plugin com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"

#Reports
Extent Reports HTML file is generated under test-output folder.

#Tags
@Improvement : Only run scenarios with new updations (Data Table demo, Examples demo, etc)

#Framework
#src/main/java/com/qe/vt
apihelper -> It consists of Wrapper classes to build the Request Specification object and Execute Http methods to post the request and collect response.
constants -> It consists of Java file having all constants being used.
dataprovider -> It consists of classes which generates the Test Data and ValidationData.java uses those generated data to create methods to be used in Testcases.
pojo -> consists of RequestPojo and RespnsePojo which are used to Post the request and deserialize the json response to ResponsePojo
utils -> It consists of various utility classes with static methods which are used to generate data being used in execution

#src/main/resources
config -> consists of ApiConfig.properties file which has base url , end points and other key value pairs being used

#src/test/java/com/qe/vt
runner -> It consists of runner class which is used to execute all scenarios or only those scenarios based on filter provided using tags.
steps -> It consists of step definition class which has all the corresponding steps mapped to steps used in Feature files

#src/test/resources
features -> Consists of all feature files
InputJsons-> Consists of all Input jsons being used in automation

As part of improvements, I have demonstrated :

1. Test Hooks @Before and @After
2. Added data in Extent Reports (Request and Response) for more readable reports
3. Loaded Json files at run time and removed hardcoded paths from Constants
4. Moved Input JSONs under src/test/resources directory
5. Removed StringBuilder and used JSONArray put() method to create json array from multiple json objects.
6. Removed Object Mapper from Steps file and added methods in JSON Utils to deserialize Json to Pojo
7. Removed unused dependencies from pom.xml



