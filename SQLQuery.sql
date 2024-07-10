-- Ensure you are using the flickmovies database
USE flickmovies;

-- Create the movies table
CREATE TABLE movies (
    movie_id INT PRIMARY KEY IDENTITY(1,1),
    title VARCHAR(255) NOT NULL,
    descr VARCHAR(MAX),
	poster VARCHAR(255) NOT NULL,
    release_date DATE,
	duration INTEGER NOT NULL,
    rate INTEGER,
    trailer VARCHAR(500),
    banner VARCHAR(255)
);

-- Create the actors table
CREATE TABLE actors (
    id INT PRIMARY KEY IDENTITY(1,1),
    movie_id INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    image VARCHAR(255),
    inname_movie VARCHAR(255) NOT NULL,
    inimage_movie VARCHAR(255) NOT NULL,
    FOREIGN KEY (movie_id) REFERENCES movies(movie_id)
);


-- Create the genres table
CREATE TABLE genres (
    name VARCHAR(100) PRIMARY KEY NOT NULL
);

-- Create the movie_genres table (many-to-many relationship between movies and genres)
CREATE TABLE movie_genres (
    movie_id INT NOT NULL,
    genre_id VARCHAR(100) NOT NULL,
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
    avatar VARBINARY(MAX),
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
    image VARCHAR(255),
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

INSERT INTO genres (name) VALUES
('Action'),
('Adventure'),
('Animation'),
('Biography'),
('Comedy'),
('Crime'),
('Drama'),
('Horror'),
('Science Fiction'),
('Sci-Fi'),
('Fantasy'),
('Thriller'),
('Romance'),
('Music'),
('Mystery'),
('Documentary'),
('Musical'),
('War'),
('Western');

INSERT INTO movies (title, descr, poster, release_date, duration, rate, trailer, banner)
VALUES 
('The Shawshank Redemption', 'Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.', 'https://m.media-amazon.com/images/I/71715eBi1sL._AC_UF894,1000_QL80_.jpg', '1994-09-23', 142, 90, 'shawshank_trailer.mp4', 'https://i.ytimg.com/vi/gQt08ojR12I/maxresdefault.jpg'),
('The Godfather', 'The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.', 'https://i.pinimg.com/originals/ae/fc/56/aefc56760546239f121a5baca0cdd373.jpg', '1972-03-24', 175, 90, 'godfather_trailer.mp4', 'https://i.ebayimg.com/images/g/x54AAOSwCzdklKmz/s-l1200.jpg'),
('The Dark Knight', 'When the menace known as the Joker emerges from his mysterious past, he wreaks havoc and chaos on the people of Gotham.', 'https://m.media-amazon.com/images/M/MV5BMTk4ODQzNDY3Ml5BMl5BanBnXkFtZTcwODA0NTM4Nw@@._V1_FMjpg_UX1000_.jpg', '2008-07-18', 152, 90, 'dark_knight_trailer.mp4', 'https://batman-on-film.com/wp-content/uploads/2018/07/TDK-logo-1024x576.jpg'),
('Pulp Fiction', 'The lives of two mob hitmen, a boxer, a gangster and his wife intertwine in four tales of violence and redemption.', 'https://cdn.europosters.eu/image/750/posters/pulp-fiction-cover-i1288.jpg', '1994-10-14', 154, 90, 'pulp_fiction_trailer.mp4', 'https://mir-s3-cdn-cf.behance.net/project_modules/hd/06c74886648489.5d9f8edd30b9a.jpg'),
('Forrest Gump', 'The presidencies of Kennedy and Johnson, the Vietnam War, the Watergate scandal and other historical events unfold from the perspective of an Alabama man with an IQ of 75.', 'https://c8.alamy.com/comp/W0FA8P/forrest-gump-us-poster-for-2019-re-release-tom-hanks-1994-paramount-courtesy-everett-collection-W0FA8P.jpg', '1994-07-06', 142, 90, 'forrest_gump_trailer.mp4', 'https://rukminim2.flixcart.com/image/850/1000/xif0q/poster/g/n/c/small-spos7042-poster-movie-forrest-gump-tom-hanks-sl-7042-wall-original-imaghmmjbhxqzhes.jpeg?q=90&crop=false'),
('Inception', 'A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O.', 'https://m.media-amazon.com/images/I/61cN-XN94TL._AC_UF894,1000_QL80_.jpg', '2010-07-16', 148, 80, 'inception_trailer.mp4', 'https://collider.com/wp-content/uploads/inception_movie_poster_banner_02.jpg'),
('Fight Club', 'An insomniac office worker and a devil-may-care soap maker form an underground fight club that evolves into much more.', 'https://i.ebayimg.com/images/g/3UQAAOSwroxfeYhm/s-l1600.jpg', '1999-10-15', 139, 80, 'fight_club_trailer.mp4', 'https://www.alivewithideas.com/wp-content/uploads/2019/09/Fight_Club_Creative_blog_banner_2720x932px_v2-01-1360x466.jpg'),
('The Matrix', 'A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.', 'https://m.media-amazon.com/images/M/MV5BNzQzOTk3OTAtNDQ0Zi00ZTVkLWI0MTEtMDllZjNkYzNjNTc4L2ltYWdlXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_.jpg', '1999-03-31', 136, 90, 'matrix_trailer.mp4', 'https://www.whatisthematrix.com/assetsNew/images/banner_img_mobile.jpg'),
('Goodfellas', 'The story of Henry Hill and his life in the mob, covering his relationship with his wife Karen Hill and his mob partners Jimmy Conway and Tommy DeVito.', 'https://www.movieposters.com/cdn/shop/products/ef4b93ef8f7157de3f97ae1ff5d21b56_0526bb98-1f4a-4da5-b928-b0025f5e6371_480x.progressive.jpg?v=1573585487', '1990-09-19', 146, 90, 'goodfellas_trailer.mp4', 'https://territorystudio.com/wp-content/uploads/2018/09/Film_Goodfellas_M_001.jpg'),
('The Lord of the Rings: The Return of the King', 'Gandalf and Aragorn lead the World of Men against Sauron''s army to draw his gaze from Frodo and Sam as they approach Mount Doom with the One Ring.', 'https://i.ebayimg.com/images/g/xEcAAOSwUXFbOB5e/s-l1600.jpg', '2003-12-17', 201, 90, 'lotr_return_king_trailer.mp4', 'https://media.cinemacloud.co.uk/imageFilm/1290_1_1.jpg'),
('Star Wars: Episode V - The Empire Strikes Back', 'After the rebels are brutally overpowered by the Empire on the ice planet Hoth, Luke Skywalker begins Jedi training with Yoda.', 'https://i.pinimg.com/736x/1f/7c/f4/1f7cf4a04cacd49f0ceae7b84219e8b6.jpg', '1980-05-21', 124, 90, 'empire_strikes_back_trailer.mp4', 'https://riotheatre.ca/wp-content/uploads/2020/06/empire-banner-1-1024x576.jpg'),
('The Silence of the Lambs', 'A young F.B.I. cadet must receive the help of an incarcerated and manipulative cannibal killer to help catch another serial killer.', 'https://m.media-amazon.com/images/I/81SVDO6WcrL.jpg', '1991-02-14', 118, 90, 'silence_lambs_trailer.mp4', 'https://i.ytimg.com/vi/Msigx2eqO6s/maxresdefault.jpg'),
('Schindler''s List', 'In German-occupied Poland during World War II, Oskar Schindler gradually becomes concerned for his Jewish workforce after witnessing their persecution by the Nazis.', 'https://m.media-amazon.com/images/M/MV5BNDE4OTMxMTctNmRhYy00NWE2LTg3YzItYTk3M2UwOTU5Njg4XkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_.jpg', '1993-12-15', 195, 90, 'schindlers_list_trailer.mp4', 'https://www.silverscreen.tours/wp-content/uploads/2021/02/schindlers-list-banner-01.jpg'),
('Se7en', 'Two detectives, a rookie and a veteran, hunt a serial killer who uses the seven deadly sins as his motives.', 'https://m.media-amazon.com/images/M/MV5BOTUwODM5MTctZjczMi00OTk4LTg3NWUtNmVhMTAzNTNjYjcyXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_.jpg', '1995-09-22', 127, 80, 'se7en_trailer.mp4', 'https://hqcovers.net/wp-content/uploads/2014/06/se7en_banner.jpg'),
('The Usual Suspects', 'A sole survivor tells of the twisty events leading up to a horrific gun battle on a boat, which began when five criminals met at a seemingly random police lineup.', 'https://m.media-amazon.com/images/I/71mg9JO1ISL._AC_UF894,1000_QL80_.jpg', '1995-08-16', 106, 80, 'usual_suspects_trailer.mp4', 'https://streamcoimg-a.akamaihd.net/000/390/629/390629-Banner-L2-cb9b9813574b0234f74f7bc702fd4e22.jpeg'),
('The Lion King', 'Lion prince Simba and his father are targeted by his bitter uncle, who wants to ascend the throne himself.', 'https://m.media-amazon.com/images/I/81S36TZzg9L._AC_SL1500_.jpg', '1994-06-24', 88, 80, 'lion_king_trailer.mp4', 'https://cdn.europosters.eu/image/hp/53708.jpg'),
('Gladiator', 'A former Roman General sets out to exact vengeance against the corrupt emperor who murdered his family and sent him into slavery.', 'https://m.media-amazon.com/images/I/51cV7a82q+L._AC_UF894,1000_QL80_.jpg', '2000-05-05', 155, 80, 'gladiator_trailer.mp4', 'https://static1.colliderimages.com/wordpress/wp-content/uploads/2022/01/gladiator-historically-accurate.jpg'),
('Jurassic Park', 'A pragmatic paleontologist visiting an almost complete theme park is tasked with protecting a couple of kids after a power failure causes the park''s cloned dinosaurs to run loose.', 'https://vice-press.com/cdn/shop/products/Jurassic-Park-Editions-poster-florey.jpg?v=1654518755&width=1024', '1993-06-11', 127, 80, 'jurassic_park_trailer.mp4', 'https://mrwallpaper.com/images/hd/awesome-jurassic-park-banner-stgf395732bu71t1.jpg'),
('The Green Mile', 'The lives of guards on Death Row are affected by one of their charges: a black man accused of child murder and rape, yet who has a mysterious gift.', 'https://img.artpal.com/50671/15-14-9-10-12-14-18m.jpg', '1999-12-10', 189, 80, 'green_mile_trailer.mp4', 'https://www.irishfilmcritic.com/wp-content/uploads/2022/01/The-Green-Mile.jpg'),
('Saving Private Ryan', 'Following the Normandy Landings, a group of U.S. soldiers go behind enemy lines to retrieve a paratrooper whose brothers have been killed in action.', 'https://m.media-amazon.com/images/I/61OK2PdNjKL._AC_UF894,1000_QL80_.jpg', '1998-07-24', 169, 80, 'saving_private_ryan_trailer.mp4', 'https://thequaysidereview.movie.blog/wp-content/uploads/2023/03/spr.jpg'),
('Braveheart', 'Scottish warrior William Wallace leads his countrymen in a rebellion to free his homeland from the tyranny of King Edward I of England.', 'https://i.ebayimg.com/images/g/D6kAAOSwBCtbI8mL/s-l1600.jpg', '1995-05-24', 178, 80, 'braveheart_trailer.mp4', 'https://i.ytimg.com/vi/1ZS1Il6q7gs/maxresdefault.jpg'),
('The Prestige', 'After a tragic accident, two stage magicians engage in a battle to create the ultimate illusion while sacrificing everything they have to outwit each other.', 'https://www.westalexfilms.com/wp-content/uploads/2016/10/WestalexFilms-The-Prestige-Movie-Poster-Are-You-Watching-Closely.jpg', '2006-10-20', 130, 80, 'prestige_trailer.mp4', 'https://image.jimcdn.com/app/cms/image/transf/none/path/s44063efe23c3239b/image/i77648177d14cd474/version/1476696101/image.png'),
('The Departed', 'An undercover cop and a mole in the police attempt to identify each other while infiltrating an Irish gang in South Boston.', 'https://i.pinimg.com/736x/8d/a8/f0/8da8f000ea37ed17c04520f0c3057347.jpg', '2006-10-06', 151, 80, 'departed_trailer.mp4', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR5z2bltWPlwMkuTKdkl322GmiZPyKASAje2g&usqp=CAU'),
('The Pianist', 'A Polish Jewish musician struggles to survive the destruction of the Warsaw ghetto of World War II.', 'https://image.tmdb.org/t/p/original/ryFXgaymUOFEwTpuqp4Osi9dRxr.jpg', '2002-12-04', 150, 80, 'pianist_trailer.mp4', 'https://www.originalposter.co.uk/uploads/766689479351043_mainphotos.jpg'),
('American History X', 'A former neo-nazi skinhead tries to prevent his younger brother from going down the same wrong path that he did.', 'https://m.media-amazon.com/images/I/51Jxom09KIL._AC_UF894,1000_QL80_.jpg', '1998-10-30', 119, 80, 'american_history_x_trailer.mp4', 'https://m.media-amazon.com/images/S/pv-target-images/78ccf3372fd23fdf4ecccb67525ea782a217df5d3614e2aa22116f263d656170._UR1920,1080_SX720_FMjpg_.jpg'),
('Interstellar', 'A team of explorers travel through a wormhole in space in an attempt to ensure humanity''s survival.', 'https://upload.wikimedia.org/wikipedia/vi/4/46/Interstellar_poster.jpg', '2014-11-07', 169, 80, 'interstellar_trailer.mp4', 'https://i.imgur.com/Y9WpgvM.jpeg'),
('The Intouchables', 'After he becomes a quadriplegic from a paragliding accident, an aristocrat hires a young man from the projects to be his caregiver.', 'https://m.media-amazon.com/images/I/51WbHjvEceL._AC_UF894,1000_QL80_.jpg', '2011-11-02', 112, 80, 'intouchables_trailer.mp4', 'https://facts.net/wp-content/uploads/2023/06/32-facts-about-the-movie-the-intouchables-1687590894.jpeg'),
('Whiplash', 'A promising young drummer enrolls at a cut-throat music conservatory where his dreams of greatness are mentored by an instructor who will stop at nothing to realize a student''s potential.', 'https://m.media-amazon.com/images/I/51GXgsoSPaL._AC_UF894,1000_QL80_.jpg', '2014-10-10', 106, 80, 'whiplash_trailer.mp4', 'https://ih1.redbubble.net/image.2256731188.0633/raf,360x360,075,t,fafafa:ca443f4786.jpg'),
('Memento', 'A man with short-term memory loss attempts to track down his wife''s murderer.', 'https://i.pinimg.com/736x/91/2e/89/912e89d9c707099aa18ea52c9c6feddd.jpg', '2000-10-11', 113, 80, 'memento_trailer.mp4', 'https://mir-s3-cdn-cf.behance.net/project_modules/max_3840/42fbdb111636979.6005d5f74e5be.jpg'),
('The Dark Knight Rises', 'Eight years after the Joker''s reign of anarchy, Batman, with the help of the enigmatic Selina, is forced from his exile to save Gotham City, now on the edge of total annihilation, from the brutal guerrilla terrorist Bane.', 'https://collider.com/wp-content/uploads/the-dark-knight-rises-christian-bale-poster1.jpg', '2012-07-20', 164, 80, 'dark_knight_rises_trailer.mp4', 'https://static1.colliderimages.com/wordpress/wp-content/uploads/dark-knight-rises-movie-poster-banner-slice.jpg'),
('Mad Max: Fury Road', 'In a post-apocalyptic wasteland, Max teams up with a mysterious woman, Furiosa, to try and survive.', 'https://i.ebayimg.com/images/g/38sAAOSwdRdgSdGL/s-l1600.jpg', '2015-05-15', 120, 80, 'mad_max_fury_road_trailer.mp4', 'https://thelovepirate.net/wp-content/uploads/2014/08/image45.jpg'),
('The Grand Budapest Hotel', 'A writer encounters the owner of an aging high-class hotel, who tells him of his early years serving as a lobby boy in the hotel''s glorious years under an exceptional concierge.', 'https://ih1.redbubble.net/image.2028117166.1407/flat,750x,075,f-pad,750x1000,f8f8f8.jpg', '2014-03-28', 99, 80, 'grand_budapest_hotel_trailer.mp4', 'https://c4.wallpaperflare.com/wallpaper/504/809/128/movie-the-grand-budapest-hotel-hotel-pink-wallpaper-preview.jpg'),
('Django Unchained', 'With the help of a German bounty-hunter, a freed slave sets out to rescue his wife from a brutal Mississippi plantation owner.', 'https://m.media-amazon.com/images/I/912vL7muRwL._AC_UF894,1000_QL80_.jpg', '2012-12-25', 165, 80, 'django_unchained_trailer.mp4', 'https://images2.alphacoders.com/112/1121054.jpg'),
('Coco', 'Aspiring musician Miguel, confronted with his family''s ancestral ban on music, enters the Land of the Dead to find his great-great-grandfather, a legendary singer.', 'https://upload.wikimedia.org/wikipedia/vi/7/7b/Coco_%28phim_2017%29_poster.jpg', '2017-11-22', 105, 80, 'coco_trailer.mp4', 'https://i.blogs.es/d1fab2/cartel-coco/1366_2000.jpg'),
('La La Land', 'While navigating their careers in Los Angeles, a pianist and an actress fall in love while attempting to reconcile their aspirations for the future.', 'https://i.pinimg.com/474x/9d/42/bd/9d42bdbfa02a05e4685ad367cccdd774.jpg', '2016-12-09', 128, 80, 'la_la_land_trailer.mp4', 'https://moviebabble.com/wp-content/uploads/2016/12/la-la-land-banner101.jpg'),
('Black Panther', 'T''Challa, heir to the hidden but advanced kingdom of Wakanda, must step forward to lead his people into a new future and must confront a challenger from his country''s past.', 'https://m.media-amazon.com/images/I/810SlMj1+eL._AC_UF894,1000_QL80_.jpg', '2018-02-16', 134, 80, 'black_panther_trailer.mp4', 'https://filmexposure.ch/wp-content/uploads/2018/02/black-panther-banner.jpg'),
('Parasite', 'Greed and class discrimination threaten the newly formed symbiotic relationship between the wealthy Park family and the destitute Kim clan.', 'https://i.etsystatic.com/23402008/r/il/0e5769/2499687838/il_570xN.2499687838_7a06.jpg', '2019-05-30', 132, 90, 'parasite_trailer.mp4', 'https://socialism.com/wp-content/uploads/2020/03/41-2_review-movie.jpg'),
('Joker', 'In Gotham City, mentally troubled comedian Arthur Fleck is disregarded and mistreated by society. He then embarks on a downward spiral of revolution and bloody crime.', 'https://i.ebayimg.com/images/g/aN0AAOSw50BduC3-/s-l1200.webp', '2019-10-04', 122, 80, 'joker_trailer.mp4', 'https://w0.peakpx.com/wallpaper/700/371/HD-wallpaper-joker-movie-8k-banner.jpg'),
('Avengers: Endgame', 'After the devastating events of Avengers: Infinity War, the universe is in ruins. With the help of remaining allies, the Avengers assemble once more in order to reverse Thanos'' actions and restore balance to the universe.', 'https://m.media-amazon.com/images/I/71niXI3lxlL._AC_SL1183_.jpg', '2019-04-26', 181, 80, 'avengers_endgame_trailer.mp4', 'https://i.pinimg.com/originals/29/7d/e0/297de0761b0c756266d74ca50d03cc1d.jpg'),
('Spider-Man: Into the Spider-Verse', 'Teen Miles Morales becomes the Spider-Man of his reality, crossing his path with five counterparts from other dimensions to stop a threat for all realities.', 'https://posterspy.com/wp-content/uploads/2023/01/spiderverse-final.jpg', '2018-12-14', 117, 80, 'spider_verse_trailer.mp4', 'https://cdn.flickeringmyth.com/wp-content/uploads/2023/05/Across-the-Spider-Verse.jpg');

INSERT INTO movie_genres (movie_id, genre_id) VALUES
(1, 'Drama'),
(1, 'Crime'),
(2, 'Drama'),
(2, 'Crime'),
(3, 'Action'),
(3, 'Crime'),
(3, 'Drama'),
(4, 'Crime'),
(4, 'Drama'),
(5, 'Drama'),
(5, 'Romance'),
(6, 'Action'),
(6, 'Adventure'),
(6, 'Sci-Fi'),
(7, 'Drama'),
(7, 'Thriller'),
(8, 'Action'),
(8, 'Sci-Fi'),
(9, 'Biography'),
(9, 'Crime'),
(9, 'Drama'),
(10, 'Action'),
(10, 'Adventure'),
(10, 'Drama'),
(11, 'Action'),
(11, 'Adventure'),
(11, 'Fantasy'),
(12, 'Crime'),
(12, 'Drama'),
(12, 'Thriller'),
(13, 'Animation'),
(13, 'Adventure'),
(13, 'Drama'),
(14, 'Action'),
(14, 'Adventure'),
(14, 'Drama'),
(15, 'Crime'),
(15, 'Mystery'),
(15, 'Thriller'),
(16, 'Crime'),
(16, 'Mystery'),
(16, 'Thriller'),
(17, 'Animation'),
(17, 'Adventure'),
(17, 'Drama'),
(18, 'Action'),
(18, 'Adventure'),
(18, 'Drama'),
(19, 'Crime'),
(19, 'Drama'),
(20, 'Biography'),
(20, 'Drama'),
(21, 'Drama'),
(21, 'Mystery'),
(21, 'Sci-Fi'),
(22, 'Comedy'),
(22, 'Drama'),
(22, 'Music'),
(23, 'Mystery'),
(23, 'Thriller'),
(24, 'Mystery'),
(24, 'Thriller'),
(25, 'Action'),
(25, 'Adventure'),
(25, 'Drama'),
(26, 'Action'),
(26, 'Adventure'),
(26, 'Sci-Fi'),
(27, 'Adventure'),
(27, 'Drama'),
(27, 'Fantasy'),
(28, 'Drama'),
(28, 'Mystery'),
(28, 'Thriller'),
(29, 'Adventure'),
(29, 'Drama'),
(29, 'Sci-Fi'),
(30, 'Drama'),
(30, 'Music'),
(30, 'Romance'),
(31, 'Action'),
(31, 'Adventure'),
(31, 'Sci-Fi'),
(32, 'Drama'),
(32, 'Thriller'),
(33, 'Drama'),
(33, 'War'),
(34, 'Action'),
(34, 'Adventure'),
(34, 'Sci-Fi'),
(35, 'Biography'),
(35, 'Drama'),
(36, 'Drama'),
(36, 'Romance'),
(37, 'Action'),
(37, 'Adventure'),
(37, 'Sci-Fi'),
(38, 'Action'),
(38, 'Adventure'),
(38, 'Sci-Fi'),
(39, 'Animation'),
(39, 'Adventure'),
(39, 'Comedy'),
(40, 'Action'),
(40, 'Adventure'),
(40, 'Sci-Fi');