package com.example.safeher;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class DBHelper {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private Context context;

    public DBHelper(Context context) {
        this.context = context;
    }

    // Add user to Firestore
    public void insertData(String username, String password) {
        DocumentReference userRef = db.collection("users").document(username);
        userRef.set(new User(username, password))
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(context, "User added to Firestore!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(context, "Failed to add user!", Toast.LENGTH_SHORT).show();
                });
    }

    // Check if user exists
    public void checkUserExists(String username, final OnUserExistsListener listener) {
        db.collection("users")
                .whereEqualTo("username", username)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        listener.onUserExists(true);
                    } else {
                        listener.onUserExists(false);
                    }
                });
    }

    // Firebase Authentication - Sign In
    public void signIn(String username, String password, final OnSignInListener listener) {
        mAuth.signInWithEmailAndPassword(username, password)
                .addOnSuccessListener(authResult -> listener.onSignInSuccess())
                .addOnFailureListener(e -> listener.onSignInFailed());
    }

    public interface OnUserExistsListener {
        void onUserExists(boolean exists);
    }

    public interface OnSignInListener {
        void onSignInSuccess();
        void onSignInFailed();
    }
}
