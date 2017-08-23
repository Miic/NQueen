
public class Main {
	public static void main(String[] args) {
		
		final int max = 200;
		Board board = new Board(22, true);
		for (int i = 0; i < max; i++) {
			Board newBoard = board.MinConflicts(10000);
			System.out.println("\nTrial: " + i + "/" + max + "\n    Conflicts: " + newBoard.getNumberImproperQueens());
			
			if (newBoard.isGoal()) {
				System.out.println("\n" + newBoard);
				System.out.println("\nTrial: " + i + "/" + max + "\n\nNumberOfQueens: " + newBoard.getNumberOfQueens() + "\nIsGoal?: " + newBoard.isGoal() + "\nNumberOfConflictingQueens: " + newBoard.getNumberImproperQueens() + "\nNumber Proper Queens: " + newBoard.getNumberProperQueens());
				break;
			}
		}
	}
}
