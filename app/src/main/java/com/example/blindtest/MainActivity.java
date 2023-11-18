package com.example.blindtest;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private Integer score = 0;
    private MediaPlayer mediaPlayer;
    private EditText nameEditText;
    private Button submitButton;
    private String[] correctAnswer = {"sao","fma","aot","hxh","opm"};
    private TextView scoreTextView;

    private Button replayButton;
    private int currentQuestionIndex = 0;
    private int[] audioResources = {
            R.raw.sao_courage,
            R.raw.fma_again,
            R.raw.aot_op1,
            R.raw.hxh_departure,
            R.raw.opm_op1
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEditText = findViewById(R.id.nameEditText);
        submitButton = findViewById(R.id.submitButton);
        scoreTextView = findViewById(R.id.scoreTextView);
        replayButton = findViewById(R.id.replayButton);

        Log.d("MainActivity", "MainActivity created");

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
            }
        });

        mediaPlayer = MediaPlayer.create(this, audioResources[currentQuestionIndex]);

        playSound();

        mediaPlayer = MediaPlayer.create(this, audioResources[currentQuestionIndex]);
        replayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score = 0;
                currentQuestionIndex = 0;
                scoreTextView.setText("Score: " + score);

                findViewById(R.id.endScreen).setVisibility(View.GONE);

                nameEditText.setVisibility(View.VISIBLE);
                submitButton.setVisibility(View.VISIBLE);

                mediaPlayer = MediaPlayer.create(MainActivity.this, audioResources[currentQuestionIndex]);
                playSound();
            }
        });
    }

    private void playSound() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    private boolean areAllQuestionsAnswered() {
        return currentQuestionIndex >= audioResources.length;
    }
    private void checkAnswer() {
        String userAnswer = nameEditText.getText().toString().trim();
        if (userAnswer.equalsIgnoreCase(correctAnswer[currentQuestionIndex])) {
            score++;
            scoreTextView.setText("Score: " + score);
        }
        playNextQuestion();
        }

    private void playNextQuestion() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }

        currentQuestionIndex++;
        if (areAllQuestionsAnswered()) {
            findViewById(R.id.endScreen).setVisibility(View.VISIBLE);
            nameEditText.setVisibility(View.GONE);
            submitButton.setVisibility(View.GONE);


            TextView scoreEndTextView = findViewById(R.id.scoreEndTextView);
            scoreEndTextView.setText("Final Score: " + score);
        } else {
            mediaPlayer = MediaPlayer.create(this, audioResources[currentQuestionIndex]);
            playSound();
        }
    }

    @Override
    protected void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        super.onDestroy();
    }
}