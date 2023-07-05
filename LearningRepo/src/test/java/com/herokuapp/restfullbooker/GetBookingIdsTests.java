package com.herokuapp.restfullbooker;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class GetBookingIdsTests extends BaseTest {

	@Test
	public void getBookingIdsWithoutFilterTest() {
		// Get response with booking ids
		Response response = RestAssured.given(spec).get("/booking");
		response.print();
		// verify response 200
		Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200 but it is not");
		// verify atleast 1 booking id in response
		List<Integer> bookingIds = response.jsonPath().getList("bookingid");
		Assert.assertFalse(bookingIds.isEmpty(), "List of bookingids is empty but it should not be");
	}

	@Test
	public void getBookingIdsWithFilterTest() {
		//add query parameter to spec
		spec.queryParam("firstname", "Susan");
		spec.queryParam("lastname", "Brown");
		
		// Get response with booking ids
		Response response = RestAssured.given(spec).get("/booking");
		response.print();
		
		// verify response 200
		Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200 but it is not");
		// verify atleast 1 booking id in response
		List<Integer> bookingIds = response.jsonPath().getList("bookingid");
		Assert.assertFalse(bookingIds.isEmpty(), "List of bookingids is empty but it should not be");
	}
}
