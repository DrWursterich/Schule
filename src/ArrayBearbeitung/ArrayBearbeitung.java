package ArrayBearbeitung;

public interface ArrayBearbeitung<T extends Addable> {
	public void ausgebenArray(T[] array);
	public int elementeAnzahl(T[] array);
	public double elementeSumme(T[] array);
}
