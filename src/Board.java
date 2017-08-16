import java.util.concurrent.ThreadLocalRandom;

public class Board {

	private boolean[][] board;
	private int numberOfQueens;
	
	public Board(int size) {
		board = new boolean[size][size];
		numberOfQueens = 0;
	}
	
	public Board(int size, boolean randomize) {
		if (randomize) {
			board = new boolean[size][size];
			numberOfQueens = 0;
			for(int i = 0; i < size; i++) {
				togglePosition(i, ThreadLocalRandom.current().nextInt(0, size-1));
			}
		} else {
			board = new boolean[size][size];
			numberOfQueens = 0;
		}
	}
	
	private Board(Board board, int x, int y) {
		this.board = new boolean[board.getSize()][board.getSize()];
		for (int i = 0; i < board.getSize(); i++) {
			for(int j = 0; j < board.getSize(); j++) {
				this.board[i][j] = board.board[i][j];
			}
		}
		numberOfQueens = board.numberOfQueens;
		togglePosition(x, y);
	}
	
	public boolean togglePosition(int x, int y) {
		if (x >= 0 && y >= 0 && x < board.length && y < board.length) {
			board[x][y] = !board[x][y];
			if (board[x][y]) {
				numberOfQueens++;
			} else {
				numberOfQueens--;
			}
		}
		return false;
	}
	
	public int getNumberOfQueens() {
		return numberOfQueens;
	}
	
	public int getSize() {
		return board.length;
	}
	
	public boolean isGoal() {
		return (getNumberOfQueens() == getSize() && getNumberImproperQueens() == 0);
	}
	
	public int getNumberImproperQueens() {
		int counter = 0;
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board.length; j++) {
				if (board[i][j] && checkRadialCollisions(i, j) != 0) {
					counter++;
				}
			}
		}
		return counter;
	}
	
	public int checkRadialCollisions(int x, int y) {
		int numberCount = 0;
		for(int i = 0; i < board.length; i++) {
			if ( i != x && board[i][y]) {
				numberCount++;
			}
		}
		for(int i = 0; i < board.length; i++) {
			if (i != y && board[x][i]) {
				numberCount++;
			}
		}
		for(int i = 1; i < board.length; i++) {
			if (x + i < board.length && y + i < board.length) {
				if (board[x+i][y+i]) {
					numberCount++;
				}
			} else {
				break;
			}
		}
		for(int i = 1; i < board.length; i++) {
			if (x - i >= 0 && y - i >= 0) {
				if (board[x-i][y-i]) {
					numberCount++;
				}
			} else {
				break;
			}
		}
		for(int i = 1; i < board.length; i++) {
			if (x - i >= 0 && y + i < board.length) {
				if (board[x-i][y+i]) {
					numberCount++;
				}
			} else {
				break;
			}
		}
		for(int i = 1; i < board.length; i++) {
			if (x + i < board.length && y - i >= 0) {
				if (board[x+i][y-i]) {
					numberCount++;
				}
			} else {
				break;
			}
		}
		return numberCount;
	}
	
	public boolean hasQueen(int x, int y) {
		return board[x][y];
	}
	
	public boolean isValidPosition(int x, int y) {
		return (checkRadialCollisions(x, y) == 0 && !hasQueen(x, y));
	}
	
	public int[][] getCollisionMap() {
		int[][] map = new int[board.length][board.length];
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board.length; j++) {
				map[i][j] = checkRadialCollisions(i,j);
				if (board[i][j]) {
					map[i][j]++;
				}
			}
		}
		return map;
	}
	
	public Board HillClimbing() {
		Board current = this;
		Board neighbor;
		int minX, minY;
		int[][] collisions = new int[current.getSize()][current.getSize()];
		while (true) {
			minX = minY = 0;
			for(int i = 0; i < current.getSize(); i++) {
				for (int j = 0; j < current.getSize(); j++) {
					if (collisions[i][j] <= collisions[minX][minY] && current.isValidPosition(i, j)) {
						minX = i;
						minY = j;
					}
				}
			}
			neighbor = new Board(current, minX, minY);
			if (neighbor.getNumberOfQueens() <= current.getNumberOfQueens()) {
				return current;
			}
			current = neighbor;
		}
	}
	
	public Board MinConflicts(int maxSteps) {
		Board current = this;
		Board neighbor;
		int lastConflictCount = Integer.MAX_VALUE;
		for (int i = 0; i < maxSteps; i++) {
			if (current.isGoal()) {
				return current;
			}
			int column = ThreadLocalRandom.current().nextInt(0, board.length-1);
			int minVar = 0;
			int minVal = Integer.MAX_VALUE;
			int queenLoc = -1;
			for(int j = 0; j < board.length; j++) {
				if (board[column][j]) {
					queenLoc = j;
				}
				int result = checkRadialCollisions(column, j);
				if ( !current.hasQueen(column, j)) {
					int randomizedOption = ThreadLocalRandom.current().nextInt(0,3);
					if (randomizedOption == 0) { 
						if (result <= minVal) {
							minVal = result;
							minVar = j;
						}
					} else if (randomizedOption == 1) {
						if (result < minVal) {
							minVal = result;
							minVar = j;
						}						
					} else if (randomizedOption == 2) {
						if (result < minVal && j >= 0.35 * board.length && j <= 0.65 * board.length) {
							minVal = result;
							minVar = j;
						}
					} else if (randomizedOption == 3) {
						if (result <= minVal && j >= 0.35 * board.length && j <= 0.65 * board.length) {
							minVal = result;
							minVar = j;
						}
					}
				}
			}
			if (checkRadialCollisions(column, queenLoc) != 0) {
				if ( minVal <= checkRadialCollisions(column, queenLoc) && minVar != queenLoc) {
					neighbor = new Board(current, column, minVar);
					for(int g = 0; g < board.length; g++) {
						if (neighbor.board[column][g] && g != minVar) {
							neighbor.togglePosition(column, g);
						}
					}
					if (lastConflictCount != neighbor.getNumberImproperQueens()) {
						lastConflictCount = current.getNumberImproperQueens();
						if (neighbor.getNumberImproperQueens() <= current.getNumberImproperQueens()) {
							current = neighbor;
						}
					} else {
						lastConflictCount = current.getNumberImproperQueens();
						current = neighbor;
					}
				} else {
					i--;
				}
			} else {
				i--;
			}
			System.out.println( ((float) i / (float) maxSteps) * 100 + "%\n    Number of Conflicts: " + current.getNumberImproperQueens());
		}
		return current;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[i].length; j++) {
				if (board[i][j]) {
					sb.append("X");
				} else {
					if (i % 2 == 0) {
						if (j % 2 != 0) {
							sb.append(" ");
						} else {
							sb.append(".");
						}
					} else {
						if (j % 2 == 0) {
							sb.append(" ");
						} else {
							sb.append(".");
						}
					}
				}
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	
	public boolean equals(Board otherBoard) {
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[i].length; j++) {
				if (board[i][j] != otherBoard.board[i][j]) {
					return false;
				}
			}
		}
		return true;
	}
	
}


