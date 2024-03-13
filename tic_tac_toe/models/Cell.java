package machine_coding.tic_tac_toe.models;

public class Cell {
    private int row;
    private int column;
    CellStatus cellStatus;
    private Player player;

    public Cell (int row, int column) {
        this.row = row;
        this.column = column;
        this.cellStatus = CellStatus.EMPTY;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public CellStatus getCellStatus() {
        return cellStatus;
    }

    public void setCellStatus(CellStatus cellStatus) {
        this.cellStatus = cellStatus;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
        this.cellStatus = CellStatus.FILLED;
    }

    public void removePlayer() {
        this.player = null;
        this.cellStatus = CellStatus.EMPTY;
    }

    public boolean isOccupied() {
        return this.cellStatus == CellStatus.FILLED && this.player != null;
    }
    public void printCell() {
        if (cellStatus == CellStatus.EMPTY) {
            System.out.print(" _ ");
        }
        else {
            System.out.print(" " + this.player.getSymbol().getSymbol() + " ");
        }
    }
}