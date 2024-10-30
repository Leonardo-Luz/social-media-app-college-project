CREATE DATABASE projeto;

CREATE TABLE User(
    userId SERIAL NOT NULL,
    name VARCHAR(100) NOT NULL,
    username VARCHAR(16) NOT NULL UNIQUE,
    password VARCHAR(16) NOT NULL,
    createdAt DATE DEFAULT(NOW()),
    updatedAt DATE DEFAULT(NOW()),
    CONSTRAINT pk_user PRIMARY KEY (userId)
    CONSTRAINT unique_username UNIQUE (username)
);

CREATE TABLE User_Friend(
    userId INT NOT NULL, 
    friendId INT NOT NULL, 
    createdAt DATE DEFALUT(NOW()), 
    updatedAt DATE DEFALUT(NOW()),
    CONSTRAINT pk_user_friend PRIMARY KEY (userId, friendId),
    CONSTRAINT fk_user_friend FOREIGN KEY (userId) REFERENCES user (userId)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT fk_friend_user FOREIGN KEY (friendId) REFERENCES user (userId)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
);

CREATE TABLE User_Block(
    userId INT NOT NULL, 
    blockId INT NOT NULL, 
    createdAt DATE DEFALUT(NOW()), 
    updatedAt DATE DEFALUT(NOW()),
    CONSTRAINT pk_user_block PRIMARY KEY (userId, blockId),
    CONSTRAINT fk_user_block FOREIGN KEY (userId) REFERENCES user (userId),
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT fk_block_user FOREIGN KEY (blockId) REFERENCES user (userId)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
);

CREATE TABLE Chat(
    chatId SERIAL NOT NULL, 
    title VARCHAR(12), 
    adminId INT NOT NULL, 
    createdAt DATE DEFALUT(NOW()), 
    updatedAt DATE DEFALUT(NOW()),
    CONSTRAINT pk_chat PRIMARY KEY (chatId),
    CONSTRAINT fk_chat_admin FOREIGN KEY (adminId) REFERENCES user (userId)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
);

CREATE TABLE Chat_User(
    chatId INT NOT NULL,
    userId INT NOT NULL, 
    createdAt DATE DEFALUT(NOW()), 
    updatedAt DATE DEFALUT(NOW()),
    CONSTRAINT pk_chat_user PRIMARY KEY (chatId, userId),
    CONSTRAINT fk_chat_user FOREIGN KEY (chatId) REFERENCES user (userId),
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT fk_user_chat FOREIGN KEY (userId) REFERENCES user (chatId)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
);

CREATE TABLE Message(
    messageId SERIAL NOT NULL, 
    text TEXT NOT NULL,
    userId INT,
    chatId INT,
    createdAt DATE DEFALUT(NOW()), 
    updatedAt DATE DEFALUT(NOW()),
    CONSTRAINT pk_message PRIMARY KEY (messageId),
    CONSTRAINT fk_message_user FOREIGN KEY (userId) REFERENCES user (userId),
        ON DELETE SET NULL
        ON UPDATE CASCADE,
    CONSTRAINT fk_message_chat FOREIGN KEY (chatId) REFERENCES chat (chatId)
        ON DELETE SET NULL
        ON UPDATE CASCADE,
);

INSERT INTO user (
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
    'blocked user',
    'blocked',
    'user'
),
(
    'friend user',
    'friend',
    'user'
);

INSERT INTO User_Block(
    userid,
    blockId
)
VALUE(
    1,
    2
);

INSERT INTO User_Friend(
    userid,
    friendId
)
VALUE(
    1,
    3
);


INSERT INTO chat(
    title, 
    adminid
)
VALUE(
    'global', 
    '0'
);

INSERT INTO Chat_User(
    chatid,
    userId
)
VALUES(
    0,
    0
),
(
    0,
    1
)

INSERT INTO message(
    text, 
    userid, 
    chatid
) 
VALUE(
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer nec mauris non orci porttitor laoreet. Vestibulum feugiat purus eu nunc malesuada dignissim.', 
    0, 
    0
);

