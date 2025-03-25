package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.endpoints.UserEndPoints2;
import api.payload.User;
import io.restassured.response.Response;

public class UserTests2 {
	
	Faker faker;
	User userPayload;
	
	public Logger logger;
	
	@BeforeClass
	public void setup() {
		
		faker = new Faker();
		userPayload = new User();
		
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstname(faker.name().firstName());
		userPayload.setLastname(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5,10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());
	
	// logs
		logger = LogManager.getLogger(this.getClass());
	}
	
	@Test (priority = 1)
	public void testPostUser() {
	
		logger.info("******************* creation of user **************");
	    Response response =	UserEndPoints2.createUser(userPayload);
	    response.then().log().all();
	    
	    Assert.assertEquals(response.getStatusCode(), 200);
	    
	    logger.info("******************* user is created **************");
	}
	
	@Test (priority = 2)
	public void testGetUserByName() {
		
	logger.info("******************* reading user info **************");
	Response response =	UserEndPoints2.readUser(this.userPayload.getUsername());
	
	response.then().log().all();
	
	Assert.assertEquals(response.getStatusCode(),200); 
	
	logger.info("******************* user info is displayed **************");
		
	}
	
	@Test (priority = 3)
	public void updateUserByName() {
		
		logger.info("******************* updating  user **************");
		
		userPayload.setFirstname(faker.name().firstName());
		userPayload.setLastname(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		
		Response response = UserEndPoints2.updateUser(this.userPayload.getUsername(), userPayload);
//	   response.then().log().body().statusCode(200);  // this is assertion as well  - this is CHAI assertion which comes along with Rest Assured
	   
		 response.then().log().body();
		
		Assert.assertEquals(response.getStatusCode(),200);  // this is testNG asertion
		
		logger.info("*******************  user is updated **************");
		
		// checking data after update
		Response responseAfterUpdate =	UserEndPoints2.readUser(this.userPayload.getUsername());
		Assert.assertEquals(responseAfterUpdate.getStatusCode(),200); 
		
		
		
	}
	
	@Test (priority = 4)
	public void deleteUserByName() {
		
		logger.info("******************* deleting  user **************");
	Response response =	UserEndPoints2.deleteUser(this.userPayload.getUsername());
	Assert.assertEquals(response.getStatusCode(),404); 	
	logger.info("******************* user deleted **************");
	}

}
