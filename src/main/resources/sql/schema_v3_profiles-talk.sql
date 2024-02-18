
create table users( --entity
    user_id autoincrement primary key,
    username varchar(255) not null,
    password varchar(255) not null,
)

create table profiles(
    -- a user can have multiple profiles
    profile_id autoincrement primary key,
    user_id int not null,

    profile_name varchar(255) not null,
    profile_username varchar(255) not null,
    profile_dp blob,
    foreign key(user_id) references users(user_id)
)

create table folders(
    -- each profile can have multiple folders
    folder_id autoincrement primary key,
    profile_id int not null,
    folder_name varchar(255) not null,
    foreign key(profile_id) references profiles(profile_id)
)

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

create table profile_talks_in( --relationship btw profile and conversations
    profile_id int not null,
    conversation_id int not null,
    primary key(user_id, conversation_id),
    foreign key(profile_id) references profiles(profile_id),
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
    profile_id int not null,
    primary key(message_id, profile_id),
    foreign key(message_id) references messages(message_id),
    foreign key(profile_id) references profiles(profile_id)
)
