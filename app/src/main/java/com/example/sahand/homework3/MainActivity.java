package com.example.sahand.homework3;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.Period;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener{
    private EditText dateField;
    private EditText nameField;
    private EditText emailField;
    private EditText usernameField;
    private EditText descriptionField;
    private EditText occupationField;
    private TextView validMessage;
    private int age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dateField = findViewById(R.id.date_of_birth_field);
        nameField = findViewById(R.id.name_field);
        emailField = findViewById(R.id.email_field);
        usernameField = findViewById(R.id.username_field);
        occupationField = findViewById(R.id.occupation_field);
        descriptionField = findViewById(R.id.description_field);
        validMessage = findViewById(R.id.validation_text);

        if(savedInstanceState != null) {
            validMessage.setText(savedInstanceState.getString("vMsg"));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putString("vMsg", validMessage.getText().toString());

        super.onSaveInstanceState(savedInstanceState);
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    @Override
    public void onRestart() {
        super.onRestart();
        dateField.setText("");
        nameField.setText("");
        emailField.setText("");
        usernameField.setText("");
        descriptionField.setText("");
        occupationField.setText("");
    }

    public void submitForm(View v) {

        if(nameField.getText().toString().length() == 0 | usernameField.getText().toString().length() == 0
                | emailField.getText().toString().length() == 0 | dateField.getText().toString().length() == 0
                | descriptionField.getText().toString().length() == 0 | occupationField.getText().toString().length() == 0 ) {
            validMessage.setText(R.string.emptyFields);
        }
        else if(!isEighteen(dateField.getText().toString())){
            validMessage.setText(R.string.underEighteen);
        }
        else {
            String nameAndAge = nameField.getText().toString() + ", " + age;

            Intent intent = new Intent(this, SecondaryActivity.class);
            intent.putExtra("NAMEANDAGE", nameAndAge);
            intent.putExtra("OCCUPATION", occupationField.getText().toString());
            intent.putExtra("DESCRIPTION", descriptionField.getText().toString());
            startActivity(intent);
        }
    }

    public boolean isEighteen(String string) {
        String DOB[] = string.split("/");
        if(DOB.length == 3) {
            int month = Integer.parseInt(DOB[0]);
            int day = Integer.parseInt(DOB[1]);
            int year = Integer.parseInt(DOB[2]);

            LocalDate today = LocalDate.now();

            if(day < 1 | day > 31 | month < 1 | month > 12 | year < 1880 | year > today.getYear())
                return false;

            LocalDate birthday = LocalDate.of(year, month, day);
            Period p = Period.between(birthday, today);
            age = p.getYears();

            return (age >= 18);
        }
        else {
            return false;
        }
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        dateField.setText(String.format(Locale.ENGLISH, "%1$d/%2$d/%3$d", month+1,day,year));
    }

    public static class DatePickerFragment extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), (MainActivity)getActivity(), year, month, day);
        }
    }
}