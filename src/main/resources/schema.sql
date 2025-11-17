-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- Create Database Schema
Drop Database if exists appdb;
Create Database appdb;
USE appdb;

-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- Create Tables
-- -- This is the users table, this holds all the user information for both
-- -- admins and regular users.
-- UserId is the primary key.
-- Username is a unique identifier for each user.
-- Email is used for communication and must be unique.
-- EncryptedPassword stores the hashed password for security.
-- Enabled indicates whether the user account is active.
-- ProfilePictureURL stores the URL of the user's profile picture.

CREATE TABLE users (
                       UserID BIGINT AUTO_INCREMENT NOT NULL,
                       Username VARCHAR(50) NOT NULL,
                       Email VARCHAR(100) NOT NULL UNIQUE,
                       EncryptedPassword VARCHAR(255) NOT NULL,
                       Enabled BOOLEAN DEFAULT TRUE,
                       ProfilePictureURL TEXT,
                       PRIMARY KEY (UserID)
);

-- -- This is the roles table, this holds all the roles available in the system.
-- id is the primary key.
-- name is an ENUM that currently only includes 'ADMIN' role at the moment, but can
--  be changed if more roles/permissions are needed.

CREATE TABLE roles (
                       RoleID int NOT NULL AUTO_INCREMENT,
                       name ENUM('ADMIN', 'BASIC') NOT NULL UNIQUE,
                       PRIMARY KEY (RoleID)
);

-- -- This is the user_roles table, this holds the mapping between users and their
-- --  roles.
-- id is the primary key.
-- UserID is a foreign key referencing the users table.
-- RoleID is a foreign key referencing the roles table.

CREATE TABLE user_roles (
                            id bigint AUTO_INCREMENT NOT NULL,
                            UserID BIGINT NOT NULL,
                            RoleID int NOT NULL,
                            PRIMARY KEY (id),
                            FOREIGN KEY (UserID) REFERENCES users(UserID),
                            FOREIGN KEY (RoleID) REFERENCES roles(RoleID)
);

-- -- This is the games table, this holds all the information about the games
-- -- available in the system.
-- GameID is the primary key.
-- GameName is the name of the game.
-- Developer is the name of the game's developer.
-- AgeRating is the age rating of the game.
-- LinkToStore is the URL to the game's store page.
-- GameIconURL is the URL to the game's icon image.
-- GameDescription is a detailed description of the game.
-- ReleaseDate is the date the game was released.
-- VersionNumber is the current version of the game.
-- AboutVision, AboutHearing, AboutCognitive, AboutSpeech, AboutMotor are
--  accessibility information related to different disabilities.

CREATE TABLE games(
                      GameID BIGINT AUTO_INCREMENT NOT NULL,
                      GameName VARCHAR(200) NOT NULL,
                      Developer VARCHAR(100) NOT NULL,
                      AgeRating VARCHAR(10) NOT NULL,
                      LinkToStore TEXT NOT NULL,
                      GameIconURL TEXT NOT NULL,
                      GameDescription TEXT NOT NULL,
                      ReleaseDate DATE NOT NULL,
                      VersionNumber VARCHAR(30) NOT NULL,
                      AboutVision TEXT,
                      AboutHearing TEXT,
                      AboutCognitive TEXT,
                      AboutSpeech TEXT,
                      AboutMotor TEXT,
                      PRIMARY KEY (GameID)
);

-- -- This is the gallery table, this holds all the images related to different
-- --  games.
-- ImageID is the primary key.
-- GameID is a foreign key referencing the games table.
-- ImageURL is the URL of the image.
-- AltText is the alternative text for the image for accessibility.

CREATE TABLE gallery(
                        ImageID BIGINT AUTO_INCREMENT NOT NULL,
                        GameID BIGINT NOT NULL,
                        ImageURL TEXT NOT NULL,
                        AltText TEXT,
                        PRIMARY KEY (ImageID),
                        FOREIGN KEY (GameID) REFERENCES games(GameID)
);

-- -- This is the reviews table, this holds all the reviews made by users
-- -- for different games.
-- ReviewID is the primary key.
-- UserID is a foreign key referencing the users table.
-- GameID is a foreign key referencing the games table.
-- HearingRating, VisionRating, CognitiveRating, SpeechRating, MotorRating,
--  GameplayRating are integer ratings for different accessibility aspects and
--  overall gameplay.
-- ReviewText is the textual review provided by the user.
-- ReviewDate is the date and time when the review was made.
-- Flagged indicates whether the review has been flagged for review.
-- Removed indicates whether the review has been removed from public view.

CREATE TABLE reviews(
                        ReviewID BIGINT AUTO_INCREMENT NOT NULL,
                        UserID BIGINT NOT NULL,
                        GameID BIGINT NOT NULL,
                        HearingRating INT,
                        VisualRating INT,
                        CognitiveRating INT,
                        SpeechRating INT,
                        MotorRating INT,
                        GameplayRating INT,
                        ReviewText TEXT,
                        ReviewDate DATETIME DEFAULT CURRENT_TIMESTAMP,
                        Flagged BOOLEAN DEFAULT FALSE,
                        Removed BOOLEAN DEFAULT FALSE,
                        PRIMARY KEY (ReviewID),
                        FOREIGN KEY (UserID) REFERENCES users(UserID),
                        FOREIGN KEY (GameID) REFERENCES games(GameID)
);

