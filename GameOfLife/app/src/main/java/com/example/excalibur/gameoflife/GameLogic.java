package com.example.excalibur.gameoflife;

import android.widget.Button;
import android.widget.GridView;
import android.widget.ToggleButton;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by excalibur on 1/22/2018.
 */

class GameLogic {

    private SavedConfiguration savedConfiguration;
    private Cell[][] currentGeneration;
    private Cell[][] gameFieldData;

    private int rowAmount;
    private int columnAmount;
    private int aliveCellsAmount;
    private int aliveAdjCellsAmount;

    private boolean adjGenerationsHaveDifference;
    private boolean isGameRunning;
    private boolean isBeingDrawn;
    private boolean unlimitedBorders;
//    private boolean drawingMode;
//    private boolean isScreenTouched;
//    private boolean isFirstCell;
//    private boolean firstCellState;

    GameLogic(Cell[][] gameFieldData, int dimension){
        rowAmount = dimension;
        columnAmount = dimension;
        currentGeneration = CellsCreator.createEmpty(rowAmount, columnAmount);
        this.gameFieldData = gameFieldData;
        aliveAdjCellsAmount = 0;
        isGameRunning = false;
        unlimitedBorders = false;
    }

    boolean saveConfiguration(){
        if(isGameFieldEmpty()){
            return false;
        }
        else {
            if(savedConfiguration != null){
                savedConfiguration.saveGame();
            }
            else {
                savedConfiguration = new SavedConfiguration();
                savedConfiguration.saveGame();
            }
            return true;
        }
    }

    boolean loadConfiguration(){
        if(savedConfiguration != null){
            savedConfiguration.loadGame();
            return true;
        }
        else {
            return false;
        }
    }

//    void setDrawingMode(boolean drawingMode) {
//        this.drawingMode = drawingMode;
//    }
//
//    public boolean isDrawingMode() {
//        return drawingMode;
//    }
//
//    public boolean isScreenTouched() {
//        return isScreenTouched;
//    }
//
//    public void setScreenTouched(boolean screenTouched) {
//        isScreenTouched = screenTouched;
//    }
//
//    public boolean isFirstCell() {
//        return isFirstCell;
//    }
//
//    public void setFirstCell(boolean firstCell) {
//        isFirstCell = firstCell;
//    }
//
//    public boolean isFirstCellState() {
//        return firstCellState;
//    }
//
//    public void setFirstCellState(boolean firstCellState) {
//        this.firstCellState = firstCellState;
//    }

    void setUnlimitedBorders(boolean unlimitedBorders) {
        this.unlimitedBorders = unlimitedBorders;
    }

    void stopGame(){
        isGameRunning = false;
    }

    boolean isGameRunning(){
        if(isGameRunning){
            return true;
        }
        else{
            return false;
        }
    }

    void addOneCell(){
        aliveCellsAmount++;
    }

    void killOneCell(){
        aliveCellsAmount--;
    }

    boolean isGameFieldEmpty(){
        if(aliveCellsAmount == 0){
            return true;
        }
        else{
            return false;
        }
    }

    private void changeCellState(int rowIndex, int columnIndex){
        if(gameFieldData[rowIndex][columnIndex].isAlive()) {
            gameFieldData[rowIndex][columnIndex].setState(false);
            aliveCellsAmount--;
        }
        else {
            gameFieldData[rowIndex][columnIndex].setState(true);
            aliveCellsAmount++;
        }
    }

    private void checkCell(int rowIndex, int columnIndex){
        if(!currentGeneration[rowIndex][columnIndex].isAlive()){
            if(aliveAdjCellsAmount == 3){
                changeCellState(rowIndex, columnIndex);
            }
        }
        else {
            if((aliveAdjCellsAmount != 2) && (aliveAdjCellsAmount != 3)){
                changeCellState(rowIndex, columnIndex);
            }
        }
        aliveAdjCellsAmount = 0;
    }

