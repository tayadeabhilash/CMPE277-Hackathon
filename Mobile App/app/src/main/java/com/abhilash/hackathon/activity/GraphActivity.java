package com.abhilash.hackathon.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.abhilash.hackathon.R;

public class GraphActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        // Retrieve data from intent
        String country = getIntent().getStringExtra("country");
        boolean isGDPChecked = getIntent().getBooleanExtra("gdp", false);
        boolean isFDIInflowsChecked = getIntent().getBooleanExtra("fdiInflows", false);
        boolean isFDIOutflowsChecked = getIntent().getBooleanExtra("fdiOutflows", false);
        boolean isImportExportChecked = getIntent().getBooleanExtra("importExport", false);

        // Example: Display the received data
        TextView infoTextView = findViewById(R.id.infoTextView);
        infoTextView.setText("Country: " + country +
                "\nGDP: " + isGDPChecked +
                "\nFDI Inflows: " + isFDIInflowsChecked +
                "\nFDI Outflows: " + isFDIOutflowsChecked +
                "\nImport/Export: " + isImportExportChecked);
    }
}