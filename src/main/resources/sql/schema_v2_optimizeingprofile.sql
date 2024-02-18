-- This seems to be a bad idea.
create table users( --entity
    user_id autoincrement primary key,
    username varchar(255) not null,
    password varchar(255) not null,
)

create table folders(
    -- each profile can have multiple folders
    folder_id autoincrement primary key,
    profile_id int not null,
    folder_name varchar(255) not null,
)
-- This seems to be a bad idea.
create table profiles( -- relationship btw user and folder
    user_id int not null,
    folder_id int not null,
    profile_name varchar(255) not null,
    foreign key(user_id) references users(user_id)
    foreign key(folder_id) references folders(folder_id)
)
-- This seems to be a bad idea.-- This seems to be a bad idea.-- This seems to be a bad idea.-- This seems to be a bad idea.-- This seems to be a bad idea.
-- as of now...


create table conversations( --entity
    conversation_id autoincrement primary key,
--     folder_id int not null, -- a conversation can be in only one folder
    number_of_users int not null,
)

-- If you wish to be able to have a conversation in multiple folders...
create table conversation_folders( --relationship
    conversation_id int not null,
    folder_id       int not null,
    primary key (conversation_id, folder_id),
    foreign key (conversation_id) references conversations (conversation_id),
    foreign key (folder_id) references folders (folder_id)
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