package com.herokuapp.restfullbooker;

import org.json.JSONObject;
import org.testng.annotations.BeforeMethod;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BaseTest {
	protected RequestSpecification spec;
	@BeforeMethod
	public void setUp() {
		spec = new RequestSpecBuilder().
				setBaseUri("https://restful-booker.herokuapp.com").
				build();
	}
	protected Response createBooking() {
		// create JSON body
		JSONObject body = new JSONObject();
		body.put("firstname", "Shraddha");
		body.put("lastname", "Wadake");
		body.put("totalprice", 150);
		body.put("depositpaid", false);
		JSONObject bookingDates = new JSONObject();
		bookingDates.put("checkin", "2023-07-01");
		bookingDates.put("checkout", "2023-07-03");
		body.put("bookingdates", bookingDates);
		body.put("additionalneeds", "baby crib in room");

		// get response
		Response response = RestAssured.given(spec).contentType(ContentType.JSON).body(body.toString())
				.post("/booking");
		response.print();
		return response;
	}

}
