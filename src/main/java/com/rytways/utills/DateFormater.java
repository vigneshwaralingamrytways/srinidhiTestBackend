package com.rytways.utills;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateFormater {

	public static String formatDate(LocalDate date) {
		 

	        // Parse the string to a LocalDate
	   //     LocalDate date = LocalDate.parse(date.toString());

	        // Define the target format
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	        // Format the LocalDate to the desired format
	        String formattedDate = date.format(formatter);
	        
	        return formattedDate.toString();

	}
	
}
