package model;

public enum RoomType {
	SINGLE("1"), DOUBLE("2");

	private final String type;

	private RoomType(String type) {

		this.type = type;
	}

	public static RoomType roomTypeValue(String type) {
		for (RoomType roomType : RoomType.values()) {
			if (roomType.type.equals(type)) {
				return roomType;
			}
		}
		throw new IllegalArgumentException();

	}

}
