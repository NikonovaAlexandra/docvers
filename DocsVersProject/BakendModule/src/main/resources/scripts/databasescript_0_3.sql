alter table version add column IS_RELEASED boolean;
alter table version add column version_name varchar(255);
alter table version add column version_type varchar(5);
alter table document add column code_Document_Name bigint;