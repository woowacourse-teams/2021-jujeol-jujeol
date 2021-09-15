alter table drink drop imageFilePath;
alter table drink add column smallImageFilePath varchar(1000);
alter table drink add column mediumImageFilePath varchar(1000);
alter table drink add column largeImageFilePath varchar(1000);