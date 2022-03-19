create table t_coin_notification_alarm (
id INTEGER PRIMARY KEY AUTOINCREMENT, --主键 自增id
hash_code varchar(256) not null, --通知内容hash code
alarm_type varchar(20) not null, --通知类型
alarm_content text, -- 通知内容
alarm_users text, -- 通知用户
create_date date -- 创建时间
);
create index hash_index on t_coin_notification_alarm(hash_code);