import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner kb = new Scanner(System.in);
		do {
			System.out.println();
			System.out.println("(1) Steepest-Ascent Hill Climb");
			System.out.println("(2) Min-Conflicts\n");
			System.out.println("Select Option to test for 100 Iterations:");		
			Board board = new Board(22, true);
			int counter = 0;
			long time;
			ArrayList<Board> solutions = new ArrayList<Board>(); 
			switch (kb.nextLine()) {
			case "1":
				time = System.nanoTime();
				for(int i = 0; i < 300; i++) {
					Board newBoard = new Board(22, false);
					for(int i1 = 0; i1 < 3; i1++) {
						newBoard.togglePosition(ThreadLocalRandom.current().nextInt(0, 22), ThreadLocalRandom.current().nextInt(0, 22));
					}
					newBoard = newBoard.HillClimbing();
					System.out.println("\nTrial: " + i + "\n    Conflicts: " + newBoard.getNumberImproperQueens() + "\n    NumberOfQueens: " + newBoard.getNumberOfQueens());
					
					if (newBoard.isGoal()) {
						counter++;
						solutions.add(newBoard);
					}
				}
				time = System.nanoTime() - time;
				break;
			case "2":
				time = System.nanoTime();
				for(int i = 0; i < 300; i++) {
					Board newBoard = board.MinConflicts(200);
					System.out.println("\nTrial: " + i + "\n    Conflicts: " + newBoard.getNumberImproperQueens());
					if (newBoard.isGoal()) {
						counter++;
						solutions.add(newBoard);
					}
				}
				time = System.nanoTime() - time;
				break;
			default:
				System.out.println("Invalid answer. Try Again!");
				continue;
			}
			
			for(int i2 = 0; i2 < solutions.size(); i2++) {
				System.out.println("==========================");
				System.out.println(solutions.get(i2));
				System.out.println("==========================");
			}
			
			System.out.println("\n<!> " + counter + "/300 tests resulted in valid boards. (" + ((float)counter/100f)*100 + "%, Solved in " + time + "ns )");
		} while (true);
	}
}
