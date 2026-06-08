insert into match (
    season,
    match_number,
    datetime,
    place,
    puskas,
    winner
) values (
    2,
    55,
    '2026-06-06 12:00:00',
    'Cantoblanco',
    14,
    2 -- 1 = blanco, 2 = negro
);

insert into match_player (id_match, id_player, team, goals, own_goals)
values
    (55, 1, 1, 3, 0),
    (55, 7, 1, 2, 0),
    (55, 15, 1, 2, 0),
    (55, 5, 1, 2, 0),
    --(55, 20, 1, 7, 0),
    --(55, 1, 2, 2, 0),
    (55, 14, 2, 5, 0),
    (55, 3, 2, 3, 0),
    (55, 4, 2, 3, 0),
    (55, 6, 2, 1, 0);
