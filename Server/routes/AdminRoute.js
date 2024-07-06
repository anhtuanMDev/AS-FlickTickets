const express = require('express');
const router = express.Router();
const adminControllers = require('../controller/AdminController');

// Login route
router.post('/login', adminControllers.login);

// Register route
router.post('/register', adminControllers.register);

// Change user role route
router.put('/changeUserRole', adminControllers.changeUserRole);

// Add movie route
router.post('/addMovie', adminControllers.addMovie);

// Delete movie route
router.delete('/deleteMovie/:movieId', adminControllers.deleteMovie);

// Delete review route
router.delete('/deleteReview/:reviewId', adminControllers.deleteReview);

module.exports = router;