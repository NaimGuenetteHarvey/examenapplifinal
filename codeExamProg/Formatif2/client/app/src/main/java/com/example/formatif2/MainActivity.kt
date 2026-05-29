package com.example.formatif2

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ca.cem.formatif1.api.RetrofitInstance
import com.example.formatif2.ui.theme.Formatif2Theme
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Formatif2Theme {
                App();
            }
        }
    }
}

@Composable
fun App() {

    var x by remember { mutableStateOf("") }
    var y by remember { mutableStateOf("") }
    var listeResultats by remember { mutableStateOf(listOf<Int>()) }
    var messageErreur by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        OutlinedTextField(
            value = x,
            onValueChange = { x = it },
            label = { Text("X") }
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = y,
            onValueChange = { y = it },
            label = { Text("Y") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                Log.d("Formatif", "Entrée dans onClick : x=$x, y=$y")

                val xInt = x.toIntOrNull()
                val yInt = y.toIntOrNull()

                if (xInt == null || yInt == null) {
                    messageErreur = "Erreur : entre deux nombres valides"
                    return@Button
                }

                RetrofitInstance.api.nombresPairs(xInt, yInt)
                    .enqueue(object : Callback<List<Int>> {

                        override fun onResponse(
                            call: Call<List<Int>>,
                            response: Response<List<Int>>
                        ) {
                            Log.d("Formatif", "onResponse : code=${response.code()}")

                            if (response.isSuccessful) {
                                listeResultats = response.body() ?: emptyList()
                                messageErreur = ""
                            } else {
                                messageErreur = "Erreur serveur : ${response.code()}"
                            }
                        }

                        override fun onFailure(
                            call: Call<List<Int>>,
                            t: Throwable
                        ) {
                            Log.e("Formatif", "onFailure : ${t.message}", t)
                            messageErreur = "Erreur réseau : ${t.message}"
                        }
                    })

                Log.d("Formatif", "Sortie de onClick")
            }
        ) {
            Text("Envoyer")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(messageErreur)

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(listeResultats) { nombre ->
                Text(
                    text = nombre.toString(),
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}