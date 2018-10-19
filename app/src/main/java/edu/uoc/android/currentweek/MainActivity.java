package edu.uoc.android.currentweek;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String NUMBER_KEY = "Number";
    public static final String EMPTY_STRING = "";

    private Button checkButton;
    // TODO: Cambiar por AutoCompleteTextView
    private EditText enterText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        checkButton = findViewById(R.id.check_button);
        enterText = findViewById(R.id.enter_text);

        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (enterTextIsEmpty()) {
                    // TODO: Hacer que salga en el prompt del EditText
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.prompt_no_number), Toast.LENGTH_SHORT)
                            .show();
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
            //Do nothing
            number = 0;
        }

        intent.putExtra(NUMBER_KEY, number);
        startActivity(intent);

        cleanEditText();
    }

    private void cleanEditText() {
        enterText.setText(EMPTY_STRING);
    }


}
