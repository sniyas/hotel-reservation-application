package model;

import java.util.Objects;

public class Room implements IRoom {

	private String roomNumber;
	private Double price;
	private RoomType enumeration;

	public Room(String roomNumber, Double price, RoomType enumeration) {
		super();
		this.roomNumber = roomNumber;
		this.price = price;
		this.enumeration = enumeration;

	}

	public String getRoomNumber() {
		return roomNumber;
	}

	public Double getRoomPrice() {
		return price;
	}

	public RoomType getRoomType() {
		return enumeration;
	}

	public Boolean isFree() {
		return price != null && price.doubleValue() == 0.0;

	}

	@Override
	public String toString() {
		return "Room Number : " + roomNumber + " Room Price : " + price + " Room Type : " + enumeration;
	}

	@Override
	public boolean equals(Object obj) {

		Room room = (Room) obj;
		return room.getRoomNumber().equals(this.roomNumber);
	}

	@Override
	public int hashCode() {
		return Objects.hash(roomNumber);
	}

}
