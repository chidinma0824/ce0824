package com.mycompany.app.model;

public class Tool {
	private String code;
	private String type;
	private String brand;
	private Double dailyAmt;
	private Boolean chargeWeekday = true;
	private Boolean chargeWeekend;
	private Boolean chargeHoliday;
	
	
	public Tool(String code, String type, String brand, Double dailyAmt, Boolean chargeWeekend,
			Boolean chargeHoliday) {
		super();
		this.code = code;
		this.type = type;
		this.brand = brand;
		this.dailyAmt = dailyAmt;
		this.chargeWeekend = chargeWeekend;
		this.chargeHoliday = chargeHoliday;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Double getDailyAmt() {
		return dailyAmt;
	}

	public void setDailyAmt(Double dailyAmt) {
		this.dailyAmt = dailyAmt;
	}

	public Boolean getChargeWeekday() {
		return chargeWeekday;
	}

	public void setChargeWeekday(Boolean chargeWeekday) {
		this.chargeWeekday = chargeWeekday;
	}

	public Boolean getChargeWeekend() {
		return chargeWeekend;
	}

	public void setChargeWeekend(Boolean chargeWeekend) {
		this.chargeWeekend = chargeWeekend;
	}

	public Boolean getChargeHoliday() {
		return chargeHoliday;
	}

	public void setChargeHoliday(Boolean chargeHoliday) {
		this.chargeHoliday = chargeHoliday;
	}
	
	
	
	

}
