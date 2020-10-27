INSERT INTO currency (code) VALUES
  ('EUR'),
  ('USD');

INSERT INTO account (name, currency_id, treasury) VALUES
  ('cuenta A', 1, FALSE),
  ('cuenta B', 2, TRUE),
  ('cuenta C', 1, FALSE);
  
INSERT INTO money (account_id, balance) VALUES
  (1, 100),
  (2, 50.42),
  (3, 150);