package com.example.verasteguipc02

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var db = FirebaseFirestore.getInstance()

        val txtDni: EditText = findViewById(R.id.txtDni)
        val txtClave: EditText = findViewById(R.id.txtClave)

        val btnIngresar: Button = findViewById(R.id.btnIngresar)
        val btnCrear: Button = findViewById(R.id.btnCrear)

        btnIngresar.setOnClickListener {
            val dni = txtDni.text.toString()
            val clave = txtClave.text.toString()

            db.collection("usuario").whereIn("dni", listOf(dni))
                .addSnapshotListener{snapshots, e ->
                    if(e != null){
                        Log.w("Firebase", "Error al Consultar Usuario")
                        Toast.makeText(this,"EL USUARIO Y/O CLAVE NO EXISTE EN EL SISTEMA", Toast.LENGTH_LONG).show()
                        return@addSnapshotListener
                    }

                    for (dc in snapshots!!.documentChanges){
                        Log.i("FirebaseUESAN","Recorriendo Usuario")
                        when(dc.type){
                            DocumentChange.Type.ADDED,
                            DocumentChange.Type.MODIFIED,
                            DocumentChange.Type.REMOVED -> {
                                Log.d("Firebase","Data: "+ dc.document.data)
                                if(dc.document.data["clave"].toString() == clave){
                                    Toast.makeText(this,"ACCESO PERMITIDO", Toast.LENGTH_LONG).show()
                                }
                            }

                            else -> {
                                Log.e("Firebase", "Error in document")
                                Toast.makeText(this,"EL USUARIO Y/O CLAVE NO EXISTE EN EL SISTEMA", Toast.LENGTH_LONG).show()
                            }
                        }
                    }

                }

        }


        btnCrear.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
       }


    }
}