insert into match (
    season,
    match_number,
    datetime,
    place,
    puskas,
    winner
) values (
    3,
    58,
    '2026-09-04 19:00:00',
    'Cantoblanco',
    2,
    NULL -- 1 = blanco, 2 = negro
);

insert into match_player (id_match, id_player, team, goals, own_goals)
values
    (58, 1, 1, 2, 0),
    (58, 12, 1, 1, 0),
    (58, 20, 1, 2, 0),
    (58, 32, 1, 0, 0),
    (58, 2, 1, 4, 0),
    (58, 3, 2, 2, 0),
    (58, 4, 2, 0, 0),
    (58, 10, 2, 3, 0),
    (58, 33, 2, 3, 0),
    (58, 31, 2, 0, 0);
