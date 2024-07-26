use dotple;

select * from `user`;
select * from `category`;
select * from `todo`;
select * from `task`;

select * from task where todo_id=3 and state > 1 and date between '2024-07-28' and '2024-07-27';
select count(*) from task where todo_id=3 and state >= 2 and date between '2024-07-21' and '2024-07-27';
select * from task where todo_id=3 and date between '2024-07-28' and '2024-08-03';
select * from task where date between '2024-07-21' and '2024-07-27';
select * from task where todo_id=3;
-- update task set state = 1 where todo_id=3 and state = 0 and date between '2024-07-21' and '2024-07-27';

insert into `user` (username, password, nickname, created, updated) value ('test', 'test1234', '사용자1', now(), now());

insert into category (user_id, category_nm, color, created, updated) values
	(1, '운동', 'fff44336', now(), now()),
    (1, '공부', 'ff4f2cd2', now(), now());

insert into todo (category_id, todo_nm, start, end, iter_type, iter_val, alarm, created, updated) value
	(1, '걷기', '2024-07-01', '2024-07-20', 1, 3, null, now(), now()),
	(2, '파이썬', '2024-07-01', '2024-08-08', 2, 36, null, now(), now()),	-- 월목
	(2, '자바', '2024-07-17', '2024-08-01', 1, 4, null, now(), now());

insert into task (todo_id, date, state, created, updated) values
	(1, '2024-07-01', 3, now(), now()),
	(1, '2024-07-02', 0, now(), now()),
	(1, '2024-07-03', 0, now(), now()),
	(1, '2024-07-04', 3, now(), now()),
	(1, '2024-07-05', 3, now(), now()),
	(1, '2024-07-06', 0, now(), now()),
	(1, '2024-07-07', 0, now(), now()),
	(1, '2024-07-08', 3, now(), now()),
	(1, '2024-07-09', 0, now(), now()),
	(1, '2024-07-10', 0, now(), now()),
	(1, '2024-07-11', 3, now(), now()),
	(1, '2024-07-12', 3, now(), now()),
	(1, '2024-07-13', 0, now(), now()),
	(1, '2024-07-14', 3, now(), now()),
	(1, '2024-07-15', 0, now(), now()),
	(1, '2024-07-16', 0, now(), now()),
	(1, '2024-07-17', 3, now(), now()),
	(1, '2024-07-18', 0, now(), now()),
	(1, '2024-07-19', 0, now(), now()),
	(1, '2024-07-20', 2, now(), now()),
	(2, '2024-07-01', 3, now(), now()),
	(2, '2024-07-04', 3, now(), now()),
	(2, '2024-07-08', 3, now(), now()),
	(2, '2024-07-11', 3, now(), now()),
	(2, '2024-07-15', 3, now(), now()),
	(2, '2024-07-18', 3, now(), now()),
	(2, '2024-07-22', 2, now(), now()),
	(2, '2024-07-25', 2, now(), now()),
	(2, '2024-07-29', 2, now(), now()),
	(2, '2024-08-01', 2, now(), now()),
	(2, '2024-08-05', 2, now(), now()),
	(2, '2024-08-08', 2, now(), now()),
	(3, '2024-07-17', 3, now(), now()),
	(3, '2024-07-18', 1, now(), now()),
	(3, '2024-07-19', 1, now(), now()),
	(3, '2024-07-20', 2, now(), now()),
	(3, '2024-07-21', 2, now(), now()),	-- 7월 넷째주 시작
	(3, '2024-07-22', 1, now(), now()),
	(3, '2024-07-23', 2, now(), now()),
	(3, '2024-07-24', 1, now(), now()),
	(3, '2024-07-25', 2, now(), now()),
	(3, '2024-07-26', 1, now(), now()),
	(3, '2024-07-27', 1, now(), now()),	-- 7월 넷째주 끝
	(3, '2024-07-28', 2, now(), now()),
	(3, '2024-07-29', 2, now(), now()),
	(3, '2024-07-30', 1, now(), now()),
	(3, '2024-07-31', 1, now(), now()),
	(3, '2024-08-01', 1, now(), now());
