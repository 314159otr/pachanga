package com.example.pachanga.shared

object Constants {
    object Database {
        const val DOWNLOAD_FILE_URL = "https://github.com/314159otr/pachanga/releases/latest/download/pachanga.db"
        const val NAME = "pachanga.db"
    }

    enum class Nav(val route: String) {
        GREETING("Greeting"),
        PLAYERS_STATS("Players Stats"),
        MATCHES("Matches");
    }
    object Messages {
        const val DOWNLOAD_REQUIRED = "↑ Necesita descargar para acceder ↑"
        const val ERROR_GENERIC = "Ha ocurrido un error"
    }
}
