INSERT INTO users (first_name, last_name, username, email_address, hashed_password)
VALUES 
('Relja', 'Aleksic', 'relja', 'relja@example.com', '$2a$12$examplehash1'),
('Ana', 'Petrovic', 'ana_p', 'ana.petrovic@example.com', '$2a$12$examplehash2'),
('Marko', 'Jovanovic', 'marko', 'marko.jovanovic@example.com', '$2a$12$examplehash3'),
('Jelena', 'Nikolic', 'jelena_n', 'jelena.nikolic@example.com', '$2a$12$examplehash4'),
('Stefan', 'Radic', 'stefan_r', 'stefan.radic@example.com', '$2a$12$examplehash5');

INSERT INTO projects (name, id_owner)
VALUES
('Website Redesign', 1),
('Mobile App Development', 2),
('Marketing Campaign', 3);

INSERT INTO task_lists (id_project, name, position, id_user_created)
VALUES
(1, 'TODO', 0, 1),
(1, 'In progress', 1, 1),
(1, 'Finished', 2, 1);

INSERT INTO tasks (id_task_list, title, body_text, position, checked, id_user_created)
VALUES
(1, 'Setup project structure', 'Create initial folders and configs', 0, 0, 1),
(1, 'Collect assets', 'Gather icons, images, and logos', 1, 0, 1),
(1, 'Implement navbar', 'Responsive navbar with dropdowns', 2, 0, 1),
(2, 'Cross-browser testing', 'Test layout in Chrome, Firefox, and Edge', 0, 0, 1),
(2, 'Fix CSS bugs', 'Resolve layout issues on mobile', 1, 0, 1),
(3, 'Define app requirements', 'Write MVP requirements for mobile app', 0, 0, 1);
