package com.fractal.kdsmobile.services.memo

import android.content.Context
import com.fractal.kdsmobile.config.keys.GuildMemoChunks

interface IGuildMemo {
    fun save(context : Context, key: String, value : String, chunk : String = GuildMemoChunks().default)
    fun saveAsJson(context: Context, key: String, value: Any, chunk: String = GuildMemoChunks().default)
    fun saveAsJsonList(context: Context, key: String, list: List<Any>, chunk: String)
    fun edit(context : Context, key: String, chunk : String, newValue : String)
    fun exists(context: Context, chunk: String, refId : String) : Boolean
    fun remove(context: Context, key: String, chunk: String)
    fun find(context : Context, key: String, chunk : String = GuildMemoChunks().default) : String?
    fun <T> findAsJson(context: Context, key: String, clazz: Class<T>, chunk: String): T?
    fun <T> findAsJsonList(context: Context, key: String, clazz: Class<T>, chunk: String): List<T>?
    fun <T> getAll(context: Context, chunk: String, tClass: Class<T>): List<T>
}