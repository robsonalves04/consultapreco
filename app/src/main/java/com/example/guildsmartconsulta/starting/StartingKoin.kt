package com.example.guildsmartconsulta.starting

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.guildsmartconsulta.produtos_service.GuildProdutoService
import com.example.guildsmartconsulta.produtos_service.IGuildProdutoService
import com.example.guildsmartconsulta.view_models.GuildProdutoViewModel
import com.fractal.kdsmobile.services.memo.GuildMemo
import com.fractal.kdsmobile.services.memo.IGuildMemo
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module


class Starting : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        //==Inicialização pelo Koin
        startKoin {
            androidLogger()
            androidContext(this@Starting)
            modules(module {
                //== Injeção de dependencia do Service
                single<IGuildProdutoService> { GuildProdutoService() }
                // --== Injetando Serviços de entidade
                single<IGuildMemo> { GuildMemo() }

                //==Injeção do ViewModel
                viewModel { GuildProdutoViewModel(get(),) }
            })
        }
        //== Rodapé padrão do celular
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
    }
    //== Variavel que faz com que a instacia inicie depois
    companion object {
        lateinit var instance: Starting
            private set
    }
}