    private void evaluateInternalCells(){
        for (int i = 1; i < rowAmount - 1; i++) {
            for (int j = 1; j < columnAmount - 1; j++) {

                if(currentGeneration[i - 1][j - 1].isAlive()){
                    aliveAdjCellsAmount++;
                }
                if(currentGeneration[i - 1][j].isAlive()){
                    aliveAdjCellsAmount++;
                }
                if(currentGeneration[i - 1][j + 1].isAlive()){
                    aliveAdjCellsAmount++;
                }
                if(currentGeneration[i][j - 1].isAlive()){
                    aliveAdjCellsAmount++;
                }
                if(currentGeneration[i][j + 1].isAlive()){
                    aliveAdjCellsAmount++;
                }
                if(currentGeneration[i + 1][j - 1].isAlive()){
                    aliveAdjCellsAmount++;
                }
                if(currentGeneration[i + 1][j].isAlive()){
                    aliveAdjCellsAmount++;
                }
                if(currentGeneration[i + 1][j + 1].isAlive()){
                    aliveAdjCellsAmount++;
                }
                checkCell(i, j);

            }
        }
    }

    void performOneStep(){
        if(unlimitedBorders){
            makeOneStepUnlimitedBorders();
        }
        else{
            makeOneStep();
        }
    }

    private void makeOneStep(){
        int i, j;
        aliveAdjCellsAmount = 0;
        for (i = 0; i < rowAmount; i++) {
            for (j = 0; j < columnAmount; j++) {
                currentGeneration[i][j].setState(gameFieldData[i][j].isAlive());
            }
        }

        evaluateInternalCells();

        for (i = 1; i < rowAmount - 1; i++) {
            j = 0;
            if(currentGeneration[i - 1][j].isAlive()){
                aliveAdjCellsAmount++;
            }
            if(currentGeneration[i - 1][j + 1].isAlive()){
                aliveAdjCellsAmount++;
            }
            if(currentGeneration[i][j + 1].isAlive()){
                aliveAdjCellsAmount++;
            }
            if(currentGeneration[i + 1][j].isAlive()){
                aliveAdjCellsAmount++;
            }
            if(currentGeneration[i + 1][j + 1].isAlive()){
                aliveAdjCellsAmount++;
            }
            checkCell(i, j);

            j = columnAmount - 1;
            if(currentGeneration[i - 1][j - 1].isAlive()){
                aliveAdjCellsAmount++;
            }
            if(currentGeneration[i - 1][j].isAlive()){
                aliveAdjCellsAmount++;
            }
            if(currentGeneration[i][j - 1].isAlive()){
                aliveAdjCellsAmount++;
            }
            if(currentGeneration[i + 1][j - 1].isAlive()){
                aliveAdjCellsAmount++;
            }
            if(currentGeneration[i + 1][j].isAlive()){
                aliveAdjCellsAmount++;
            }
            checkCell(i, j);
        }

        for (j = 1; j < columnAmount - 1; j++){
            i = 0;
            if(currentGeneration[i][j - 1].isAlive()){
                aliveAdjCellsAmount++;
            }
            if(currentGeneration[i][j + 1].isAlive()){
                aliveAdjCellsAmount++;
            }
            if(currentGeneration[i + 1][j - 1].isAlive()){
                aliveAdjCellsAmount++;
            }
            if(currentGeneration[i + 1][j].isAlive()){
                aliveAdjCellsAmount++;
            }
            if(currentGeneration[i + 1][j + 1].isAlive()){
                aliveAdjCellsAmount++;
            }
            checkCell(i, j);

            i = rowAmount - 1;
            if(currentGeneration[i][j - 1].isAlive()){
                aliveAdjCellsAmount++;
            }
            if(currentGeneration[i][j + 1].isAlive()){
                aliveAdjCellsAmount++;
            }
            if(currentGeneration[i - 1][j - 1].isAlive()){
                aliveAdjCellsAmount++;
            }
            if(currentGeneration[i - 1][j].isAlive()){
                aliveAdjCellsAmount++;
            }
            if(currentGeneration[i - 1][j + 1].isAlive()){
                aliveAdjCellsAmount++;
            }
            checkCell(i, j);
        }

        i = 0;
        j = 0;
        if(currentGeneration[i][j + 1].isAlive()){
            aliveAdjCellsAmount++;
        }
        if(currentGeneration[i + 1][j + 1].isAlive()){
            aliveAdjCellsAmount++;
        }
        if(currentGeneration[i + 1][j].isAlive()){
            aliveAdjCellsAmount++;
        }
        checkCell(i, j);

        j = columnAmount - 1;
        if(currentGeneration[i][j - 1].isAlive()){
            aliveAdjCellsAmount++;
        }
        if(currentGeneration[i + 1][j - 1].isAlive()){
            aliveAdjCellsAmount++;
        }
        if(currentGeneration[i + 1][j].isAlive()){
            aliveAdjCellsAmount++;
        }
        checkCell(i, j);

        i = rowAmount - 1;
        if(currentGeneration[i][j - 1].isAlive()){
            aliveAdjCellsAmount++;
        }
        if(currentGeneration[i - 1][j - 1].isAlive()){
            aliveAdjCellsAmount++;
        }
        if(currentGeneration[i - 1][j].isAlive()){
            aliveAdjCellsAmount++;
        }
        checkCell(i, j);

        j = 0;
        if(currentGeneration[i][j + 1].isAlive()){
            aliveAdjCellsAmount++;
        }
        if(currentGeneration[i - 1][j + 1].isAlive()){
            aliveAdjCellsAmount++;
        }
        if(currentGeneration[i - 1][j].isAlive()){
            aliveAdjCellsAmount++;
        }
        checkCell(i, j);
    }

