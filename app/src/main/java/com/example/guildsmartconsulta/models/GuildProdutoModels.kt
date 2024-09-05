package com.example.guildsmartconsulta.models

data class GuildProdutoModel (
    val products: List <GuildCategoriaModel>,
)

data class GuildCategoriaModel(
    val id: String? = null,
    var code: String? = null,
    var name: String? = null,
    val descricao: String? = null,
    val price: Double? = null,
    val path_image: String? = null,
)
