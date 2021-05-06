package com.example.splitebill

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_register.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        signup_btn.setOnClickListener{
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }

        login_btn.setOnClickListener{
            when{
                //if user do not enter email
                TextUtils.isEmpty(editTextTextEmailAddress.text.toString().trim{it <= ' '}) ->{
                    Toast.makeText(
                        this@LoginActivity,
                        "Please enter emails",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                //if user do not enter password
                TextUtils.isEmpty(editTextTextPassword.text.toString().trim{it <= ' '}) ->{
                    Toast.makeText(
                        this@LoginActivity,
                        "Please enter password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else ->{

                    val email: String = editTextTextEmailAddress.text.toString().trim{it<=' '}
                    val password: String = editTextTextPassword.text.toString().trim {it<=' '}

                    //login
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener(this){ task ->
                            if (task.isSuccessful){


                                Toast.makeText(
                                    this@LoginActivity,
                                    "Sign Up Successfully",
                                    Toast.LENGTH_SHORT

                                ).show()

                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                intent.putExtra("user_id", FirebaseAuth.getInstance().currentUser!!.uid)
                                intent.putExtra("email_id", email)
                                startActivity(intent)
                                finish()
                            }else{

                                Toast.makeText(
                                    this@LoginActivity,
                                    task.exception!!.message.toString(),
                                    Toast.LENGTH_SHORT

                                ).show()
                            }
                        }
                }


            }

        }
    }
}