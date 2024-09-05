package com.example.guildsmartconsulta.screens


import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.example.guildsmartconsulta.components.GuildCarouselProdutos
import com.example.guildsmartconsulta.view_models.GuildProdutoViewModel
import org.koin.android.ext.android.inject


class GuildTelaInicial : AppCompatActivity()  {
    //== Variavel privada que recebe as informações contidas no ViewModel e trafega por secção autorizada
    private val _produtoViewModel: GuildProdutoViewModel by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //== Formulario de Carousel Produtos
//            GuildOnboardForm()
            GuildCarouselProdutos(_produtoViewModel)

        }
    }
}