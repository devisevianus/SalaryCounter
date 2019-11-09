package com.devis.salarycounter.core.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.core.widget.addTextChangedListener
import com.bumptech.glide.Glide
import com.devis.salarycounter.R
import com.devis.salarycounter.base.BaseActivity
import com.devis.salarycounter.core.main.MainActivity
import com.devis.salarycounter.model.UserMdl
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * Created by devis on 2019-11-09
 */

class HomeActivity : BaseActivity() {

    companion object {
        fun startThisActivity(context: Context, userMdl: UserMdl) {
            val intent = Intent(context, HomeActivity::class.java)
            intent.putExtra("data", Gson().toJson(userMdl))
            context.startActivity(intent)
        }
    }

    val salaryRate = 50000
    val groupARate = 100000
    val groupBRate = 50000

    override fun layoutRes(): Int {
        return R.layout.activity_home
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tv_toolbar_title.text = "Home"
        setSupportActionBar(toolbar)

        val extras = intent.getStringExtra("data")
        val userMdl = Gson().fromJson(extras, UserMdl::class.java)

        tv_name.text = userMdl.fullName
        tv_employee_id.text = userMdl.employeeId.toString()
        tv_group.text = userMdl.group
        tv_rate.text = if (userMdl.group == "A") {
            "Rp$groupARate"
        } else {
            "Rp$groupBRate"
        }

        Glide.with(this)
            .load("http://placehold.it/200/F3F3F5/00AB4E&text=${userMdl.fullName}")
            .circleCrop()
            .into(iv_user_avatar)

        et_working_time.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.isNullOrEmpty()) {
                    tv_total_salary.text = ""
                } else {
                    if (p0.toString().toInt() <= 8) {
                        tv_total_salary.text = "${salaryRate * p0.toString().toInt()}"
                    } else {
                        if (userMdl.group == "A") {
                            tv_total_salary.text =
                                "${(salaryRate * 8) + (groupARate * (p0.toString().toInt() - 8))}"
                        } else if (userMdl.group == "B") {
                            tv_total_salary.text =
                                "${(salaryRate * 8) + (groupBRate * (p0.toString().toInt() - 8))}"
                        }
                    }
                }
            }
        })

        btn_logout.setOnClickListener {
            MainActivity.startThisActivity(this)
            finish()
        }

    }

}