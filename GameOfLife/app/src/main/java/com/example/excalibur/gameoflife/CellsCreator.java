package com.example.excalibur.gameoflife;

/**
 * Created by excalibur on 1/21/2018.
 */

class CellsCreator {

    static Cell[][] createAndSet(int rowCount, int columnCount){
        Cell[][] cells = new Cell[rowCount][columnCount];
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                cells[i][j] = new Cell();
                cells[i][j].setState(false);
            }
        }
        return cells;
    }

    static Cell[][] createEmpty(int rowCount, int columnCount){
        Cell[][] cells = new Cell[rowCount][columnCount];
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                cells[i][j] = new Cell();
            }
        }
        return cells;
    }

    static void copy(Cell[][] source, Cell[][] destination){
        for (int i = 0; i < source.length; i++) {
            for (int j = 0; j < source[0].length; j++) {
                destination[i][j].setState(source[i][j].isAlive());
            }
        }
    }

}
