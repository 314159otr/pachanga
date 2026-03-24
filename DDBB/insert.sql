
insert into match (
    season,
    match_number,
    datetime,
    place,
    puskas,
    winner
) values (
    2,
    7,
    '2025-11-08 11:00:00',
    'Cantoblanco',
    20,
    1 -- 1 = blanco, 2 = negro
);

insert into match_player (id_match, id_player, team, goals, own_goals)
values
    (37, 12, 1, 2, 1),
    (37, 3, 1, 1, 1),
    (37, 4, 1, 0, 0),
    (37, 19, 1, 1, 0),
    (37, 20, 1, 7, 0),
    (37, 1, 2, 2, 0),
    (37, 5, 2, 3, 0),
    (37, 9, 2, 1, 0),
    (37, 6, 2, 0, 0),
    (37, 7, 2, 2, 0);

