CREATE DATABASE IF NOT EXISTS Finalsdb;
USE Finalsdb;

CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL
);

INSERT INTO users (id, username, password)
VALUES (1, 'marlou', '123');

CREATE TABLE IF NOT EXISTS body_scan_prompts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    prompt_text TEXT NOT NULL
);

INSERT INTO body_scan_prompts (prompt_text)
VALUES
    ('Focus on your head, Relax and let go of the tension.'),
    ('Move your attention to your shoulders. Feel them relaxing and softening.'),
    ('Now focus on your arms. Release any tightness in your forearms and hands.'),
    ('Shift your attention to your chest. Feel your breath flowing freely and deeply.'),
    ('Focus on your abdomen. Let go of any discomfort and feel it softening.');

CREATE TABLE IF NOT EXISTS visualization_prompts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    prompt_text TEXT NOT NULL
);

INSERT INTO visualization_prompts (prompt_text)
VALUES
    ('Visualize a calm and peaceful beach. Hear the gentle sound of the waves.'),
    ('Now imagine yourself walking along the beach, feeling the warm sand under your feet.'),
    ('Visualize the sun setting on the horizon, the colors of the sky changing beautifully.'),
    ('Imagine the cool breeze brushing against your face, calming and relaxing you.'),
    ('Visualize yourself achieving a personal goal, feeling proud and fulfilled.');
