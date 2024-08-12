package com.mycompany.app;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class StoreFrontTest {

	/**
	 * Throws an exception indicating tool code is not found
	 * @throws Exception
	 */
	@Test
	public void testInvalidToolCode() {
		StoreFront t1 = new StoreFront();
		try {
			t1.checkout("BAIN", "9/5/15", 5, 50);
		} catch(Exception exception) {
			exception.getMessage().contains("tool code");
		}
	}
	
	
	/**
	 * Throws an exception indicating discount is out of range
	 * @throws Exception
	 */
	@Test
	public void testInvalidDiscount() {
		StoreFront t1 = new StoreFront();
		try {
			t1.checkout("JAKR", "9/5/15", 5, 101);
		} catch(Exception exception) {
			exception.getMessage().contains("discount percentage");
		}
	}
	
	@Test
	public void testInvalidRentalDays() {
		StoreFront t1 = new StoreFront();
		try {
			t1.checkout("CHNS", "7/2/20", 0, 10);
		} catch(Exception exception) {
			exception.getMessage().contains("number of rental day(s)");
		}
	}
	
	/**
	 * Provide valid tool code and assert it matches what we received from rental agreement
	 */
	@Test
	public void testValidToolCode() {
		StoreFront t2 = new StoreFront();
		try {
			String code = "LADW";
			LocalDate checkoutDate = LocalDate.of(2020, 7, 2);

			t2.checkout(code, "7/2/20", 3, 10);
			Tool tool = t2.getTool();
			assertNotNull(tool);
			assertTrue(tool.getCode().equals(code));
			assertTrue(t2.getCheckout().isEqual(checkoutDate));

			
		} catch(Exception e) {}
	}
	
	/**
	 * Provide valid tool code with no discount and assert pre-discount and final amount are same
	 */
	@Test
	public void testValidToolCodeZeroDiscount() {
		StoreFront t3 = new StoreFront();
		try {
			String code = "LADW";
			LocalDate checkoutDate = LocalDate.of(2020, 7, 2);

			t3.checkout(code, "7/2/20", 3, 0);
			Tool tool = t3.getTool();
			assertNotNull(tool);
			assertTrue(tool.getCode().equals(code));
			assertTrue(t3.getCheckout().isEqual(checkoutDate));
			assertTrue(t3.getPreDiscountAmt().equals(t3.getFinalAmt()));
			
		} catch(Exception e) {}
	}

}
