create INDEX member_nickname_idx ON member (nickname);
create INDEX member_createdAt_idx ON member (created_at);
create INDEX preference_createdAt_idx ON preference (created_at);
create INDEX preference_memberId_rate_idx ON preference (member_id, rate);
create INDEX review_createdAt_idx ON review (created_at);
create INDEX drink_drinkName_idx ON drink (name);
create INDEX drink_preferenceAvg_idx ON drink (preference_avg);