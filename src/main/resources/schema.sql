CREATE TABLE IF NOT EXISTS recipes (
  id SERIAL PRIMARY KEY,
  title VARCHAR(150) NOT NULL,
  description VARCHAR(300),
  instructions JSONB DEFAULT '{}',
  rating NUMERIC(3,2) CHECK (rating >= 0.0 AND rating <= 5.0),
  image TEXT,
  duration INT NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS  ingredients (
  id SERIAL PRIMARY KEY,
  name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS  recipes_ingredients (
  ingredient_id INT NOT NULL REFERENCES ingredients(id),
  recipe_id INT NOT NULL REFERENCES recipes(id),
  PRIMARY KEY (ingredient_id, recipe_id)
);

CREATE TABLE IF NOT EXISTS  types (
  id SERIAL PRIMARY KEY,
  name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS  tags (
  id SERIAL PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  type_id INT NOT NULL REFERENCES types(id)
);

CREATE TABLE IF NOT EXISTS  recipes_tags (
  recipe_id INT NOT NULL REFERENCES recipes(id),
  tag_id INT NOT NULL REFERENCES tags(id),
  PRIMARY KEY (recipe_id, tag_id)
);

CREATE TABLE IF NOT EXISTS  categories (
  id SERIAL PRIMARY KEY,
  name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS  ingredients_categories (
  ingredient_id INT NOT NULL REFERENCES ingredients(id),
  category_id INT NOT NULL REFERENCES categories(id),
  PRIMARY KEY (ingredient_id, category_id)
);

CREATE TABLE IF NOT EXISTS  users (
  id SERIAL PRIMARY KEY,
  username VARCHAR(30) NOT NULL UNIQUE,
  email VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(100) NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  profile_picture TEXT
);

CREATE TABLE IF NOT EXISTS  reviews (
  id SERIAL PRIMARY KEY,
  user_id INT NOT NULL REFERENCES users(id),
  recipe_id INT NOT NULL REFERENCES recipes(id),
  rating INT NOT NULL CHECK (rating >= 1 AND rating <= 5),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  review_text VARCHAR(150)
);