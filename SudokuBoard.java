/**
 * SudokuBoard class represents a 9x9 Sudoku puzzle board with game logic and state management.
 * It handles puzzle storage, user moves, hint system, and validation of Sudoku rules.
 */
class SudokuBoard {
    // Game constants
    int SIZE = 9;           // Standard Sudoku grid size
    int BOX = 3;            // Size of each 3x3 sub-box
    
    // Board state arrays
    int[][] board;          // Current state of the board (including user moves)
    int[][] originalPuzzle; // Stores the original puzzle (immutable clues)
    boolean[][] userFilled; // Tracks cells filled by the user (not original puzzle)
    boolean[][] hintCells;  // Tracks cells filled using hints

    /**
     * Constructor initializes all board arrays to empty state.
     */
    SudokuBoard() {
        board = new int[SIZE][SIZE];
        originalPuzzle = new int[SIZE][SIZE];
        userFilled = new boolean[SIZE][SIZE];
        hintCells = new boolean[SIZE][SIZE];
        clearBoard();
    }

    /**
     * Resets the entire board to empty state.
     * All cells are set to 0, and tracking arrays are cleared.
     */
    void clearBoard() {
        for (int row = 0; row < SIZE; row++) {
            for (int column = 0; column < SIZE; column++) {
                board[row][column] = 0;
                originalPuzzle[row][column] = 0;
                userFilled[row][column] = false;
                hintCells[row][column] = false;
            }
        }
    }

    /**
     * Places a number entered by the user.
     * @param row The row number (1-based)
     * @param column The column number (1-based)
     * @param number The number to place (1-9)
     */
    void putNumber(int row, int column, int number) {
        row = row - 1;
        column = column - 1;
        board[row][column] = number;
        userFilled[row][column] = true;
        hintCells[row][column] = false; // User fills are not hint cells
    }

    /**
     * Sets a puzzle clue (original number that cannot be changed by user).
     * @param row The row number (1-based)
     * @param column The column number (1-based)
     * @param number The puzzle clue number (1-9)
     */
    void setNumber(int row, int column, int number) {
        row = row - 1;
        column = column - 1;
        board[row][column] = number;
        originalPuzzle[row][column] = number;
        userFilled[row][column] = false; // Not user-filled
        hintCells[row][column] = false;  // Not a hint
    }

    /**
     * Places a hint number provided by the system.
     * @param row The row number (1-based)
     * @param column The column number (1-based)
     * @param number The hint number (1-9)
     */
    void putHint(int row, int column, int number) {
        row = row - 1;
        column = column - 1;
        board[row][column] = number;
        userFilled[row][column] = true;  // Hint counts as user-filled
        hintCells[row][column] = true;   // Mark as hint cell
    }

    /**
     * Retrieves the current number at a given position.
     * @param row The row number (1-based)
     * @param column The column number (1-based)
     * @return The number at the position (0 if empty)
     */
    int getNumber(int row, int column) {
        return board[row - 1][column - 1];
    }

    /**
     * Retrieves the original puzzle number at a given position.
     * @param row The row number (1-based)
     * @param column The column number (1-based)
     * @return The original puzzle number (0 if not an original clue)
     */
    int getOriginalNumber(int row, int column) {
        return originalPuzzle[row - 1][column - 1];
    }

    /**
     * Checks if a number can be legally placed at a given position.
     * @param row The row number (1-based)
     * @param column The column number (1-based)
     * @param number The number to check (1-9)
     * @return true if the placement is valid according to Sudoku rules
     */
    boolean canPlace(int row, int column, int number) {
        row = row - 1;
        column = column - 1;

        if (board[row][column] != 0)
            return false;

        // Check row
        for (int c = 0; c < SIZE; c++)
            if (board[row][c] == number)
                return false;

        // Check column
        for (int r = 0; r < SIZE; r++)
            if (board[r][column] == number)
                return false;

        // Check 3x3 box
        int startRow = (row / BOX) * BOX;
        int startColumn = (column / BOX) * BOX;
        for (int r = startRow; r < startRow + BOX; r++)
            for (int c = startColumn; c < startColumn + BOX; c++)
                if (board[r][c] == number)
                    return false;

        return true;
    }

    /**
     * Checks if all cells on the board are filled.
     * @return true if no empty cells (0 values) remain
     */
    boolean isFull() {
        for (int row = 0; row < SIZE; row++)
            for (int column = 0; column < SIZE; column++)
                if (board[row][column] == 0)
                    return false;
        return true;
    }

    /**
     * Validates if the completely filled board is a correct Sudoku solution.
     * @return true if the board is full and valid
     */
    boolean checkBoard() {
        if (!isFull()) {
            return false;
        }
        return isValidCompleteBoard();
    }

