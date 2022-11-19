package com.example.verasteguipc02

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.example.verasteguipc02.Model.Usuario
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.util.*

class RegistroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        val txtDniRegistro: EditText = findViewById(R.id.txtDniRegistro)
        val txtNombre: EditText = findViewById(R.id.txtNombre)
        val txtClaveRegistro: EditText = findViewById(R.id.txtClaveRegistro)
        val txtClaveConfirm: EditText = findViewById(R.id.txtClaveConfirm)
        val btnRegistro: Button = findViewById(R.id.btnRegistro)



        val db = FirebaseFirestore.getInstance()

        btnRegistro.setOnClickListener {
            val nombre = txtNombre.text.toString()
            val dni = txtDniRegistro.text.toString()
            val clave = txtClaveRegistro.text.toString()

            val nuevoUsuario = Usuario(dni, nombre, clave)
            val id: UUID = UUID.randomUUID()


            db.collection("usuario")
                .document(id.toString())
                .set(nuevoUsuario)
                .addOnSuccessListener {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }.addOnFailureListener{
                    Log.d("error", "fallo al registrar")
                }
        }



    }
}