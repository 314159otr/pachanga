insert into match (
    season,
    match_number,
    datetime,
    place,
    puskas,
    winner
) values (
    3,
    60,
    '2026-09-18 18:00:00',
    'Cantoblanco',
    7,
    2 -- 1 = blanco, 2 = negro
);

insert into match_player (id_match, id_player, team, goals, own_goals)
values
    (60, 20, 1, 6, 0),
    (60, 32, 1, 0, 0),
    (60, 4, 1, 1, 0),
    (60, 12, 1, 3, 0),
    (60, 5, 1, 2, 0),
    (60, 7, 2, 2, 1),
    (60, 3, 2, 1, 0),
    (60, 1, 2, 3, 0),
    (60, 10, 2, 5, 0),
    (60, 8, 2, 3, 0);
