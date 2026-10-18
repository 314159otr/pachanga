insert into match (
    season,
    match_number,
    datetime,
    place,
    puskas,
    winner
) values (
    3,
    62,
    '2026-10-16 18:00:00',
    'Cantoblanco',
    37,
    1 -- 1 = blanco, 2 = negro
);

insert into match_player (id_match, id_player, team, goals, own_goals)
values
    (62, 10, 1, 2, 0),
    (62, 35, 1, 4, 0),
    (62, 36, 1, 0, 0),
    (62, 37, 1, 7, 0),
    (62, 31, 1, 0, 0),
    (62, 3, 2, 4, 0),
    (62, 2, 2, 1, 0),
    (62, 4, 2, 0, 0),
    (62, 8, 2, 1, 0),
    (62, 7, 2, 4, 0);
