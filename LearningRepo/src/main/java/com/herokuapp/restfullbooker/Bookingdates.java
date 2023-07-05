package com.herokuapp.restfullbooker;

public class Bookingdates {
	private String checkin;
	private String checkout;
	
	// create parameterized constructor
	public Bookingdates(String checkin, String checkout) {
		
		this.checkin = checkin;
		this.checkout = checkout;
	}

	//create empty constructor
	public Bookingdates() {
		
	}

	@Override
	public String toString() {
		return "Bookingdates [checkin=" + checkin + ", checkout=" + checkout + "]";
	}

	public String getCheckin() {
		return checkin;
	}

	public void setCheckin(String checkin) {
		this.checkin = checkin;
	}

	public String getCheckout() {
		return checkout;
	}

	public void setCheckout(String checkout) {
		this.checkout = checkout;
	}

}
