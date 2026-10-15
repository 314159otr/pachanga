insert into match (
    season,
    match_number,
    datetime,
    place,
    puskas,
    winner
) values (
    3,
    61,
    '2026-10-11 11:00:00',
    'Cantoblanco',
    20,
    NULL -- 1 = blanco, 2 = negro
);

insert into match_player (id_match, id_player, team, goals, own_goals)
values
    (61, 14, 1, 3, 0),
    (61, 4, 1, 1, 0),
    (61, 19, 1, 4, 0),
    (61, 5, 1, 0, 0),
    (61, 34, 1, 0, 0),
    (61, 1, 1, 2, 0),
    (61, 7, 2, 2, 1),
    (61, 20, 2, 4, 1),
    (61, 12, 2, 3, 0),
    (61, 8, 2, 0, 0),
    (61, 6, 2, 0, 0),
    (61, 3, 2, 1, 0);
