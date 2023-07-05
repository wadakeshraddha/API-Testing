package com.herokuapp.restfullbooker;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UpdateBookingTests extends BaseTest {

	@Test
	public void updateBookingTest() {
		// create booking
		Response responseCreate = createBooking();
		// get booking id of new booking
		int bookingid = responseCreate.jsonPath().getInt("bookingid");

		// create json body
		JSONObject body = new JSONObject();
		body.put("firstname", "NewShraddha");
		body.put("lastname", "NewWadake");
		body.put("totalprice",50);
		body.put("depositpaid", true);
		JSONObject bookingDates = new JSONObject();
		bookingDates.put("checkin", "2023-07-01");
		bookingDates.put("checkout", "2023-07-03");
		body.put("bookingdates", bookingDates);
		body.put("additionalneeds", "baby crib in room");

		// update booking
		Response responseUpdate = RestAssured.given(spec).auth().preemptive().basic("admin","password123").contentType(ContentType.JSON).body(body.toString())
				.put("/booking/" + bookingid);
		responseUpdate.print();
		
		// verifications
		// verify response 200
		Assert.assertEquals(responseUpdate.getStatusCode(), 200, "Status code should be 200 but it is not");

		// Verify all fields
		SoftAssert softAssert = new SoftAssert();
		String actualFirstName = responseUpdate.jsonPath().getString("firstname");
		softAssert.assertEquals(actualFirstName, "NewShraddha", "first name in response is not expected");

		String actualLastName = responseUpdate.jsonPath().getString("lastname");
		softAssert.assertEquals(actualLastName, "NewWadake", "last name in response is not expected");

		int price = responseUpdate.jsonPath().getInt("totalprice");
		softAssert.assertEquals(price, 50, "totalprice in response is not expected");

		boolean depositpaid = responseUpdate.jsonPath().getBoolean("depositpaid");
		softAssert.assertTrue(depositpaid, "depositpaid should be true but its not");

		String actualCheckin = responseUpdate.jsonPath().getString("bookingdates.checkin");
		softAssert.assertEquals(actualCheckin, "2023-07-01", "Checkin in response is not expected");

		String actualCheckout = responseUpdate.jsonPath().getString("bookingdates.checkout");
		softAssert.assertEquals(actualCheckout, "2023-07-03", "Checkout in response is not expected");

		String additionalNeeds = responseUpdate.jsonPath().getString("additionalneeds");
		softAssert.assertEquals(additionalNeeds, "baby crib in room", "additionalNeeds in response is not expected");

		softAssert.assertAll();
	}

}
