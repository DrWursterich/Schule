package Paketstation;

public class Package {
	public static int MAX_RECEIVER_LENGTH = 16;
	private final String receiver;
	private final int number;

	public Package(String receiver, int number) throws IllegalArgumentException {
		if (receiver == null) {
			throw new IllegalArgumentException("Receiver cannot be null");
		}
		this.receiver = receiver;
		this.number = number;
	}

	public String getReceiver() {
		return this.receiver;
	}

	public int getNumber() {
		return this.number;
	}

	@Override
	public boolean equals(Object other) {
		try {
			if (other == null || !(other instanceof Package)) {
				return false;
			}
			Package otherPackage = ((Package)other);
			return this.number == otherPackage.number
					&& this.receiver.equals(otherPackage.receiver);
		} catch (NullPointerException e) {
			e.printStackTrace();
			return false;
		}
	}
}
