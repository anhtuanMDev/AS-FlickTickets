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
    name VARCHAR(100) PRIMARY KEY NOT NULL
);

-- Create the movie_genres table (many-to-many relationship between movies and genres)
CREATE TABLE movie_genres (
    movie_id INT NOT NULL,
    genre_id INT NOT NULL,
    PRIMARY KEY (movie_id, genre_id),
    FOREIGN KEY (movie_id) REFERENCES movies(movie_id),
    FOREIGN KEY (genre_id) REFERENCES genres(name)
);

-- Create the users table (for user-related data)
CREATE TABLE users (
    user_id INT PRIMARY KEY IDENTITY(1,1),
    name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE,
    phone VARCHAR(11) UNIQUE,
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

INSERT INTO movies (title, descr, poster, release_date, duration, rate, trailer, banner)
VALUES 
('The Shawshank Redemption', 'Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.', 'shawshank.jpg', '1994-09-23', 142, 90, 'shawshank_trailer.mp4', 'shawshank_banner.jpg'),
('The Godfather', 'The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.', 'godfather.jpg', '1972-03-24', 175, 90, 'godfather_trailer.mp4', 'godfather_banner.jpg'),
('The Dark Knight', 'When the menace known as the Joker emerges from his mysterious past, he wreaks havoc and chaos on the people of Gotham.', 'dark_knight.jpg', '2008-07-18', 152, 90, 'dark_knight_trailer.mp4', 'dark_knight_banner.jpg'),
('Pulp Fiction', 'The lives of two mob hitmen, a boxer, a gangster and his wife intertwine in four tales of violence and redemption.', 'pulp_fiction.jpg', '1994-10-14', 154, 90, 'pulp_fiction_trailer.mp4', 'pulp_fiction_banner.jpg'),
('Forrest Gump', 'The presidencies of Kennedy and Johnson, the Vietnam War, the Watergate scandal and other historical events unfold from the perspective of an Alabama man with an IQ of 75.', 'forrest_gump.jpg', '1994-07-06', 142, 90, 'forrest_gump_trailer.mp4', 'forrest_gump_banner.jpg'),
('Inception', 'A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O.', 'inception.jpg', '2010-07-16', 148, 80, 'inception_trailer.mp4', 'inception_banner.jpg'),
('Fight Club', 'An insomniac office worker and a devil-may-care soap maker form an underground fight club that evolves into much more.', 'fight_club.jpg', '1999-10-15', 139, 80, 'fight_club_trailer.mp4', 'fight_club_banner.jpg'),
('The Matrix', 'A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.', 'matrix.jpg', '1999-03-31', 136, 90, 'matrix_trailer.mp4', 'matrix_banner.jpg'),
('Goodfellas', 'The story of Henry Hill and his life in the mob, covering his relationship with his wife Karen Hill and his mob partners Jimmy Conway and Tommy DeVito.', 'goodfellas.jpg', '1990-09-19', 146, 90, 'goodfellas_trailer.mp4', 'goodfellas_banner.jpg'),
('The Lord of the Rings: The Return of the King', 'Gandalf and Aragorn lead the World of Men against Sauron''s army to draw his gaze from Frodo and Sam as they approach Mount Doom with the One Ring.', 'lotr_return_king.jpg', '2003-12-17', 201, 90, 'lotr_return_king_trailer.mp4', 'lotr_return_king_banner.jpg'),
('Star Wars: Episode V - The Empire Strikes Back', 'After the rebels are brutally overpowered by the Empire on the ice planet Hoth, Luke Skywalker begins Jedi training with Yoda.', 'empire_strikes_back.jpg', '1980-05-21', 124, 90, 'empire_strikes_back_trailer.mp4', 'empire_strikes_back_banner.jpg'),
('The Silence of the Lambs', 'A young F.B.I. cadet must receive the help of an incarcerated and manipulative cannibal killer to help catch another serial killer.', 'silence_lambs.jpg', '1991-02-14', 118, 90, 'silence_lambs_trailer.mp4', 'silence_lambs_banner.jpg'),
('Schindler''s List', 'In German-occupied Poland during World War II, Oskar Schindler gradually becomes concerned for his Jewish workforce after witnessing their persecution by the Nazis.', 'schindlers_list.jpg', '1993-12-15', 195, 90, 'schindlers_list_trailer.mp4', 'schindlers_list_banner.jpg'),
('Se7en', 'Two detectives, a rookie and a veteran, hunt a serial killer who uses the seven deadly sins as his motives.', 'se7en.jpg', '1995-09-22', 127, 80, 'se7en_trailer.mp4', 'se7en_banner.jpg'),
('The Usual Suspects', 'A sole survivor tells of the twisty events leading up to a horrific gun battle on a boat, which began when five criminals met at a seemingly random police lineup.', 'usual_suspects.jpg', '1995-08-16', 106, 80, 'usual_suspects_trailer.mp4', 'usual_suspects_banner.jpg'),
('The Lion King', 'Lion prince Simba and his father are targeted by his bitter uncle, who wants to ascend the throne himself.', 'lion_king.jpg', '1994-06-24', 88, 80, 'lion_king_trailer.mp4', 'lion_king_banner.jpg'),
('Gladiator', 'A former Roman General sets out to exact vengeance against the corrupt emperor who murdered his family and sent him into slavery.', 'gladiator.jpg', '2000-05-05', 155, 80, 'gladiator_trailer.mp4', 'gladiator_banner.jpg'),
('Jurassic Park', 'A pragmatic paleontologist visiting an almost complete theme park is tasked with protecting a couple of kids after a power failure causes the park''s cloned dinosaurs to run loose.', 'jurassic_park.jpg', '1993-06-11', 127, 80, 'jurassic_park_trailer.mp4', 'jurassic_park_banner.jpg'),
('The Green Mile', 'The lives of guards on Death Row are affected by one of their charges: a black man accused of child murder and rape, yet who has a mysterious gift.', 'green_mile.jpg', '1999-12-10', 189, 80, 'green_mile_trailer.mp4', 'green_mile_banner.jpg'),
('Saving Private Ryan', 'Following the Normandy Landings, a group of U.S. soldiers go behind enemy lines to retrieve a paratrooper whose brothers have been killed in action.', 'saving_private_ryan.jpg', '1998-07-24', 169, 80, 'saving_private_ryan_trailer.mp4', 'saving_private_ryan_banner.jpg'),
('Braveheart', 'Scottish warrior William Wallace leads his countrymen in a rebellion to free his homeland from the tyranny of King Edward I of England.', 'braveheart.jpg', '1995-05-24', 178, 80, 'braveheart_trailer.mp4', 'braveheart_banner.jpg'),
('The Prestige', 'After a tragic accident, two stage magicians engage in a battle to create the ultimate illusion while sacrificing everything they have to outwit each other.', 'prestige.jpg', '2006-10-20', 130, 80, 'prestige_trailer.mp4', 'prestige_banner.jpg'),
('The Departed', 'An undercover cop and a mole in the police attempt to identify each other while infiltrating an Irish gang in South Boston.', 'departed.jpg', '2006-10-06', 151, 80, 'departed_trailer.mp4', 'departed_banner.jpg'),
('The Pianist', 'A Polish Jewish musician struggles to survive the destruction of the Warsaw ghetto of World War II.', 'pianist.jpg', '2002-12-04', 150, 80, 'pianist_trailer.mp4', 'pianist_banner.jpg'),
('American History X', 'A former neo-nazi skinhead tries to prevent his younger brother from going down the same wrong path that he did.', 'american_history_x.jpg', '1998-10-30', 119, 80, 'american_history_x_trailer.mp4', 'american_history_x_banner.jpg'),
('Interstellar', 'A team of explorers travel through a wormhole in space in an attempt to ensure humanity''s survival.', 'interstellar.jpg', '2014-11-07', 169, 80, 'interstellar_trailer.mp4', 'interstellar_banner.jpg'),
('The Intouchables', 'After he becomes a quadriplegic from a paragliding accident, an aristocrat hires a young man from the projects to be his caregiver.', 'intouchables.jpg', '2011-11-02', 112, 80, 'intouchables_trailer.mp4', 'intouchables_banner.jpg'),
('Whiplash', 'A promising young drummer enrolls at a cut-throat music conservatory where his dreams of greatness are mentored by an instructor who will stop at nothing to realize a student''s potential.', 'whiplash.jpg', '2014-10-10', 106, 80, 'whiplash_trailer.mp4', 'whiplash_banner.jpg'),
('Memento', 'A man with short-term memory loss attempts to track down his wife''s murderer.', 'memento.jpg', '2000-10-11', 113, 80, 'memento_trailer.mp4', 'memento_banner.jpg'),
('The Dark Knight Rises', 'Eight years after the Joker''s reign of anarchy, Batman, with the help of the enigmatic Selina, is forced from his exile to save Gotham City, now on the edge of total annihilation, from the brutal guerrilla terrorist Bane.', 'dark_knight_rises.jpg', '2012-07-20', 164, 80, 'dark_knight_rises_trailer.mp4', 'dark_knight_rises_banner.jpg'),
('Mad Max: Fury Road', 'In a post-apocalyptic wasteland, Max teams up with a mysterious woman, Furiosa, to try and survive.', 'mad_max_fury_road.jpg', '2015-05-15', 120, 80, 'mad_max_fury_road_trailer.mp4', 'mad_max_fury_road_banner.jpg'),
('The Grand Budapest Hotel', 'A writer encounters the owner of an aging high-class hotel, who tells him of his early years serving as a lobby boy in the hotel''s glorious years under an exceptional concierge.', 'grand_budapest_hotel.jpg', '2014-03-28', 99, 80, 'grand_budapest_hotel_trailer.mp4', 'grand_budapest_hotel_banner.jpg'),
('Django Unchained', 'With the help of a German bounty-hunter, a freed slave sets out to rescue his wife from a brutal Mississippi plantation owner.', 'django_unchained.jpg', '2012-12-25', 165, 80, 'django_unchained_trailer.mp4', 'django_unchained_banner.jpg'),
('Coco', 'Aspiring musician Miguel, confronted with his family''s ancestral ban on music, enters the Land of the Dead to find his great-great-grandfather, a legendary singer.', 'coco.jpg', '2017-11-22', 105, 80, 'coco_trailer.mp4', 'coco_banner.jpg'),
('La La Land', 'While navigating their careers in Los Angeles, a pianist and an actress fall in love while attempting to reconcile their aspirations for the future.', 'la_la_land.jpg', '2016-12-09', 128, 80, 'la_la_land_trailer.mp4', 'la_la_land_banner.jpg'),
('Black Panther', 'T''Challa, heir to the hidden but advanced kingdom of Wakanda, must step forward to lead his people into a new future and must confront a challenger from his country''s past.', 'black_panther.jpg', '2018-02-16', 134, 80, 'black_panther_trailer.mp4', 'black_panther_banner.jpg'),
('Parasite', 'Greed and class discrimination threaten the newly formed symbiotic relationship between the wealthy Park family and the destitute Kim clan.', 'parasite.jpg', '2019-05-30', 132, 90, 'parasite_trailer.mp4', 'parasite_banner.jpg'),
('Joker', 'In Gotham City, mentally troubled comedian Arthur Fleck is disregarded and mistreated by society. He then embarks on a downward spiral of revolution and bloody crime.', 'joker.jpg', '2019-10-04', 122, 80, 'joker_trailer.mp4', 'joker_banner.jpg'),
('Avengers: Endgame', 'After the devastating events of Avengers: Infinity War, the universe is in ruins. With the help of remaining allies, the Avengers assemble once more in order to reverse Thanos'' actions and restore balance to the universe.', 'avengers_endgame.jpg', '2019-04-26', 181, 80, 'avengers_endgame_trailer.mp4', 'avengers_endgame_banner.jpg'),
('Spider-Man: Into the Spider-Verse', 'Teen Miles Morales becomes the Spider-Man of his reality, crossing his path with five counterparts from other dimensions to stop a threat for all realities.', 'spider_verse.jpg', '2018-12-14', 117, 80, 'spider_verse_trailer.mp4', 'spider_verse_banner.jpg');

