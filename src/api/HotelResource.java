package api;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

public class HotelResource {

	private static final HotelResource hotelResource = new HotelResource();

	private final CustomerService customerService = CustomerService.getInstance();

	private final ReservationService reservationService = ReservationService.getInstance();

	private HotelResource() {
	}

	public static HotelResource getInstance() {
		return hotelResource;
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
	 * Creates a new customer
	 * 
	 * @param email
	 * @param firstName
	 * @param lastName
	 */
	public void createACustomer(String email, String firstName, String lastName) {
		customerService.addCustomer(email, firstName, lastName);
	}

	/**
	 * Fetches Room for a room number
	 * 
	 * @param roomNumber
	 * @return
	 */
	public IRoom getRoom(String roomNumber) {
		return reservationService.getARoom(roomNumber);
	}

	/**
	 * Make a new reservation
	 * 
	 * @param customerEmail
	 * @param room
	 * @param checkInDate
	 * @param checkOutDate
	 * @return
	 */
	public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate) {
		return reservationService.reserveARoom(getCustomer(customerEmail), room, checkInDate, checkOutDate);
	}

	/**
	 * Fetches reservations made by a customer
	 * 
	 * @param customerEmail
	 * @return
	 */
	public Collection<Reservation> getCustomerReservations(String customerEmail) {
		
		final Customer customer = getCustomer(customerEmail);

		if (customer == null) {
			return Collections.emptyList();
		}

		return reservationService.getCustomersReservation(customer);
	}

	/**
	 * Find available rooms for date period
	 * 
	 * @param checkIn
	 * @param checkOut
	 * @return
	 */
	public Collection<IRoom> findARoom(Date checkIn, Date checkOut) {
		return reservationService.findRooms(checkIn, checkOut);
	}

}
