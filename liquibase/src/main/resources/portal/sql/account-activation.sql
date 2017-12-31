CREATE TABLE activation_token(
    user_id INTEGER NOT NULL REFERENCES portal_user (id),
    token varchar(50),
    UNIQUE(user_id)
);
ALTER TABLE portal_user 
ADD activated boolean NOT NULL DEFAULT true;