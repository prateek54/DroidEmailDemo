package com.droidiot.demoProject.ui.emailListing

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.droidiot.demoProject.R
import com.droidiot.demoProject.data.model.EmailModel
import com.droidiot.demoProject.databinding.ItemEmailViewBinding


class EmailListingAdapter(
    private val callBack: (EmailModel) -> Unit
) :
    RecyclerView.Adapter<EmailListingAdapter.ViewHolder>(), Filterable {
    private val list: ArrayList<EmailModel> = arrayListOf()
    private val originalList: ArrayList<EmailModel> = arrayListOf()

    class ViewHolder(binding: ItemEmailViewBinding) : RecyclerView.ViewHolder(binding.root) {
        val ctx = binding.root.context
        val senderImage = binding.ivSender
        val senderName = binding.tvSenderName
        val emailMessage = binding.tvMessage
        val emailTime = binding.tvTime

        fun bind(emailItem: EmailModel) {
            Glide.with(ctx)
                .load(emailItem.sender.picture)
                .into(senderImage)
            senderName.text = "${emailItem.sender.first_name} ${emailItem.sender.last_name}"
            emailMessage.text = emailItem.message
            emailTime.text = emailItem.time
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemEmailViewBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_email_view, parent, false
        )

        return ViewHolder(
            binding
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
        holder.itemView.setOnClickListener { callBack.invoke(list[position]) }
    }

    fun updateList(list: List<EmailModel>?) {
        this.list.clear()
        this.list.addAll(list ?: arrayListOf())
        this.originalList.addAll(list ?: arrayListOf())
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = arrayListOf<EmailModel>()
                if (constraint == null || constraint.isEmpty()) {
                    filteredList.addAll(originalList)
                } else {
                    val filterPattern = constraint.toString().toLowerCase()
                    for (item in originalList) {
                        val name =
                            "${item.sender.first_name?.toLowerCase()} ${item.sender.last_name?.toLowerCase()}"
                        if (name.contains(filterPattern))
                            filteredList.add(item)
                    }
                }
                val results = FilterResults()
                results.values = filteredList
                return results
            }

            override fun publishResults(p0: CharSequence?, results: FilterResults?) {
                list.clear()
                list.addAll(results?.values as List<EmailModel>)
                notifyDataSetChanged()
            }

        }
    }

}