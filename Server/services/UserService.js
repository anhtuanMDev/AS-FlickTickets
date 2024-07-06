require("dotenv").config();
const jwt = require("jsonwebtoken");
const bcrypt = require('bcryptjs');
const { poolPromise } = require("../configs/db");
const sql = require("mssql");

var SECRET_KEY = process.env.JWT_SECRET_KEY;

const loginMethod = (username) => {
    return typeof username;
}

// authentication for user's login token
const authenticationToken = async (username, token) => {
    try {
        //checking token, include checking expires time
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

// authentication for user's login information
const authentication = async (username, password) => {
    try {
        const isEmail = loginMethod(username);
        const pool = await poolPromise;
        let query;
        if (isEmail === "string") {
            query = "SELECT email AS username, password, user_id FROM users WHERE email = @username AND role = 'user'";
        } else if (isEmail === "number") {
            query = "SELECT phone AS username, password, user_id FROM users WHERE phone = @username AND role = 'user'";
        } else {
            throw new Error("Invalid login method");
        }

        const userResult = await pool.request()
            .input('username', username)
            .query(query);

        // The email and phone are sure to be unique so the result will only has 1
        const user = userResult.recordset[0];
        if (!user) {
            throw new Error("User not found");
        }

        const validPass = await bcrypt.compare(password, user.password);
        if (!validPass) {
            throw new Error("Incorrect password");
        }

        const token = jwt.sign({ userId: user.user_id, username: user.username }, SECRET_KEY, { expiresIn: '3d' });
        const data = {token, username, password, role: user.role};

        return data;

    } catch (error) {
        console.error("Authentication error:", error);
        throw error;
    }
}

// create login information for new user
const createAccess = async (fullName, email, password) => {
    try {
        const hashPass = await bcrypt.hash(password, 10);

        const pool = await poolPromise;
        const userlist = await pool.request().input('email', sql.VarChar, email).query("SELECT * FROM users WHERE email = @email");
        if(userlist.recordset.length != 0){
            throw new Error("Sorry, your email has been sign up");
        }
        await pool.request()
            .input('email', sql.VarChar, email)
            .input('password', sql.VarChar, hashPass)
            .input('fullName', sql.VarChar, fullName)
            .input('role', sql.VarChar, "user")
            .query('INSERT INTO users (email, password, name, role) VALUES (@email, @password, @fullName, @role)');

        return { status: true, message: 'User registered successfully' };

    } catch (error) {
        console.log("userService error: ",error);
        throw error;
    }
}

// get user favorite list
const getFavoriteList = async (userId) => {
    try {
        const pool = await poolPromise;

        const query = `
            SELECT m.movie_id AS id, m.title, m.poster, m.release_date, m.duration, m.rate
            FROM favorites f
            JOIN movies m ON f.movie_id = m.movie_id
            WHERE f.user_id = @userId;
        `;

        const result = await pool.request()
            .input('userId', sql.Int, userId)
            .query(query);

        return result.recordset;
    } catch (error) {
        console.error('Error fetching favorite list:', error);
        throw error;
    }
};

// Add movie into your favorite list
const addFavorite = async (userId, movieId) => {
    try {
        const pool = await poolPromise;

        const query = `
            INSERT INTO favorites (user_id, movie_id)
            VALUES (@userId, @movieId);
        `;

        await pool.request()
            .input('userId', sql.Int, userId)
            .input('movieId', sql.Int, movieId)
            .query(query);

        return { status: true, message: 'Movie added to favorites' };
    } catch (error) {
        console.error('Error adding favorite:', error);
        throw error;
    }
};

// Delete movie into your favorite list
const deleteFavorite = async (userId, movieId) => {
    try {
        const pool = await poolPromise;

        const query = `
            DELETE FROM favorites
            WHERE user_id = @userId AND movie_id = @movieId;
        `;

        await pool.request()
            .input('userId', sql.Int, userId)
            .input('movieId', sql.Int, movieId)
            .query(query);

        return { status: true, message: 'Movie removed from favorites' };
    } catch (error) {
        console.error('Error removing favorite:', error);
        throw error;
    }
};



module.exports = {
    authentication,
    createAccess,
    authenticationToken,
    getFavoriteList,
    addFavorite,
    deleteFavorite
};