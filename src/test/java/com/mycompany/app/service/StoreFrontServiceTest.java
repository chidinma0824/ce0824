package com.mycompany.app.service;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mycompany.app.model.Tool;

@ExtendWith(MockitoExtension.class)
class StoreFrontServiceTest {

	@InjectMocks
	private StoreFrontService sfService;
	
	/**
	 * Throws an exception indicating tool code is not found
	 * @throws Exception
	 */
	@Test
	public void testInvalidToolCode() {

		String toolCode = "BAIN";
		try {
			sfService.checkout(toolCode, "9/5/15", 5, 50);
		} catch(Exception exception) {
			String message = "The tool code provided, " + toolCode + ", is invalid";
			assertEquals(message, exception.getMessage());
		}
	}
	
	
	/**
	 * Throws an exception indicating discount is out of range
	 * @throws Exception
	 */
	@Test
	public void testInvalidDiscount() {

		try {
			sfService.checkout("JAKR", "9/5/15", 5, 101);
			
		} catch(Exception exception) {
			String message = "The discount percentage should be between 0 and 100";
			assertEquals(message, exception.getMessage());
		}
	}
	
	@Test
	public void testInvalidRentalDays() {

		try {
			sfService.checkout("CHNS", "7/2/20", 0, 10);
		} catch(Exception exception) {
			String message = "The number of rental day(s) should be 1 or greater";
			assertEquals(message, exception.getMessage());		}
	}
	
	/**
	 * Provide valid tool code and assert it matches what we received from rental agreement
	 */
	@Test
	public void testValidToolCode() {

		try {
			String code = "LADW";
			LocalDate checkoutDate = LocalDate.of(2020, 7, 2);

			sfService.checkout(code, "7/2/20", 3, 10);
			Tool tool = sfService.getTool();
			assertNotNull(tool);
			assertTrue(tool.getCode().equals(code));
			assertTrue(sfService.getCheckout().isEqual(checkoutDate));

			
		} catch(Exception e) {}
	}
	
	/**
	 * Provide valid tool code with no discount and assert pre-discount and final amount are same
	 */
	@Test
	public void testValidToolCodeZeroDiscount() {

		try {
			String code = "LADW";
			LocalDate checkoutDate = LocalDate.of(2020, 7, 2);

			sfService.checkout(code, "7/2/20", 3, 0);
			Tool tool = sfService.getTool();
			assertNotNull(tool);
			assertTrue(tool.getCode().equals(code));
			assertTrue(sfService.getCheckout().isEqual(checkoutDate));
			assertTrue(sfService.getPreDiscountAmt().equals(sfService.getFinalAmt()));
			
		} catch(Exception e) {}
	}

}
