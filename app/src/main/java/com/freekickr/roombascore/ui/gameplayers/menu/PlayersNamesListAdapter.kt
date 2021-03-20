package com.freekickr.roombascore.ui.gameplayers.menu

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.freekickr.roombascore.R
import com.freekickr.roombascore.ui.gamesetup.menu.PlayerCount
import com.freekickr.roombascore.ui.gamesetup.menu.PlayerSettings


class PlayersNamesListAdapter(): RecyclerView.Adapter<PlayersNamesListAdapter.PlayersNamesViewHolder>() {

    private var numberOfPlayers: Int = 0

    private var menuItems = mutableListOf(
        PlayerSettings("1 игрок", 1, null),
        PlayerSettings("2 игрок", 2, null),
        PlayerSettings("3 игрок", 3, null),
        PlayerSettings("4 игрок", 4, null),
        PlayerSettings("5 игрок", 5, null),
        PlayerSettings("6 игрок", 6, null),
        PlayerSettings("7 игрок", 7, null),
        PlayerSettings("8 игрок", 8, null)
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayersNamesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_player_name, parent, false)
        return PlayersNamesViewHolder(view, MyCustomEditTextListener())
    }

    override fun getItemCount() = menuItems.size

    override fun onBindViewHolder(holder: PlayersNamesViewHolder, position: Int) {
        holder.bind(position)
    }

    fun setNumberOfPlayers(number: Int) {
        numberOfPlayers = number
        val newList = mutableListOf<PlayerSettings>()
        menuItems.forEachIndexed { index, playerSettings ->
            if (index < number) {
                newList.add(playerSettings)
            }
        }
        menuItems = newList
        notifyDataSetChanged()
    }

    fun checkNamesForFilling(): Boolean {
        menuItems.forEachIndexed { index, playerSettings ->
            if (index < numberOfPlayers && playerSettings.name == null) return false
        }
        return true
    }

    fun getPlayerNames() = menuItems.toList()

    inner class PlayersNamesViewHolder(itemView: View, private val listener: MyCustomEditTextListener): RecyclerView.ViewHolder(itemView) {

        private val textView: TextView = itemView.findViewById(R.id.tvPlayerNameHeader)
        private val editText: EditText = itemView.findViewById(R.id.etPlayerName)

        fun bind(position: Int) {
            textView.text = menuItems[position].title
            listener.updatePosition(position)
            editText.addTextChangedListener(listener)
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
            menuItems[position].name = charSequence.toString()
        }

        override fun afterTextChanged(editable: Editable) {
            // no op
        }
    }

}