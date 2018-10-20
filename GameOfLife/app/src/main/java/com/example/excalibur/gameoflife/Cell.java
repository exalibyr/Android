package com.example.excalibur.gameoflife;

/**
 * Created by excalibur on 1/20/2018.
 */

class Cell {

    private boolean isAlive;

    boolean isAlive() {
        return isAlive;
    }

    void setState(boolean isAlive) {
        this.isAlive = isAlive;
    }
}
