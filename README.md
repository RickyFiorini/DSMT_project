ALTER TABLE listing MODIFY winner varchar(32) NULL;

ALTER TABLE listing
    DROP COLUMN status_listing;

ALTER TABLE box
    ADD COLUMN listed BOOLEAN DEFAULT FALSE;



ALTER TABLE offer
    DROP COLUMN checked;


ALTER TABLE user
    DROP COLUMN online_flag;
