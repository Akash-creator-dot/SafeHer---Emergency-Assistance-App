package com.example.safeher;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    ImageSlider imageSlider;
    CardView cardView;
    FloatingActionButton btnAddNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        imageSlider = findViewById(R.id.imageslider);
        cardView = findViewById(R.id.card1);
        btnAddNumber = findViewById(R.id.btnAddNumber);

        ArrayList<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.alert, ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.w1, ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.w2, ScaleTypes.CENTER_CROP));

        imageSlider.setImageList(slideModels, ScaleTypes.CENTER_CROP);

        cardView.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, MapsActivity.class);
            startActivity(intent);
        });

        btnAddNumber.setOnClickListener(v -> showAddNumberDialog());
    }

    private void showAddNumberDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Emergency Phone Number");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_PHONE);
        builder.setView(input);

        builder.setPositiveButton("Save", (dialog, which) -> {
            String phoneNumber = input.getText().toString();
            if (!phoneNumber.isEmpty()) {
                savePhoneNumberToFirestore(phoneNumber);
            } else {
                Toast.makeText(this, "Phone number can't be empty", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    private void savePhoneNumberToFirestore(String phoneNumber) {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Map<String, Object> data = new HashMap<>();
        data.put("phoneNumber", phoneNumber);

        FirebaseFirestore.getInstance().collection("users")
                .document(uid)
                .set(data, SetOptions.merge())  // <-- This will create or update the document
                .addOnSuccessListener(unused -> Toast.makeText(this, "Number saved!", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to save number", Toast.LENGTH_SHORT).show());
    }

}
