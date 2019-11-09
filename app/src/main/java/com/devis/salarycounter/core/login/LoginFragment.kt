package com.devis.salarycounter.core.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.devis.salarycounter.R
import com.devis.salarycounter.base.BaseFragment
import com.devis.salarycounter.core.home.HomeActivity
import com.devis.salarycounter.core.register.RegisterFragment
import com.devis.salarycounter.helper.DatabaseHelper
import com.devis.salarycounter.utils.replaceFragment
import com.devis.salarycounter.utils.toast
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * Created by devis on 2019-11-09
 */

class LoginFragment : BaseFragment(), View.OnClickListener {

    companion object {
        fun newInstance(): Fragment = LoginFragment()
    }

    override fun layoutRes(): Int {
        return R.layout.fragment_login
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tv_sign_up.setOnClickListener(this)
        btn_login.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.tv_sign_up -> {
                activity!!.replaceFragment(
                    RegisterFragment.newInstance(),
                    R.id.container,
                    ""
                )
            }
            R.id.btn_login -> {
                login()
            }
        }
    }

    private fun login() {
        val dbHelper = DatabaseHelper(context!!)
        val employeeId = et_employee_id.text.toString()
        val password = et_password.text.toString()
        when {
            employeeId.isEmpty() -> context!!.toast("Employee ID must not be empty")
            password.isEmpty() -> context!!.toast("Password must not be empty")
            else -> {
                val userMdl = dbHelper.loginEmployee(employeeId.toInt(), password)
                if (userMdl != null) {
                    context!!.toast("Login Success")
                    HomeActivity.startThisActivity(context!!, userMdl)
                    activity!!.finish()
                } else {
                    context!!.toast("Employee ID or password is incorrect")
                }
            }
        }
    }

}