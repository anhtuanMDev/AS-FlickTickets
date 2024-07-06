-- Ensure you are using the flickmovies database
USE flickmovies;

-- Create the movies table
CREATE TABLE movies (
    movie_id INT PRIMARY KEY IDENTITY(1,1),
    title VARCHAR(255) NOT NULL,
    descr VARCHAR(MAX),
	poster VARCHAR(100) NOT NULL,
    release_date DATE,
	duration INTEGER NOT NULL,
    rate INTEGER,
    trailer VARCHAR(500),
    banner VARCHAR(100)
);

-- Create the actors table
CREATE TABLE actors (
    id INT PRIMARY KEY IDENTITY(1,1),
    movie_id INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    image VARCHAR(100),
    inname_movie VARCHAR(255) NOT NULL,
    inimage_movie VARCHAR(100) NOT NULL,
    FOREIGN KEY (movie_id) REFERENCES movies(movie_id)
);


-- Create the genres table
CREATE TABLE genres (
    genre_id INT PRIMARY KEY IDENTITY(1,1),
    name VARCHAR(100) NOT NULL
);

-- Create the movie_genres table (many-to-many relationship between movies and genres)
CREATE TABLE movie_genres (
    movie_id INT NOT NULL,
    genre_id INT NOT NULL,
    PRIMARY KEY (movie_id, genre_id),
    FOREIGN KEY (movie_id) REFERENCES movies(movie_id),
    FOREIGN KEY (genre_id) REFERENCES genres(genre_id)
);

-- Create the users table (for user-related data)
CREATE TABLE users (
    user_id INT PRIMARY KEY IDENTITY(1,1),
    name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    phone VARCHAR(11),
    role VARCHAR(50) NOT NULL,
    avatar VARCHAR(100),
    create_at DATETIME DEFAULT GETDATE(),
    update_at DATETIME
);

-- Create table for user's favorite list
CREATE TABLE favorites (
    favorite_id INT PRIMARY KEY IDENTITY(1,1),
    movie_id INT NOT NULL,
    user_id INT NOT NULL,
    FOREIGN KEY (movie_id) REFERENCES movies(movie_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Create the reviews table (to store user reviews for movies)
CREATE TABLE reviews (
    review_id INT PRIMARY KEY IDENTITY(1,1),
    movie_id INT NOT NULL,
    user_id INT NOT NULL,
    review_text TEXT,
    rating INT NOT NULL,
    review_date DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (movie_id) REFERENCES movies(movie_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Create the cinemas table
CREATE TABLE cinemas (
    cinema_id INT PRIMARY KEY IDENTITY(1,1),
    name VARCHAR(255) NOT NULL,
    location VARCHAR(255) NOT NULL,
    image VARCHAR(100),
    descr VARCHAR(MAX)
);

-- Create the showtime table
CREATE TABLE showtime (
    showtime_id INT PRIMARY KEY IDENTITY(1,1),
    movie_id INT NOT NULL,
    cinema_id INT NOT NULL,
    start_time DATETIME NOT NULL,
    end_time DATETIME NOT NULL,
    FOREIGN KEY (movie_id) REFERENCES movies(movie_id),
    FOREIGN KEY (cinema_id) REFERENCES cinemas(cinema_id)
);

-- Create the seats table
CREATE TABLE seats (
    seat_id INT PRIMARY KEY IDENTITY(1,1),
    showtime_id INT NOT NULL,
    number VARCHAR(10) NOT NULL,
	row VARCHAR(10) NOT NULL,
	price DECIMAL(10, 2) NOT NULL,
    status INT DEFAULT 0,
    FOREIGN KEY (showtime_id) REFERENCES showtime(showtime_id)
);

-- Create the cart table
CREATE TABLE cart (
    cart_id INT PRIMARY KEY IDENTITY(1,1),
    user_id INT NOT NULL,
    status VARCHAR(10) NOT NULL,
    create_at DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Create the item table
CREATE TABLE item (
    item_id INT PRIMARY KEY IDENTITY(1,1),
    cart_id INT NOT NULL,
    movie_id INT NOT NULL,
    showtime_id INT NOT NULL,
    seat_id INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (cart_id) REFERENCES cart(cart_id),
    FOREIGN KEY (movie_id) REFERENCES movies(movie_id),
    FOREIGN KEY (showtime_id) REFERENCES showtime(showtime_id),
    FOREIGN KEY (seat_id) REFERENCES seats(seat_id)
);

