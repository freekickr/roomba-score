package com.freekickr.roombascore.ui.gameplay

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.freekickr.roombascore.R
import com.freekickr.roombascore.databinding.ItemPlayerGameBinding

class GamePlayersAdapter(): RecyclerView.Adapter<GamePlayersAdapter.GamePlayersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GamePlayersViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_player_game, parent, false)
        return GamePlayersViewHolder(view)
    }

    override fun onBindViewHolder(holder: GamePlayersViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    inner class GamePlayersViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

}