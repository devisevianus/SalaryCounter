package com.devis.salarycounter.core.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.devis.salarycounter.R
import com.devis.salarycounter.base.BaseActivity
import com.devis.salarycounter.core.login.LoginFragment
import com.devis.salarycounter.utils.replaceFragment

class MainActivity : BaseActivity() {

    companion object {
        fun startThisActivity(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun layoutRes(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            replaceFragment(
                LoginFragment.newInstance(),
                R.id.container,
                "")
        }
    }
}
