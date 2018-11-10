package com.example.jamesluo.donationtracker

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

/**
 * Created by jamesluo on 10/28/18.
 */

object AuthModel {
    private var mAuth: FirebaseAuth? = null
        private set

    fun initAuth() {
        mAuth = FirebaseAuth.getInstance()
    }

    fun signIn(from: Context, success2: Class<*>, fail2: Class<*>, email: String, password: String) {
        val tag = "sign in activity"
        Log.d(tag, "input email:$email")
        Log.d(tag, "input password:$password")
        mAuth?.signInWithEmailAndPassword(email, password)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(tag, "signInWithEmail:success")
                        val intent = Intent(from, success2)
                        intent.putExtra("username", task.result.user.email) // email
                        intent.putExtra("pw", task.result.user.uid)
                        from.startActivity(intent)

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(tag, "signInWithEmail:failure", task.exception)

                        val intent = Intent(from, fail2)
                        from.startActivity(intent)
                        Toast.makeText(from, "wrong login info", Toast.LENGTH_LONG).show()
                    }
                }
    }

    fun createNewUser(from: Context, success2: Class<*>, fail2: Class<*>, email: String, name: String, type: String, password: String) {
        val tag = "create new user"
        Log.d(tag, "input email:$email")
        Log.d(tag, "input password:$password")

        mAuth?.createUserWithEmailAndPassword(email, password)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(tag, "createUserWithEmail:success")
                        val profileUpdates = UserProfileChangeRequest.Builder()
                                .setDisplayName("name")
                                .build()
                        task.result.user.updateProfile(profileUpdates)
                        ServerModel.createNewUserInDB(from, success2, fail2, email, name, type, task.result.user.uid)
                        /*
                            Intent intent = new Intent(from,success2);
                            from.startActivity(intent);*/
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(tag, "createUserWithEmail:failure", task.exception)

                        val intent = Intent(from, fail2)
                        from.startActivity(intent)
                        Toast.makeText(from, "fail in creatring account", Toast.LENGTH_LONG).show()
                        Toast.makeText(from, task.exception!!.message, Toast.LENGTH_LONG).show()
                    }

                    // ...
                }
    }

}
