package Paketstation;

public class Package {
	public static int MAX_RECEIVER_LENGTH = 16;
	private final String receiver;
	private final int number;

	public Package(String receiver, int number) {
		this.receiver = receiver;
		this.number = number;
	}

	public String getReceiver() {
		return this.receiver;
	}

	public int getNumber() {
		return this.number;
	}
}
