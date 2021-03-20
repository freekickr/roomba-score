package com.freekickr.roombascore.ui.gamesetup.menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PlayersListAdapter(private val onClickListener: OnPlayersNumberClickListener): RecyclerView.Adapter<PlayersListAdapter.PlayersMenuViewHolder>() {

    private val menuItems = listOf(
        PlayerCount.TWO_PLAYERS,
        PlayerCount.THREE_PLAYERS,
        PlayerCount.FOUR_PLAYERS,
        PlayerCount.FIVE_PLAYERS,
        PlayerCount.SIX_PLAYERS,
        PlayerCount.SEVEN_PLAYERS,
        PlayerCount.EIGHT_PLAYERS,
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayersMenuViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
        return PlayersMenuViewHolder(view)
    }

    override fun getItemCount() = menuItems.size

    override fun onBindViewHolder(holder: PlayersMenuViewHolder, position: Int) {
        holder.bind(menuItems[position], onClickListener)
    }

    inner class PlayersMenuViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val textView: TextView = itemView.findViewById(android.R.id.text1)

        fun bind(
            menuItem: PlayerCount,
            onClickListener: OnPlayersNumberClickListener
        ) {
            textView.text = menuItem.title
            itemView.setOnClickListener {
                onClickListener.numberChoosen(menuItem.value)
            }
        }

    }

}