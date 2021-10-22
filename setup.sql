-- Drop tables if they already exist.
DROP TABLE IF EXISTS address CASCADE;
DROP TABLE IF EXISTS employee CASCADE;
DROP TABLE IF exists "role" CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS determination_type CASCADE;
DROP TABLE IF EXISTS ticket CASCADE;
DROP TABLE IF EXISTS ticket_determination CASCADE;

-- Create the new tables.
CREATE TABLE address (
	id serial4 NOT NULL,
	firstline varchar(32) NOT NULL,
	secondline varchar(32) NULL,
	city varchar(32) NULL,
	state varchar(2) NULL,
	zip varchar(5) NULL,
	CONSTRAINT address_pkey PRIMARY KEY (id)
);


CREATE TABLE employee (
	id serial4 NOT NULL,
	fname varchar(32) NOT NULL,
	lname varchar(32) NOT NULL,
	gender varchar(16) NOT NULL,
	address_id int4 NOT NULL,
	CONSTRAINT employee_pkey PRIMARY KEY (id),
	CONSTRAINT employee_address_id FOREIGN KEY (address_id) REFERENCES address(id)
);


CREATE TABLE "role" (
	id serial4 NOT NULL,
	"name" varchar(32) NOT NULL,
	CONSTRAINT role_pkey PRIMARY KEY (id)
);


CREATE TABLE users (
	id serial4 NOT NULL,
	employee_id int4 NOT NULL,
	role_id int4 NOT NULL,
	username varchar(32) NULL,
	"password" varchar(64) NULL,
	CONSTRAINT user_pkey PRIMARY KEY (id),
	CONSTRAINT user_employee_id FOREIGN KEY (employee_id) REFERENCES employee(id),
	CONSTRAINT user_role_id FOREIGN KEY (role_id) REFERENCES "role"(id)
);


CREATE TABLE determination_type (
	id serial4 NOT NULL,
	"name" varchar(32) NOT NULL,
	CONSTRAINT determination_type_pkey PRIMARY KEY (id)
);

CREATE TABLE ticket_determination (
	id serial4 NOT NULL,
	determination_type int4 NOT NULL DEFAULT 0,
	manager_id int4 NOT NULL,
	reimbursement_amount numeric NULL,
	CONSTRAINT ticket_determination_pkey PRIMARY KEY (id)
);

CREATE TABLE ticket (
	id serial4 NOT NULL,
	owner_id int4 NOT NULL,
	subject varchar(128) NOT NULL,
	description text NULL,
	determination_id int4 NULL,
	CONSTRAINT ticket_pkey PRIMARY KEY (id),
	CONSTRAINT ticket_determination_id FOREIGN KEY (determination_id) REFERENCES ticket_determination(id),
	CONSTRAINT ticket_user_owner_id FOREIGN KEY (owner_id) REFERENCES users(id)
);

-- Instantiate default table values.
insert into "role" values (1, 'Employee'), (2, 'Manager');
insert into determination_type values (-1, 'ALL'), (0, 'PENDING'), (1, 'REJECTED'), (2, 'APPROVED');
insert into address values (1, 'lineOne', 'lineTwo', 'city', 'ST', 'zip');
insert into employee values (1, 'Administrator', 'Admin', 'N/A', 1);
insert into users values (1, 1, 2, 'admin', '$2a$10$JzJkwNQS01VvedCPJRqOQunI8m9gSxSv05K.v5i4xyQkoj6FL47KC');