const { poolPromise } = require("../configs/db");
const sql = require("mssql");
const jwt = require("jsonwebtoken");
const bcrypt = require('bcryptjs');

var SECRET_KEY = process.env.JWT_SECRET_KEY;

// Function to check login method (email or phone)
const loginMethod = (username) => {
    return typeof username === "string" ? "email" : "phone";
}

// Authentication for user's login token
const authenticationToken = async (username, token) => {
    try {
        const decoded = jwt.verify(token, SECRET_KEY);
        if (decoded.username !== username) {
            throw new Error("Token does not match username");
        }
        return true;
    } catch (error) {
        console.error("Token validation error:", error);
        throw new Error("Invalid token");
    }
}

// Authentication for user's login information
const authentication = async (username, password) => {
    try {
        const isEmail = loginMethod(username);
        const pool = await poolPromise;
        let query;
        if (isEmail === "email") {
            query = "SELECT email AS username, password, user_id, role FROM users WHERE email = @username AND role = 'admin'";
        } else if (isEmail === "phone") {
            query = "SELECT phone AS username, password, user_id, role FROM users WHERE phone = @username AND role = 'admin'";
        } else {
            throw new Error("Invalid login method");
        }

        const userResult = await pool.request()
            .input('username', username)
            .query(query);

        const user = userResult.recordset[0];
        if (!user) {
            throw new Error("User not found");
        }

        const validPass = await bcrypt.compare(password, user.password);
        if (!validPass) {
            throw new Error("Incorrect password");
        }

        const token = jwt.sign({ userId: user.user_id, username: user.username, role: user.role }, SECRET_KEY, { expiresIn: '3d' });
        return { token, username, role: user.role };

    } catch (error) {
        console.error("Authentication error:", error);
        throw error;
    }
}

// Register a new admin user
const registerAdmin = async (fullName, email, password) => {
    try {
        const hashPass = await bcrypt.hash(password, 10);

        const pool = await poolPromise;
        const userlist = await pool.request()
            .input('email', sql.VarChar, email)
            .query("SELECT * FROM users WHERE email = @email");

        if (userlist.recordset.length !== 0) {
            throw new Error("Email is already registered");
        }

        await pool.request()
            .input('email', sql.VarChar, email)
            .input('password', sql.VarChar, hashPass)
            .input('fullName', sql.VarChar, fullName)
            .input('role', sql.VarChar, "admin")
            .query('INSERT INTO users (email, password, name, role) VALUES (@email, @password, @fullName, @role)');
        
        return { status: true, message: 'Admin registered successfully' };

    } catch (error) {
        console.error("Admin registration error: ", error);
        throw error;
    }
}

// Change user role
const changeUserRole = async (userId, newRole) => {
    try {
        const pool = await poolPromise;
        await pool.request()
            .input('userId', sql.Int, userId)
            .input('newRole', sql.VarChar, newRole)
            .query('UPDATE users SET role = @newRole WHERE user_id = @userId');
        
        return { status: true, message: 'User role updated successfully' };

    } catch (error) {
        console.error("Error changing user role:", error);
        throw error;
    }
}

// Add movie to movies table
const addMovie = async (title, poster, releaseDate, duration, rate) => {
    try {
        const pool = await poolPromise;

        const query = `
            INSERT INTO movies (title, poster, release_date, duration, rate)
            VALUES (@title, @poster, @releaseDate, @duration, @rate);
        `;

        await pool.request()
            .input('title', sql.VarChar, title)
            .input('poster', sql.VarChar, poster)
            .input('releaseDate', sql.Date, releaseDate)
            .input('duration', sql.Int, duration)
            .input('rate', sql.Int, rate)
            .query(query);

        return { status: true, message: 'Movie added successfully' };
    } catch (error) {
        console.error('Error adding movie:', error);
        throw error;
    }
};

// Delete movie from movies table
const deleteMovie = async (movieId) => {
    try {
        const pool = await poolPromise;

        const query = `
            DELETE FROM movies
            WHERE movie_id = @movieId;
        `;

        await pool.request()
            .input('movieId', sql.Int, movieId)
            .query(query);

        return { status: true, message: 'Movie deleted successfully' };
    } catch (error) {
        console.error('Error deleting movie:', error);
        throw error;
    }
};

// Delete review from reviews table
const deleteReview = async (reviewId) => {
    try {
        const pool = await poolPromise;

        const query = `
            DELETE FROM reviews
            WHERE review_id = @reviewId;
        `;

        await pool.request()
            .input('reviewId', sql.Int, reviewId)
            .query(query);

        return { status: true, message: 'Review deleted successfully' };
    } catch (error) {
        console.error('Error deleting review:', error);
        throw error;
    }
};

module.exports = {
    authentication,
    registerAdmin,
    changeUserRole,
    addMovie,
    deleteMovie,
    deleteReview,
    authenticationToken
}