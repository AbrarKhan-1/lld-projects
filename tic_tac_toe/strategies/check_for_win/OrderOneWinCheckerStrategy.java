package machine_coding.tic_tac_toe.strategies.check_for_win;

import machine_coding.tic_tac_toe.models.Board;
import machine_coding.tic_tac_toe.models.Cell;
import machine_coding.tic_tac_toe.models.Move;
import machine_coding.tic_tac_toe.models.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderOneWinCheckerStrategy implements winCheckerStrategy {

    private List<HashMap<Character, Integer>> rowMaps;
    private List<HashMap<Character, Integer>> columnMaps;
    private HashMap<Character, Integer> diagonalMap;
    private HashMap<Character, Integer> antiDiagonalMap;

    private int boardSize;
    public OrderOneWinCheckerStrategy(int boardSize) {
        rowMaps = new ArrayList<>();
        columnMaps = new ArrayList<>();
        for (int i = 0; i < boardSize; i++) {
            rowMaps.add(new HashMap<>());
            columnMaps.add(new HashMap<>());
        }

        diagonalMap = new HashMap<>();
        antiDiagonalMap = new HashMap<>();

        this.boardSize = boardSize;
    }

    // The idea is to maintain (a list of) HashMaps that store the count of each symbol in a row/column or the two diagonals.
    // For eg. something like this (list of row maps):
    //
    //          Symbols
    //          | X | O |        This means that row 0 contains
    //        --+---+---|       two X's and one O, row 1 contains
    //         0| 2 | 1 |       no X's and two O's and so on. If
    //  Rows  --+---+---|       the count of symbols is equal to
    //         1| 0 | 2 |       the board size, then we can determine
    //        --+---+---|       that that particular player has won,
    //         2| 1 | 1 |       and likewise for other outcomes.

    @Override
    public boolean checkForWin(Board board, Cell currentCell) {
        int row = currentCell.getRow();
        int column = currentCell.getColumn();
        char symbol = currentCell.getPlayer().getSymbol().getSymbol();

        // Updating row maps
        HashMap<Character, Integer> rowMap = rowMaps.get(row);
        rowMap.put(symbol, rowMap.getOrDefault(symbol, 0) + 1);

        // Updating column maps
        HashMap<Character, Integer> columnMap = columnMaps.get(column);
        columnMap.put(symbol, columnMap.getOrDefault(symbol, 0) + 1);

        // Update diagonal map
        if (isCellOnDiagonal(row, column)) {
            diagonalMap.put(symbol, diagonalMap.getOrDefault(symbol, 0) + 1);
        }

        // Update anti-diagonal map
        if (isCellOnAntiDiagonal(row, column, boardSize)) {
            antiDiagonalMap.put(symbol, antiDiagonalMap.getOrDefault(symbol, 0) + 1);
        }

        // Check if the player has won
        if (rowMaps.get(row).get(symbol) == boardSize ||
            columnMaps.get(column).get(symbol) == boardSize ||
            (isCellOnDiagonal(row, column) && diagonalMap.get(symbol) == boardSize) ||
            (isCellOnAntiDiagonal(row, column, boardSize) && antiDiagonalMap.get(symbol) == boardSize)) {
            return true;
        }
        return false;
    }

    @Override
    public void handleUndo(Move move) {
        Cell cell = move.getCell();
        int row = cell.getRow();
        int column = cell.getColumn();
        Player player = move.getPlayer();
        char symbol = player.getSymbol().getSymbol();

        // Remove occurrence of symbol in a given row/column/diagonal/anti-diagonal
        HashMap<Character, Integer> rowMap = rowMaps.get(row);
        rowMap.put(symbol, rowMap.get(symbol) - 1);

        HashMap<Character, Integer> columnMap = columnMaps.get(column);
        columnMap.put(symbol, columnMap.get(symbol) - 1);

        if (isCellOnDiagonal(row, column)) {
            diagonalMap.put(symbol, diagonalMap.get(symbol) - 1);
        }

        if (isCellOnAntiDiagonal(row, column, boardSize)) {
            antiDiagonalMap.put(symbol, antiDiagonalMap.get(symbol) - 1);
        }

    }

    private boolean isCellOnDiagonal(int row, int column) {
        return row == column;
    }

    private boolean isCellOnAntiDiagonal(int row, int column, int boardSize) {
        return (row + column) == boardSize - 1;
    }
}
