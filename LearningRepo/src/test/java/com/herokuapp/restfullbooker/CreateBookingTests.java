package com.herokuapp.restfullbooker;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CreateBookingTests extends BaseTest {

	// @Test
	public void createBookingTest() {
		Response response = createBooking();

		// verifications
		// verify response 200
		Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200 but it is not");

		// Verify all fields
		SoftAssert softAssert = new SoftAssert();
		String actualFirstName = response.jsonPath().getString("booking.firstname");
		softAssert.assertEquals(actualFirstName, "Shraddha", "first name in response is not expected");

		String actualLastName = response.jsonPath().getString("booking.lastname");
		softAssert.assertEquals(actualLastName, "Wadake", "last name in response is not expected");

		int price = response.jsonPath().getInt("booking.totalprice");
		softAssert.assertEquals(price, 150, "totalprice in response is not expected");

		boolean depositpaid = response.jsonPath().getBoolean("booking.depositpaid");
		softAssert.assertFalse(depositpaid, "depositpaid should be false but its not");

		String actualCheckin = response.jsonPath().getString("booking.bookingdates.checkin");
		softAssert.assertEquals(actualCheckin, "2023-07-01", "Checkin in response is not expected");

		String actualCheckout = response.jsonPath().getString("booking.bookingdates.checkout");
		softAssert.assertEquals(actualCheckout, "2023-07-03", "Checkout in response is not expected");

		String additionalNeeds = response.jsonPath().getString("booking.additionalneeds");
		softAssert.assertEquals(additionalNeeds, "baby crib in room", "additionalNeeds in response is not expected");

		softAssert.assertAll();
	}

	@Test
	public void createBookingWithPOJOTest() {
		// create body using POJOs
		Bookingdates bookingDates = new Bookingdates("2023-07-01", "2023-07-03");
		Booking booking = new Booking("Olga", "Shyshkin", 200, false, bookingDates, "Baby crib");

		// get response
		Response response = RestAssured.given(spec).contentType(ContentType.JSON).body(booking).post("/booking");
		response.print();
		Bookingid bookingid = response.as(Bookingid.class);

		// verifications
		// verify response 200
		Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200 but it is not");
		System.out.println("Request booking : "+booking.toString());
		System.out.println("Response booking: "+bookingid.getBooking().toString());
		// Verify all fields
		Assert.assertEquals(bookingid.getBooking().toString(),booking.toString());
	}

}
