package com.mycompany.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mycompany.app.service.StoreFrontService;

@SpringBootApplication
public class StoreFrontApplication implements CommandLineRunner {
	@Autowired private StoreFrontService sfService;

	public static void main(String[] args) {
        SpringApplication.run(StoreFrontApplication.class, args);
	}        		
        		
	@Override
	public void run(String... args) throws Exception {

		// check to make sure filename is provided
		if (args.length < 4) {
			System.err.println("Missing fields to help generate agreement, nothing to process.  Exiting...");
			System.exit(0);
		}		
		
		// valid input, process 
		sfService.checkout(args[0], args[1], Integer.valueOf(args[2]), Integer.valueOf(args[3]));
	}

}
