package com.example.excalibur.gameoflife;

import android.graphics.Point;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    CellsAdapter adapter;
    GameLogic gameLogic;
    ToggleButton gameRunningSwitch;
    GridView gameField;
    Button oneStepButton;
    Cell currentCell;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int numColumns = setLayoutMeasured();
        
        gameField = findViewById(R.id.game_field);
        Cell[][] gameFieldData = CellsCreator.createAndSet(numColumns, numColumns);
        adapter = new CellsAdapter(this, R.layout.table_item, gameFieldData, numColumns);
        gameField.setAdapter(adapter);
        gameField.setNumColumns(numColumns);
        gameLogic = new GameLogic(gameFieldData, numColumns);

        gameField.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!gameLogic.isGameRunning()){
                    currentCell = adapter.getItem(position);
                    if(currentCell.isAlive()){
                        currentCell.setState(false);
                        gameLogic.killOneCell();
                    }
                    else {
                        currentCell.setState(true);
                        gameLogic.addOneCell();
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });

        gameRunningSwitch = findViewById(R.id.game_running_switch);
        oneStepButton = findViewById(R.id.one_step_button);
//        Button test = findViewById(R.id.test);
//
//        test.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                gameLogic.setTestFigure();
//                adapter.notifyDataSetChanged();
//            }
//        });

        gameRunningSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    if(gameLogic.isGameFieldEmpty()){
                        gameRunningSwitch.setChecked(false);
                        Toast.makeText(getApplicationContext(),
                                "Cells are empty!",
                                Toast.LENGTH_SHORT).show();
                    }
                    else {
                        gameLogic.startGame(gameField, adapter, gameRunningSwitch,
                                oneStepButton);
                    }
                }
                else {
                    gameLogic.stopGame();
                }
            }
        });

        oneStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!gameLogic.isGameFieldEmpty()){
                    gameLogic.performOneStep();
                    adapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(getApplicationContext(),
                            "Cells are empty!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        gameLogic.stopGame();
        gameRunningSwitch.setChecked(false);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.isCheckable()){
            switch (item.getItemId()){
                case R.id.borders_mode_switch:{
                    if(!item.isChecked()){
                        gameLogic.setUnlimitedBorders(true);
                        item.setChecked(true);
                    }
                    else {
                        gameLogic.setUnlimitedBorders(false);
                        item.setChecked(false);
                    }
                    break;
                }
//                case R.id.drawing_mode_switch:{
//                    if(!item.isChecked()){
//                        gameLogic.setDrawingMode(true);
//                        item.setChecked(true);
//                    }
//                    else {
//                        gameLogic.setDrawingMode(false);
//                        item.setChecked(false);
//                    }
//                    break;
//                }
                default:break;
            }
        }
        else {
            switch (item.getItemId()){
                case R.id.clean_cells_button:{
                    if(!gameLogic.isGameFieldEmpty()){
                        gameLogic.cleanCells();
                        adapter.notifyDataSetChanged();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),
                                "Already clean!",
                                Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case R.id.save_configuration_button:{
                    if(gameLogic.saveConfiguration()){
                        Toast.makeText(getApplicationContext(),
                                "Saved!",
                                Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),
                                "Nothing to save!",
                                Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case R.id.load_configuration_button:{
                    if(gameLogic.loadConfiguration()){
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getApplicationContext(),
                                "Loaded!",
                                Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),
                                "Nothing to load!",
                                Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                default:break;
            }
        }
        return true;
    }

    int setLayoutMeasured(){
        int cellSize = getResources().getDimensionPixelSize(R.dimen.table_element_size);
        Point displaySize = new Point();
        getWindowManager().getDefaultDisplay().getSize(displaySize);
        int numColumns = displaySize.x / cellSize - 1;
        int layoutPadding = (displaySize.x - numColumns * cellSize) / 2;
        LinearLayout layout = findViewById(R.id.base_layout);
        layout.setPadding(layoutPadding, layoutPadding, layoutPadding, layoutPadding);
        return numColumns;
    }
}
