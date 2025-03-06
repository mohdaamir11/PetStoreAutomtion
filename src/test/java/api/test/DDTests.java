package api.test;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import api.endpoints.UserEndPoints;
import api.payload.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;

public class DDTests {

	@Test (priority = 1, dataProvider = "Data", dataProviderClass = DataProviders.class)
	public void testPostUser(String userID,String userName, String firstName, String lastName, String userEmail,String pwd, String phone) {
	
		User userPayload = new User();
		
		userPayload.setId(Integer.parseInt(userID));
		userPayload.setUsername(userName);
		userPayload.setFirstname(firstName);
		userPayload.setLastname(lastName);
		userPayload.setEmail(userEmail);
		userPayload.setPassword(pwd);
		userPayload.setPhone(phone);
		
	    Response response =	UserEndPoints.createUser(userPayload);
	    response.then().log().all();
	    
	    Assert.assertEquals(response.getStatusCode(), 200);
	    
	   
	}
	
	@Test (priority = 2, dataProvider = "UserNames", dataProviderClass = DataProviders.class)
	public void deleteUserByName(String username) {
		
		Response response =	UserEndPoints.deleteUser(username);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(),200); 
		
	}
	
}
