package com.fractal.kdsmobile.config.keys

class GuildMemoKeys private constructor() {
    companion object {
        const val token: String = "UIDREFTOKEN"
        const val userId: String = "USERID"
        const val userName: String ="USERNAME"
        const val pedidos : String = "PEDIDOSCONFIG"
        const val vendaEmAndamento: String = "VNDREFANDAMENTO"
        const val modoTerminalAtivacao: String = "MDATIVATERMINAL-"
        const val historicoDePesquisaKey: String = "HISTORICO"
        const val permissoes: String = "PERMISSOES"
        const val terminalId: String = "TERMIDLOCAL-"
    }
}

data class GuildMemoChunks (
        val identidade: String = "AUTHTICKETDEF",
        val default: String = "DEFMEMOTICKET",
        val venda: String = "DEFMEMOVENDA",
        val carrinho: String = "CARTMEMREF",
        val ativacao: String = "ATIVATERMDEF",
        val historicoDePesquisa: String = "HISTORICODEPESQUISA",
        val config : String = "CONFIGTERMINAL",
        val permissoes: String = "PERMISSOES"
//    const val uIdKey = "UIDREFTOKEN"
)

val GuildMemoChunksData = GuildMemoChunks();