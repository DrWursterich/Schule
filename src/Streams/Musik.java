package Streams;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Musik {
	private String name;
	private int laenge;

	public Musik(String name, int laenge) {
		this.name = name;
		this.laenge = laenge;
	}

	public static List<Musik> getRandomMusik(int count) throws IllegalArgumentException{
		String[] musik = new String[]{
				"Stueck1",
				"Lied12",
				"hastenichgesehn",
				"wasweisich",
				"nocheinlied"
		};
		if (count > musik.length) {
			throw new IllegalArgumentException("count ist zu groß!");
		}
		IntStream a = IntStream.range(0, count);
		Stream<Musik> b = a.mapToObj(
				i -> new Musik(musik[(int)(Math.random()*musik.length)], (int)(Math.random()*120)+120)
			);
		Collector<Musik, ?, List<Musik>> c = Collectors.toList();
		return b.collect(c);
		/* ODER
		return IntStream.range(0, count).mapToObj(
				i -> new Musik(musik[(int)(Math.random()*musik.length)], (int)(Math.random()*120)+120)
			).collect(Collectors.toList());
		*/
	}

	@Override
	public String toString() {
		return String.format("%-12s  %d:%02d", this.name, (int)(this.laenge / 60), this.laenge%60);
	}
}
