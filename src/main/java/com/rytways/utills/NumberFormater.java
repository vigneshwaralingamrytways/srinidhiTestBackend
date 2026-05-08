package com.rytways.utills;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class NumberFormater {
	
	public static String formatAsIndianRupee(double amount) {
		//DecimalFormat indianFormat = new DecimalFormat("#,##,##,###.00");
		if (amount % 1 == 0) {
            // Format as a whole number if no decimal part
            return formatNumberInIndianStyle((long) amount);
        } else {
            // Format with two decimal places if there's a fractional part
            DecimalFormat indianFormat = new DecimalFormat("#,##,##,###.##");
            return indianFormat.format(amount);
        }
    }
	
	 public static String formatNumberInIndianStyle(long number) {
	        String numStr = String.valueOf(number);
	        int len = numStr.length();

	        // Handling cases based on length
	        if (len <= 3) {
	            return numStr;
	        }

	        // Last 3 digits after the first comma
	        String lastThree = numStr.substring(len - 3);
	        StringBuilder result = new StringBuilder("," + lastThree);

	        // Process the remaining digits in pairs of two from the end
	        int remainingLength = len - 3;
	        while (remainingLength > 2) {
	            result.insert(0, "," + numStr.substring(remainingLength - 2, remainingLength));
	            remainingLength -= 2;
	        }

	        // Insert the remaining digits at the beginning
	        result.insert(0, numStr.substring(0, remainingLength));

	        return result.toString();
	    }
}


