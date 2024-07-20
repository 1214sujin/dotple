use dotple;

select * from `user`;
select * from `category`;
select * from `todo`;
select * from `task`;

insert into `user` (username, password, nickname, created, updated) value ('test', 'test1234', '사용자1', now(), now());

insert into category (user_id, category_nm, color, created, updated) values
	(1, '운동', 'fff44336', now(), now()),
    (1, '공부', 'ff4f2cd2', now(), now());

insert into todo (category_id, todo_nm, start, end, iter_type, iter_val, alarm, created, updated) value
	(1, '걷기', '2024-07-01', '2024-07-20', 1, 3, null, now(), now()),
	(2, '파이썬', '2024-07-01', '2024-08-08', 2, 36, null, now(), now()),	-- 월목
	(2, '자바', '2024-07-17', '2024-08-01', 1, 4, null, now(), now());

insert into task (todo_id, date, state, created, updated) values
	(1, '2024-07-01', 2, now(), now()),
	(1, '2024-07-02', 3, now(), now()),
	(1, '2024-07-03', 3, now(), now()),
	(1, '2024-07-04', 2, now(), now()),
	(1, '2024-07-05', 2, now(), now()),
	(1, '2024-07-06', 3, now(), now()),
	(1, '2024-07-07', 3, now(), now()),
	(1, '2024-07-08', 2, now(), now()),
	(1, '2024-07-09', 3, now(), now()),
	(1, '2024-07-10', 3, now(), now()),
	(1, '2024-07-11', 2, now(), now()),
	(1, '2024-07-12', 2, now(), now()),
	(1, '2024-07-13', 3, now(), now()),
	(1, '2024-07-14', 2, now(), now()),
	(1, '2024-07-15', 3, now(), now()),
	(1, '2024-07-16', 3, now(), now()),
	(1, '2024-07-17', 2, now(), now()),
	(1, '2024-07-18', 3, now(), now()),
	(1, '2024-07-19', 3, now(), now()),
	(1, '2024-07-20', 1, now(), now()),
	(2, '2024-07-01', 2, now(), now()),
	(2, '2024-07-04', 2, now(), now()),
	(2, '2024-07-08', 2, now(), now()),
	(2, '2024-07-11', 2, now(), now()),
	(2, '2024-07-15', 2, now(), now()),
	(2, '2024-07-18', 2, now(), now()),
	(2, '2024-07-22', 1, now(), now()),
	(2, '2024-07-25', 1, now(), now()),
	(2, '2024-07-29', 1, now(), now()),
	(2, '2024-08-01', 1, now(), now()),
	(2, '2024-08-05', 1, now(), now()),
	(2, '2024-08-08', 1, now(), now()),
	(3, '2024-07-17', 2, now(), now()),
	(3, '2024-07-18', 0, now(), now()),
	(3, '2024-07-19', 0, now(), now()),
	(3, '2024-07-20', 1, now(), now()),
	(3, '2024-07-21', 1, now(), now()),
	(3, '2024-07-22', 3, now(), now()),
	(3, '2024-07-23', 1, now(), now()),
	(3, '2024-07-24', 3, now(), now()),
	(3, '2024-07-25', 1, now(), now()),
	(3, '2024-07-26', 3, now(), now()),
	(3, '2024-07-27', 1, now(), now()),
	(3, '2024-07-28', 1, now(), now()),
	(3, '2024-07-29', 1, now(), now()),
	(3, '2024-07-30', 0, now(), now()),
	(3, '2024-07-31', 0, now(), now()),
	(3, '2024-08-01', 0, now(), now());
