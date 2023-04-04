package com.example.duzezadanieocena

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var logged = false
        var User_Data = arrayOf("", "", "", "") // tablica danych uzytkownika

        val data = intent.getStringArrayExtra("userinfo")
        if (data != null) {
            User_Data = data
            RefreshUserUIElements(User_Data)
            logged = true
            findViewById<LinearLayout>(R.id.Login_Form).visibility = View.GONE
            findViewById<TextView>(R.id.Textview_form_text).visibility = View.VISIBLE
        }

        supportActionBar?.hide() // ukrycie defaultowego topbara

        // Obsługa chowania się manu nawigacji oraz jego animowanie
        findViewById<Button>(R.id.Button_nav_close).setOnClickListener {
            val navigationView = findViewById<NavigationView>(R.id.nav_view)
            val animation = AnimationUtils.loadAnimation(this, R.anim.slide_out_left)
            navigationView.startAnimation(animation)
            navigationView.visibility = View.GONE
        }

        // Obsługa pokazywania się manu nawigacji oraz jego animowanie
        findViewById<ImageView>(R.id.button_hamburger_menu).setOnClickListener {
            val navigationView = findViewById<NavigationView>(R.id.nav_view)
            val animation = AnimationUtils.loadAnimation(this, R.anim.slide_in_left)
            navigationView.startAnimation(animation)
            navigationView.visibility = View.VISIBLE
        }

        // Obsługa przejść pomiędzy activity
        findViewById<Button>(R.id.Button_nav_homepage).setOnClickListener {
            Toast.makeText(this, "Jesteś już na stronie głównej", Toast.LENGTH_SHORT).show()
        }

        findViewById<Button>(R.id.Button_nav_marks).setOnClickListener {
            if (logged){
                startActivity(Intent(this, oceny::class.java).putExtra("userinfo", User_Data))
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_left)
            }else
                Toast.makeText(this, "Zaloguj się!", Toast.LENGTH_SHORT).show()
        }

        findViewById<Button>(R.id.Button_nav_user_info).setOnClickListener {
            if (logged){
                startActivity(Intent(this, informacje::class.java).putExtra("userinfo", User_Data))
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_left)
            }else
                Toast.makeText(this, "Zaloguj się!", Toast.LENGTH_SHORT).show()

        }

        // Obsługa przycisku logowania
        findViewById<Button>(R.id.Button_login).setOnClickListener {
            val nazwauzytkownika = findViewById<EditText>(R.id.Input_nazwauzytkownika).text.toString()
            val imie = findViewById<EditText>(R.id.Input_imie).text.toString()
            val nazwisko = findViewById<EditText>(R.id.Input_nazwisko).text.toString()
            val klasa = findViewById<EditText>(R.id.Input_klasa).text.toString()

            if (nazwauzytkownika != "" && imie != "" && nazwisko != "" && klasa != ""){
                User_Data[0] = nazwauzytkownika
                User_Data[1] = imie
                User_Data[2] = nazwisko
                User_Data[3] = klasa

                logged = true

                findViewById<LinearLayout>(R.id.Login_Form).visibility = View.GONE
                findViewById<TextView>(R.id.Textview_form_text).visibility = View.VISIBLE
                RefreshUserUIElements(User_Data)
            }else{
                Toast.makeText(this, "Wypełnij wszystkie pola!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun RefreshUserUIElements(User_Data: Array<String>):Unit{
        findViewById<TextView>(R.id.TextView_nav_username).text = User_Data[0]
        findViewById<TextView>(R.id.Textview_Toolbar_text).text = "${User_Data[1]} ${User_Data[2]}"
    }
}