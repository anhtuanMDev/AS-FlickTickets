const { poolPromise } = require("../configs/db");
const sql = require("mssql");

const getMoviesData = async (page, pageSize) => {
    try {
        page = Math.max(page, 1);
        const pool = await poolPromise;
        const offset = (page - 1) * pageSize;

        const recommendQuery = `
            SELECT m.movie_id AS id, m.title, m.poster, m.rate, STRING_AGG(g.name, ', ') AS genres
            FROM movies m
            LEFT JOIN movie_genres mg ON m.movie_id = mg.movie_id
            LEFT JOIN genres g ON mg.genre_name = g.name
            GROUP BY m.movie_id, m.title, m.poster, m.rate
            ORDER BY m.movie_id
            OFFSET @offset ROWS
            FETCH NEXT @pageSize ROWS ONLY;
        `;

        const displayQuery = `
            SELECT movie_id AS id, title, poster
            FROM movies
            ORDER BY movie_id
            OFFSET @offset ROWS
            FETCH NEXT @pageSize ROWS ONLY;
        `;

        const recommendResult = await pool.request()
            .input('offset', sql.Int, offset)
            .input('pageSize', sql.Int, pageSize)
            .query(recommendQuery);

        const displayResult = await pool.request()
            .input('offset', sql.Int, offset)
            .input('pageSize', sql.Int, pageSize)
            .query(displayQuery);

        const recommendList = recommendResult.recordset.map(row => ({
            title: row.title,
            poster: row.poster,
            rate: row.rate,
            id: row.id,
            genres: row.genres
        }));

        const displayList = displayResult.recordset.map(row => ({
            title: row.title,
            poster: row.poster,
            id: row.id
        }));

        return {
            recommendList,
            displayList
        };
    } catch (error) {
        console.error('Error fetching movies data:', error);
        throw error;
    }
}

const getRecommend = async (page, pageSize) => {
    try {
        page = Math.max(page, 1);
        const pool = await poolPromise;
        const offset = (page - 1) * pageSize;

        const recommendQuery = `
            SELECT m.movie_id AS id, m.title, m.poster, m.rate, STRING_AGG(g.name, ', ') AS genres
            FROM movies m
            LEFT JOIN movie_genres mg ON m.movie_id = mg.movie_id
            LEFT JOIN genres g ON mg.genre_name = g.name
            GROUP BY m.movie_id, m.title, m.poster, m.rate
            ORDER BY m.movie_id
            OFFSET @offset ROWS
            FETCH NEXT @pageSize ROWS ONLY;
        `;

        const recommendResult = await pool.request()
            .input('offset', sql.Int, offset)
            .input('pageSize', sql.Int, pageSize)
            .query(recommendQuery);

        const recommendList = recommendResult.recordset.map(row => ({
            title: row.title,
            poster: row.poster,
            rate: row.rate,
            id: row.id,
            genres: row.genres
        }));

        return recommendList;
    } catch (error) {
        console.error('Error fetching recommend movies:', error);
        throw error;
    }
}

const getDisplay = async (page, pageSize) => {
    try {
        page = Math.max(page, 1);
        const pool = await poolPromise;
        const offset = (page - 1) * pageSize;

        const displayQuery = `
            SELECT movie_id AS id, title, poster
            FROM movies
            ORDER BY movie_id
            OFFSET @offset ROWS
            FETCH NEXT @pageSize ROWS ONLY;
        `;

        const displayResult = await pool.request()
            .input('offset', sql.Int, offset)
            .input('pageSize', sql.Int, pageSize)
            .query(displayQuery);

        const displayList = displayResult.recordset.map(row => ({
            title: row.title,
            poster: row.poster,
            id: row.id
        }));

        return displayList;
    } catch (error) {
        console.error('Error fetching display movies:', error);
        throw error;
    }
}

const getHomePage = async (req, res) => {
    const page = parseInt(req.query.page) || 1;
    const pageSize = parseInt(req.query.pageSize) || 10;

    try {
        const data = await getMoviesData(page, pageSize);
        res.status(200).json(data);
    } catch (error) {
        res.status(500).json({ error: 'Failed to fetch movies data' });
    }
}

const getMovieDetail = async (id, userId) => {
    try {
        const pool = await poolPromise;

        const query = `
            DECLARE @movie_id INT = @id;

            -- Movie details
            SELECT movie_id AS id, title, descr, poster, release_date, duration, rate, trailer, banner
            FROM movies
            WHERE movie_id = @movie_id;

            -- Check if the movie exists
            IF EXISTS (SELECT 1 FROM movies WHERE movie_id = @movie_id)
            BEGIN
                -- Genres
                SELECT STRING_AGG(g.name, ', ') AS genres
                FROM genres g
                JOIN movie_genres mg ON g.name = mg.genre_name
                WHERE mg.movie_id = @movie_id;

                -- Actors
                SELECT id, name, image, inname_movie, inimage_movie
                FROM actors
                WHERE movie_id = @movie_id;

                -- User favorite status
                SELECT COUNT(*) AS is_favorite
                FROM user_favorites
                WHERE user_id = @userId AND movie_id = @movie_id;

                -- Reviews
                SELECT r.review_id, r.review_text, r.rating, r.review_date, u.role AS user_role
                FROM reviews r
                JOIN users u ON r.user_id = u.user_id
                WHERE r.movie_id = @movie_id;

                -- Showtimes and cinemas
                SELECT s.showtime_id, s.start_time, s.end_time, c.cinema_id, c.name AS cinema_name, c.location AS cinema_location
                FROM showtime s
                JOIN cinemas c ON s.cinema_id = c.cinema_id
                WHERE s.movie_id = @movie_id;

                -- Available seats
                SELECT seat_id, number, row, price, status
                FROM seats
                WHERE showtime_id IN (SELECT showtime_id FROM showtime WHERE movie_id = @movie_id) AND status = 0;
            END
            ELSE
            BEGIN
                -- Return a result indicating the movie was not found
                SELECT -1 AS id;
            END
        `;

        const result = await pool.request()
            .input('id', sql.Int, id)
            .input('userId', sql.Int, userId)
            .query(query);

        if (result.recordsets[0][0].id === -1) {
            throw new Error('Movie not found');
        }

        const movieDetail = result.recordsets[0][0];
        movieDetail.genres = result.recordsets[1][0].genres;
        movieDetail.actors = result.recordsets[2];
        movieDetail.is_favorite = result.recordsets[3][0].is_favorite > 0;
        movieDetail.reviews = result.recordsets[4];
        movieDetail.showtimes = result.recordsets[5];
        movieDetail.available_seats = result.recordsets[6];

        return movieDetail;
    } catch (error) {
        console.error('Error fetching movie details:', error);
        throw error;
    }
}

module.exports = {
    getHomePage,
    getMovieDetail,
    getRecommend,
    getDisplay
}
