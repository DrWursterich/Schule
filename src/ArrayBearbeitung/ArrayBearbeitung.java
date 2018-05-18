package ArrayBearbeitung;

public interface ArrayBearbeitung<T extends Comparable<?>> {
	public void ausgebenArray(T[] array);
	public int elementeAnzahl(T[] array);
	public T elementeSumme(T[] array);
}
