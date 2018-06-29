DROP SEQUENCE IF EXISTS id_seq;

CREATE SEQUENCE id_seq
  INCREMENT 1
  START 1000
CACHE 5;

INSERT INTO todo_user (id, username, password, role, enabled) VALUES
  (1, 't', '$2a$06$WIaZCw/jJXAJ8pvhvSYDS.1jDBvvSRgBMKjgBjA/M0T2wYtpD4MmC', 'ROLE_GUEST', true), /*pwd: t*/
  (2, 'user', '$2a$06$zcmu8ZWRx5vnaQC30t3pJ.O3i2wvp7uNf97yzsWItxoi7MCpictKK', 'ROLE_USER', true), /*pwd: user*/
  (3, 'admin', '$2a$06$IQACzDoIe0xUAupeycYrteu432CGaA7YCe078s2aSTSaOuMjaBUOC', 'ROLE_ADMIN', true); /*pwd: admin*/

INSERT INTO todo (id, date, creation_date, description, user_id,deletestatus) VALUES
  (1, {ts '2017-09-27'}, null, 'Wyscie w piątek', 2, false),
  (2, {ts '2017-08-17'}, null, 'Dentysta', 2, false),
  (3, {ts '2017-10-09'}, null, 'Wycieczka nad morze', 3, false),
  (4, {ts '2017-10-09'}, null, 'Wycieczka nad morze', 3, false),
  (5, {ts '2017-10-09'}, null, 'Wycieczka nad morze', 3, false),
  (6, {ts '2019-10-09'}, null, 'Wycieczka nad morze', 3, false);

INSERT INTO oauthuser (id, username, password, user_id) VALUES
  (1, 'oauthuser', '$2a$06$jqPmImEpUGNq3yIb9T17yOT.kwd4tW1mQSHwVFLKJbS3CWMkteOj2', 3), /*pwd: oauthuser*/
  (2, 'oauthuser1', '$2a$06$jqPmImEpUGNq3yIb9T17yOT.kwd4tW1mQSHwVFLKJbS3CWMkteOj2', 2), /*pwd: oauthuser*/
  (3, 'oauthuser2', '$2a$06$jqPmImEpUGNq3yIb9T17yOT.kwd4tW1mQSHwVFLKJbS3CWMkteOj2', 1); /*pwd: oauthuser*/




