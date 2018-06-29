CREATE TABLE user
(
  id                   BIGINT NOT NULL ,
  username             VARCHAR(255),
  password             VARCHAR(255),
  role                 VARCHAR(255),
  oauth_user           BIGINT,
  enabled              BOOLEAN NOT NULL ,
  CONSTRAINT user_pkey PRIMARY KEY (id)
);
CREATE TABLE user1
(
  username             VARCHAR(255)
);
CREATE TABLE todo
(
  id                    BIGINT NOT NULL,
  date               TIMESTAMP NOT NULL,
  creationDate                TIMESTAMP,
  description              VARCHAR(255),
  user_id                        BIGINT,
  image_id                       BIGINT,
  CONSTRAINT todo_pkey PRIMARY KEY (id),
  CONSTRAINT user_fk FOREIGN KEY (user_id)
  REFERENCES user (id),
  CONSTRAINT image_fk FOREIGN KEY (image_id)
  REFERENCES image_img (id)
  ON UPDATE NO ACTION
  ON DELETE NO ACTION
);

CREATE TABLE oauthuser
(
  id                         BIGINT NOT NULL,
  username                      VARCHAR(255),
  password                      VARCHAR(255),
  user_id                             BIGINT,
  CONSTRAINT oauthuser_pkey PRIMARY KEY (id),
  CONSTRAINT user_fk1 FOREIGN KEY (user_id)
  REFERENCES user (id)
  ON UPDATE NO ACTION
  ON DELETE NO ACTION
);

CREATE TABLE image_img(
  id                         BIGINT NOT NULL,
  image                                  OID,
  CONSTRAINT image_pkey PRIMARY KEY (id),


);