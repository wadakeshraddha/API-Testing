package com.herokuapp.restfullbooker;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;

public class GetBookingTests extends BaseTest {

	//@Test
	public void getBookingTest() {

		// create booking
		Response responseCreate = createBooking();
		// get booking id of new booking
		int bookingid = responseCreate.jsonPath().getInt("bookingid");

		// Set path parameter
		spec.pathParam("bookingId", bookingid);

		// Get response with booking
		Response response = RestAssured.given(spec).get("/booking/{bookingId}");
		response.print();

		// verify response 200
		Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200 but it is not");

		// Verify all fields
		SoftAssert softAssert = new SoftAssert();
		String actualFirstName = response.jsonPath().getString("firstname");
		softAssert.assertEquals(actualFirstName, "Shraddha", "first name in response is not expected");

		String actualLastName = response.jsonPath().getString("lastname");
		softAssert.assertEquals(actualLastName, "Wadake", "last name in response is not expected");

		int price = response.jsonPath().getInt("totalprice");
		softAssert.assertEquals(price, 150, "totalprice in response is not expected");

		boolean depositpaid = response.jsonPath().getBoolean("depositpaid");
		softAssert.assertFalse(depositpaid, "depositpaid should be false but its not");

		String actualCheckin = response.jsonPath().getString("bookingdates.checkin");
		softAssert.assertEquals(actualCheckin, "2023-07-01", "Checkin in response is not expected");

		String actualCheckout = response.jsonPath().getString("bookingdates.checkout");
		softAssert.assertEquals(actualCheckout, "2023-07-03", "Checkout in response is not expected");

		String additionalNeeds = response.jsonPath().getString("additionalneeds");
		softAssert.assertEquals(additionalNeeds, "baby crib in room", "additionalNeeds in response is not expected");

		softAssert.assertAll();
	}
	
	@Test
	public void getBookingXMLTest() {

		// create booking
		Response responseCreate = createBooking();
		// get booking id of new booking
		int bookingid = responseCreate.jsonPath().getInt("bookingid");

		// Set path parameter
		spec.pathParam("bookingId", bookingid);

		// Get response with booking
		Header xml = new Header("Accept","application/xml");
		spec.header(xml);
		Response response = RestAssured.given(spec).get("/booking/{bookingId}");
		response.print();

		// verify response 200
		Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200 but it is not");

		// Verify all fields
		SoftAssert softAssert = new SoftAssert();
		String actualFirstName = response.xmlPath().getString("booking.firstname");
		softAssert.assertEquals(actualFirstName, "Shraddha", "first name in response is not expected");

		String actualLastName = response.xmlPath().getString("booking.lastname");
		softAssert.assertEquals(actualLastName, "Wadake", "last name in response is not expected");

		int price = response.xmlPath().getInt("booking.totalprice");
		softAssert.assertEquals(price, 150, "totalprice in response is not expected");

		boolean depositpaid = response.xmlPath().getBoolean("booking.depositpaid");
		softAssert.assertFalse(depositpaid, "depositpaid should be false but its not");

		String actualCheckin = response.xmlPath().getString("booking.bookingdates.checkin");
		softAssert.assertEquals(actualCheckin, "2023-07-01", "Checkin in response is not expected");

		String actualCheckout = response.xmlPath().getString("booking.bookingdates.checkout");
		softAssert.assertEquals(actualCheckout, "2023-07-03", "Checkout in response is not expected");

		String additionalNeeds = response.xmlPath().getString("booking.additionalneeds");
		softAssert.assertEquals(additionalNeeds, "baby crib in room", "additionalNeeds in response is not expected");

		softAssert.assertAll();
	}
}
