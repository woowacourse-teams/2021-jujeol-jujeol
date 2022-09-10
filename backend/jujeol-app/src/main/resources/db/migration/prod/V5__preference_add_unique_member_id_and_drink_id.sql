ALTER TABLE preference ADD constraint PREFERENCE_UNIQUE_DRINK_ID_MEMBER_ID UNIQUE (drink_id, member_id);
