package com.example.jamesluo.donationtracker;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

/**
 * Created by jamesluo on 10/28/18.
 */

public class AuthModel {
    private static FirebaseAuth mAuth;
    public static void initAuth() {
        mAuth = FirebaseAuth.getInstance();
    }
    public static FirebaseAuth getAuth(){
        return mAuth;
    }

    public static void signIn(final Context from, final Class success2, final Class fail2,String email, String password) {
        final String TAG = "sign in activity";
        Log.d(TAG, "input email:"+email);
        Log.d(TAG, "input password:"+password);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(from,success2);
                            intent.putExtra("username", task.getResult().getUser().getEmail()); // email
                            intent.putExtra("pw", (String) task.getResult().getUser().getUid());
                            from.startActivity(intent);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());

                            Intent intent = new Intent(from,fail2);
                            from.startActivity(intent);
                            Toast.makeText(from, "wrong login info", Toast.LENGTH_LONG).show();
                        }


                    }
                });
    }

    public static void createNewUser(final Context from, final Class success2, final Class fail2, final String email,  final String name, final String type, String password) {
        final String TAG = "create new user";
        Log.d(TAG, "input email:"+email);
        Log.d(TAG, "input password:"+password);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName("name")
                                    .build();
                            task.getResult().getUser().updateProfile(profileUpdates);
                            ServerModel.createNewUserInDB(from, success2, fail2,email, name, type, task.getResult().getUser().getUid());
                            /*
                            Intent intent = new Intent(from,success2);
                            from.startActivity(intent);*/
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());

                            Intent intent = new Intent(from,fail2);
                            from.startActivity(intent);
                            Toast.makeText(from, "fail in creatring account", Toast.LENGTH_LONG).show();
                            Toast.makeText(from, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }

                        // ...
                    }
                });
    }

}
