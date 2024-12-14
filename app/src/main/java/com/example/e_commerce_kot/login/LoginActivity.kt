package com.example.e_commerce_kot.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.e_commerce_kot.R
import com.example.e_commerce_kot.databinding.LoginScreenBinding
import com.example.e_commerce_kot.databinding.RegisterScreenBinding
import com.example.e_commerce_kot.main.MainActivity
import com.example.e_commerce_kot.register.RegisterActivity
import com.google.android.gms.dynamic.SupportFragmentWrapper
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var loginButton: Button
    private lateinit var signUp :TextView
    private var isPasswordVisible = false
    private var lastClickTime: Long = 0 // To handle debounce
    private lateinit var binding: LoginScreenBinding
    private lateinit var fireBaseAuth: FirebaseAuth




    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?){
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            Log.e("Crash", "Uncaught exception in thread ${thread.name}", throwable)
        }
        super.onCreate(savedInstanceState)
        binding = LoginScreenBinding.inflate(layoutInflater)

        setContentView(binding.root);

        emailInput = findViewById(R.id.email_input)
        passwordInput = findViewById(R.id.password_input)
        loginButton = findViewById(R.id.login_btn)
        signUp = findViewById(R.id.signUp_text)
        // Set OnTouchListener for password visibility toggle with debounce
        passwordInput.setOnTouchListener { _, event ->
            val drawableEnd = 2 // Index for drawableEnd in LTR layout
            val drawable = passwordInput.compoundDrawables[drawableEnd]

            if (drawable != null && event.action == MotionEvent.ACTION_UP &&
                event.rawX >= (passwordInput.right - drawable.bounds.width())) {

                val currentTime = System.currentTimeMillis()
                if (currentTime - lastClickTime < 500) { // 500ms debounce time
                    return@setOnTouchListener true
                }
                lastClickTime = currentTime

                // Toggle password visibility
                isPasswordVisible = !isPasswordVisible
                if (isPasswordVisible) {
                    // Show password
                    passwordInput.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    passwordInput.setCompoundDrawablesWithIntrinsicBounds(
                        null, null, ContextCompat.getDrawable(this, R.drawable.ic_visibility), null
                    )
                } else {
                    // Hide password
                    passwordInput.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                    passwordInput.setCompoundDrawablesWithIntrinsicBounds(
                        null, null, ContextCompat.getDrawable(this, R.drawable.ic_visibility_off), null
                    )
                }
                passwordInput.setSelection(passwordInput.text.length) // Maintain cursor position
                return@setOnTouchListener true
            }
            false
        }

        fireBaseAuth = FirebaseAuth.getInstance();
        // Set onClickListener for login button
        loginButton.setOnClickListener {
            if (validateInputs()) {
                fireBaseAuth.signInWithEmailAndPassword(
                    emailInput.text.toString(),
                    passwordInput.text.toString()
                ).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Sign In Successful!", Toast.LENGTH_SHORT).show()

//                        overridePendingTransition(/* enterAnim = */ R.navigation.navigate_to, /* exitAnim = */
//                            R.navigation.navigation_from)
                        // Navigate to MainActivity
                        val intent = Intent(

                            this, MainActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(/* enterAnim = */ R.navigation.navigate_to, /* exitAnim = */
                            R.navigation.navigation_from)
                        // Finish LoginActivity to prevent going back
                        finish()
                    } else {
                        Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }


        signUp.setOnClickListener{
            val intent = Intent(this ,RegisterActivity::class.java)
            startActivity(intent)
            overridePendingTransition(/* enterAnim = */ R.navigation.navigate_to, /* exitAnim = */
                R.navigation.navigation_from)
        }
    }
    private fun validateInputs(): Boolean {
        val email = emailInput.text.toString().trim()
        val password = passwordInput.text.toString().trim()

        if (TextUtils.isEmpty(email)) {
            emailInput.error = "Email is required"
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInput.error = "Invalid email format"
            return false
        }

        // Validate password
        if (TextUtils.isEmpty(password)) {
            passwordInput.error = "Password is required"
            return false
        }
        if (password.length < 6) {
            passwordInput.error = "Password must be at least 6 characters"
            return false
        }

        return true
    }

}
