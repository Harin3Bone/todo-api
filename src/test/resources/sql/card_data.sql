-- Get Script
INSERT INTO todo.card (id, topic, content, priority, status, remove_status, created_timestamp, modified_timestamp, completed_timestamp) VALUES
('21459006-5f5e-48fa-a1e8-44b6e688d1af', 'GET Card A', 'GET Content', 0, 'todo', 0, '2022-10-29 02:28:19', '2022-10-29 14:48:04', null),
('2f198178-f865-40fb-9483-7b4b55a3f0de', 'GET Card B', 'GET Content', 1, 'todo', 0, '2022-10-29 21:26:27', '2022-10-30 19:47:49', null),
('328fc0ca-7115-4425-bf6c-0ea0808f39b7', 'GET Card C', 'GET Content', 2, 'todo', 0, '2022-10-29 21:26:27', '2022-10-30 19:47:49', null),
('7217b014-d190-424b-896e-9302dbd7434c', 'GET Card D', 'GET Content', 3, 'in_progress', 0, '2022-11-08 21:23:53', '2022-11-08 21:23:53', null),
('c401cab4-f7c8-4586-adc8-20b411f0d72f', 'GET Card E', 'GET Content', 3, 'in_progress', 0, '2022-11-08 21:23:53', '2022-11-08 21:23:53', null),
('d754ab56-0e62-4587-b8af-14a71afe0cb9', 'GET Card F', 'GET Content', 3, 'in_progress', 1, '2022-10-29 15:43:00', '2022-10-29 15:43:00', null),
('fe4fa930-57e3-4ef6-b76b-2e9fd949ff2b', 'GET Card G', 'GET Content', 5, 'done', 1, '2022-10-29 02:29:55', '2022-10-29 14:48:04', '2022-10-31 17:30:14');

-- Put Script
INSERT INTO todo.card (id, topic, content, priority, status, remove_status, created_timestamp, modified_timestamp, completed_timestamp) VALUES
('62ef0d73-370c-4559-a65a-d17c925a1675', 'PUT Card A', 'PUT Content', 0, 'in_progress', 0, '2022-11-08 21:23:53', '2022-11-08 21:23:53', null),
('5ebad87d-da81-4880-89f4-79c3ee33bc83', 'PUT Card B', 'PUT Content', 3, 'in_progress', 1, '2022-11-08 21:23:53', '2022-11-08 21:23:53', null),
('b7e1db33-e8b5-41cd-b6ac-f6835d76ef04', 'PUT Card C', 'PUT Content', 5, 'done', 0, '2022-10-29 15:43:00', '2022-10-29 15:43:00', '2022-11-01 18:00:10');

-- Delete Script
INSERT INTO todo.card (id, topic, content, priority, status, remove_status, created_timestamp, modified_timestamp, completed_timestamp) VALUES
('70a6a450-14cf-4f73-84ea-091343d500e2', 'DELETE Card A', 'DELETE Content', 5, 'done', 0, '2022-10-29 14:22:28', '2022-10-29 14:48:04', '2022-11-01 18:00:10'),
('8435d308-ffbc-419b-b8d3-45ffe56d032a', 'DELETE Card B', 'DELETE Content', 3, 'todo', 1, '2022-10-29 14:22:28', '2022-10-29 14:48:04', null);
