package com.abhilash.hackathon.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.abhilash.hackathon.R;

public class ResearcherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_researcher);

        Spinner countrySpinner = findViewById(R.id.countrySpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.country_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        countrySpinner.setAdapter(adapter);

        CheckBox gdpCheckbox = findViewById(R.id.gdpCheckbox);
        CheckBox fdiInflowsCheckbox = findViewById(R.id.fdiInflowsCheckbox);
        CheckBox fdiOutflowsCheckbox = findViewById(R.id.fdiOutflowsCheckbox);
        CheckBox importExportCheckbox = findViewById(R.id.importExportCheckbox);

        Button showButton = findViewById(R.id.showButton);

        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent graphIntent = new Intent(ResearcherActivity.this, GraphActivity.class);

                // Pass data to GraphActivity
                graphIntent.putExtra("country", countrySpinner.getSelectedItem().toString());
                graphIntent.putExtra("gdp", gdpCheckbox.isChecked());
                graphIntent.putExtra("fdiInflows", fdiInflowsCheckbox.isChecked());
                graphIntent.putExtra("fdiOutflows", fdiOutflowsCheckbox.isChecked());
                graphIntent.putExtra("importExport", importExportCheckbox.isChecked());

                startActivity(graphIntent);
            }
        });
    }
}