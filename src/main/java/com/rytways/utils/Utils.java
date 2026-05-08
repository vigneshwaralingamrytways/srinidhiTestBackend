package com.rytways.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Utils {
	
	public static String DATEFORMATyyyyMMdd = "yyyy-MM-dd";
	
	public static Date getFinancialStartDate(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DATE, 1);

		int month = cal.get(Calendar.MONTH);
		// If months between Jan and March subtract 1 from year
		if (month >= Calendar.JANUARY && month <= Calendar.MARCH)
		{
			cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) - 1);
		}
		cal.set(Calendar.MONTH, Calendar.APRIL);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}
	
	public static String getFormattedDateTime(Date date, String format)
	{
		String returnDate = "";
		if (date == null)
			return returnDate;
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		returnDate = formatter.format(date);
		return returnDate;
	}
	
	public static Date getDateFromString(String format, String datetoConvert)
	{
		Date date = null;
		try
		{
			date = new SimpleDateFormat(format, Locale.ENGLISH).parse(datetoConvert);

		}
		catch (ParseException e)
		{

		}
		return date;
	}
	
	  public static LocalDate getFinancialStartDate(LocalDate date) {
	        int year = date.getYear();
	        LocalDate financialStartDate = LocalDate.of(year, Month.APRIL, 1);
	        
	        if (date.isBefore(financialStartDate)) {
	            financialStartDate = financialStartDate.minusYears(1);
	        }

	        return financialStartDate;
	    }
	  
	  
	  public static String getFinancialYear(LocalDate date) {
		    int year = date.getYear();
		    LocalDate financialStartDate = LocalDate.of(year, Month.APRIL, 1);
		    
		    // Determine the start year of the financial year
		    if (date.isBefore(financialStartDate)) {
		        year -= 1;
		    }
		    
		    int startYear = year;
		    int endYear = year + 1;

		    // Format years as two digits
		    String startYearStr = String.format("%02d", startYear % 100);
		    String endYearStr = String.format("%02d", endYear % 100);

		    return startYearStr + "/" + endYearStr;
		}
}