    private void makeOneStepUnlimitedBorders(){
        int i, j;
        aliveAdjCellsAmount = 0;
        for (i = 0; i < rowAmount; i++) {
            for (j = 0; j < columnAmount; j++) {
                currentGeneration[i][j].setState(gameFieldData[i][j].isAlive());
            }
        }

        evaluateInternalCells();

        //borders evaluating--------------------------------------
        for (i = 1; i < rowAmount - 1; i++) {
            j = 0;
            if(currentGeneration[i - 1][columnAmount - 1].isAlive()){
                aliveAdjCellsAmount++;
            }
            if(currentGeneration[i - 1][j].isAlive()){
                aliveAdjCellsAmount++;
            }
            if(currentGeneration[i - 1][j + 1].isAlive()){
                aliveAdjCellsAmount++;
            }
            if(currentGeneration[i][columnAmount - 1].isAlive()){
                aliveAdjCellsAmount++;
            }
            if(currentGeneration[i][j + 1].isAlive()){
                aliveAdjCellsAmount++;
            }
            if(currentGeneration[i + 1][columnAmount - 1].isAlive()){
                aliveAdjCellsAmount++;
            }
            if(currentGeneration[i + 1][j].isAlive()){
                aliveAdjCellsAmount++;
            }
            if(currentGeneration[i + 1][j + 1].isAlive()){
                aliveAdjCellsAmount++;
            }
            checkCell(i, j);

            j = columnAmount - 1;
            if(currentGeneration[i - 1][j - 1].isAlive()){
                aliveAdjCellsAmount++;
            }
            if(currentGeneration[i - 1][j].isAlive()){
                aliveAdjCellsAmount++;
            }
            if(currentGeneration[i - 1][0].isAlive()){
                aliveAdjCellsAmount++;
            }
            if(currentGeneration[i][j - 1].isAlive()){
                aliveAdjCellsAmount++;
            }
            if(currentGeneration[i][0].isAlive()){
                aliveAdjCellsAmount++;
            }
            if(currentGeneration[i + 1][j - 1].isAlive()){
                aliveAdjCellsAmount++;
            }
            if(currentGeneration[i + 1][j].isAlive()){
                aliveAdjCellsAmount++;
            }
            if(currentGeneration[i + 1][0].isAlive()){
                aliveAdjCellsAmount++;
            }
            checkCell(i, j);
        }

        for (j = 1; j < columnAmount - 1; j++){
            i = 0;
            if(currentGeneration [rowAmount - 1][j - 1].isAlive()){
                aliveAdjCellsAmount++;
            }
            if(currentGeneration [rowAmount - 1][j].isAlive()){
                aliveAdjCellsAmount++;
            }
            if(currentGeneration [rowAmount - 1][j + 1].isAlive()){
                aliveAdjCellsAmount++;
            }
            if(currentGeneration[i][j - 1].isAlive()){
                aliveAdjCellsAmount++;
            }
            if(currentGeneration[i][j + 1].isAlive()){
                aliveAdjCellsAmount++;
            }
            if(currentGeneration[i + 1][j - 1].isAlive()){
                aliveAdjCellsAmount++;
            }
            if(currentGeneration[i + 1][j].isAlive()){
                aliveAdjCellsAmount++;
            }
            if(currentGeneration[i + 1][j + 1].isAlive()){
                aliveAdjCellsAmount++;
            }
            checkCell(i, j);

            i = rowAmount - 1;
            if(currentGeneration [0][j - 1].isAlive()){
                aliveAdjCellsAmount++;
            }
            if(currentGeneration [0][j].isAlive()){
                aliveAdjCellsAmount++;
            }
            if(currentGeneration [0][j + 1].isAlive()){
                aliveAdjCellsAmount++;
            }
            if(currentGeneration[i][j - 1].isAlive()){
                aliveAdjCellsAmount++;
            }
            if(currentGeneration[i][j + 1].isAlive()){
                aliveAdjCellsAmount++;
            }
            if(currentGeneration[i - 1][j - 1].isAlive()){
                aliveAdjCellsAmount++;
            }
            if(currentGeneration[i - 1][j].isAlive()){
                aliveAdjCellsAmount++;
            }
            if(currentGeneration[i - 1][j + 1].isAlive()){
                aliveAdjCellsAmount++;
            }
            checkCell(i, j);
        }

        i = 0;
        j = 0;
        if(currentGeneration [rowAmount - 1][columnAmount - 1].isAlive()){
            aliveAdjCellsAmount++;
        }
        if(currentGeneration [rowAmount - 1][j].isAlive()){
            aliveAdjCellsAmount++;
        }
        if(currentGeneration [rowAmount - 1][j + 1].isAlive()){
            aliveAdjCellsAmount++;
        }
        if(currentGeneration[i][columnAmount - 1].isAlive()){
            aliveAdjCellsAmount++;
        }
        if(currentGeneration[i][j + 1].isAlive()){
            aliveAdjCellsAmount++;
        }
        if(currentGeneration[i + 1][columnAmount - 1].isAlive()){
            aliveAdjCellsAmount++;
        }
        if(currentGeneration[i + 1][j].isAlive()){
            aliveAdjCellsAmount++;
        }
        if(currentGeneration[i + 1][j + 1].isAlive()){
            aliveAdjCellsAmount++;
        }
        checkCell(i, j);

        j = columnAmount - 1;
        if(currentGeneration [rowAmount - 1][j - 1].isAlive()){
            aliveAdjCellsAmount++;
        }
        if(currentGeneration [rowAmount - 1][j].isAlive()){
            aliveAdjCellsAmount++;
        }
        if(currentGeneration [rowAmount - 1][0].isAlive()){
            aliveAdjCellsAmount++;
        }
        if(currentGeneration[i][j - 1].isAlive()){
            aliveAdjCellsAmount++;
        }
        if(currentGeneration [i][0].isAlive()){
            aliveAdjCellsAmount++;
        }
        if(currentGeneration[i + 1][j - 1].isAlive()){
            aliveAdjCellsAmount++;
        }
        if(currentGeneration[i + 1][j].isAlive()){
            aliveAdjCellsAmount++;
        }
        if(currentGeneration [i + 1][0].isAlive()){
            aliveAdjCellsAmount++;
        }
        checkCell(i, j);

        i = rowAmount - 1;
        if(currentGeneration[i - 1][j - 1].isAlive()){
            aliveAdjCellsAmount++;
        }
        if(currentGeneration[i - 1][j].isAlive()){
            aliveAdjCellsAmount++;
        }
        if(currentGeneration[i - 1][0].isAlive()){
            aliveAdjCellsAmount++;
        }
        if(currentGeneration[i][j - 1].isAlive()){
            aliveAdjCellsAmount++;
        }
        if(currentGeneration[i][0].isAlive()){
            aliveAdjCellsAmount++;
        }
        if(currentGeneration[0][j - 1].isAlive()){
            aliveAdjCellsAmount++;
        }
        if(currentGeneration[0][j].isAlive()){
            aliveAdjCellsAmount++;
        }
        if(currentGeneration[0][0].isAlive()){
            aliveAdjCellsAmount++;
        }
        checkCell(i, j);

        j = 0;
        if(currentGeneration[i - 1][columnAmount - 1].isAlive()){
            aliveAdjCellsAmount++;
        }
        if(currentGeneration[i - 1][j].isAlive()){
            aliveAdjCellsAmount++;
        }
        if(currentGeneration[i - 1][j + 1].isAlive()){
            aliveAdjCellsAmount++;
        }
        if(currentGeneration[i][columnAmount - 1].isAlive()){
            aliveAdjCellsAmount++;
        }
        if(currentGeneration[i][j + 1].isAlive()){
            aliveAdjCellsAmount++;
        }
        if(currentGeneration[0][columnAmount - 1].isAlive()){
            aliveAdjCellsAmount++;
        }
        if(currentGeneration[0][j].isAlive()){
            aliveAdjCellsAmount++;
        }
        if(currentGeneration[0][j + 1].isAlive()){
            aliveAdjCellsAmount++;
        }
        checkCell(i, j);
    }

