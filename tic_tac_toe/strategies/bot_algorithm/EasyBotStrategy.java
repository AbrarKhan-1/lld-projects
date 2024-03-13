package machine_coding.tic_tac_toe.strategies.bot_algorithm;

import machine_coding.tic_tac_toe.exceptions.InvalidGameStateException;
import machine_coding.tic_tac_toe.models.*;

import java.util.List;

public class EasyBotStrategy implements BotStrategy {

    @Override
    public Position makeMove(Board board) {
        for (List<Cell> cells : board.getBoard()) {
            for (Cell cell : cells) {
                if (cell.getCellStatus() == CellStatus.EMPTY) {
                    return new Position(cell.getRow(), cell.getColumn());
                }
            }
        }

        throw new InvalidGameStateException("No valid places for the bot to make a move.");
    }
}
