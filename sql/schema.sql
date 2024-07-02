drop database dotple;
create database dotple;
use dotple;

create table `user` (
	user_id int unsigned primary key auto_increment,
	username varchar(50) not null,
	password varchar(255) not null,
	nickname varchar(20) not null,
	created timestamp not null,
	updated timestamp not null
);

create table `group` (
	group_id int unsigned primary key auto_increment,
	group_nm varchar(50) not null,
	group_pw int,
	manager int unsigned not null,
	max_user int not null,
	created timestamp not null,
	updated timestamp not null,

	foreign key (manager) references `user` (user_id)
);

create table belong (
	belong_id int unsigned primary key auto_increment,
	group_id int unsigned not null,
	user_id int unsigned not null,
	created timestamp not null,
	updated timestamp not null,

	foreign key (group_id) references `group` (group_id),
	foreign key (user_id) references `user` (user_id)
);

create table eval_day (
	eval_day_id int unsigned primary key auto_increment,
	group_id int unsigned not null,
	eval_date date not null,
	created timestamp not null,
	updated timestamp not null,

	foreign key (group_id) references `group` (group_id)
);

create table eval (
	eval_id int unsigned primary key auto_increment,
	eval_day_id int unsigned not null,
	user_id int unsigned not null,
	penalty text not null,
	created timestamp not null,
	updated timestamp not null,

	foreign key (eval_day_id) references eval_day (eval_day_id),
	foreign key (user_id) references `user` (user_id)
);

create table category (
	category_id int unsigned primary key auto_increment,
	user_id int unsigned not null,
	category_nm varchar(10) not null,
	color varchar(8) not null,
	created timestamp not null,
	updated timestamp not null,

	foreign key (user_id) references `user` (user_id)
);

create table todo (
	todo_id int unsigned primary key auto_increment,
	category_id int unsigned not null,
	todo_nm varchar(10) not null,
	start date not null,
	end date not null,
	iter_type int not null check (iter_type in (0, 1, 2)),	-- 0: 없음(임시), 1: 횟수, 2: 요일
	iter_val int not null,
	alarm bigint,
	created timestamp not null,
	updated timestamp not null,

	foreign key (category_id) references category (category_id)
);

create table task (
	task_id int unsigned primary key auto_increment,
	todo_id int unsigned not null,
	date date not null,
	state bool not null default 0,
	created timestamp not null,
	updated timestamp not null,

	foreign key (todo_id) references todo (todo_id)
);

create table sharing (
	sharing_id int unsigned primary key auto_increment,
	todo_id int unsigned not null,
	eval_id int unsigned not null,
	created timestamp not null,
	updated timestamp not null,

	foreign key (todo_id) references todo (todo_id),
	foreign key (eval_id) references eval (eval_id)
);

create table pass (
	pass_id int unsigned primary key auto_increment,
	task_id int unsigned not null,
	user_id int unsigned not null,
	reason text not null,
	is_ok bool not null default 0,
	created timestamp not null,
	updated timestamp not null,

	foreign key (task_id) references task (task_id),
	foreign key (user_id) references `user` (user_id)
);

create table confirm (
	confirm_id int unsigned primary key auto_increment,
	eval_day_id int unsigned not null,
	user_id int unsigned not null,
	reason text not null,
	is_ok bool not null default 0,
	created timestamp not null,
	updated timestamp not null,

	foreign key (eval_day_id) references eval_day (eval_day_id),
	foreign key (user_id) references `user` (user_id)
);