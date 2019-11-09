package com.devis.salarycounter.core.register

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import com.devis.salarycounter.R
import com.devis.salarycounter.base.BaseFragment
import com.devis.salarycounter.core.login.LoginFragment
import com.devis.salarycounter.core.main.MainActivity
import com.devis.salarycounter.helper.DatabaseHelper
import com.devis.salarycounter.model.UserMdl
import com.devis.salarycounter.utils.replaceFragment
import com.devis.salarycounter.utils.toast
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * Created by devis on 2019-11-09
 */

class RegisterFragment : BaseFragment() {

    companion object {
        fun newInstance(): Fragment = RegisterFragment()
    }

    override fun layoutRes(): Int {
        return R.layout.fragment_register
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()

        btn_sign_up.setOnClickListener {
            registerEmployee()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> activity!!.replaceFragment(LoginFragment.newInstance(), R.id.container, "")
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initToolbar() {
        tv_toolbar_title.text = "Become a Member"
        (activity as MainActivity).setSupportActionBar(toolbar)
        (activity as MainActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        (activity as MainActivity).supportActionBar!!.setDisplayShowTitleEnabled(false)
    }

    private fun registerEmployee() {
        val dbHelper = DatabaseHelper(context!!)
        var group: String? = null
        var gender: String? = null
        if (radio_group_a.isChecked) {
            group = radio_group_a.text.toString()
        } else if (radio_group_b.isChecked) {
            group = radio_group_b.text.toString()
        }
        if (radio_gender_man.isChecked) {
            gender = radio_gender_man.text.toString()
        } else if (radio_gender_woman.isChecked) {
            gender = radio_gender_woman.text.toString()
        }
        val employeeId = et_employee_id.text.toString()
        val name = et_fullName.text.toString()
        val password = et_password.text.toString()
        val confirmPassword = et_confirm_password.text.toString()

        if (group.isNullOrEmpty()
            || employeeId.isEmpty()
            || name.isEmpty()
            || password.isEmpty()
            || confirmPassword.isEmpty()
            || gender.isNullOrEmpty()) {
            context!!.toast("Register failed. All fields are required")
        } else {
            if (password != confirmPassword) {
                context!!.toast("Your password and confirmation password do not match")
            } else {
                if (employeeId.length > 2 && password.length > 6) {
                    val userMdl = UserMdl(group, employeeId.toInt(), name, password, gender)
                    dbHelper.registerEmployee(userMdl)
                    dbHelper.close()
                    context!!.toast("Register Success")
                    activity!!.replaceFragment(LoginFragment.newInstance(), R.id.container, "")
                }
            }
        }
    }

}