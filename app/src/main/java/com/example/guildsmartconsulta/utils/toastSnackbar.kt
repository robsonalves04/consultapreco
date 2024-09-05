package com.example.guildsmartconsulta.utils

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.widget.FrameLayout
import com.google.android.material.snackbar.Snackbar

fun toastSnackbar(context: Context, message: String, duration: Int = Snackbar.LENGTH_SHORT) {
    val snackbar = Snackbar.make((context as Activity).findViewById(android.R.id.content), message, duration)
    //== Altera a cor de fundo da Snackbar
    snackbar.view.setBackgroundColor(Color.RED)

    //== Alterar a posição que aparece na tela
    val params = snackbar.view.layoutParams as FrameLayout.LayoutParams
    params.gravity = Gravity.HORIZONTAL_GRAVITY_MASK

    snackbar.show()
}