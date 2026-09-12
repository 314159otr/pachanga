insert into match (
    season,
    match_number,
    datetime,
    place,
    puskas,
    winner
) values (
    3,
    59,
    '2026-09-12 10:00:00',
    'Cantoblanco',
    12,
    2 -- 1 = blanco, 2 = negro
);

insert into match_player (id_match, id_player, team, goals, own_goals)
values
    (59, 3, 1, 2, 0),
    (59, 4, 1, 5, 0),
    (59, 19, 1, 3, 0),
    (59, 20, 1, 1, 0),
    (59, 32, 1, 1, 0),
    (59, 7, 2, 6, 0),
    (59, 12, 2, 2, 0),
    (59, 5, 2, 2, 0),
    (59, 34, 2, 0, 0),
    (59, 1, 2, 4, 0);
