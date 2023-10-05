insert into roles(role_name) values ('ROLE_CUSTOMER'), ('ROLE_EMPLOYEE'), ('ROLE_ADMIN');


insert into users(username, password)
values  ('maarten', '$2a$12$9qIobewTpo9mny6mN9duA.gSt3FAcy8BTJFivYfLzYQP.XKAcjuk.'),
        ('luc', '$2a$12$crYfT.yxfCbjUy4SURk67.Ws2cj0ipC8GbQly5uqb9InkZ.8RcqFe'),
        ('bas', '$2a$12$oQvR9.Kzmj/.dGimRungfu.FLQArKBux1txBHNuejDY3IEit.kXNG'),
        ('marieke', '$2a$12$2gFikZJoTVWRQjvMbukvmeNdqBzdAU3Uu7NJBYmT3oAy/8PCXJD1C')


insert into users_roles(username, roleName)
values  ('maarten', 'ROLE_ADMIN'),
        ('luc', 'ROLE_EMPLOYEE'),
        ('bas', 'ROLE_CUSTOMER'),
        ('marieke', 'ROLE_CUSTOMER')

insert into cars(licensePlate, carBrand, carModel, carType, carColor, carStatus, userName)
values  ('XK-783-P', 'Volkswagen', 'T-roc', 'CarType.HATCHBACK', 'blue', 'CarStatus.CHECKED_IN', 'bas'),
        ('XX-781-G', 'Ford', 'Ecosport', 'CarType.HATCHBACK', 'red', 'CarStatus.MOT', 'marieke')
