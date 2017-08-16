
public class Main {
	public static void main(String[] args) {
		Board board = new Board(22, true);
		Board newBoard = board.MinConflicts(Integer.MAX_VALUE);
		System.out.println(newBoard);
		System.out.println(newBoard.getNumberOfQueens() + " " + newBoard.isGoal() + " " + newBoard.getNumberImproperQueens());
		System.out.println();
		int map[][] = newBoard.getCollisionMap();
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[i].length; j++) {
				System.out.print(map[i][j] + "  ");
			}
			System.out.println();
		}
	}
}
