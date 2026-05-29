package com.example.formatif1

import android.os.Bundle
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
import com.example.formatif1.ui.theme.Formatif1Theme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            App()
        }
    }



}

@Composable
fun App() {

    var nom by remember { mutableStateOf("") }

    var listeResultats by remember {
        mutableStateOf(listOf<String>())
    }

    val scope = rememberCoroutineScope()

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

        Button(onClick = {

            scope.launch {

                try {

                    val response =
                        RetrofitInstance.api.bonjour(nom)
                    //en post

                    if (response.isSuccessful) {

                        val resultat =
                            response.body() ?: ""

                        listeResultats =
                            listeResultats + resultat

                    } else {

                        listeResultats =
                            listeResultats + "Erreur : ${response.code()}"
                    }

                } catch (e: Exception) {

                    listeResultats =
                        listeResultats + "Erreur réseau"
                }
            }

        }) {

            Text("Envoyer")
        }

        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn {

            items(listeResultats) { item ->

                Text(
                    text = item,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

