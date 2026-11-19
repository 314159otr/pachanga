insert into match (
    season,
    match_number,
    datetime,
    place,
    puskas,
    winner
) values (
    3,
    64,
    '2026-11-15 11:00:00',
    'Cantoblanco',
    1,
    2 -- 1 = blanco, 2 = negro, NULL = empate
);

insert into match_player (id_match, id_player, team, goals, own_goals)
values
    (64, 10, 1, 6, 0),
    (64, 37, 1, 3, 0),
    (64, 1, 1, 4, 0),
    (64, 34, 1, 0, 0),
    (64, 38, 1, 4, 0),
    (64, 7, 2, 4, 0),
    (64, 4, 2, 3, 0),
    (64, 3, 2, 5, 0),
    (64, 6, 2, 0, 0),
    (64, 5, 2, 6, 0);
