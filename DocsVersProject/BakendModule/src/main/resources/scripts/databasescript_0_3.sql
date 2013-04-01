alter table version add column IS_RELEASED boolean NOT NULL;
alter table version add column version_name bigint NOT NULL;
alter table version add column version_type varchar(5) NOT NULL;
alter table document add column code_Document_Name bigint NOT NULL;