package com.mycompany.app.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

import org.springframework.stereotype.Service;

import com.mycompany.app.model.Tool;
import com.mycompany.app.model.Tools;

@Service
public class StoreFrontService {
	
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy");
	private LocalDate checkout;
	private LocalDate dueDate;
	private Integer chargeDays;
	private Double preDiscountAmt;
	private Double discountAmt;
	private Double finalAmt;
	private Tool tool;
	
	
	public String checkout(String code, String checkoutDate, Integer numRentalDays, Integer discount) throws Exception {
		//Validate number of rental days and discount amount before processing
		if(numRentalDays < 1) {
			throw new Exception("The number of rental day(s) should be 1 or greater");
		}
		
		if(discount.intValue() > 100 || discount.intValue() < 0) {
			throw new Exception("The discount percentage should be between 0 and 100");
		}
		
		//Retrieve the tool using the code
		tool = Tools.getTool(code);
		
		if(tool == null) {
			throw new Exception("The tool code provided, " + code + ", is invalid");
		
		} else { //valid tool, now generate agreement
			
			//convert string date to date type so we can check for weekends and holidays
			checkout = LocalDate.parse(checkoutDate, formatter);
			dueDate = checkout.plusDays(numRentalDays);
			
			int weekendDays = calculateWeekendDays(checkout, dueDate);
			int holidayDays = calculateHolidayDays(checkout, dueDate);
			chargeDays = numRentalDays;
			
			// if weekends are free, then no charge
			if(!tool.getChargeWeekend())
				chargeDays = chargeDays - weekendDays;
			
			// if holidays are free, then no charge
			if(!tool.getChargeHoliday())
				chargeDays = chargeDays - holidayDays;
			
			//Calculate amounts
			preDiscountAmt = chargeDays.intValue() * tool.getDailyAmt();
			discountAmt = preDiscountAmt * discount/100;
			finalAmt = preDiscountAmt - discountAmt;
			
			return getAgreement(tool, numRentalDays, discount);
		}
	}
	
	public String getAgreement(Tool tool, Integer numRentalDays, Integer discount) {
		StringBuffer agreement = new StringBuffer();
		
		agreement.append("Tool code: " + tool.getCode() + "\n");
		agreement.append("Tool type: " + tool.getType() + "\n");
		agreement.append("Tool brand: " + tool.getBrand() + "\n");
		agreement.append("Rental days: " + numRentalDays + "\n");
		agreement.append("Checkout date: " + checkout.format(formatter) + "\n");
		agreement.append("Due date: " + dueDate.format(formatter) + "\n");
		agreement.append("Daily rental charge: $" + tool.getDailyAmt() + "\n");
		agreement.append("Charge days: " + chargeDays + "\n");
		agreement.append("Pre-discount charge: $" + BigDecimal.valueOf(preDiscountAmt).setScale(2, RoundingMode.HALF_UP) + "\n");
		agreement.append("Discount percent: " + discount + "%\n");
		agreement.append("Discount amount: $" + BigDecimal.valueOf(discountAmt).setScale(2, RoundingMode.HALF_UP) +"\n");
		agreement.append("Final charge: $" + BigDecimal.valueOf(finalAmt).setScale(2, RoundingMode.HALF_UP) + "\n");

		
		System.out.println(agreement);
		return agreement.toString();
	}
	
	/**
	 * Start from checkout date and count till due date to check for weekend days
	 * @param checkout
	 * @param dueDate
	 * @return
	 */
	public int calculateWeekendDays(LocalDate checkout, LocalDate dueDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("e");
		int numWeekendDays = 0;
		
		//Go between the checkout and due date to calculate # of weekend days
		LocalDate start = checkout;
		while(start.isBefore(dueDate)) {
			int dayOfWeek = Integer.valueOf(start.format(formatter));
			if(dayOfWeek == 1 || dayOfWeek == 7) //Sunday or Saturday then keep track
				numWeekendDays++;
			
			start = start.plusDays(1);
		}
		
		return numWeekendDays;
	}

	
	public int calculateHolidayDays(LocalDate checkout, LocalDate dueDate) {
		LocalDate July4 = LocalDate.of(checkout.getYear(), 7, 4);
		LocalDate laborDay = LocalDate.of(checkout.getYear(), 9, 1).with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));
		int holidayDays = 0;
		
		//Go between the checkout and due date to calculate # of holidays
		LocalDate start = checkout;
		while(start.isBefore(dueDate)) {
			
			//check for labor: 1st Monday in September
			if(start.isEqual(laborDay))
				holidayDays++;
			
			//check for July 4th
			if(start.isEqual(July4))
				holidayDays++;
			
			start = start.plusDays(1);
		}
		
		return holidayDays;
	}
	
	
	
	public LocalDate getCheckout() {
		return checkout;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public Tool getTool() {
		return tool;
	}

	public Double getPreDiscountAmt() {
		return preDiscountAmt;
	}

	public Double getFinalAmt() {
		return finalAmt;
	}

/**
	public static void main(String[] args) throws Exception {
		StoreFront store = new StoreFront();
		
		store.checkout("CHNS", "06/22/24", 95, 20);
	}
**/

}
