package com.example.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val usernameEdit = findViewById<TextInputEditText>(R.id.signupUsernameEditText)
        val passwordEdit = findViewById<TextInputEditText>(R.id.signupPasswordEditText)
        val signupButton = findViewById<MaterialButton>(R.id.signupButton)

        signupButton.setOnClickListener {
            val username = usernameEdit.text.toString()
            val password = passwordEdit.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                // Save to database in background
                CoroutineScope(Dispatchers.IO).launch {
                    val db = AppDatabase.getDatabase(applicationContext)
                    db.userDao().insertUser(User(username = username, password = password))

                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@SignupActivity, "Account Created!", Toast.LENGTH_SHORT).show()
                        finish() // Go back to login screen
                    }
                }
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}