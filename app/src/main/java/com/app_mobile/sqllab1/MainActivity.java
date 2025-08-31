package com.app_mobile.sqllab1;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity {
    EditText etName, etEmail, etId;
    Button btnAdd, btnView, btnUpdate;
    TextView tvResult;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etId = findViewById(R.id.etId);
        btnAdd = findViewById(R.id.btnAdd);
        btnView = findViewById(R.id.btnView);
        btnUpdate = findViewById(R.id.btnUpdate);
        tvResult = findViewById(R.id.tvResult);

        dbHelper = new DatabaseHelper(this);

        // Add User
        btnAdd.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String email = etEmail.getText().toString();
            if (!name.isEmpty() && !email.isEmpty()) {
                dbHelper.addUser(name, email);
                Toast.makeText(this, "User Added", Toast.LENGTH_SHORT).show();
                etName.setText("");
                etEmail.setText("");
            } else {
                Toast.makeText(this, "Please enter name and email", Toast.LENGTH_SHORT).show();
            }
        });

        // View All Users
        btnView.setOnClickListener(v -> {
            Cursor cursor = dbHelper.getAllUsers();
            StringBuilder sb = new StringBuilder();
            if (cursor.moveToFirst()) {
                do {
                    sb.append("ID: ").append(cursor.getInt(0))
                            .append(", Name: ").append(cursor.getString(1))
                            .append(", Email: ").append(cursor.getString(2))
                            .append("\n");
                } while (cursor.moveToNext());
            } else {
                sb.append("No users found.");
            }
            cursor.close();
            tvResult.setText(sb.toString());
        });

        // Update User
        btnUpdate.setOnClickListener(v -> {
            String idStr = etId.getText().toString();
            String name = etName.getText().toString();
            String email = etEmail.getText().toString();
            if (!idStr.isEmpty() && !name.isEmpty() && !email.isEmpty()) {
                int id = Integer.parseInt(idStr);
                int rows = dbHelper.updateUser(id, name, email);
                if (rows > 0) {
                    Toast.makeText(this, "User Updated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Update Failed", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Enter ID, Name, and Email", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
