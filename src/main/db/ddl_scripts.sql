CREATE TABLE users(
	id BIGINT NOT NULL AUTO_INCREMENT,
	name VARCHAR(40) NOT NULL,
  password VARCHAR(60) NOT NULL,
	created_date DATETIME NOT NULL,
	CONSTRAINT user_pk PRIMARY KEY (id),
	CONSTRAINT user_name_uk UNIQUE KEY (name)
);

CREATE TABLE domains(
	id BIGINT NOT NULL AUTO_INCREMENT,
	name VARCHAR(40) NOT NULL,
	CONSTRAINT domain_pk PRIMARY KEY (id),
	CONSTRAINT domain_name_uk UNIQUE KEY (name)
);

CREATE TABLE user_domains(
  id BIGINT NOT NULL AUTO_INCREMENT,
	user_id BIGINT NOT NULL,
	domain_id BIGINT NOT NULL,
	CONSTRAINT user_domain_pk PRIMARY KEY (id),
	CONSTRAINT user_domain_user_fk FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT user_domain_domain_fk FOREIGN KEY (domain_id) REFERENCES domains(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE authorities(
	id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(60) NOT NULL,
	CONSTRAINT authority_pk PRIMARY KEY (id),
	CONSTRAINT authority_name_uk UNIQUE KEY (name)
);

CREATE TABLE user_authorities(
  id BIGINT NOT NULL AUTO_INCREMENT,
	user_id BIGINT NOT NULL,
	authority_id INT NOT NULL,
	CONSTRAINT user_authority_pk PRIMARY KEY (id),
	CONSTRAINT user_authority_user_fk FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT user_authority_authority_fk FOREIGN KEY (authority_id) REFERENCES authorities(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE categories(
	id BIGINT NOT NULL AUTO_INCREMENT,
	name VARCHAR(60) NOT NULL,
	parent_id BIGINT,
	domain_id BIGINT NOT NULL,
	CONSTRAINT category_pk PRIMARY KEY (id),
	CONSTRAINT category_category_fk FOREIGN KEY (parent_id) REFERENCES categories(id) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT category_domain_fk FOREIGN KEY (domain_id) REFERENCES domains(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE expenses(
	id BIGINT NOT NULL AUTO_INCREMENT,
	name VARCHAR(60) NOT NULL,
	category_id BIGINT NOT NULL,
	CONSTRAINT expense_pk PRIMARY KEY (id),
	CONSTRAINT expense_category_fk FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE expense_details(
	id BIGINT NOT NULL AUTO_INCREMENT,
	expense_id BIGINT NOT NULL,
	full_price DECIMAL(19, 4),
	quantity DECIMAL(19, 4),
	unit VARCHAR(10),
	price_per_unit DECIMAL(19, 4),
	pay_date DATE NOT NULL,
	CONSTRAINT expense_detail_pk PRIMARY KEY (id),
	CONSTRAINT expense_detail_expense_fk FOREIGN KEY (expense_id) REFERENCES expenses(id) ON DELETE CASCADE ON UPDATE CASCADE
);
