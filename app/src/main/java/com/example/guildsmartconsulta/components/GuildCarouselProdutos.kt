package com.example.guildsmartconsulta.components


import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.fontscaling.MathUtils.lerp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.guildsmartconsulta.R
import com.example.guildsmartconsulta.models.GuildCategoriaModel
import com.example.guildsmartconsulta.screens.GuildTelaConsulta
import com.example.guildsmartconsulta.utils.toBRL
import com.example.guildsmartconsulta.view_models.GuildProdutoViewModel
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@SuppressLint("RestrictedApi")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GuildCarouselProdutos(
    _produtoViewModel: GuildProdutoViewModel,
    onEnterPressed: (() -> Unit)? = null,
) {
    val itemMock = _produtoViewModel.produto_api.observeAsState()
    val context = LocalContext.current
    var isInteracting by remember { mutableStateOf(false) }
    val pagerState = rememberPagerState(initialPage = 0)
    var enterPressCount by remember { mutableStateOf(0) }


    LaunchedEffect(Unit) {
        _produtoViewModel.produtoMock(context)
        _produtoViewModel.tokenInicial(context)
        _produtoViewModel.produto_api
        _produtoViewModel.tokenInicial(context)

        launch {
            while (true) {
                delay(8000)
                val nextPage = (pagerState.currentPage + 1)
                pagerState.animateScrollToPage(nextPage)
                itemMock.value?.map { it ->
                    if (nextPage == it.products.size) {
                        pagerState.animateScrollToPage(0)
                    } else {
                        delay(100)
                    }
                }
            }
        }
    }
    Column(modifier = Modifier
        .clickable {
            enterPressCount += 1
            if (enterPressCount == 2) {
                onEnterPressed?.invoke()
                val intent = Intent(context, GuildTelaConsulta::class.java)
                context.startActivity(intent)

                enterPressCount = 0
            }
        }) {
        itemMock.value?.map { it ->
            it.products.let { items ->
                HorizontalPager(
                    count = it.products.size,
                    state = pagerState,
                    contentPadding = PaddingValues(
                        horizontal = 200.dp // Ajuste o valor para controlar a visualização do próximo card
                    ),
                    modifier = Modifier

                        .fillMaxWidth() // Expande para a largura total da tela
                        .fillMaxHeight() // Ajusta para a altura total da tela
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onPress = {
                                    isInteracting = true
                                },
                                onLongPress = {
                                    isInteracting = true

                                }
                            )
                        }
                ) { page ->
                    key(it.products[page].id) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            it.products[page]?.let { item ->
                                Card(
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier
                                        .padding(horizontal = 10.dp) // Diminua o padding lateral para ajustar a visualização
                                        .graphicsLayer {
                                            val scaleFactor = 1.1f
                                            val pageOffset =
                                                calculateCurrentOffsetForPage(page).absoluteValue
                                            // Mantém o mesmo tamanho, mas com um pequeno ajuste de escala para os cards laterais
                                            scaleX =
                                                lerp(0.95f, 1f, 1f - pageOffset.coerceIn(0f, 1f))
                                            scaleY =
                                                lerp(0.95f, 1f, 1f - pageOffset.coerceIn(0f, 1f))

                                            // Ajusta a opacidade
                                            alpha = lerp(0.5f, 1f, 1f - pageOffset.coerceIn(0f, 1f))
                                        }
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .width(700.dp) // Ajuste a largura para a orientação horizontal
                                                .height(600.dp) // Ajuste a altura conforme necessário
                                                .clickable { }
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
                                                    Text(
                                                        text = item.name ?: "", fontSize = 22.sp,
                                                        modifier = Modifier.padding(bottom = 4.dp)
                                                    )
                                                    Spacer(modifier = Modifier.height(8.dp))

                                                    Image(
                                                        painter = rememberImagePainter(
                                                            data = "https://www.shutterstock.com/shutterstock/photos/2472187383/display_1500/stock-photo-healthy-food-background-healthy-vegan-vegetarian-food-in-paper-bag-vegetables-and-fruits-on-white-2472187383.jpg",
                                                            builder = {
                                                                crossfade(false)
                                                                placeholder(R.drawable.sem_produto)
                                                                error(R.drawable.sem_produto)
                                                            }
                                                        ),
                                                        contentDescription = "Imagem carregada da internet",
                                                        contentScale = ContentScale.Crop,
                                                        modifier = Modifier
                                                            .height(300.dp) // Ajuste a altura da imagem
                                                            .width(300.dp) // Ajuste a largura da imagem
                                                            .clip(RoundedCornerShape(12.dp))
                                                    )
                                                    Spacer(modifier = Modifier.height(8.dp))
                                                    Text(
                                                        text = item.descricao ?: "",
                                                        textAlign = TextAlign.Justify,
                                                        fontSize = 22.sp,
                                                        color = Color.White
                                                    )
                                                    Spacer(modifier = Modifier.height(8.dp))
                                                    Text(
                                                        text = " R$ ${
                                                            item.price?.toBRL()
                                                                ?: "Indisponivel"
                                                        }",
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
                            }
                        }
                    }
                }
            }
        }
    }
}

@SuppressLint("RestrictedApi")
@Composable
fun ProductCard(item: GuildCategoriaModel, page: Int) {

    HorizontalPager(count = page) {

        Row {

            Card(
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .graphicsLayer {
                        val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
                        scaleX = lerp(0.95f, 1f, 1f - pageOffset.coerceIn(0f, 1f))
                        scaleY = lerp(0.95f, 1f, 1f - pageOffset.coerceIn(0f, 1f))
                        alpha = lerp(0.5f, 1f, 1f - pageOffset.coerceIn(0f, 1f))
                    }
            ) {
                Column(
                    modifier = Modifier
                        .width(700.dp)
                        .height(600.dp)
                        .clickable { }
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier
                                .width(700.dp) // Ajuste a largura para a orientação horizontal
                                .height(600.dp) // Ajuste a altura conforme necessário
                                .clickable { }
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
                                    Text(
                                        text = item.name ?: "", fontSize = 22.sp,
                                        modifier = Modifier.padding(bottom = 4.dp)
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))

                                    Image(
                                        painter = rememberImagePainter(
                                            data = item.path_image,
                                            builder = {
                                                crossfade(false)
                                                placeholder(R.drawable.sem_produto)
                                                error(R.drawable.sem_produto)
                                            }
                                        ),
                                        contentDescription = "Imagem carregada da internet",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .height(300.dp) // Ajuste a altura da imagem
                                            .width(300.dp) // Ajuste a largura da imagem
                                            .clip(RoundedCornerShape(12.dp))
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = item.descricao ?: "",
                                        textAlign = TextAlign.Justify,
                                        fontSize = 22.sp,
                                        color = Color.White
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    item.price?.let { it1 ->
                                        Text(
                                            text = it1.toBRL(),
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
                }
            }
        }
    }
}