-- -- This is the user_favorites table, this holds the mapping between users
-- --  and their favorite games.
-- id is the primary key.
-- UserID is a foreign key referencing the users table.
-- GameID is a foreign key referencing the games table.

CREATE TABLE user_favorites(
                               id BIGINT AUTO_INCREMENT NOT NULL,
                               UserID BIGINT NOT NULL,
                               GameID BIGINT NOT NULL,
                               PRIMARY KEY (id),
                               FOREIGN KEY (UserID) REFERENCES users(UserID),
                               FOREIGN KEY (GameID) REFERENCES games(GameID)
);

-- -- This is the tags table, this holds all the tags that can be associated
-- --  with games for the filtering system.
-- TagID is the primary key.
-- TagName is the name of the tag.
-- Type is an ENUM that specifies whether the tag is related to accessibility
--  or genre.

CREATE TABLE tags(
                     TagID INT AUTO_INCREMENT NOT NULL,
                     TagName VARCHAR(50) NOT NULL UNIQUE,
                     Type ENUM('ACCESSIBILITY', 'GENRE') NOT NULL,
                     PRIMARY KEY (TagID)
);

-- -- This is the game_tags table, this holds the mapping between games
-- --  and their associated tags.
-- id is the primary key.
-- GameID is a foreign key referencing the games table.
-- TagID is a foreign key referencing the tags table.

CREATE TABLE game_tags(
                          id BIGINT AUTO_INCREMENT NOT NULL,
                          GameID BIGINT NOT NULL,
                          TagID INT NOT NULL,
                          PRIMARY KEY (id),
                          FOREIGN KEY (GameID) REFERENCES games(GameID),
                          FOREIGN KEY (TagID) REFERENCES tags(TagID)
);

-- -- This is the discussions table, this holds all the discussion threads
-- -- created by users.
-- DiscussionID is the primary key.
-- UserID is a foreign key referencing the users table.
-- DiscussionTitle is the title of the discussion.
-- DateCreated is the date and time when the discussion was created.
-- DiscussionText is the main content of the discussion.

CREATE TABLE discussions(
                            DiscussionID BIGINT AUTO_INCREMENT NOT NULL,
                            UserID BIGINT NOT NULL,
                            DiscussionTitle VARCHAR(200) NOT NULL,
                            DateCreated DATETIME DEFAULT CURRENT_TIMESTAMP,
                            DiscussionText TEXT NOT NULL,
                            PRIMARY KEY (DiscussionID),
                            FOREIGN KEY (UserID) REFERENCES users(UserID)
);

-- -- This is the replies table, this holds all the replies made by users
-- -- to different discussion threads.
-- ReplyID is the primary key.
-- UserID is a foreign key referencing the users table.
-- DiscussionID is a foreign key referencing the discussions table.
-- ReplyText is the content of the reply.
-- DateCreated is the date and time when the reply was made.

CREATE TABLE replies(
                        ReplyID BIGINT AUTO_INCREMENT NOT NULL,
                        UserID BIGINT NOT NULL,
                        DiscussionID BIGINT NOT NULL,
                        ReplyText TEXT NOT NULL,
                        DateCreated DATETIME DEFAULT CURRENT_TIMESTAMP,
                        PRIMARY KEY (ReplyID),
                        FOREIGN KEY (UserID) REFERENCES users(UserID),
                        FOREIGN KEY (DiscussionID) REFERENCES discussions(DiscussionID)
);

-- -- This is the faq table, this holds all the frequently asked questions
-- -- and their answers.
-- FaqID is the primary key.
-- Question is the frequently asked question.
-- Answer is the answer to the frequently asked question.

CREATE TABLE faq(
                    FaqID BIGINT AUTO_INCREMENT NOT NULL,
                    Question TEXT NOT NULL,
                    Answer TEXT NOT NULL,
                    PRIMARY KEY (FaqID)
);

-- -- This is the blog table, this holds all the blog posts.
-- BlogId is the primary key.
-- BlogTitle is the title of the blog post.
-- DateCreated is the date and time when the blog post was created.
-- LinkToBlog is the URL to the blog post.
-- BlogDescription is a brief description of the blog post.
-- ReadTime is the estimated read time for the blog post in minutes.

CREATE TABLE Blog(
                     BlogId BIGINT AUTO_INCREMENT NOT NULL,
                     BlogTitle VARCHAR(200) NOT NULL,
                     DateCreated DATETIME DEFAULT CURRENT_TIMESTAMP,
                     LinkToBlog TEXT NOT NULL,
                     BlogDescription TEXT NOT NULL,
                     ReadTime INT NOT NULL,
                     PRIMARY KEY (BlogId)
);

-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- Create Views