package machine_coding.tic_tac_toe.models;

import java.util.ArrayList;
import java.util.List;

import static machine_coding.tic_tac_toe.utils.Delay.delay;

public class Board {
    private List<List<Cell>> cells;

    // Constructor creates a new board based on the board length provided.
    public Board(int boardSize) {
        this.cells = new ArrayList<>();
        for (int i = 0; i < boardSize; i++) {
            List<Cell> row = new ArrayList<>();
            for (int j = 0; j < boardSize; j++) {
                row.add(new Cell(i, j));
            }
            this.cells.add(row);
        }
    }

    public List<List<Cell>> getBoard() {
        return cells;
    }

    public Cell getCell(int row, int column) {
        return cells.get(row).get(column);
    }

    public void setBoard(List<List<Cell>> cells) {
        this.cells = cells;
    }

    public boolean isCellOccupied(int row, int column) {
        Cell cell = cells.get(row).get(column);
        return cell.isOccupied();
    }

    public void storeMove(int row, int column, Player player) {
        Cell cell = cells.get(row).get(column);
        cell.setPlayer(player);
    }

    public void resetBoard() {
        for (List<Cell> row : cells) {
            for (Cell cell : row) {
                cell.removePlayer();
            }
        }
    }

    public void printBoard() {
        delay(500);
        int boardSize = cells.size();
        System.out.println("\n\033[1;41m TIC TAC TOE \033[0m");
        for (int i = 0; i < boardSize; i++) {
            List<Cell> row = this.cells.get(i);
            System.out.print("  ");
            for (int j = 0; j < boardSize; j++) {
                Cell cell = row.get(j);
                cell.printCell();
            }
            System.out.println();
        }
    }
}