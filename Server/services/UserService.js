require("dotenv").config();
const jwt = require("jsonwebtoken");
const bcrypt = require('bcryptjs');
const { poolPromise } = require("./configs/db");

var SECRET_KEY = process.env.JWT_SECRET_KEY;

const loginMethod = (username) => {
    return typeof username;
}

const authentication = async (username, password) => {
    try {
        const isEmail = loginMethod(req.username);
        const pool = await poolPromise;
        let userList = await pool.request().query("SELECT email, password, user_id FROM users")
        switch (isEmail) {
            case "string":

                const validUser = userList.find(userList => userList.username === username);
                if (!validUser) {
                    throw new Error("Couldn't find one");
                }

                const validPass = await bcrypt.compare(password, validUser.password);
                if (!validPass) {
                    throw new Error("Wrong password, please try again.");
                }

                const emailToken = jwt.sign({ userId: validUser.user_id, username: username }, SECRET_KEY, { expiresIn: '1d' });
                // create token base on user id and email with secretkey and has 24h before expire

                return emailToken;

            case "number":
                const validNumber = userList.find(userList => userList.username === username);
                if (!validNumber) {
                    throw new Error("Couldn't find one");
                }

                const validPassord = await bcrypt.compare(password, validNumber.password);
                if (!validPassord) {
                    throw new Error("Wrong password, please try again.");
                }

                const numberToken = jwt.sign({ userId: validNumber.user_id, username: username }, SECRET_KEY, { expiresIn: '1d' });
                // create token base on user id and email with secretkey and has 24h before expire

                return numberToken;
            default:
                throw new Error("There seem to be some error, please try again later");
        }
    } catch (error) {
        console.log(error);
    }
}

const createAccess = async (fullName, email, password) => {
    try {
        const hashPass = await bcrypt.hash(password, 10);

        const pool = await poolPromise;
        await pool.request()
            .input('email', sql.VarChar, email)
            .input('password', sql.VarChar, hashPass)
            .input('fullName', sql.VarChar, fullName)
            .input('role', sql.VarChar, "user")
            .query('INSERT INTO users (email, password, name, role) VALUES (@email, @password, @fullName, @role)');

        return { status: true, message: 'User registered successfully' };

    } catch (error) {
        console.log(error);
    }
}

module.exports = {
    authentication,
    createAccess
};