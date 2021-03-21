package com.freekickr.roombascore.ui.gameplay

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.freekickr.roombascore.R

class GamePlayersAdapter(): RecyclerView.Adapter<GamePlayersAdapter.GamePlayersViewHolder>() {

    private var numberOfPlayers: Int = 0

    private var players = listOf<Player>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GamePlayersViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_gameplay_player, parent, false)
        return GamePlayersViewHolder(view, MyCustomEditTextListener())
    }

    override fun onBindViewHolder(holder: GamePlayersViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount() = players.size

    fun setPlayers(
        newPlayers: List<Player>,
        numberOfPlayers: Int
    ) {
        this.numberOfPlayers = numberOfPlayers
        players = newPlayers
        notifyDataSetChanged()
    }

    fun fillNewData(data: List<Player>) {
        players = data
        notifyDataSetChanged()
    }

    fun blockPlayer() {

    }

    fun checkScoresForEntering() {

    }

    inner class GamePlayersViewHolder(itemView: View, private val listener: MyCustomEditTextListener): RecyclerView.ViewHolder(itemView) {

        private val playerName: TextView = itemView.findViewById(R.id.tvPlayerName)
        private val playerHistory: TextView = itemView.findViewById(R.id.tvPlayerHistory)
        private val newPoints: EditText = itemView.findViewById(R.id.etNewPoints)

        fun bind(position: Int) {
            listener.updatePosition(position)
            playerName.text = players[position].playerName
            playerHistory.text = players[position].score.toString()
            if (players[position].score > 100) {
                newPoints.visibility = View.INVISIBLE
                players[position].newPoints = 0
            } else {
                newPoints.addTextChangedListener(listener)
            }
        }

    }

    inner class MyCustomEditTextListener : TextWatcher {
        private var position = 0
        fun updatePosition(position: Int) {
            this.position = position
        }

        override fun beforeTextChanged(
            charSequence: CharSequence,
            i: Int,
            i2: Int,
            i3: Int
        ) {
            // no op
        }

        override fun onTextChanged(
            charSequence: CharSequence,
            i: Int,
            i2: Int,
            i3: Int
        ) {
            players[position].newPoints = charSequence.toString().toInt()
        }

        override fun afterTextChanged(editable: Editable) {
            // no op
        }
    }

}