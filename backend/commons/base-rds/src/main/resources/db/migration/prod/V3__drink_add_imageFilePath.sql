alter table drink drop image_file_path;
alter table drink add column small_image_file_path varchar(1000);
alter table drink add column medium_image_file_path varchar(1000);
alter table drink add column large_image_file_path varchar(1000);