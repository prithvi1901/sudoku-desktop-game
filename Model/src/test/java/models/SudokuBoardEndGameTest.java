package models;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sudoku.model.exceptions.FillingBoardSudokuException;
import sudoku.model.models.SudokuBoard;
import sudoku.model.solver.BacktrackingSudokuSolver;

public class SudokuBoardEndGameTest {

    private SudokuBoard board;

    @BeforeEach
    void setUp() {
        board = new SudokuBoard(new BacktrackingSudokuSolver());
    }

    @Test
    void testEmptyBoardIsNotFinished() {
        assertFalse(board.checkEndGame(),
            "An empty board should not be considered a finished game");
    }

    @Test
    void testFullySolvedBoardIsFinished() throws FillingBoardSudokuException {
        board.solveGame();
        assertTrue(board.checkEndGame(),
            "A fully solved valid board should be recognised as finished");
    }

    @Test
    void testFilledButInvalidBoardIsNotFinished() throws FillingBoardSudokuException {
        board.solveGame();
        int valueAtCol1Row0 = board.getField(1, 0).getValue();
        int differentValue = (valueAtCol1Row0 % 9) + 1;
        board.getField(0, 0).setValue(differentValue);
        board.getField(1, 0).setValue(differentValue); // duplicate in row 0

        assertFalse(board.checkEndGame(),
            "A filled but invalid board must not be treated as a finished game");
    }

    @Test
    void testPartiallyFilledBoardIsNotFinished() throws FillingBoardSudokuException {
        board.solveGame();
        board.getField(4, 4).setValue(0); // clear one cell

        assertFalse(board.checkEndGame(),
            "A board with at least one empty cell should not be finished");
    }
}