package com.example.externalexampractice;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.externalexampractice.db.DatabaseHelper;
import com.example.externalexampractice.model.User;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {

    EditText editTextName, editTextEmail, editTextAddress, editTextPhone, editTextPassword;
    RadioGroup radioGroupGender;
    RadioButton radioMale, radioFemale;
    ImageView imageViewProfile;
    Button buttonSubmit, buttonUploadImage,listbtn;

    private static final int IMAGE_PICK_CODE = 1000;
    Uri imageUri = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextPassword = findViewById(R.id.editTextTextPassword); // fixed line
        listbtn = findViewById(R.id.listbtn);

        radioGroupGender = findViewById(R.id.radioGroupGender);
        radioMale = findViewById(R.id.radioMale);
        radioFemale = findViewById(R.id.radioFemale);
        imageViewProfile = findViewById(R.id.imageViewProfile);
        buttonUploadImage = findViewById(R.id.buttonUploadImage);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        listbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MainActivity2.class);
                startActivity(intent);
            }
        });

        buttonUploadImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, IMAGE_PICK_CODE);
        });

        buttonSubmit.setOnClickListener(v -> {
            String name = editTextName.getText().toString();
            String email = editTextEmail.getText().toString();
            String address = editTextAddress.getText().toString();
            String phone = editTextPhone.getText().toString();
            String password = editTextPassword.getText().toString();
            String gender = radioMale.isChecked() ? "Male" : (radioFemale.isChecked() ? "Female" : "");
            byte[] image = imageViewToByte(imageViewProfile);

            User user = new User(name, email, gender, address, phone, password, image);
            DatabaseHelper db = new DatabaseHelper(MainActivity.this);
            long result = db.insertUser(user);

            if (result > 0) {
                Toast.makeText(MainActivity.this, "Saved Successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);  // Start MainActivity2
                finish();
            } else {
                Toast.makeText(MainActivity.this, "Failed to Save", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE && data != null) {
            imageUri = data.getData();
            imageViewProfile.setImageURI(imageUri);
        }
    }

    private byte[] imageViewToByte(ImageView imageView) {
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
}
