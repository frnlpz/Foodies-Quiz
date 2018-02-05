package com.example.android.foodiesquiz;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int points = 0;
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void finish(View view){
        EditText nameText = findViewById(R.id.name_text);
        String name = nameText.getText().toString();
        calculatePoints();
        generateMessage(name,points);
        showResults();
    }

    public void send(View view){
        EditText nameText = findViewById(R.id.name_text);
        String name = nameText.getText().toString();
        generateMessage(name,points);
        composeEmail(name,message);
    }

    public void composeEmail(String name, String text) {
        String subject = getString(R.string.subject) + " " + name;
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private String generateMessage(String name, int points){
        message = name + getString(R.string.you_got) + " " + points + " " + getString(R.string.out_of_100) + "\n";

        if (points < 45) message += "\n" + getString(R.string.bad);
        else if (points < 75) message += "\n" + getString(R.string.regular);
        else message += "\n" + getString(R.string.good);

        return message;
    }

    private void showResults(){
        TextView header = findViewById(R.id.results_text_view);
        header.setVisibility(View.VISIBLE);
        TextView results = findViewById(R.id.order_summary_text_view);
        results.setText(message);
    }

    private int calculatePoints(){
        points = 0;

        CheckBox answer1 = findViewById(R.id.answer1);
        CheckBox answer21 = findViewById(R.id.answer21);
        CheckBox answer22 = findViewById(R.id.answer22);
        CheckBox answer23 = findViewById(R.id.answer23);
        CheckBox answer3 = findViewById(R.id.answer3);
        CheckBox answer4 = findViewById(R.id.answer4);
        CheckBox answer5 = findViewById(R.id.answer5);

        CheckBox wrong1 = findViewById(R.id.wrong1);
        CheckBox wrong2 = findViewById(R.id.wrong2);
        CheckBox wrong3 = findViewById(R.id.wrong3);
        CheckBox wrong4 = findViewById(R.id.wrong4);
        CheckBox wrong5 = findViewById(R.id.wrong5);
        CheckBox wrong6 = findViewById(R.id.wrong6);
        CheckBox wrong7 = findViewById(R.id.wrong7);
        CheckBox wrong8 = findViewById(R.id.wrong8);

        if (answer1.isChecked())  points += 100;
        if (answer21.isChecked()) points += 34;
        if (answer22.isChecked()) points += 33;
        if (answer23.isChecked()) points += 33;
        if (answer3.isChecked())  points += 100;
        if (answer4.isChecked())  points += 100;
        if (answer5.isChecked())  points += 100;

        points /= 5;

        if (wrong1.isChecked()) points -= 5;
        if (wrong2.isChecked()) points -= 5;
        if (wrong3.isChecked()) points -= 5;
        if (wrong4.isChecked()) points -= 5;
        if (wrong5.isChecked()) points -= 5;
        if (wrong6.isChecked()) points -= 5;
        if (wrong7.isChecked()) points -= 5;
        if (wrong8.isChecked()) points -= 5;

        if (points < 0) points = 0;

        return points;
    }
}
