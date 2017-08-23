
public class Main {
	public static void main(String[] args) {
		Board board = new Board(15, true);
		Board newBoard = board.MinConflicts(100000);
		System.out.println(newBoard);
		System.out.println("NumberOfQueens: " + newBoard.getNumberOfQueens() + " IsGoal?: " + newBoard.isGoal() + " NumberOfConflictingQueens" + newBoard.getNumberImproperQueens());
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
