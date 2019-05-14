create database db_stories character set utf8 collate utf8_bin;
use db_stories;

create table users (
user_id int auto_increment not null primary key,
user_name varchar(50) not null,
user_lastname varchar(50) not null,
user_email varchar(70) not null,
user_phone varchar(8),
user_password varchar(20) not null
) engine = innodb;

create table categories (
category_id int auto_increment not null primary key,
category_name varchar(20) not null
) engine = innodb;

create table stories (
story_id int auto_increment not null primary key,
story_title varchar(100) not null,
story_image_name varchar(250),
story_image_format varchar(5),
story_image_path varchar(250),
story_description varchar(200),
story_approved boolean not null,
user_id int not null,
category_id int not null
) engine = innodb;

create table sections (
section_id int auto_increment not null primary key,
section_title varchar(100) not null,
section_content varchar(1000) not null,
section_image_name varchar(250),
section_image_format varchar(5),
section_image_path varchar(250),
story_id int not null
) engine = innodb;

alter table stories
add constraint fk_stories_users
foreign key (user_id) references users(user_id) on delete cascade;

alter table stories
add constraint fk_stories_categories
foreign key (category_id) references categories(category_id) on delete cascade;

alter table sections
add constraint fk_sections_stories
foreign key (story_id) references stories(story_id) on delete cascade;
