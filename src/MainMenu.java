import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;

import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;
import util.Util;

public class MainMenu {

	private static final HotelResource hotelResource = HotelResource.getInstance();

	/**
	 * Prints Main Menu
	 */
	public static final void mainMenu() {

		String line;

		Scanner scanner = new Scanner(System.in);

		System.out.print("\nWelcome to the Hotel Reservation Application\n"
				+ "--------------------------------------------\n" 
				+ "1. Find and reserve a room\n"
				+ "2. See my reservations\n" 
				+ "3. Create an Account\n" 
				+ "4. Admin\n" 
				+ "5. Exit\n"
				+ "--------------------------------------------\n" 
				+ "Please enter a number from the menu options:\n");
		
		try {
			do {
				line = scanner.nextLine();

				if (line.length() == 1) {
					switch (line.charAt(0)) {
					case '1':
						findAndReserveARoom();
						mainMenu();
						break;
					case '2':
						viewCustomerReservations();
						mainMenu();
						break;
					case '3':
						createCustomerAccount();
						mainMenu();
						break;
					case '4':
						AdminMenu.adminMenu();
						break;
					case '5':
						System.out.println("Exit");
						System. exit(0);
						break;
					default:
						System.out.println("Error Invalid Input \n");
						mainMenu();
						break;
					}
				} else {
					System.out.println("Error Invalid Input \n");
					mainMenu();
				}
			} while (line.charAt(0) != '5');

		} catch (StringIndexOutOfBoundsException ex) {

			System.out.println("No valid input entered");
			mainMenu();
		}
	}


	/**
	 * Option 1: Find and reserve a room
	 */
	private static void findAndReserveARoom() {

		final Scanner sc = new Scanner(System.in);

		try {

			System.out.println("Please Enter Check-In Date in mm/dd/yyyy format");
			Date checkInDate = Util.getDateInput(sc.nextLine());

			System.out.println("Please Enter Check-Out Date in mm/dd/yyyy format");
			Date checkOutDate = Util.getDateInput(sc.nextLine());

			if (checkInDate != null && checkOutDate != null && checkInDate.before(checkOutDate)) {

				Collection<IRoom> availableRoomList = hotelResource.findARoom(checkInDate, checkOutDate);

				if (availableRoomList != null && !availableRoomList.isEmpty()) {
					
					reserveARoom(sc, checkInDate, checkOutDate, availableRoomList);
				} else {
					
					Date alternateCheckInDate = Util.addDaysToDate(checkInDate, 7);
					Date alternateCheckOutDate = Util.addDaysToDate(checkOutDate, 7);

					Collection<IRoom> alternateRoomList = hotelResource.findARoom(alternateCheckInDate, alternateCheckOutDate);

					if (alternateRoomList != null && !alternateRoomList.isEmpty()) {
						System.out.println("Rooms available only on alternate dates. Check-In Date:"
								+ alternateCheckInDate + " Check-Out Date:" + alternateCheckOutDate);

						reserveARoom(sc, alternateCheckInDate, alternateCheckOutDate, alternateRoomList);
					} else {
						System.out.println("No available rooms found");
					}
				}
			} else {
				System.out.println("Error Invalid Entry");
				findAndReserveARoom();
			}
		} catch (ParseException ex) {
			System.out.println("Error Invalid Entry. Please try again");
			findAndReserveARoom();
		} catch (Exception ex) {
			System.out.println("Error Invalid Entry. Please try again");
			findAndReserveARoom();
		} 
	}

	private static void reserveARoom(Scanner sc, Date checkInDate, final Date checkOutDate,
			final Collection<IRoom> availableRoomList) {

		System.out.println("Available Rooms are:");
		for (IRoom iRoom : availableRoomList) {
			System.out.println(iRoom);
		}

		System.out.println("Would you like to reserve the room? Enter Y/N");
		final String bookRoomAnswer = sc.nextLine();

		if ("Y".equalsIgnoreCase(bookRoomAnswer)) {

			System.out.println("Do you have an account with us? Enter Y/N");
			final String haveAccountAnswer = sc.nextLine();

			if ("Y".equalsIgnoreCase(haveAccountAnswer)) {

				System.out.println("Please enter your Email:");
				String customerEmail = sc.nextLine();
				
				while(!Util.isValidEmailFormat(customerEmail)) {
					
					System.out.println("Please enter a valid email:");
					customerEmail = sc.nextLine();
				}
				
				if(Util.isValidEmailFormat(customerEmail)) {
					
					Customer existingCustomer = hotelResource.getCustomer(customerEmail);
					
					if (existingCustomer != null) {
						
						createReservation(availableRoomList, customerEmail, checkInDate, checkOutDate, sc);

					} else {
						
						System.out.println("Not an existing customer. Please create a new account.");
						createCustomerAccount();
					}
				}

			} else {
				System.out.println("Please create a new account");
				createCustomerAccount();
			}
		}else if(!"N".equalsIgnoreCase(bookRoomAnswer)){
			
			System.out.println("Error Invalid Entry. Please try again");
			reserveARoom(sc, checkInDate, checkOutDate, availableRoomList);
		}
	}
	
	/**
	 * Create reservation for a customer 
	 * @param availableRoomList
	 * @param customerEmail
	 * @param checkInDate
	 * @param checkOutDate
	 * @param sc
	 */
	private static void createReservation(final Collection<IRoom> availableRoomList, String customerEmail, Date checkInDate, Date checkOutDate, Scanner sc) {
		
		System.out.println("Please enter the room number you would like to reserve:");
		final String roomNumber = sc.nextLine();

		IRoom roomReserved = null;
		for (IRoom iRoom : availableRoomList) {
			if (iRoom.getRoomNumber().equalsIgnoreCase(roomNumber)) {
				roomReserved = iRoom;
				break;
			}
		}

		if (roomReserved != null) {

			final Reservation reservation = hotelResource.bookARoom(customerEmail, roomReserved, checkInDate, checkOutDate);
			System.out.println("Room reservation successful \n"+ reservation);
		} else {
			System.out.println("Entered room number not available. Please try again");
		}
	}
	
	
	/**
	 * Option 2: View customer reservations
	 */
	private static void viewCustomerReservations() {

		final Scanner scanner = new Scanner(System.in);

		System.out.println("Please enter your Email:");
		final String customerEmail = scanner.nextLine();
		
		if(!Util.isValidEmailFormat(customerEmail)) {
			System.out.println("Error invalid email format");
			
			//Start from beginning
			viewCustomerReservations();
		}else {

			Collection<Reservation> reservations = hotelResource.getCustomerReservations(customerEmail);
	
			if (reservations != null && !reservations.isEmpty()) {
				for (Reservation reservation : reservations) {
					System.out.println(reservation);
				}
			} else {
				System.out.println("No reservations found");
			}
		}

	}

	/**
	 * Option 3: Create customer account
	 */
	private static void createCustomerAccount() {

		final Scanner scanner = new Scanner(System.in);

		System.out.println("Please enter your Email to create a new account: ");
		final String email = scanner.nextLine();
		
		if(!Util.isValidEmailFormat(email)) {
			System.out.println("Error invalid email format");
			createCustomerAccount();
		}else {

			System.out.println("First Name:");
			final String firstName = scanner.nextLine();
	
			System.out.println("Last Name:");
			final String lastName = scanner.nextLine();
	
			try {
	
				hotelResource.createACustomer(email, firstName, lastName);
	
				System.out.println("Account created successfully");
	
			} catch (IllegalArgumentException ex) {
				System.out.println("Account creation failed. Please try again");
				createCustomerAccount();
			} 
		}
	}
}
