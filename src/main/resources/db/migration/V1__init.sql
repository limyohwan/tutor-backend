create table teacher (
   teacher_id  bigserial not null,
   memo varchar(255),
   name varchar(255),
   primary key (teacher_id)
);

create table student (
   student_id  bigserial not null,
   name varchar(255),
   primary key (student_id)
);

create table student_teacher (
   student_id int8 not null,
   teacher_id int8 not null,
   primary key (student_id, teacher_id),
   foreign key (student_id) references student,
   foreign key (teacher_id) references teacher
);

create table groups (
   group_id  bigserial not null,
   name varchar(255),
   standard_description varchar(255),
   primary key (group_id)
);


create table member (
   member_id  bigserial not null,
   group_id int8,
   student_id int8,
   primary key (member_id),
   foreign key (group_id) references groups,
   foreign key (student_id) references student,
   constraint member_uk unique (group_id, student_id)
);

create table question (
   question_id  bigserial not null,
   description varchar(255),
   level int4 not null,
   primary key (question_id)
);

create table recommend_question (
   recommend_question_id  bigserial not null,
   group_id int8,
   question_id int8,
   primary key (recommend_question_id),
   foreign key (group_id) references groups,
   foreign key (question_id) references question,
   constraint recommend_question_uk unique (group_id, question_id)
);

create table answer (
   answer_id  bigserial not null,
   is_correct boolean not null,
   time int4 not null,
   member_id int8,
   question_id int8,
   recommend_question_id int8,
   student_id int8,
   primary key (answer_id),
   foreign key (member_id) references member,
   foreign key (question_id) references question,
   foreign key (recommend_question_id) references recommend_question,
   foreign key (student_id) references student
);
