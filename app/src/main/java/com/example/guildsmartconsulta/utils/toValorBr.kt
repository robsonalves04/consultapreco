package com.example.guildsmartconsulta.utils

import java.text.DecimalFormat

// --== Formata para valores financeiros em reais (BRL)
fun Double.toBRL(): String {
    return DecimalFormat("#,##0.00").format(this)
}