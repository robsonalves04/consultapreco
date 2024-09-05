package com.example.guildsmartconsulta.produtos_service

import android.content.Context
import com.example.guildsmartconsulta.mocks.produtosMock
import com.example.guildsmartconsulta.models.GuildCategoriaModel
import com.example.guildsmartconsulta.models.GuildProdutoModel
import com.example.guildsmartconsulta.view_models.Callback
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class GuildProdutoService : IGuildProdutoService {

    //== Serviço que é injetado, para que possa ser usado apos tratado
    override suspend fun obterProdutos(
        context: Context,
        opcao: Callback<List<GuildCategoriaModel>>
    ) {
        //== requisição da API, basicamente nesse lugar ficará a baseURL
        //== getAsync, path: "https://fakestoreapi.com.",
        opcao.onSucesso(produtosMock)
    }

    private val apiService: IGuildProdutoService

    init {
        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl("https://guildtech.podevender.com.br/pdv/api/json/") // Certifique-se de que a URL termina com "/"
            .client(client)
            .build()

        apiService = retrofit.create(IGuildProdutoService::class.java)
    }

    // Método que faz o POST
    override suspend fun tokenProdutos(api_password: String): GuildProdutoModel {
        return apiService.tokenProdutos(api_password)

    }

//    override suspend fun tokenProdutos(api_password: String): GuildTokenModels {
//        return iguildProdutoService.tokenProdutos(apiPassword)
//    }
}

//    private val apiService: IGuildProdutoService
//
//    init {
//        val client = OkHttpClient.Builder()
//            .addInterceptor(HttpLoggingInterceptor().apply {
//                level = HttpLoggingInterceptor.Level.BODY
//            })
//            .build()
//        //== Base URL da requisição
//        val retrofit = Retrofit.Builder()
//            .addConverterFactory(MoshiConverterFactory.create())
//            .baseUrl("https://guildtech.podevender.com.br/pdv/api/json/listar-loja-virtual")
//            .client(client)
//            .build()
//
//        apiService = retrofit.create(IGuildProdutoService::class.java)
//    }
//    //== Retorno da requisição
//    override suspend fun findAndress(cep: String): GuildProdutoModel {
//        return apiService.findAndress(cep)
//    }
//}