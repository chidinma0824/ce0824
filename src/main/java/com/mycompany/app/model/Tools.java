package com.mycompany.app.model;

import java.util.Map;

public class Tools {
	
	//mimic the data from the database, have all necessary info in 1 place
	private static Map<String, Tool> tools = Map.of("CHNS", new Tool("CHNS", "Chainsaw", "Stihl", 1.49, false, true),
			"LADW", new Tool("LADW", "Ladder", "Werner", 1.99, true, false),
			"JAKD", new Tool("JAKD", "Jackhammer", "DeWalt", 2.99, false, false),
			"JAKR", new Tool("JAKR", "Jackhammer", "Ridgid", 2.99, false, false));
	
	
	//provide getter to retrieve Tool object using unique identifier
	public static Tool getTool(String code) {
		return tools.get(code);
	}

}
