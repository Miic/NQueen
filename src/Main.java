
public class Main {
	public static void main(String[] args) {
		
		Board board = new Board(22, true);
		int i = 0;
		while (true) {
			Board newBoard = board.MinConflicts(200);
			System.out.println("\nTrial: " + i + "\n    Conflicts: " + newBoard.getNumberImproperQueens());
			
			if (newBoard.isGoal()) {
				System.out.println("\n" + newBoard);
				System.out.println("\nTrial: " + i + "\n\nNumberOfQueens: " + newBoard.getNumberOfQueens() + "\nIsGoal?: " + newBoard.isGoal());
				break;
			}
			i++;
		}
	}
}
