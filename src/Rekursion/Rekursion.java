package Rekursion;

public class Rekursion {
	public static final int EMPTY = 0;
	public static final int CHEESE = 1;
	public static final int MOUSE = 2;
	public static final int VISITED = 3;
	public static final int WALL = 4;
	private static final int[] dirVecs = new int[] {0, 1, 1, 0, 0, -1, -1, 0};
	
	public static void main(String...args) {
		int[][] field = new int[][]{	{WALL,	WALL, 	EMPTY, 	WALL, 	WALL, 	EMPTY, 	WALL, 	WALL},
										{WALL,	MOUSE,	EMPTY,	WALL,	EMPTY,	EMPTY, 	EMPTY,	WALL},
										{WALL, 	EMPTY,	EMPTY,	EMPTY,	EMPTY,	WALL,	EMPTY,	WALL},
										{CHEESE,WALL,	WALL,	WALL,	EMPTY,	WALL,	WALL,	WALL},
										{EMPTY,	WALL,	EMPTY,	WALL,	EMPTY,	WALL,	WALL,	WALL},
										{EMPTY,	WALL,	EMPTY,	WALL,	EMPTY,	WALL,	WALL,	WALL},
										{EMPTY,	WALL,	EMPTY,	WALL,	EMPTY,	EMPTY,	EMPTY,	EMPTY},
										{EMPTY,	EMPTY,	EMPTY,	EMPTY,	WALL,	EMPTY,	WALL,	EMPTY},
										{WALL,	EMPTY,	WALL,	EMPTY,	WALL,	EMPTY,	WALL,	EMPTY},
										{WALL,	EMPTY,	WALL,	EMPTY,	EMPTY,	EMPTY,	WALL,	EMPTY},
										{EMPTY,	EMPTY,	EMPTY,	WALL,	WALL,	EMPTY,	EMPTY,	EMPTY}};
		
		Rekursion.mouseFindCheeseInLabyrinthIfThereIsOneXD(field);
		for (int i=0;i<field.length;i++) {
			for (int j=0;j<field[i].length;j++) {
				System.out.print("|" + (field[i][j] == WALL ? "X" : (field[i][j] == VISITED ? "+" : "_")) + (j+1 == field[i].length ? "|\n" : ""));
			}
		}
	}

	public static int fib(int iteration) {
		return iteration > 2 ? fib(iteration-1) + fib(iteration-2) : 1;
	}

	public static int fak(int val) {
		return val > 1 ? val * fak(val-1) : 1;
	}
	
	public static boolean[][] queen(int n) {
		boolean[][] field = new boolean[n][n];
		queenRecursive(field, 0);
		return field;
	}
	
	private static boolean queenRecursive(boolean[][] field, int row) {
		if (row == field.length) {
			return true;
		}
		for (int i=field.length-1;i>=0;i--) {
			if (isViable(field, row, i)) {
				field[row][i] = true;
				if (queenRecursive(field, row+1)) {
					return true;
				}
				field[row][i] = false;
			}
		}
		return false;
	}
	
	private static boolean isViable(boolean[][] field, int row, int col) {
		for (int j=field.length-1;j>=0;j--) {
			for (int k=field.length-1;k>=0;k--) {
				if ((j==row || k==col || j-k==row-col || j+k==row+col) && field[j][k]) {
					return false;
				}
			}
		}
		return true;
	}
	
	public static void mouseFindCheeseInLabyrinthIfThereIsOneXD(int[][] field) {
		
	}
	
	public static void mouseRecursive(int[][] field, int x, int y) {
		if (field[x][y] == CHEESE) {
			return;
		}
		field[x][y] = VISITED;
		if (placeEmpty(field, x+1, y)  || placeEmpty(field, x, y-1) || 
				placeEmpty(field, x-1, y) || placeEmpty(field, x, y+1)) {
			for (int i = dirVecs.length-1;i>=0;i-=2) {
				if (placeEmpty(field, x+dirVecs[i], y+dirVecs[i+1])) {
					mouseRecursive(field, x+dirVecs[i], y+dirVecs[i+1]);
				}
			}
		}
	}
	
	private static boolean placeEmpty(int[][] field, int x, int y) {
		return x >= 0 && y >= 0 && x < field.length && y < field[0].length ? field[x][y] > 2 : false;
	}
}
