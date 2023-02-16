package com.nightstalker.artic.features.ticket.presentation.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.nightstalker.artic.R
import com.nightstalker.artic.databinding.ItemTicketBinding
import com.nightstalker.artic.features.ticket.domain.model.ExhibitionTicket


/**
 * Адаптер для отображения списка билетов
 * @author Maxim Zimin
 * @created 2022-10-13
 */
class TicketsListAdapter(private val onItemClicked: (id: Long) -> Unit) :
    RecyclerView.Adapter<TicketsListAdapter.ViewHolder>() {
    private var _data: List<ExhibitionTicket> = mutableListOf()
    val data get() = _data

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemTicketBinding.bind(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_ticket, parent, false)
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = _data[position]
        with(holder.binding) {
            textTitle.text = item.title
            placeImage.load(item.imageUrl)
            root.setOnClickListener {
                onItemClicked(item.id)
            }
        }
    }

    override fun getItemCount(): Int = _data.size

    fun setData(data: List<ExhibitionTicket>) {
        if (data.isNotEmpty()) {
            this._data = data
            notifyDataSetChanged()
        }
    }

    class ViewHolder(val binding: ItemTicketBinding) :
        RecyclerView.ViewHolder(binding.root)

}