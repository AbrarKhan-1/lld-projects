package machine_coding.tic_tac_toe.strategies.bot_algorithm;

import machine_coding.tic_tac_toe.models.Board;
import machine_coding.tic_tac_toe.models.Player;
import machine_coding.tic_tac_toe.models.PlayerBot;
import machine_coding.tic_tac_toe.models.Position;

public interface BotStrategy {
    public Position makeMove(Board board);
}
