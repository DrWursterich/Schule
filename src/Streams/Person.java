package Streams;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Person {
	private String name;
	private int alter;

	public Person(String name, int alter) {
		this.name = name;
		this.alter = alter;
	}

	public String getName() {
		return name;
	}

	public int getAlter() {
		return alter;
	}

	@Override
	public String toString() {
		return name + ", " + alter + " Jahre";
	}

	public static List<Person> getRandomPersons(int count) {
		String[] names = {"Maisie", "Davis", "Glinda", "Chieko", "Elaine", "Karyl", "Jamika", "Nieves", "Lucila",
				"Dorsey", "Sherice", "Daisey", "Julissa", "Valery", "Millicent", "Monty", "Nilsa", "Melanie", "Carin",
				"Orlando", "Ewa", "Ling", "Albertha", "Nobuko", "Stacie", "Lonny", "Darby", "Kory", "Nadia", "Gladys"};
		return IntStream.range(0, count)
				.mapToObj(i -> new Person(names[(int)(Math.random()*names.length)], (int)(Math.random()*91)))
				.collect(Collectors.toList());
	}

	public int hashCode() {
		final int prime = 31;
		return prime * (prime + alter) + ((name == null) ? 0 : name.hashCode());
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		return this.name == ((Person)obj).getName() && this.alter == ((Person)obj).getAlter();
	}
}

