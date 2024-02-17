
create table users( --entity
    user_id autoincrement primary key,
    username varchar(255) not null,
    password varchar(255) not null,
)

create table conversations( --entity
    conversation_id autoincrement primary key,
    number_of_users int not null,
)

create table participates_in( --relationship
    user_id int not null,
    conversation_id int not null,
    primary key(user_id, conversation_id),
    foreign key(user_id) references users(user_id),
    foreign key(conversation_id) references conversations(conversation_id)
)

create table messages( --entity
    message_id autoincrement primary key,
    conversation_id int not null,
    sender_user_id int not null,
    message_text text not null,
    sent_time timestamp not null,
)

create table message_read_by( --relationship
    message_id int not null,
    user_id int not null,
    primary key(message_id, user_id),
    foreign key(message_id) references messages(message_id),
    foreign key(user_id) references users(user_id)
)
