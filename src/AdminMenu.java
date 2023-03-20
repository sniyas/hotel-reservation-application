import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;

import api.AdminResource;
import model.Customer;
import model.IRoom;
import model.Room;
import model.RoomType;
import util.Util;

public class AdminMenu {
	private static final AdminResource adminResource = AdminResource.getInstance();

	public static final void adminMenu() {
		String line;
		final Scanner scanner = new Scanner(System.in);

		System.out.print("\nAdmin Menu\n" + "--------------------------------------------\n" 
				+ "1. See all Customers\n"
				+ "2. See all Rooms\n" 
				+ "3. See all Reservations\n" 
				+ "4. Add a Room\n" 
				+ "5. Back to Main Menu\n"
				+ "--------------------------------------------\n" 
				+ "Please enter a number from the menu options:\n");

		try {
			
			do {
				line = scanner.nextLine();

				if (line.length() == 1) {
					switch (line.charAt(0)) {
					case '1':
						viewAllCustomers();
						adminMenu();
						break;
					case '2':
						viewAllRooms();
						adminMenu();
						break;
					case '3':
						viewAllReservations();
						adminMenu();
						break;
					case '4':
						addARoom();
						adminMenu();
						break;
					case '5':
						MainMenu.mainMenu();
						break;
					default:
						System.out.println("Error invalid input \n");
						break;
					}
				} else {
					System.out.println("Error invalid input \n");
				}
			} while (line.charAt(0) != '5');
		} catch (StringIndexOutOfBoundsException ex) {
			System.out.println("No valid input entered, exiting the application");
		}
	}


	/**
	 * Option 1: To view all customers
	 */
	private static void viewAllCustomers() {

		Collection<Customer> customers = adminResource.getAllCustomers();

		if (customers != null && !customers.isEmpty()) {
			for (Customer customer : customers) {
				System.out.println(customer);
			}
		} else {
			System.out.println("No customers found");
		}
	}

	/**
	 * Option 2: To view all rooms
	 */
	private static void viewAllRooms() {
		
		Collection<IRoom> rooms = adminResource.getAllRooms();

		if (rooms == null | rooms.isEmpty()) {
			System.out.println("No rooms found");
		} else {
			for (IRoom iRoom : rooms) {
				System.out.println(iRoom);
			}
		}
	}

	
	/**
	 * Option 3: To view all reservations
	 */
	private static void viewAllReservations() {
		
		adminResource.displayAllReservations();
	}

	
	/**
	 * Option 4: To add a new room
	 */
	private static void addARoom() {

		final Scanner scanner = new Scanner(System.in);

		try {
			System.out.println("Enter the room number:");
			String roomNumber = scanner.nextLine();
			if(Util.isValidNumber(roomNumber)) {
				
				IRoom existingRoom = adminResource.getRoom(roomNumber);
				if(existingRoom == null) {
					System.out.println("Enter the price per night for the room:");
					double roomPrice = readRoomPrice(scanner);

					System.out.println("Enter room type: 1 for single bed, 2 for double bed:");
					RoomType roomType = readRoomType(scanner);

					Room room = new Room(roomNumber, roomPrice, roomType);

					adminResource.addRoom(Collections.singletonList(room));

					System.out.println("Room added successfully");

					addAnotherRoom();
				}else {
					System.out.println("Room Number already exists. Please try again");
					addARoom();
				}

			}else {
				System.out.println("Not a valid room number. Please try again");
				addARoom();
			}

		} catch (Exception ex) {
			System.out.println("Error invalid input. Please try again");
			addARoom();
		}
	}
	

	/**
	 * To check if another room need to be added
	 */
	private static void addAnotherRoom() {

		System.out.println("Would you like to add another room? Enter Y/N");
		final Scanner sc = new Scanner(System.in);

		try {
			String addAnotherRoomAns = sc.nextLine();

			if ("Y".equalsIgnoreCase(addAnotherRoomAns)) {

				addARoom();
			} else if (!"N".equalsIgnoreCase(addAnotherRoomAns)) {
				System.out.println("Error invalid input. Please try again");
				addAnotherRoom();
			}

		} catch (Exception ex) {
			System.out.println("Error invalid input. Please try again");
			addAnotherRoom();
		}
	}

	
	/**
	 * Reads room price
	 * 
	 * @param scanner
	 * @return
	 */
	private static Double readRoomPrice(Scanner scanner) {
		try {
			return Util.StringToDouble(scanner.nextLine());
		} catch (NumberFormatException exp) {
			System.out.println("Error invalid number. Please enter the price again: ");
			return readRoomPrice(scanner);
		}
	}
	

	/**
	 * Reads room type
	 * 
	 * @param scanner
	 * @return
	 */
	private static RoomType readRoomType(Scanner scanner) {
		try {
			return RoomType.roomTypeValue(scanner.nextLine());
		} catch (IllegalArgumentException exp) {
			System.out.println("Error invalid input. Please enter 1 for single bed or 2 for double bed: ");
			return readRoomType(scanner);
		}
	}

}
