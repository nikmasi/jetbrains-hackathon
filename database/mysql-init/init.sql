CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    username VARCHAR(100) NOT NULL UNIQUE,
    email_address VARCHAR(255) NOT NULL UNIQUE,
    hashed_password VARCHAR(255) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE projects (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    id_owner INT NOT NULL,
    time_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    time_last_change TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_project_owner
        FOREIGN KEY (id_owner) REFERENCES users(id)
        ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE project_users (
    id_project INT NOT NULL,
    id_user INT NOT NULL,
    time_joined TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (id_project, id_user),

    CONSTRAINT fk_pu_project
        FOREIGN KEY (id_project) REFERENCES projects(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_pu_user
        FOREIGN KEY (id_user) REFERENCES users(id)
        ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE project_invites (
    id_project INT NOT NULL,
    id_user INT NOT NULL,
    time_sent TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (id_project, id_user),

    CONSTRAINT fk_pi_project
        FOREIGN KEY (id_project) REFERENCES projects(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_pi_user
        FOREIGN KEY (id_user) REFERENCES users(id)
        ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE task_lists (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_project INT NOT NULL,
    name VARCHAR(255) DEFAULT '',
    position INT NOT NULL DEFAULT 0,
    time_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	id_user_created INT,

    CONSTRAINT fk_tl_project
        FOREIGN KEY (id_project) REFERENCES projects(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_tl_user
        FOREIGN KEY (id_user_created) REFERENCES users(id)
        ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE tasks (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_task_list INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    body_text VARCHAR(1000) DEFAULT '',
    position INT NOT NULL DEFAULT 0,
	checked INT DEFAULT 0,
    time_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    time_changed TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	id_user_created INT,

    CONSTRAINT fk_task_list
        FOREIGN KEY (id_task_list) REFERENCES task_lists(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_task_user
        FOREIGN KEY (id_user_created) REFERENCES users(id)
        ON DELETE CASCADE
) ENGINE=InnoDB;
