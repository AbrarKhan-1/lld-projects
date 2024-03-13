package machine_coding.tic_tac_toe.controllers;

import machine_coding.tic_tac_toe.models.Game;
import machine_coding.tic_tac_toe.models.GameStatus;
import machine_coding.tic_tac_toe.models.Player;

import java.util.List;

public class GameController {

    public Game createGame(List<Player> players, int undoLimitPerPlayer) throws Exception {
        return Game.getBuilder().setPlayersAndBoard(players).setUndoLimitPerPlayer(undoLimitPerPlayer).build();
    }

    public GameStatus getGameStatus(Game game) {
        return game.getGameStatus();
    }

    public Player getCurrentPlayer(Game game) {
        return game.getCurrentPlayer();
    }

    public void makeMove(Game game) {
        game.makeMove();
    }

    public void askUndo(Game game) {
        game.askUndo();
    }

    public void recapGame(Game game) {
        game.recapGame();
    }

    public void printBoard(Game game) {
        game.printBoard();
    }
}