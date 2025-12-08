
CREATE TABLE player (
    id integer primary key,
    nickname text unique not null,
    first_name text not null,
    last_name text not null,
    profile_image_url text not null default 'https://res.cloudinary.com/dlf47buhm/image/upload/v1748251889/User_my5mcw.png');

CREATE TABLE match (
    id integer primary key,
    season integer not null,
    match_number integer not null,
    datetime text not null,
    place text not null,
    puskas integer null,
    winner integer null check(winner in (1, 2)),
    unique(season, match_number),
    --check (datetime like '____-__-__ __:__:__'),
    foreign key (puskas) references player(id)
);

CREATE TABLE match_player (
    id_match integer not null,
    id_player integer not null,
    team integer not null check(team in (1, 2)),
    goals integer not null default 0,
    own_goals integer not null default 0,
    primary key (id_match, id_player),
    foreign key (id_match) references match(id),
    foreign key (id_player) references player(id)
);

CREATE VIEW vw_player_stats as
select
    p.id,
    p.nickname,
    p.first_name,
    p.last_name,
    coalesce(SUM(mp.goals), 0) as goals,
    coalesce(SUM(mp.own_goals), 0) as own_goals,
    coalesce(pu.puskas_count, 0) as puskas,
    coalesce(sum(case when m.winner = mp.team then 1 else 0 end), 0) as wins,
    count(distinct mp.id_match) as matches,
    COALESCE(
        ROUND(
            CAST(SUM(CASE WHEN m.winner = mp.team THEN 1 ELSE 0 END) AS REAL)
            / NULLIF(COUNT(DISTINCT mp.id_match), 0) * 100,
        2),
    0
    ) AS win_ratio
from player p
left join match_player mp ON mp.id_player = p.id
left join match m on m.id = mp.id_match
left join (
    select m.puskas as id_player, count(*) as puskas_count
    from match m
    group by m.puskas
) pu on pu.id_player = p.id
group by p.id, p.nickname, p.first_name, p.last_name;

create view vw_player_stats_season_2 as
select
    p.nickname,
    p.id,
    coalesce(SUM(mp.goals), 0) as goals,
    coalesce(SUM(mp.own_goals), 0) as own_goals,
    coalesce(pu.puskas_count, 0) as puskas,
    coalesce(sum(case when m.winner = mp.team then 1 else 0 end), 0) as wins,
    count(distinct mp.id_match) as matches,
    COALESCE(
        ROUND(
            CAST(SUM(CASE WHEN m.winner = mp.team THEN 1 ELSE 0 END) AS REAL)
            / NULLIF(COUNT(DISTINCT mp.id_match), 0) * 100,
        2),
    0
    ) AS win_ratio
from player p
join match_player mp ON mp.id_player = p.id
join match m on m.id = mp.id_match and m.season = 2
left join (
    select m.puskas as id_player, count(*) as puskas_count
    from match m
    where m.season = 2
    group by m.puskas
) pu on pu.id_player = p.id
group by p.id, p.nickname, p.first_name, p.last_name;

create view vw_match_stats as
select
    mp.id_match,
    mp.id_player,
    p.nickname,
    mp.team,
    mp.goals,
    mp.own_goals,
    case 
        when m.puskas = p.id then 1
        else 0
    end as puskas,
    p.profile_image_url
from match_player mp
join match m
    on m.id = mp.id_match
join player p
    on p.id = mp.id_player
-- where m.season = 2 
order by m.id, mp.team;

CREATE VIEW vw_match as
select
    m.id,
    m.season,
    m.match_number,
    m.datetime,
    m.place,
    p.nickname as puskas,
    case
        when m.winner = 1 then 'white'
        when m.winner = 2 then 'black'
        else 'tie'
    end as winner,
    sum(case when mp.team = 1 then mp.goals end) + sum(case when mp.team = 2 then mp.own_goals end)  as goals_white,
    sum(case when mp.team = 2 then mp.goals end) + sum(case when mp.team = 1 then mp.own_goals end) as goals_black
from match m
join match_player mp
    on mp.id_match = m.id
left join player p
    on p.id = m.puskas
group by
    m.id,
    m.season,
    m.match_number,
    m.datetime,
    m.place,
    m.puskas,
    m.winner;
