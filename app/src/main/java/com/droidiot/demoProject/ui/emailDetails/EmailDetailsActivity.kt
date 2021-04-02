package com.droidiot.demoProject.ui.emailDetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.droidiot.demoProject.R
import com.droidiot.demoProject.data.model.EmailModel
import com.droidiot.demoProject.databinding.ActivityEmailDetailsBinding

class EmailDetailsActivity : AppCompatActivity() {
    lateinit var view: ActivityEmailDetailsBinding

    companion object {
        const val PARAM_EMAIL_ITEM = "PARAM_EMAIL_ITEM"
        fun getStartIntent(ctx: Context, emailItem: EmailModel): Intent {
            return Intent(ctx, EmailDetailsActivity::class.java)
                .putExtra(PARAM_EMAIL_ITEM, emailItem)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDependency()
        val emailItem:EmailModel = intent.getSerializableExtra(PARAM_EMAIL_ITEM) as EmailModel
        Glide.with(this)
            .load(emailItem.sender.picture)
            .into(view.ivSender)
        view.tvSenderName.text = "${emailItem.sender.first_name} ${emailItem.sender.last_name}"
        view.tvMessage.text = emailItem.message
        view.tvTime.text = emailItem.time
    }

    private fun initDependency() {
        view = DataBindingUtil.setContentView(this, R.layout.activity_email_details)
        view.lifecycleOwner = this
    }
}