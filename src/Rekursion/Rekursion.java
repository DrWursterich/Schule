package Rekursion;

public class Rekursion {
	public static void main(String...args) {
		System.out.print("fib: ");
		for (int i=1;i<=10;i++)
			System.out.print(fib(i) + ", ");
		System.out.print("\nfak: ");
		for (int i=1;i<=10;i++)
			System.out.print(fak(i) + ", ");
	}

	public static int fib(int iteration) {
		return iteration > 2 ? fib(iteration-1) + fib(iteration-2) : 1;
	}

	public static int fak(int val) {
		return val > 1 ? val * fak(val-1) : 1;
	}
}
