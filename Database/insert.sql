insert into match (
    season,
    match_number,
    datetime,
    place,
    puskas,
    winner
) values (
    2,
    57,
    '2026-08-07 17:00:00',
    'Cantoblanco',
    14,
    2 -- 1 = blanco, 2 = negro
);

insert into match_player (id_match, id_player, team, goals, own_goals)
values
    (57, 1, 1, 1, 0),
    (57, 3, 1, 3, 0),
    (57, 12, 1, 1, 0),
    (57, 14, 1, 5, 0),
    (57, 6, 1, 0, 0),
    (57, 8, 2, 1, 0),
    (57, 10, 2, 3, 0),
    (57, 5, 2, 1, 0),
    (57, 13, 2, 4, 0),
    (57, 30, 2, 2, 0),
    (57, 31, 2, 1, 0);
