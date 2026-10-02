package com.example.listadecompras


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

data class Compra(
    val produto: String,
    val valor: Double,
    val quantidade: Int
) {
    val subtotal: Double
        get() = valor * quantidade
}

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                CadastroCompras()
            }
        }
    }
}

@Composable
fun CadastroCompras() {

    var produto by remember {
        mutableStateOf("")
    }

    var valor by remember {
        mutableStateOf("")
    }

    var quantidade by remember {
        mutableStateOf("")
    }

    val compras = remember {
        mutableStateListOf<Compra>()
    }

    val total = compras.sumOf {
        it.subtotal
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Cadastro de Compras",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = produto,
            onValueChange = {
                produto = it
            },
            label = {
                Text("Produto")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = valor,
            onValueChange = {
                valor = it
            },
            label = {
                Text("Valor")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = quantidade,
            onValueChange = {
                quantidade = it
            },
            label = {
                Text("Quantidade")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(15.dp))

        Button(
            onClick = {

                val valorDouble = valor
                    .replace(",", ".")
                    .toDoubleOrNull()

                val quantidadeInt = quantidade
                    .toIntOrNull()

                if (
                    produto.isNotBlank() &&
                    valorDouble != null &&
                    quantidadeInt != null &&
                    quantidadeInt > 0
                ) {

                    compras.add(
                        Compra(
                            produto = produto,
                            valor = valorDouble,
                            quantidade = quantidadeInt
                        )
                    )

                    // Limpa os campos
                    produto = ""
                    valor = ""
                    quantidade = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cadastrar Compra")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Compras cadastradas",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            itemsIndexed(compras) { index, compra ->

                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text(
                            text = compra.produto,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Text(
                            text = "Valor: R$ %.2f".format(compra.valor)
                        )

                        Text(
                            text = "Quantidade: ${compra.quantidade}"
                        )

                        Text(
                            text = "Subtotal: R$ %.2f"
                                .format(compra.subtotal)
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {

                            TextButton(
                                onClick = {
                                    compras.removeAt(index)
                                }
                            ) {
                                Text("Excluir")
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Total: R$ %.2f".format(total),
            style = MaterialTheme.typography.headlineSmall
        )
    }
}
