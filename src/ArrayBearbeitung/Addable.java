package ArrayBearbeitung;

public interface Addable {
	public double getValue();
	public default double add(Addable other) {
		return getValue() + other.getValue();
	}
}
