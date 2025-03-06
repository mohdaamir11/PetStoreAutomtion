package api.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentReporter;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;


public class ExtentReportManager implements ITestListener {
	
	public ExtentSparkReporter sparkReporter;   // this is for UI of the report
	public ExtentReports extent;                // this is for common information
	public ExtentTest test;                   
	
	String repName;
	
	
	public void onStart(ITestContext testContext) {    // this will start once before starting the test case
		
		
		
		String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		repName = "Test-Report-" + timestamp + ".html";

		sparkReporter = new ExtentSparkReporter(".\\reports\\" + repName);       // specify location of the report
		sparkReporter.config().setDocumentTitle("RestAssuredAutomationProject");   // Title of report 
		sparkReporter.config().setReportName("Pet Store User API");             // name of the report
		sparkReporter.config().setTheme(Theme.DARK);

		extent = new ExtentReports();
		extent.attachReporter (sparkReporter);
		extent.setSystemInfo("Application", "Pet Store Users API");
		extent.setSystemInfo("User Name", System.getProperty("user.name"));
		extent.setSystemInfo("Environemnt", "QA");
		extent.setSystemInfo("Operating System", System.getProperty("os.name"));
		extent.setSystemInfo("user", "Aamir");
		
		
	}
	
	
	  public void onTestSuccess(ITestResult result) {
	        // This method is triggered when a test is successful
	        test = extent.createTest(result.getTestClass().getName());
	        test.assignCategory(result.getMethod().getGroups()); // to display groups in report
	        test.createNode(result.getName());
	        // Log the success information
	        test.log(Status.PASS, "Test Passed");
	        
	    }
	  
	  
	  public void onTestFailure (ITestResult result) {
		  
		  test = extent.createTest(result.getTestClass().getName());	
		  test.createNode(result.getName());
		  test.assignCategory(result.getMethod().getGroups());
		  test.log(Status.FAIL,"Test Failed");	  	
		  test.log(Status.FAIL, result.getThrowable().getMessage());
		  
		  }
	  
	  public void onTestSkip (ITestResult result) {
		  test = extent.createTest(result.getTestClass().getName());	
		  test.createNode(result.getName());
		  test.assignCategory(result.getMethod().getGroups());
		  test.log(Status.SKIP,"Test Skipped");	  	
		  test.log(Status.SKIP, result.getThrowable().getMessage());
	  }
	  
	  
	  public void onFinish (ITestContext testContext) {
		  
		  extent.flush();   //  this will make everything ready in the report 

		  }


}
