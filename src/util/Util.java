package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class Util {

	private static final String DATE_FORMAT = "MM/dd/yyyy";
	private static final SimpleDateFormat SDF = new SimpleDateFormat(DATE_FORMAT);
	private static final String EMAIL_REGEX = "^(.+)@(.+).(.+)$";
	private static final String NUMBER_REGEX = "\\d+";

	/**
	 * Add days to a date
	 * 
	 * @param date
	 * @param dayCount
	 * @return
	 */
	public static Date addDaysToDate(Date date, int dayCount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, dayCount);
		return calendar.getTime();
	}

	/**
	 * Returns Date object for entered date string
	 * 
	 * @param scanner
	 * @return
	 * @throws ParseException
	 */
	public static Date getDateInput(String dateStr) throws ParseException {
		try {
			SDF.setLenient(false);
			return SDF.parse(dateStr);
		} catch (ParseException ex) {
			throw ex;
		}
	}

	/**
	 * Converts string to double
	 * @param numberStr
	 * @return
	 * @throws NumberFormatException
	 */
	public static double StringToDouble(String numberStr) throws NumberFormatException {
		try {
			return Double.parseDouble(numberStr);
		} catch (NumberFormatException ex) {
			throw ex;
		}
	}
	
	/**
	 * Validates email format
	 * @param email
	 * @return
	 */
	public static boolean isValidEmailFormat(String email) {
		Pattern pattern = Pattern.compile(EMAIL_REGEX);
		return pattern.matcher(email).matches();
	}
	
	public static boolean isValidNumber(String numberStr) {
		Pattern pattern = Pattern.compile(NUMBER_REGEX);
		return pattern.matcher(numberStr).matches();
	}

}