    void cleanCells(){
        for (int i = 0; i < rowAmount; i++) {
            for (int j = 0; j < columnAmount; j++) {
                gameFieldData[i][j].setState(false);
            }
        }
        aliveCellsAmount = 0;
    }

    void startGame(final GridView gameField,
                   final CellsAdapter adapter,
                   final ToggleButton gameRunningSwitch,
                   final Button oneStepButton)
    {
        isGameRunning = true;
        oneStepButton.setEnabled(false);
        final Timer gameExecutionTimer = new Timer();
        gameExecutionTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(!isBeingDrawn){
                    //------------------make a calculation part------------
                    if(unlimitedBorders){
                        makeOneStepUnlimitedBorders();
                    }
                    else{
                        makeOneStep();
                    }
                    //-------------------redraw in main thread-------------
                    isBeingDrawn = true;
                    gameField.post(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                            isBeingDrawn = false;
                        }
                    });
                    //-------------------------checking condition------------------
                    adjGenerationsHaveDifference = false;
                    for (int i = 0; i < rowAmount; i++) {
                        for (int j = 0; j < columnAmount; j++) {
                            if (currentGeneration[i][j].isAlive() != gameFieldData[i][j].isAlive()) {
                                adjGenerationsHaveDifference = true;

                            }
                        }
                    }
                    if(!isGameRunning){
                        oneStepButton.post(new Runnable() {
                            @Override
                            public void run() {
                                oneStepButton.setEnabled(true);
                            }
                        });
                        gameExecutionTimer.cancel();
                        gameExecutionTimer.purge();
                        return;
                    }
                    if ((!adjGenerationsHaveDifference) || (aliveCellsAmount == 0)) {
                        gameRunningSwitch.post(new Runnable() {
                            @Override
                            public void run() {
                                gameRunningSwitch.setChecked(false);
                            }
                        });
                        oneStepButton.post(new Runnable() {
                            @Override
                            public void run() {
                                oneStepButton.setEnabled(true);
                            }
                        });
                        isGameRunning = false;
                        gameExecutionTimer.cancel();
                        gameExecutionTimer.purge();
                    }
                }
            }
        }, 0, 100);
    }
