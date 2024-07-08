require("dotenv").config();
const adminServices = require('../services/AminService');

async function login(req, res) {
    const { username, password } = req.body;

    try {
        const data = await adminServices.authentication(username, password);
        res.json({ data, message: "Login Successfully", status: true });
    } catch (error) {
        res.status(401).json({ message: error.message, status: false, token: null });
    }
}

async function register(req, res) {
    const { fullName, email, password } = req.body;

    try {
        const result = await adminServices.registerAdmin(fullName, email, password);
        res.json({ data: result, message: "Admin registered successfully", status: true });
    } catch (error) {
        res.status(400).json({ message: error.message, status: false });
    }
}

async function changeUserRole(req, res) {
    const { userId, newRole } = req.body;

    try {
        const result = await adminServices.changeUserRole(userId, newRole);
        res.json({ data: result, message: "User role updated successfully", status: true });
    } catch (error) {
        res.status(400).json({ message: error.message, status: false });
    }
}

async function addMovie(req, res) {
    const { title, poster, releaseDate, duration, rate } = req.body;

    try {
        const result = await adminServices.addMovie(title, poster, releaseDate, duration, rate);
        res.json({ data: result, message: "Movie added successfully", status: true });
    } catch (error) {
        res.status(400).json({ message: error.message, status: false });
    }
}

async function deleteMovie(req, res) {
    const { movieId } = req.params;

    try {
        const result = await adminServices.deleteMovie(movieId);
        res.json({ data: result, message: "Movie deleted successfully", status: true });
    } catch (error) {
        res.status(400).json({ message: error.message, status: false });
    }
}

async function deleteReview(req, res) {
    const { reviewId } = req.params;

    try {
        const result = await adminServices.deleteReview(reviewId);
        res.json({ data: result, message: "Review deleted successfully", status: true });
    } catch (error) {
        res.status(400).json({ message: error.message, status: false });
    }
}

module.exports = {
    login,
    register,
    changeUserRole,
    addMovie,
    deleteMovie,
    deleteReview
};
