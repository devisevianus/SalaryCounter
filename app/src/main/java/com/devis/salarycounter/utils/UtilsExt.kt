package com.devis.salarycounter.utils

import android.content.Context
import android.widget.Toast

/**
 * Created by devis on 2019-11-09
 */
 
fun Context.toast(message: String?) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}