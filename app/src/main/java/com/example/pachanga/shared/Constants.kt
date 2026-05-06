package com.example.pachanga.shared

import com.example.pachanga.R

object Constants {
    object Database {
        const val DIR = "databases"
        const val NAME = "pachanga.db"
        const val DOWNLOAD_FILE_URL = "https://github.com/314159otr/pachanga/releases/latest/download/$NAME"
    }
    enum class NavBarScreen(
        val route: String,
        val label: String,
        val resId: Int,
        val resIdSelected: Int,
    ) {
        PLAYERS("players", "Jugadores", R.drawable.ic_players, R.drawable.ic_players_filled),
        MATCHES("matches", "Partidos", R.drawable.ic_matches, R.drawable.ic_matches_filled),
    }
}
