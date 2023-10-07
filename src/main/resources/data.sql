insert into roles(role_name) values ('ROLE_CUSTOMER'), ('ROLE_EMPLOYEE'), ('ROLE_ADMIN');


insert into users(username, password)
values  ('maarten', '$2a$12$9qIobewTpo9mny6mN9duA.gSt3FAcy8BTJFivYfLzYQP.XKAcjuk.'),
        ('luc', '$2a$12$crYfT.yxfCbjUy4SURk67.Ws2cj0ipC8GbQly5uqb9InkZ.8RcqFe'),
        ('bas', '$2a$12$oQvR9.Kzmj/.dGimRungfu.FLQArKBux1txBHNuejDY3IEit.kXNG'),
        ('marieke', '$2a$12$2gFikZJoTVWRQjvMbukvmeNdqBzdAU3Uu7NJBYmT3oAy/8PCXJD1C');


insert into users_roles(users_username, roles_role_name)
values  ('maarten', 'ROLE_ADMIN'),
        ('luc', 'ROLE_EMPLOYEE'),
        ('bas', 'ROLE_CUSTOMER'),
        ('marieke', 'ROLE_CUSTOMER');

insert into cars(license_plate, car_brand, car_model, car_type, car_color, car_status, user_username)
values  ('XK-783-P', 'Volkswagen', 'T-roc', 'HATCHBACK', 'blue', 'CHECKED_IN', 'bas'),
        ('XX-781-G', 'Ford', 'Ecosport', 'HATCHBACK', 'red', 'MOT', 'marieke');
