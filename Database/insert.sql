insert into match (
    season,
    match_number,
    datetime,
    place,
    puskas,
    winner
) values (
    3,
    63,
    '2026-10-23 17:00:00',
    'Cantoblanco',
    1,
    2 -- 1 = blanco, 2 = negro, NULL = empate
);

insert into match_player (id_match, id_player, team, goals, own_goals)
values
    (63, 1, 1, 3, 0),
    (63, 34, 1, 0, 0),
    (63, 7, 1, 2, 0),
    (63, 6, 1, 0, 0),
    (63, 5, 1, 2, 0),
    (63, 37, 2, 3, 0),
    (63, 12, 2, 4, 0),
    (63, 10, 2, 6, 0),
    (63, 3, 2, 3, 0),
    (63, 31, 2, 0, 0);
