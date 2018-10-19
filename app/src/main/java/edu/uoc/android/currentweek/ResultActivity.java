package edu.uoc.android.currentweek;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

public class ResultActivity extends AppCompatActivity {

    public static final String IS_SOUND_PLAYED = "Is sound played";
    private TextView resultText;
    private Button resultButton;
    private boolean isSoundPlayed = false; // Avoid playing sound more than one time

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        setTitle(R.string.resultScreenTitle);

        init();
        checkWeekNumber();
    }

    private void init() {
        resultText = findViewById(R.id.resut_text);
        resultButton = findViewById(R.id.result_button);

        resultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void checkWeekNumber() {
        MediaPlayer mp;

        if (isRightNumber()) {
            resultText.setText(getResources().getString(R.string.right));
            resultText.setTextColor(getResources().getColor(R.color.colorRight));
            resultButton.setText(getResources().getString(R.string.start_again));
            mp = MediaPlayer.create(this, R.raw.well_done);

        } else {
            resultText.setText(getResources().getString(R.string.failed));
            resultText.setTextColor(getResources().getColor(R.color.colorFail));
            resultButton.setText(getResources().getString(R.string.try_again));

            mp = MediaPlayer.create(this, R.raw.ouch);
        }

        playSound(mp);
    }

    private void playSound(MediaPlayer mp) {
        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                if (!isSoundPlayed) {
                    mp.start();
                    isSoundPlayed = true;
                }
            }
        });
    }

    private boolean isRightNumber() {
        Bundle bundle = getIntent().getExtras();
        int enterWeekNumber = bundle.getInt(MainActivity.NUMBER_KEY);

        if (enterWeekNumber == getCurrentWeekNumber()) {
            return true;
        }

        return false;
    }

    private int getCurrentWeekNumber() {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(calendar.getTime());

        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean(IS_SOUND_PLAYED, isSoundPlayed);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(IS_SOUND_PLAYED)) {
                isSoundPlayed = savedInstanceState.getBoolean(IS_SOUND_PLAYED);
            }
        }


    }
}
