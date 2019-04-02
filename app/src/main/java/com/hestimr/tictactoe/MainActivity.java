package com.hestimr.tictactoe;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button[][] buttons = new Button[3][3];
    private boolean player1 = true;
    private int round;
    private int point1;
    private int point2;
    private TextView txtPlayer1;
    private TextView txtPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtPlayer1 = findViewById(R.id.txtPoint1);
        txtPlayer2 = findViewById(R.id.txtPoint2);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        Button buttonReset = findViewById(R.id.reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }

        if (player1) {
            ((Button) v).setText("X");
        } else {
            ((Button) v).setText("O");
        }

        round++;

        if (checkForWin()) {
            if (player1) {
                player1Wins();
            } else {
                player2Wins();
            }
        } else if (round == 9) {
            draw();
        } else {
            player1 = !player1;
        }
    }

    private void player1Wins() {
        point1++;
        Toast.makeText(this, "Player 1 wins!", Toast.LENGTH_SHORT).show();
        updatePoints();
        MediaPlayer sound = MediaPlayer.create(MainActivity.this, R.raw.applause);
        sound.start();
        resetBoard();
    }
    private void player2Wins() {
        point2++;
        Toast.makeText(this, "Player 2 wins!", Toast.LENGTH_SHORT).show();
        updatePoints();
        MediaPlayer sound = MediaPlayer.create(MainActivity.this, R.raw.applause);
        sound.start();
        resetBoard();
    }
    private void draw() {
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        MediaPlayer sound = MediaPlayer.create(MainActivity.this, R.raw.draw);
        sound.start();
        resetBoard();
    }

    private void updatePoints() {
        txtPlayer1.setText("Player 1 : " + point1);
        txtPlayer2.setText("Player 2 : " + point2);
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        round = 0;
        MediaPlayer sound = MediaPlayer.create(MainActivity.this, R.raw.draw);
        sound.start();
        player1 = true;
    }

    private boolean checkForWin() {
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }

        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }

        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }

        return false;
    }

    private void resetGame() {
        point1 = 0;
        point2 = 0;
        updatePoints();
        resetBoard();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        round = savedInstanceState.getInt("round");
        point1 = savedInstanceState.getInt("player1Points");
        point2 = savedInstanceState.getInt("player2Points");
        player1 = savedInstanceState.getBoolean("player1");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("round", round);
        outState.putInt("player1Points", point1);
        outState.putInt("player2Points", point2);
        outState.putBoolean("player1", player1);
    }
}
