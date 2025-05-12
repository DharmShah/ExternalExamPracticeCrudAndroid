package com.example.externalexampractice;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.externalexampractice.db.DatabaseHelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {

    TableLayout tableLayout;
    DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tableLayout = findViewById(R.id.tableLayout);
        helper = new DatabaseHelper(this);

        loadUserTable();
    }

    private void loadUserTable() {
        tableLayout.removeAllViews(); // Clear previous rows

        Cursor cursor = helper.getAllUsers();

        // Add header row
        TableRow headerRow = new TableRow(this);
        String[] headers = {"Id", "Name", "Email", "Address", "Gender", "Phone", "Password", "Action"};
        for (String header : headers) {
            TextView tv = new TextView(this);
            tv.setText(header);
            tv.setPadding(16, 16, 16, 16);
            headerRow.addView(tv);
        }
        tableLayout.addView(headerRow);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int userId = cursor.getInt(0); // Assuming first column is ID

                TableRow dataRow = new TableRow(this);
                for (int i = 0; i < 7; i++) { // Assuming 7 columns (excluding image)
                    TextView tv = new TextView(this);
                    tv.setText(cursor.getString(i));
                    tv.setPadding(16, 16, 16, 16);
                    dataRow.addView(tv);
                }

                // Add Delete button
                Button deleteButton = new Button(this);
                deleteButton.setText("Delete");
                deleteButton.setOnClickListener(v -> {
                    confirmAndDelete(userId);
                });

                dataRow.addView(deleteButton);
                tableLayout.addView(dataRow);
            } while (cursor.moveToNext());

            cursor.close();
        }
    }

    private void confirmAndDelete(int userId) {
        new AlertDialog.Builder(this)
                .setTitle("Delete User")
                .setMessage("Are you sure you want to delete user ID: " + userId + "?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    int rowsDeleted = helper.deleteUser(userId);
                    if (rowsDeleted > 0) {
                        Toast.makeText(this, "User deleted!", Toast.LENGTH_SHORT).show();
                        loadUserTable(); // Refresh table
                    } else {
                        Toast.makeText(this, "Delete failed!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}
