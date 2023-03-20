package api;

import java.util.Collection;
import java.util.List;

import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

public class AdminResource {

	private static final AdminResource adminResource = new AdminResource();

	private final CustomerService customerService = CustomerService.getInstance();

	private final ReservationService reservationService = ReservationService.getInstance();

	private AdminResource() {
	}

	public static AdminResource getInstance() {
		return adminResource;
	}

	/**
	 * Fetches customer for an email
	 * 
	 * @param email
	 * @return
	 */
	public Customer getCustomer(String email) {
		return customerService.getCustomer(email);
	}

	/**
	 * Add new rooms
	 * 
	 * @param rooms
	 */
	public void addRoom(List<IRoom> rooms) {
		for (IRoom iRoom : rooms) {
			reservationService.addRoom(iRoom);
		}
	}

	/**
	 * Fetches all rooms
	 * 
	 * @return
	 */
	public Collection<IRoom> getAllRooms() {
		return reservationService.getAllRooms();
	}

	/**
	 * Fetches all customers
	 * 
	 * @return
	 */
	public Collection<Customer> getAllCustomers() {
		return customerService.getAllCustomers();
	}

	/**
	 * Prints all reservations
	 */
	public void displayAllReservations() {
		reservationService.printAllReservations();
	}
	
	/**
	 * Fetches room by room number
	 * @param roomNumber
	 * @return
	 */
	public IRoom getRoom(String roomNumber) {
		return reservationService.getARoom(roomNumber);
	}


}