    /**
     * Checks if a completed board satisfies all Sudoku rules.
     * @return true if rows, columns, and boxes contain digits 1-9 exactly once
     */
    boolean isValidCompleteBoard() {
        // Check rows
        for (int row = 0; row < SIZE; row++) {
            boolean[] seen = new boolean[SIZE + 1];
            for (int col = 0; col < SIZE; col++) {
                int num = board[row][col];
                if (num < 1 || num > 9 || seen[num]) {
                    return false;
                }
                seen[num] = true;
            }
        }
        
        // Check columns
        for (int col = 0; col < SIZE; col++) {
            boolean[] seen = new boolean[SIZE + 1];
            for (int row = 0; row < SIZE; row++) {
                int num = board[row][col];
                if (num < 1 || num > 9 || seen[num]) {
                    return false;
                }
                seen[num] = true;
            }
        }
        
        // Check 3x3 boxes
        for (int boxRow = 0; boxRow < BOX; boxRow++) {
            for (int boxCol = 0; boxCol < BOX; boxCol++) {
                boolean[] seen = new boolean[SIZE + 1];
                int startRow = boxRow * BOX;
                int startCol = boxCol * BOX;
                for (int row = startRow; row < startRow + BOX; row++) {
                    for (int col = startCol; col < startCol + BOX; col++) {
                        int num = board[row][col];
                        if (num < 1 || num > 9 || seen[num]) {
                            return false;
                        }
                        seen[num] = true;
                    }
                }
            }
        }
        
        return true;
    }

    /**
     * Checks if the current board state (including partial fills) is valid.
     * @return true if all placed numbers follow Sudoku rules
     */
    boolean isValidBoard() {
        for (int row = 0; row < SIZE; row++) {
            for (int column = 0; column < SIZE; column++) {
                int number = board[row][column];
                if (number != 0) {
                    board[row][column] = 0;
                    if (!canPlace(row + 1, column + 1, number)) {
                        board[row][column] = number;
                        return false;
                    }
                    board[row][column] = number;
                }
            }
        }
        return true;
    }

    /**
     * Creates a deep copy of the current board state.
     * @return A new 2D array with the current board numbers
     */
    int[][] getBoardCopy() {
        int[][] copy = new int[SIZE][SIZE];
        for (int row = 0; row < SIZE; row++)
            for (int column = 0; column < SIZE; column++)
                copy[row][column] = board[row][column];
        return copy;
    }

    /**
     * Creates a deep copy of the original puzzle.
     * @return A new 2D array with the original puzzle numbers
     */
    int[][] getOriginalPuzzleCopy() {
        int[][] copy = new int[SIZE][SIZE];
        for (int row = 0; row < SIZE; row++)
            for (int column = 0; column < SIZE; column++)
                copy[row][column] = originalPuzzle[row][column];
        return copy;
    }

    /**
     * Replaces the entire board with a new puzzle.
     * @param newBoard The new puzzle to load
     */
    void setBoard(int[][] newBoard) {
        for (int row = 0; row < SIZE; row++) {
            for (int column = 0; column < SIZE; column++) {
                board[row][column] = newBoard[row][column];
                originalPuzzle[row][column] = newBoard[row][column];
                userFilled[row][column] = (newBoard[row][column] == 0);
                hintCells[row][column] = false;
            }
        }
    }

    /**
     * Checks if a cell is empty.
     * @param row The row number (1-based)
     * @param column The column number (1-based)
     * @return true if the cell contains 0
     */
    boolean isEmpty(int row, int column) {
        return board[row - 1][column - 1] == 0;
    }
    
    /**
     * Checks if a cell was filled by the user.
     * @param row The row number (1-based)
     * @param column The column number (1-based)
     * @return true if the cell was filled by user (not an original clue)
     */
    boolean isUserFilled(int row, int column) {
        return userFilled[row - 1][column - 1];
    }
    
    /**
     * Checks if a cell was filled using a hint.
     * @param row The row number (1-based)
     * @param column The column number (1-based)
     * @return true if the cell was filled by a hint
     */
    boolean isHintCell(int row, int column) {
        return hintCells[row - 1][column - 1];
    }
    
    /**
     * Checks if a cell is part of the original puzzle.
     * @param row The row number (1-based)
     * @param column The column number (1-based)
     * @return true if the cell contains an original puzzle clue
     */
    boolean isPuzzleCell(int row, int column) {
        return originalPuzzle[row - 1][column - 1] != 0;
    }
    
    /**
     * Clears a user-filled cell (if it's not an original puzzle cell).
     * @param row The row number (1-based)
     * @param column The column number (1-based)
     */
    void clearUserCell(int row, int column) {
        row = row - 1;
        column = column - 1;
        if (userFilled[row][column]) {
            board[row][column] = 0;
            userFilled[row][column] = false;
            hintCells[row][column] = false;
        }
    }
    
    /**
     * Clears all user-filled cells, restoring the original puzzle state.
     */
    void clearAllUserCells() {
        for (int row = 0; row < SIZE; row++) {
            for (int column = 0; column < SIZE; column++) {
                if (userFilled[row][column]) {
                    board[row][column] = 0;
                    userFilled[row][column] = false;
                    hintCells[row][column] = false;
                }
            }
        }
    }
}