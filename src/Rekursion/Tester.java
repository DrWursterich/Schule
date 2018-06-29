package Rekursion;

public class Tester {
	public static void main(String...args) {
		Bauteil b4719 = new Bauteil(4719, null, 12.5);
		Bauteil b4718 = new Bauteil(4718, null, 0.5);
		Bauteil b4717 = new Bauteil(4717, null, 1.7);
		Bauteil b4716 = new Bauteil(4716, new Bauteil[]{b4719}, 7.2);
		Bauteil b4715 = new Bauteil(4715, new Bauteil[]{b4717, b4718}, 2.8);
		Bauteil b4714 = new Bauteil(4714, null, 2.3);
		Bauteil b4713 = new Bauteil(4713, new Bauteil[]{b4714, b4715, b4715, b4715, b4716}, 1.2);
		Bauteil b4712 = new Bauteil(4712, null, 11.7);
		Bauteil b4711 = new Bauteil(4711, new Bauteil[]{b4712, b4712, b4713}, 3.8);

		System.out.println(b4711.getElementPreis());
	}
}
