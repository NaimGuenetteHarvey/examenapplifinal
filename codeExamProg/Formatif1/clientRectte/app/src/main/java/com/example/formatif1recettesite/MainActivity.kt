package com.example.formatif1recettesite

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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.formatif1recettesite.api.RetrofitInstance
import com.example.formatif1recettesite.ui.theme.Formatif1RecetteSIteTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Formatif1RecetteSIteTheme {
                App();
            }
        }
    }
}


private fun GitHubApi.bonjour(nom: String) {}

@Composable
fun App() {
    var nom by remember { mutableStateOf("") }
    var resultats by remember { mutableStateOf(listOf<String>()) }
    var messageErreur by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        OutlinedTextField(
            value = nom,
            onValueChange = { nom = it },
            label = { Text("Nom") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                Log.d("Formatif", "Entrée dans onClick : nom=$nom")

                RetrofitInstance.api.bonjour(nom)
                    .enqueue(object : Callback<String> {

                        override fun onResponse(
                            call: Call<String>,
                            response: Response<String>
                        ) {
                            Log.d("Formatif", "onResponse : code=${response.code()}")

                            if (response.isSuccessful) {
                                val reponseServeur = response.body() ?: "Pas de données"
                                resultats = resultats + reponseServeur
                                messageErreur = ""
                            } else {
                                messageErreur = "Erreur serveur : ${response.code()}"
                            }
                        }

                        override fun onFailure(
                            call: Call<String>,
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
            items(resultats) { item ->
                Text(
                    text = item,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}