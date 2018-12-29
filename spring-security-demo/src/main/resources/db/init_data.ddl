insert into user (user_name,password) values
('user1','$2a$10$CBqO/7PkbI.oGvgozEIklumEsWiOUgWSD1PF./8fR9X376zSEgFPe'),('user2','123' ),('user3','123' ),
('user4','123' ),('user5','123') ,('user6','123' ),
('user7','123' ),('user8','123');

insert into summary (group_name,comments,is_show) values
('group1','comment1','Y'),
('group2','comment2','Y'),
('group3','comment3','Y'),
('group4','comment4','Y'),
('group1','comment5','Y'),
('group2','comment6','Y'),
('group3','comment7','Y'),
('group4','comment8','Y');


insert into user_group(user_name,group_name)values
('user1','group1'),
('user2','group1'),
('user3','group2'),
('user4','group2'),
('user5','group3'),
('user6','group3'),
('user7','group4'),
('user8','group4');