//
//    void setTestFigure(){
//        int start = rowAmount / 2 - 3;
//        gameFieldData[start][start].setState(true);
//        gameFieldData[start + 1][start].setState(true);
//        gameFieldData[start + 2][start].setState(true);
//        gameFieldData[start][start + 1].setState(true);
//        gameFieldData[start + 1][start + 1].setState(true);
//        gameFieldData[start + 2][start + 1].setState(true);
//        gameFieldData[start][start + 2].setState(true);
//        gameFieldData[start + 1][start + 2].setState(true);
//        gameFieldData[start + 2][start + 2].setState(true);
//
//        gameFieldData[start + 3][start + 3].setState(true);
//        gameFieldData[start + 4][start + 3].setState(true);
//        gameFieldData[start + 5][start + 3].setState(true);
//        gameFieldData[start + 3][start + 4].setState(true);
//        gameFieldData[start + 4][start + 4].setState(true);
//        gameFieldData[start + 5][start + 4].setState(true);
//        gameFieldData[start + 3][start + 5].setState(true);
//        gameFieldData[start + 4][start + 5].setState(true);
//        gameFieldData[start + 5][start + 5].setState(true);
//        aliveCellsAmount = 18;
//    }

//    void startGame(CellsAdapter adapter, ToggleButton gameRunningSwitch,
//                   Button oneStepButton, Button cleanCellsButton,
//                   Switch unlimitedBordersSwitch){
//        GameExecutor gameExecutor = new GameExecutor(adapter, gameRunningSwitch,
//                oneStepButton, cleanCellsButton, unlimitedBordersSwitch);
//        gameExecutor.execute();
//    }
//
//    private class GameExecutor extends AsyncTask<Void, Void, Void> {
//
//        private CellsAdapter adapter;
//        private ToggleButton gameRunningSwitch;
//        private Button oneStepButton;
//        private Button cleanCellsButton;
//        private Switch unlimitedBordersSwitch;
//
//        GameExecutor(CellsAdapter adapter, ToggleButton gameRunningSwitch,
//                            Button oneStepButton, Button cleanCellsButton,
//                            Switch unlimitedBordersSwitch) {
//            super();
//            this.adapter = adapter;
//            this.gameRunningSwitch = gameRunningSwitch;
//            this.oneStepButton = oneStepButton;
//            this.cleanCellsButton = cleanCellsButton;
//            this.unlimitedBordersSwitch = unlimitedBordersSwitch;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            isGameRunning = true;
//            cleanCellsButton.setEnabled(false);
//            oneStepButton.setEnabled(false);
//            unlimitedBordersSwitch.setEnabled(false);
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            unlimitedBordersSwitch.setEnabled(true);
//            oneStepButton.setEnabled(true);
//            cleanCellsButton.setEnabled(true);
//            if(isGameRunning){
//                isGameRunning = false;
//                gameRunningSwitch.setChecked(false);
//            }
//        }
//
//        @Override
//        protected void onProgressUpdate(Void... values) {
//            adapter.notifyDataSetChanged();
//        }
//
//        @Override
//        protected Void doInBackground(Void... unused) {
//            while (isGameRunning && adjGenerationsHaveDifference && (aliveCellsAmount != 0)) {
//                if (unlimitedBorders) {
//                    makeOneStepUnlimitedBorders();
//                } else {
//                    makeOneStep();
//                }
//                publishProgress();
//                adjGenerationsHaveDifference = false;
//                for (int i = 0; i < rowAmount; i++) {
//                    for (int j = 0; j < columnAmount; j++) {
//                        if (currentGeneration[i][j].isAlive() != gameFieldData[i][j].isAlive()) {
//                            adjGenerationsHaveDifference = true;
//                        }
//                    }
//                }
//            }
//            return null;
//        }
//    }

    private class SavedConfiguration{

        private int savedAliveCellsAmount;
        private Cell[][] savedGameFieldData;

        SavedConfiguration(){
            this.savedGameFieldData = CellsCreator.createEmpty(rowAmount, columnAmount);
        }

        void saveGame(){
            CellsCreator.copy(gameFieldData, this.savedGameFieldData);
            this.savedAliveCellsAmount = aliveCellsAmount;
        }

        boolean loadGame(){
            if(this.savedGameFieldData != null){
                CellsCreator.copy(this.savedGameFieldData, gameFieldData);
                aliveCellsAmount = this.savedAliveCellsAmount;
                return true;
            }
            else {
                return false;
            }
        }
    }
}
