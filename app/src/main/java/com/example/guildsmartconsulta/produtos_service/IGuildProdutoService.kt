package com.example.guildsmartconsulta.produtos_service

import android.content.Context
import com.example.guildsmartconsulta.models.GuildCategoriaModel
import com.example.guildsmartconsulta.models.GuildProdutoModel
import com.example.guildsmartconsulta.view_models.Callback
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface IGuildProdutoService {

    //== Serviço que obtem o produto apos o tratamento da API
    suspend fun obterProdutos(
        context: Context,
        opcao: Callback<List<GuildCategoriaModel>>
    )

//    //== GET do protocolo HTTP
//    @POST("{cep}/json/")
//    suspend fun findAndress(@Path("cep") cep: String): GuildProdutoModel


//        @POST("listar-loja-virtual")
//        suspend fun tokenProdutos(@Body tokenProduto: GuildTokenModels): GuildTokenModels

    @FormUrlEncoded
    @POST("listar-loja-virtual")
    suspend fun tokenProdutos(@Field("api_password") api_password: String): GuildProdutoModel
}

