package machine_coding.tic_tac_toe.models;

import machine_coding.tic_tac_toe.exceptions.CountExceededException;
import machine_coding.tic_tac_toe.exceptions.InvalidGameStateException;
import machine_coding.tic_tac_toe.strategies.check_for_win.OrderOneWinCheckerStrategy;
import machine_coding.tic_tac_toe.strategies.check_for_win.winCheckerStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static machine_coding.tic_tac_toe.utils.Delay.delay;

public class Game {
    private Board board;
    private List<Player> players;
    private GameStatus gameStatus;
    private int currentPlayerIndex;
    private List<Move> moves;
    private machine_coding.tic_tac_toe.strategies.check_for_win.winCheckerStrategy winCheckerStrategy;
    private int undoLimitPerPlayer;

    private Game(GameBuilder builder) {
        this.board = builder.board;
        this.players = builder.players;
        this.gameStatus = builder.gameStatus;
        this.currentPlayerIndex = builder.currentPlayerIndex;
        this.moves = builder.moves;
        this.winCheckerStrategy = builder.winCheckerStrategy;
        for (Player player : players) {
            if (player instanceof PlayerHuman) {
                ((PlayerHuman) player).setRemainingUndos(builder.undoLimitPerPlayer);
            }
        }
    }

    public static GameBuilder getBuilder() {
        return new GameBuilder();
    }

    public Board getBoard() {
        return board;
    }

    public Player getCurrentPlayer() {
        return this.players.get(currentPlayerIndex);
    }

    public void makeMove() {
        Player currentPlayer = players.get(currentPlayerIndex);
        Position position = currentPlayer.makeMove(board);
        while (board.isCellOccupied(position.getRow(), position.getColumn())) {
            if (currentPlayer instanceof PlayerHuman) {
                delay(500);
                System.out.println("Cell (" + position.getRow() + ", " + position.getColumn() + ") is already taken. Please make your move on a different cell.");
            }
            position = currentPlayer.makeMove(board);
        }
        board.storeMove(position.getRow(), position.getColumn(), currentPlayer);
        Cell currentCell = board.getCell(position.getRow(), position.getColumn());
        Move currentMove = new Move(currentPlayer, currentCell);
        moves.add(currentMove);

        if (winCheckerStrategy.checkForWin(board, currentCell)) {
            gameStatus = GameStatus.ENDED;
            return;
        } else if (checkForDraw()) {
            gameStatus = GameStatus.DRAWN;
            return;
        }
        currentPlayerIndex++;
        if (currentPlayerIndex == players.size()) {
            currentPlayerIndex = 0;
        }
    }

    public boolean checkForDraw() {
        int boardSize = board.getBoard().size();
        int totalMoves = boardSize * boardSize;
        return moves.size() == totalMoves;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void printBoard() {
        this.board.printBoard();
    }

    public void askUndo() {
        int prevPlayerIndex = currentPlayerIndex - 1;
        if (prevPlayerIndex < 0) {
            prevPlayerIndex = players.size() - 1;
        }
        Player prevPlayer = players.get(prevPlayerIndex);

        if (prevPlayer instanceof PlayerHuman) {
            Scanner input = new Scanner(System.in);
            PlayerHuman humanPlayer = (PlayerHuman) prevPlayer;
            if (humanPlayer.getRemainingUndos() > 0) {
                delay(500);
                System.out.println("\nDo you want to undo your last move? (Y/N)");
                char reply = input.next().charAt(0);
                if (reply == 'y' || reply == 'Y') {
                    Move lastMove = moves.remove(moves.size() - 1);
                    Cell cell = lastMove.getCell();
                    cell.removePlayer();
                    currentPlayerIndex = prevPlayerIndex;
                    winCheckerStrategy.handleUndo(lastMove);
                    System.out.println("\nAn undo has been made successfully!");
                    humanPlayer.decreaseRemainingUndos();
                    if (humanPlayer.getRemainingUndos() == 0) {
                        System.out.println(humanPlayer.getName() + " has exhausted their undo limit.");
                    } else {
                        System.out.println(humanPlayer.getName() + " has " + humanPlayer.getRemainingUndos() + " undos left.");
                    }
                }
            }
        }
    }

    public void recapGame() throws InvalidGameStateException {
        if (gameStatus == GameStatus.IN_PROGRESS) {
            throw new InvalidGameStateException("Cannot recap now. Game is still in progress.");
        }

        board.resetBoard();
        int moveCount = 1;
        for (Move move : moves) {
            Cell cell = move.getCell();
            Player player = move.getPlayer();
            board.storeMove(cell.getRow(), cell.getColumn(), player);
            System.out.println("\nMove " + moveCount + ": " + player.getName() + " made a move on (" + cell.getRow() + ", " + cell.getColumn() + ")");
            board.printBoard();
            moveCount++;
        }
    }

    public static class GameBuilder {
        private Board board;
        private List<Player> players;
        private GameStatus gameStatus;
        private int currentPlayerIndex;
        private List<Move> moves;
        private winCheckerStrategy winCheckerStrategy;
        private int undoLimitPerPlayer;

        public GameBuilder setPlayersAndBoard(List<Player> players) {
            this.players = players;
            int boardSize = players.size() + 1;
            this.board = new Board(boardSize);
            this.winCheckerStrategy = new OrderOneWinCheckerStrategy(boardSize);
            return this;
        }

        public GameBuilder setUndoLimitPerPlayer(int undoLimitPerPlayer) {
            this.undoLimitPerPlayer = undoLimitPerPlayer;
            return this;
        }

        public Game build() throws CountExceededException {
            int playerCount = 0;
            int botCount = 0;
            for (Player p : players) {
                playerCount++;
                if (p instanceof PlayerBot) {
                    botCount++;
                    if (botCount > 1) {
                        throw new CountExceededException("Invalid number of bots. Please retry with either 1 bot or none.");
                    }
                }
            }

            if (playerCount < 2) {
                throw new CountExceededException("Insufficient number of players. Please provide at least 2 players.");
            }

            this.gameStatus = GameStatus.IN_PROGRESS;
            this.currentPlayerIndex = 0;
            this.moves = new ArrayList<>();
            return new Game(this);
        }
    }
}