package com.example.guildsmartconsulta.components

import android.content.Intent
import android.util.Log
import android.view.KeyEvent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.guildsmartconsulta.R
import com.example.guildsmartconsulta.models.GuildCategoriaModel
import com.example.guildsmartconsulta.screens.GuildTelaInicial
import com.example.guildsmartconsulta.view_models.GuildProdutoViewModel
import kotlinx.coroutines.delay


@Composable
fun GuildProdutoEncontradoForm(
    _produtoViewModel: GuildProdutoViewModel
) {
    val produto_guild = _produtoViewModel.produto_api.observeAsState()
    val searchQuery = remember { mutableStateOf("") }
    val context = LocalContext.current
    val keyEvent = KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER)
    val showCard = remember { mutableStateOf(false) }

    // Carrega os produtos na inicialização
    LaunchedEffect(Unit) {
        _produtoViewModel.produtoMock(context)
        _produtoViewModel.tokenInicial(context)
        _produtoViewModel.produto_api
        _produtoViewModel.fecharTeclado(context)
    }

    // Temporizador para iniciar a nova activity
    LaunchedEffect(showCard.value) {
        if (showCard.value) {
            delay(20000)
            _produtoViewModel.fecharTeclado(context)
            val intent = Intent(context, GuildTelaInicial::class.java)
            context.startActivity(intent)
            Log.d("showCard", "showCard ${showCard}")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Barra de pesquisa sempre invisível
        GuildSerchInputTextField(
            searchQuery = searchQuery,
            placeholder = "",
            onValueChange = {
                _produtoViewModel.fecharTeclado(context)
                searchQuery.value = it
            },
            onEnterPressed = {
                _produtoViewModel.fecharTeclado(context)
                _produtoViewModel.limparPesquisa(context, keyEvent, searchQuery)
            },
            onFocusState = { _produtoViewModel.fecharTeclado(context) },
            maxLength = 13
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Filtro de pesquisa
        val filteredProducts = produto_guild.value?.flatMap { it.products }
            ?.find { produto ->
                produto.code.equals(searchQuery.value, ignoreCase = true)
            }

        if (searchQuery.value.isNotEmpty()) {
            if (filteredProducts != null) {
                // Exibe o produto encontrado
                GuildPesquisaCard(filteredProducts)
                Log.d(
                    "ProdutoEncontrado",
                    "Produto Encontrado: ${filteredProducts}"
                )
                showCard.value = true
            } else {
                // Mostra uma mensagem de "Nenhum resultado encontrado" se não houver correspondência
                NenhumResultadoEncontrado(text = "Nenhum resultado encontrado")
                showCard.value = true
            }
        } else {
            // Mostra uma mensagem de "Aproxime o produto do leitor" se o campo de busca estiver vazio
            NenhumResultadoEncontrado(text = "Aproxime o produto do leitor")
            showCard.value = true
        }
    }
}

@Composable
fun GuildPesquisaCard(
    it: GuildCategoriaModel,
//    onClick: ((model: GuildProdutoModel) -> Unit)? = null,
//    boolean: Boolean = false
) {
    Row(
        Modifier,
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .width(700.dp) // Ajuste a largura para a orientação horizontal
                .height(600.dp) // Ajuste a altura conforme necessário

        ) {
            Box() {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    Color(0xFF397C29),
                                    Color(0xFF156919)
                                )
                            )
                        ),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
//                    produtoModel {
                    Text(
                        text = it.name ?: "", fontSize = 22.sp,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Image(
                        painter = painterResource(id = R.drawable.sem_produto),
                        contentDescription = "Imagem carregada da internet",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(300.dp) // Ajuste a altura da imagem
                            .width(300.dp) // Ajuste a largura da imagem
                            .clip(RoundedCornerShape(12.dp))
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = it.descricao ?: "",
                        textAlign = TextAlign.Justify,
                        fontSize = 22.sp,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = it.price?.let { precoString ->
                            precoString.toDouble()?.let { preco ->
                                // Formata o preço com 2 casas decimais e inclui o símbolo de moeda
                                "R$ ${"%.2f".format(preco)}"
                            } ?: "Indisponível" // Valor padrão se a conversão falhar
                        } ?: "Indisponível",
                        textAlign = TextAlign.Start,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }
            }
        }
    }
}


@Composable
fun NenhumResultadoEncontrado(text: String = "") {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = text,
            fontSize = 22.sp,
            color = Color(0xFFA8A8A8)
        )
        Image(
            painter = painterResource(id = R.drawable.no_results),
            contentDescription = "Sem resultado",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(300.dp)
                .width(300.dp)
                .clip(RoundedCornerShape(12.dp))
        )
    }
}