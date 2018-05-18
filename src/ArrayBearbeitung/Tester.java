package ArrayBearbeitung;

import java.util.Arrays;
import java.util.Enumeration;

public class Tester<T extends Addable> implements ArrayBearbeitung<T>, Enumeration<T> {
	private T[] items;
	private int pos = 0;

	@Override
	public boolean hasMoreElements() {
		return this.items == null ? false : this.pos < this.items.length-1;
	}

	@Override
	public T nextElement() {
		return this.hasMoreElements() ? this.items[pos++] : null;
	}

	@Override
	public void ausgebenArray(T[] array) {
		for (int i=0;i<array.length;i++) {
			System.out.print((i==0 ? "[" : ", ") + array[i] + (i==array.length-1 ? "]\n" : ""));
		}
	}

	@Override
	public int elementeAnzahl(T[] array) {
		return array.length;
	}

	@Override
	public double elementeSumme(T[] array) {
		return Arrays.stream(array).mapToDouble(i -> i.getValue()).sum();
	}
}
