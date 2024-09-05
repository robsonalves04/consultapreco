package com.example.guildsmartconsulta.view_models

import android.app.Activity
import android.content.Context
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.guildsmartconsulta.mocks.produtosMock
import com.example.guildsmartconsulta.models.GuildCategoriaModel
import com.example.guildsmartconsulta.models.GuildProdutoModel
import com.example.guildsmartconsulta.produtos_service.IGuildProdutoService
import com.example.guildsmartconsulta.utils.toastSnackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException


class GuildProdutoViewModel(
    //== Variavel privada que possui o serviço de requisição da API
    private val _produtoService: IGuildProdutoService,
) : ViewModel() {
    //== Variavel que contem os dados e podem ser alterados, caso requisitado
    val itemMock = MutableLiveData<List<GuildCategoriaModel>>()
    val produto_api = MutableLiveData<List<GuildProdutoModel>>()

    //== Variaveis de inclusão e manipulação de novos produtos
    val _itemNovo = MutableLiveData<List<GuildCategoriaModel>>()
    val incluirProduto = mutableStateOf("")
    val incluirDescrição = mutableStateOf("")
    val incluirValor = mutableStateOf("")

    init {
        _itemNovo.value = produtosMock.toMutableList()
    }

    //==  Essa função adiciona e trata possivel erros de inclusao de produto
    fun adicionarProduto(context: Context, produtoModel: List<GuildCategoriaModel>) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val atualLista = _itemNovo.value?.toMutableList() ?: mutableListOf()
                atualLista.add(GuildCategoriaModel())
                _itemNovo.value = atualLista
                toastSnackbar(context, "Esse produto nao pode ser inserido.")
            } catch (e: IOException) {
                toastSnackbar(context, "Erro de conexão. Verifique sua internet.")
            }
        }
    }

    //== Função que contem o retorno da lista de produtos
    fun sucessMock(model: List<GuildCategoriaModel>) {
        itemMock.value = model
    }

//    //== Função que contem o retorno da lista de produtos
//    fun sucessToken(model: GuildTokenModels) {
//        token_pedido.value = model
//    }

    //==Função que apresenta sucesso ou falha do retorno da requisição
    fun produtoMock(context: Context) {
        viewModelScope.launch(Dispatchers.Main) {
            val callback = Callback<List<GuildCategoriaModel>>(
                onSucesso = { model ->
                    sucessMock(model)
                },
            )
            _produtoService.obterProdutos(context, callback)
        }
    }

    //==Função que apresenta sucesso ou falha do retorno da requisição
    fun tokenInicial(context: Context) {

        val api_senha: String = "102030"
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = _produtoService.tokenProdutos(api_senha)
                withContext(Dispatchers.Main) {
                    produto_api.value = listOf(result)
//                    toastSnackbar(context = context, "Deu Certo")
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    toastSnackbar(context = context, "erro de PASSWORD")
                    // Handle the exception, e.g., by showing an error message
                    // You can update a LiveData variable to inform the UI of the error
                    // error_message.value = e.message
                }
            }
        }
    }

    fun fecharTeclado(context: Context) {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val view = (context as? Activity)?.currentFocus ?: View(context)
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun limparPesquisa(
        context: Context,
        keyEvent: KeyEvent,
        searchQuery: MutableState<String>,
    ): Boolean {
        return if (keyEvent.keyCode == KeyEvent.KEYCODE_ENTER) {
            searchQuery.value = "" // Limpa o valor da pesquisa
            // Oculta o teclado
            true // Indica que o evento foi tratado
        } else {
            false // Indica que o evento não foi tratado
        }
    }
}

//== Classe do callback com o tratamento da falha ou sucesso, que irá apresentar na tela
data class Callback<D>(
    val onSucesso: (res: D) -> Unit,
)