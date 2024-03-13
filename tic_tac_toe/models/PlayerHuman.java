package machine_coding.tic_tac_toe.models;

import java.util.Scanner;

public class PlayerHuman extends Player {

    private int remainingUndos;
    public PlayerHuman(String name, Symbol symbol) {
        super(name, symbol);
    }

    @Override
    public Position makeMove(Board board) {
        Scanner input = new Scanner(System.in);
        System.out.println("\nIt's " + this.getName() + "'s turn. Enter row and column:");
        int row = input.nextInt();
        int column = input.nextInt();
        int boardSize = board.getBoard().size();
        while ((row < 0 || row >= boardSize) || (column < 0 || column >= boardSize)) {
            System.out.println("Invalid row/column values. Please enter a position within the confines of the board:");
            row = input.nextInt();
            column = input.nextInt();
        }
        return new Position(row, column);
    }

    public int getRemainingUndos() {
        return remainingUndos;
    }

    public void setRemainingUndos(int remainingUndos) {
        this.remainingUndos = remainingUndos;
    }

    public void decreaseRemainingUndos() {
        this.remainingUndos--;
    }
}