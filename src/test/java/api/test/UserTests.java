package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.payload.User;
import io.restassured.response.Response;

public class UserTests {
	Faker faker;
	User userPayload;
	public Logger logger;
	@BeforeClass
	public void setupData() {
		faker=new Faker();
		userPayload=new User();
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5,10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		
		//logger=LogManager.getLogger("UserTests");

	}
	
	
	@Test(priority=1)
    public void testPostUser() {
		//logger.info("creating user*************");

		Response  response = UserEndPoints.createUser(userPayload);
		response.then().log().all();
		//logger.info(" user is created*************");

		Assert.assertEquals(response.getStatusCode(), 200);
		
	
}
	@Test(priority=2)
    public void testGetUserByName() {
		//logger.info("reading  user*************");
		Response response = UserEndPoints.readUser(this.userPayload.getUsername());
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		//logger.info("user info is displayed *****************************");

	}
	//data and schema validation TO DO
	
	@Test(priority = 3)
	public void testUpdateUserByName() {
		//logger.info("updating user***********");

		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		
		
		Response  response = UserEndPoints.updateUser(this.userPayload.getUsername(),userPayload);
		response.then().log().body().statusCode(200);
		
		Assert.assertEquals(response.getStatusCode(), 200);
		//logger.info("user is updated*************************");

		
		//checking data after updation
		Response responseAfterUpdate1 = UserEndPoints.readUser(this.userPayload.getUsername());
		Assert.assertEquals(responseAfterUpdate1.getStatusCode(), 200);
		}
	
	@Test(priority = 4)
	public void testDeleteUserByName() {
		//logger.info("deleting user**********************");

		Response response = UserEndPoints.deleteUser(this.userPayload.getUsername());
		Assert.assertEquals(response.getStatusCode(), 200);
		//logger.info("user is deleted**************");

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
