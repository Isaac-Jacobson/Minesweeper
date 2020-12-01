public class TurnOffTheLights {
	private boolean gameOver; // flag to record if the game is over
	private int playerTurn; // whose turn it is
	private int winner; // who the winner is (0 if no winner)
	private int cols, rows; // # of rows and cols in game
	protected int[][] grid;
	protected int[][] information;
	protected int[][] flag;
	private int counter = 0;

	// the grid that stores the pieces
	public enum Tile {
		ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT
	}

	// The constructor initializes the game

	public TurnOffTheLights(int r, int c) {
		// Create the board
		this.cols = c;
		this.rows = r;
		grid = new int[r][c];
		information = new int[r][c];
		flag = new int[r][c];

		// Initialize starting positions
		for (int row = 0; row < grid.length; row++) {
			for (int col = 0; col < grid[0].length; col++) {
				grid[row][col] = 1;
				flag[row][col] = 0;
			}
		}

		do {
			int row = (int) (Math.random() * 9);
			int col = (int) (Math.random() * 9);

			if (information[row][col] != -1) {
				information[row][col] = -1;
				counter++;
			}

		} while (counter < 10);

		// Set that the game is not over
		gameOver = false;

		for (int row = 0; row < grid.length; row++) {
			for (int col = 0; col < grid[0].length; col++) {

				if (information[row][col] != -1) {

					if (isValidMove(row - 1, col - 1) == true) {
						if (information[row - 1][col - 1] == -1) {
							information[row][col]++;
						}
					}
					if (isValidMove(row - 1, col) == true) {
						if (information[row - 1][col] == -1) {
							information[row][col]++;
						}
					}
					if (isValidMove(row - 1, col + 1) == true) {
						if (information[row - 1][col + 1] == -1) {
							information[row][col]++;
						}
					}
					if (isValidMove(row, col - 1) == true) {
						if (information[row][col - 1] == -1) {
							information[row][col]++;
						}
					}
					if (isValidMove(row, col + 1) == true) {
						if (information[row][col + 1] == -1) {
							information[row][col]++;
						}
					}
					if (isValidMove(row + 1, col - 1) == true) {
						if (information[row + 1][col - 1] == -1) {
							information[row][col]++;
						}
					}
					if (isValidMove(row + 1, col) == true) {
						if (information[row + 1][col] == -1) {
							information[row][col]++;
						}
					}
					if (isValidMove(row + 1, col + 1) == true) {
						if (information[row + 1][col + 1] == -1) {
							information[row][col]++;
						}
					}

				}
			}
		}

	}

	/*
	 * Return true if r, c is a valid move for the game.
	 */
	public boolean isValidMove(int r, int c) {
		if (isInGrid(r, c) == false) // if outside grid, not valid
			return false;

		if (grid[r][c] != 1)
			return false;

		return true; // otherwise it's valid
	}

	/*
	 * Return true if the location at row, col is in the bounds of the grid.
	 * Return false otherwise.
	 */
	public boolean isInGrid(int row, int col) {
		if (row < 0)
			return false;
		if (row >= grid.length)
			return false;
		if (col < 0)
			return false;
		if (col >= grid[0].length)
			return false;
		return true;
	}

	/*
	 * Return true if the location l is in the bounds of the grid. Note: this
	 * method calls the other isInGrid to do the work.
	 */
	public boolean isInGrid(Location l) {
		return isInGrid(l.getRow(), l.getCol());
	}

	// makes the move
	// returns false if no move was made, true if the move was successful.
	public boolean move(int r, int c) {
		if (isValidMove(r, c) == false)
			return false; // if not valid, exit
		if (gameOver == true)
			return false; // if game is over, exit
		if (information[r][c] == -1) {
			gameOver = true;
			System.out.println("Suck it, Loser!");
			System.exit(0);
		} else {
			toggelLight(grid, r, c);
			reveal(r, c);

		}

		gameOver = checkForWinner();

		return true; // this means the move was successfully made

	}

	public boolean flag(int r, int c) {
		if (isValidMove(r, c) == false)
			return false; // if not valid, exit
		if (gameOver == true)
			return false; // if game is over, exit

		toggelFlag(flag, r, c);

		gameOver = checkForWinner();

		return true; // this means the move was successfully made

	}

	public void reveal(int r, int c) {
		if (information[r][c] == 0) {
			if (grid[r][c] == 0) {
				toggelLight(grid, r, c);

				if (isValidMove(r - 1, c) == true) {
					toggelLight(grid, r - 1, c);
					reveal(r - 1, c);
				}
				if (isValidMove(r + 1, c) == true) {
					toggelLight(grid, r + 1, c);
					reveal(r + 1, c);
				}
				if (isValidMove(r, c - 1) == true) {
					toggelLight(grid, r, c - 1);
					reveal(r, c - 1);
				}
				if (isValidMove(r, c + 1) == true) {
					toggelLight(grid, r, c + 1);
					reveal(r, c + 1);
				}
			}
		}
	}

	/*
	 * Return true if the game is over. False otherwise.
	 */
	private boolean checkForWinner() {
		int counter = 0;

		for (int row = 0; row < grid.length; row++) {
			for (int col = 0; col < grid[0].length; col++) {
				if (information[row][col] == -1 && flag[row][col] == 1) {
					counter++;
				}
			}
		}

		if (counter == 10) {
			return true;
		}

		for (int row = 0; row < grid.length; row++) {
			for (int col = 0; col < grid[0].length; col++) {
				if (grid[row][col] == 1 && information[row][col] != -1) {
					return false;
				}
			}
		}

		return true;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public void toggelLight(int[][] grid, int r, int c) {
		if (isValidMove(r, c)) {
			if (grid[r][c] == 1) {
				grid[r][c] = 0;
			} else {
				grid[r][c] = 1;
			}
		}
	}

	public void toggelFlag(int[][] flag, int r, int c) {
		if (isValidMove(r, c)) {
			if (flag[r][c] == 1) {
				flag[r][c] = 0;
			} else {
				if (flagCount() < 10) {
					flag[r][c] = 1;
				}
			}
		}
	}

	public int[][] getGrid() {
		return grid;
	}

	public int[][] getInformationGrid() {

		return information;
	}

	public int[][] getFlag() {
		return flag;
	}

	public int flagCount() {

		int counter = 0;

		for (int row = 0; row < grid.length; row++) {
			for (int col = 0; col < grid[0].length; col++) {
				if (flag[row][col] == 1) {
					counter++;
				}
			}
		}
		return counter;
	}
}