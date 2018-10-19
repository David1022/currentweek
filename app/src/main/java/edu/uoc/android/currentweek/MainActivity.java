package edu.uoc.android.currentweek;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public static final String NUMBER_KEY = "Number";
    public static final String EMPTY_STRING = "";
    public static final String INPUT_NUMBER = "Input number";

    private Button checkButton;
    private AutoCompleteTextView enterText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        checkButton = findViewById(R.id.check_button);
        enterText = findViewById(R.id.enter_autocomplete_text);

        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (enterTextIsEmpty()) {
                    enterText.setError(getResources().getString(R.string.prompt_no_number));
                } else {
                    goToResult();
                }
            }
        });
    }

    private boolean enterTextIsEmpty() {
        return enterText.getText().toString().isEmpty();
    }

    private void goToResult() {
        Intent intent = new Intent(this, ResultActivity.class);
        int number;
        try {
            number = Integer.parseInt(enterText.getText().toString());
        } catch (Exception e) {
            number = 0;
        }

        intent.putExtra(NUMBER_KEY, number);
        startActivity(intent);

        cleanEnterText();
    }

    private void cleanEnterText() {
        enterText.setText(EMPTY_STRING);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(INPUT_NUMBER, enterText.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        String savedString = EMPTY_STRING;

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(INPUT_NUMBER)) {
                savedString = savedInstanceState.getString(INPUT_NUMBER);
            }
        }

        enterText.setText(savedString);
    }
}
