insert into match (
    season,
    match_number,
    datetime,
    place,
    puskas,
    winner
) values (
    2,
    56,
    '2026-06-20 12:00:00',
    'Cantoblanco',
    6,
    2 -- 1 = blanco, 2 = negro
);

insert into match_player (id_match, id_player, team, goals, own_goals)
values
    (56, 3, 1, 3, 0),
    (56, 6, 1, 2, 0),
    (56, 12, 1, 3, 0),
    (56, 14, 1, 3, 0),
    --(56, 20, 1, 7, 0),
    --(56, 1, 2, 2, 0),
    (56, 7, 2, 3, 0),
    (56, 8, 2, 3, 0),
    (56, 4, 2, 3, 0),
    (56, 1, 2, 4, 0);
