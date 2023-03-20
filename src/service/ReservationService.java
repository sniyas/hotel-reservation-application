package service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import model.Customer;
import model.IRoom;
import model.Reservation;

public class ReservationService {

	private static final ReservationService reservationService = new ReservationService();

	private final Map<String, IRoom> rooms = new HashMap<>();
	private final Map<String, Collection<Reservation>> reservations = new HashMap<>();

	private ReservationService() {
	}

	public static ReservationService getInstance() {
		return reservationService;
	}

	/**
	 * For admin to add new room
	 * 
	 * @param room
	 */
	public void addRoom(IRoom room) {
		rooms.put(room.getRoomNumber(), room);
	}

	/**
	 * Fetch room using room number
	 * 
	 * @param roomId
	 * @return
	 */
	public IRoom getARoom(String roomId) {
		return rooms.get(roomId);
	}

	/**
	 * To reserve a room
	 * 
	 * @param customer
	 * @param room
	 * @param checkInDate
	 * @param checkOutDate
	 * @return
	 */
	public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
		Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);

		Collection<Reservation> customerReservations = getCustomersReservation(customer);

		if (customerReservations == null) {
			customerReservations = new ArrayList<>();
		}

		customerReservations.add(reservation);
		reservations.put(customer.getEmail(), customerReservations);

		return reservation;
	}

	/**
	 * Find rooms between two dates
	 * 
	 * @param checkInDate
	 * @param checkOutDate
	 * @return
	 */
	public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {

		Collection<Reservation> allReservations = getAllReservations();
		Collection<IRoom> reservedRooms = new ArrayList<>();
		Collection<IRoom> availableRooms = new ArrayList<>();

		for (Reservation reservation : allReservations) {
			if (checkInDate.before(reservation.getCheckOutDate()) && checkOutDate.after(reservation.getCheckInDate())) {
				reservedRooms.add(reservation.getRoom());
			}
		}

		for (IRoom room : rooms.values()) {
			boolean isAvailable = true;
			for (IRoom reservedRoom : reservedRooms) {
				if (room.equals(reservedRoom)) {
					isAvailable = false;
					break;
				}
			}
			if (isAvailable) {
				availableRooms.add(room);
			}
		}

		return availableRooms;
	}

	/**
	 * Fetch reservation for a customer
	 * 
	 * @param customer
	 * @return
	 */
	public Collection<Reservation> getCustomersReservation(Customer customer) {
		return reservations.get(customer.getEmail());
	}

	/**
	 * Prints all reservations
	 */
	public void printAllReservations() {
		Collection<Reservation> reservations = getAllReservations();

		if (reservations != null && !reservations.isEmpty()) {
			for (Reservation reservation : reservations) {
				System.out.println(reservation + "\n");
			}
		} else {
			System.out.println("No reservations found");
		}
	}

	/**
	 * Fetches all rooms
	 * 
	 * @return
	 */
	public Collection<IRoom> getAllRooms() {
		return rooms.values();
	}

	Collection<Reservation> getAllReservations() {
		Collection<Reservation> allReservations = new ArrayList<>();

		for (Collection<Reservation> reservationList : reservations.values()) {
			allReservations.addAll(reservationList);
		}

		return allReservations;
	}

}
