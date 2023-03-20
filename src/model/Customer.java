package model;

import java.util.regex.Pattern;

public class Customer {

	private String firstName;

	private String lastName;

	private String email;

	private static final String EMAIL_REGEX = "^(.+)@(.+).(.+)$";

	private final Pattern pattern = Pattern.compile(EMAIL_REGEX);

	public Customer(String email, String firstName, String lastName) {
		if (!pattern.matcher(email).matches()) {
			throw new IllegalArgumentException("Error, Invalid email");
		}
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	@Override
	public String toString() {
		return "Name : " + firstName + " " + lastName + " Email : " + email;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

}
