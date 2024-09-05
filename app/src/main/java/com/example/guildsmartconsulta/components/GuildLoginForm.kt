package com.example.guildsmartconsulta.components

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.guildsmartconsulta.screens.GuildTelaInicial
import com.example.guildsmartconsulta.view_models.GuildProdutoViewModel

@Composable
fun GuildLoginForm(
    _produtoViewModel: GuildProdutoViewModel,
) {
    val context = LocalContext.current
    val tokenProd = _produtoViewModel.produto_api.observeAsState()

    Column(
        Modifier
            .fillMaxSize()
            .background (color= Color(0xFF035E00))
    ) {
        Column(Modifier.fillMaxHeight().fillMaxWidth(),verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

            //== Botão de acesso ao aplicativo
            Button(
                onClick = {
                        _produtoViewModel.tokenInicial( context = context)
                    val intent = Intent(context, GuildTelaInicial::class.java)
                    context.startActivity(intent)
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFF5A822)
                ),
                modifier = androidx.compose.ui.Modifier
                    .padding(16.dp)
                    .fillMaxWidth(0.8f)
                    .height(60.dp)

            ) {
                Log.d("tokenProduto", "tokenProduto ${_produtoViewModel.tokenInicial( context)}")
                Text(text = "Entrar", fontSize = 22.sp)
            }
        }
    }

}