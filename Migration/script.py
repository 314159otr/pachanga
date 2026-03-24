import json
import sqlite3
con = sqlite3.connect("pachanga.db")
cursor = con.cursor()
archivo_entrada = "datos.json"

with open(archivo_entrada, "r", encoding="utf-8") as file:
    json_original = json.load(file)

for key, temporadas in json_original.items():
    for temporada in temporadas:

        numeroTemporada = temporada['nombre'][-2:]
        for jornada in temporada['jornadas']:
            nombreJornada = jornada['nombre'][-2:]
            puskas = jornada['puskas']
            pichichi = jornada['pichichi']
            fecha = jornada['fecha']
            jugadores = []
            for jugador in jornada['equipo_ganador']:
                nombreJugador = jugador['nombre']
                idJugador = cursor.execute('select id from player where nickname = ?',[nombreJugador]).fetchone()
                if (idJugador is None):
                   idJugador = cursor.execute('insert into player (nickname, first_name, last_name) values (?,?,?)',[nombreJugador, 'nombre', 'apellido']).lastrowid
                   con.commit()
                else :
                    idJugador = idJugador[0]

                goles = jugador['goles']
                jugadores.append([idJugador,1,goles,0])

            for jugador in jornada['equipo_perdedor']:
                nombreJugador = jugador['nombre']
                idJugador = cursor.execute('select id from player where nickname = ?',[nombreJugador]).fetchone()
                if (idJugador is None):
                   idJugador = cursor.execute('insert into player (nickname, first_name, last_name) values (?,?,?)',[nombreJugador, 'nombre', 'apellido']).lastrowid
                   con.commit()
                else :
                    idJugador = idJugador[0]

                goles = jugador['goles']
                jugadores.append([idJugador,2,goles,0])

            if (puskas == 'Nadie'):
                puskasId = None
            else :
                puskasId = cursor.execute('select id from player where nickname = ?',[puskas]).fetchone()[0]
            idPartido = cursor.execute('insert into match (season, match_number, datetime, place, puskas, winner) values(?,?,?,?,?,?)',[numeroTemporada,nombreJornada,fecha,'Cantoblanco',puskasId, 1]).lastrowid
            con.commit()
            for jugador in jugadores:
                cursor.execute('insert into match_player (id_match, id_player, team, goals, own_goals) values (?,?,?,?,?)',[idPartido,jugador[0],jugador[1],jugador[2],jugador[3]])
                con.commit()
cursor.execute('delete from match_player where id_match = 31')
cursor.execute('delete from match where id = 31')
con.commit()




