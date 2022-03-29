-- create tables
create table if not exists employee
(
    id       int auto_increment primary key,
    email varchar(50) null unique ,
    username  varchar(50) null,
    is_manager  tinyint  default 0
);

create table if not exists timetrack
(
    id       int auto_increment primary key,
    trackdate date,
    punchin   datetime,
    punchout datetime,
    employee_id int ,
    FOREIGN KEY (employee_id) REFERENCES employee(id)
);
