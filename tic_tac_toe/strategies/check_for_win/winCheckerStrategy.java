package machine_coding.tic_tac_toe.strategies.check_for_win;

import machine_coding.tic_tac_toe.models.Board;
import machine_coding.tic_tac_toe.models.Cell;
import machine_coding.tic_tac_toe.models.Move;

public interface winCheckerStrategy {
    public boolean checkForWin(Board board, Cell currentCell);

    public void handleUndo(Move move);
}