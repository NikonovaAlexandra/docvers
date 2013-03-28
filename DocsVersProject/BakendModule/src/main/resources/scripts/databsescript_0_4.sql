create user USER password 'DocumentVersioningUser';
grant select, insert, delete, update on document to user;
grant select, insert, delete, update on version to user;
grant select, insert on author to user;
grant  insert on deleted_versions to user;