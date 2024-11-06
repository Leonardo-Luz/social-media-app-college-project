CREATE DATABASE projeto;

CREATE TABLE Users(
    usersId SERIAL NOT NULL,
    name VARCHAR(100) NOT NULL,
    username VARCHAR(16) NOT NULL UNIQUE,
    password VARCHAR(16) NOT NULL,
    createdAt DATE DEFAULT(NOW()),
    updatedAt DATE DEFAULT(NOW()),
    CONSTRAINT pk_users PRIMARY KEY (usersId),
    CONSTRAINT unique_username UNIQUE (username)
);

CREATE TABLE Users_Friend(
    usersId INT NOT NULL, 
    friendId INT NOT NULL, 
    createdAt DATE DEFAULT(NOW()), 
    updatedAt DATE DEFAULT(NOW()),
    CONSTRAINT pk_users_friend PRIMARY KEY (usersId, friendId),
    CONSTRAINT fk_users_friend FOREIGN KEY (usersId) REFERENCES users (usersId)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT fk_friend_users FOREIGN KEY (friendId) REFERENCES users (usersId)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE Users_Block(
    usersId INT NOT NULL, 
    blockId INT NOT NULL, 
    createdAt DATE DEFAULT(NOW()), 
    updatedAt DATE DEFAULT(NOW()),
    CONSTRAINT pk_users_block PRIMARY KEY (usersId, blockId),
    CONSTRAINT fk_users_block FOREIGN KEY (usersId) REFERENCES users (usersId)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT fk_block_users FOREIGN KEY (blockId) REFERENCES users (usersId)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE Chat(
    chatId SERIAL NOT NULL, 
    title VARCHAR(12), 
    adminId INT NOT NULL, 
    createdAt DATE DEFAULT(NOW()), 
    updatedAt DATE DEFAULT(NOW()),
    CONSTRAINT pk_chat PRIMARY KEY (chatId),
    CONSTRAINT fk_chat_admin FOREIGN KEY (adminId) REFERENCES users (usersId)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE Chat_Users(
    chatId INT NOT NULL,
    usersId INT NOT NULL, 
    createdAt DATE DEFAULT(NOW()), 
    updatedAt DATE DEFAULT(NOW()),
    CONSTRAINT pk_chat_users PRIMARY KEY (chatId, usersId),
    CONSTRAINT fk_chat_users FOREIGN KEY (chatId) REFERENCES chat (chatId)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT fk_users_chat FOREIGN KEY (usersId) REFERENCES users (usersId)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE Message(
    messageId SERIAL NOT NULL, 
    text TEXT NOT NULL,
    usersId INT,
    chatId INT,
    createdAt DATE DEFAULT(NOW()), 
    updatedAt DATE DEFAULT(NOW()),
    CONSTRAINT pk_message PRIMARY KEY (messageId),
    CONSTRAINT fk_message_users FOREIGN KEY (usersId) REFERENCES users (usersId)
        ON DELETE SET NULL
        ON UPDATE CASCADE,
    CONSTRAINT fk_message_chat FOREIGN KEY (chatId) REFERENCES chat (chatId)
        ON DELETE SET NULL
        ON UPDATE CASCADE
);

INSERT INTO users (
    name, 
    username, 
    password
)
VALUES(
    'admin', 
    'admin', 
    'admin'
),
(
    'José Almeida', 
    'robert', 
    'carlos'
),
(
    'blocked users',
    'blocked',
    'users'
),
(
    'friend users',
    'friend',
    'users'
);

INSERT INTO Users_Block(
    usersid,
    blockId
)
VALUES(
    1,
    2
);

INSERT INTO Users_Friend(
    usersid,
    friendId
)
VALUES(
    1,
    3
);


INSERT INTO chat(
    title, 
    adminid
)
VALUES(
    'global', 
    1
);

INSERT INTO Chat_Users(
    chatid,
    usersId
)
VALUES(
    1,
    1
),
(
    1,
    2
);

INSERT INTO message(
    text, 
    usersid, 
    chatid
) 
VALUES(
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer nec mauris non orci porttitor laoreet. Vestibulum feugiat purus eu nunc malesuada dignissim.', 
    1, 
    1
);
