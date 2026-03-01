/**
 * SudokuSolver - Solves Sudoku puzzles using backtracking.
 * Provides hints, validates solutions, and counts possible solutions.
 */
class SudokuSolver {
    int SIZE = 9;      // Sudoku grid size
    int BOX = 3;       // Sub-box size (3x3)

    /**
     * Solves Sudoku puzzle using backtracking.
     * @param board Puzzle to solve (modified in-place)
     * @return true if solution found
     */
    boolean solve(int[][] board) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == 0) {           // Find empty cell
                    for (int num = 1; num <= SIZE; num++) {
                        if (canPlace(board, row, col, num)) {
                            board[row][col] = num;    // Try number
                            if (solve(board)) return true;
                            board[row][col] = 0;      // Backtrack
                        }
                    }
                    return false;                     // No valid number
                }
            }
        }
        return true;                                  // All cells filled
    }

    /**
     * Checks if number can be placed at position.
     * @param board Current board state
     * @param row Row index (0-8)
     * @param col Column index (0-8)
     * @param num Number to check (1-9)
     * @return true if placement follows Sudoku rules
     */
    boolean canPlace(int[][] board, int row, int col, int num) {
        // Check row and column
        for (int i = 0; i < SIZE; i++) {
            if (board[row][i] == num || board[i][col] == num) return false;
        }
        
        // Check 3x3 box
        int startRow = (row / BOX) * BOX;
        int startCol = (col / BOX) * BOX;
        for (int r = startRow; r < startRow + BOX; r++) {
            for (int c = startCol; c < startCol + BOX; c++) {
                if (board[r][c] == num) return false;
            }
        }
        return true;
    }

    /**
     * Gets hint for specific cell.
     * @param board Current board
     * @param row Cell row (0-8)
     * @param col Cell column (0-8)
     * @return Correct number for cell, -1 if already filled, 0 if no solution
     */
    int getHint(int[][] board, int row, int col) {
        if (board[row][col] != 0) return -1;          // Cell not empty
        
        int[][] solved = copyBoard(board);
        if (solve(solved)) {
            return solved[row][col];                  // Return solution
        }
        return 0;                                     // Unsolvable
    }

    /**
     * Gets correct number for cell even with user mistakes.
     * @param board Current board (may have errors)
     * @param row Cell row (0-8)
     * @param col Cell column (0-8)
     * @return Correct number from solution
     */
    int getCorrectNumberForCell(int[][] board, int row, int col) {
        int[][] solved = copyBoard(board);
        if (solve(solved)) {
            return solved[row][col];
        }
        return 0;                                    // No solution
    }

    /**
     * Creates deep copy of board.
     * @param board Board to copy
     * @return New board array
     */
    int[][] copyBoard(int[][] board) {
        int[][] copy = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            System.arraycopy(board[i], 0, copy[i], 0, SIZE);
        }
        return copy;
    }

    /**
     * Checks if board has limited solutions (for puzzle difficulty).
     * @param board Puzzle to check
     * @param limit Maximum allowed solutions
     * @return true if solutions ≤ limit
     */
    boolean countSolutionsLimited(int[][] board, int limit) {
        return countLimited(copyBoard(board), 0, 0, limit) <= limit;
    }

    /**
     * Recursively counts solutions with limit.
     * @param board Board to solve
     * @param row Current row
     * @param col Current column
     * @param limit Stop counting if exceeds limit
     * @return Number of solutions found (up to limit+1)
     */
    int countLimited(int[][] board, int row, int col, int limit) {
        if (row == SIZE) return 1;                    // Found solution
        if (col == SIZE) return countLimited(board, row + 1, 0, limit);
        if (board[row][col] != 0) return countLimited(board, row, col + 1, limit);

        int count = 0;
        for (int num = 1; num <= SIZE; num++) {
            if (canPlace(board, row, col, num)) {
                board[row][col] = num;
                count += countLimited(board, row, col + 1, limit);
                if (count > limit) {                  // Early exit
                    board[row][col] = 0;
                    return count;
                }
                board[row][col] = 0;                  // Backtrack
            }
        }
        return count;
    }
}