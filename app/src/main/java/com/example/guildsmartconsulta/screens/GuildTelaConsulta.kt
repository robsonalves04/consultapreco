package com.example.guildsmartconsulta.screens

import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.guildsmartconsulta.components.GuildProdutoEncontradoForm
import com.example.guildsmartconsulta.view_models.GuildProdutoViewModel
import org.koin.android.ext.android.inject


class GuildTelaConsulta : AppCompatActivity() {
    //== Variavel privada que recebe as informações contidas no ViewModel e trafega por secção autorizada
    private val _produtoViewModel: GuildProdutoViewModel by inject()
//    private val _guildMemo: IGuildMemo by inject()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //== Formulario de Consulta de Produtos
            GuildProdutoEncontradoForm(_produtoViewModel = _produtoViewModel)

        }
    }
}