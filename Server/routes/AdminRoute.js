require("dotenv").config();
const express = require('express');
const router = express.Router();
const adminControllers = require('../controller/AdminController');

// Login route
router.post('/login', adminControllers.login);

// Register route
router.post('/register', adminControllers.register);

// Change user role route
router.put('/changeUserRole', adminControllers.changeUserRole);

// Get Movie List route
router.get('/movie/list', adminControllers.getMovieList);

// Add movie route
router.post('/addMovie', adminControllers.addMovie);

// Delete movie route
router.delete('/deleteMovie/:movieId', adminControllers.deleteMovie);

// Delete review route
router.delete('/deleteReview/:reviewId', adminControllers.deleteReview);

router.get('/getGenres', adminControllers.getGenres);

router.get('/', (req, res) => {
    res.render('home', {
      title: 'Admin Dashboard',
      linkPath: process.env.DB_HOST,
      port: process.env.HOST_PORT
    });
});

router.get('/movie', (req, res) => {
    res.render('movie', {
      title: 'Admin Movie Management',
      linkPath: process.env.DB_HOST,
      port: process.env.HOST_PORT
    });
});

router.get('/movie/create', (req, res) => {
  res.render('create-movie', {
    title: 'Admin Movie Management',
    linkPath: process.env.DB_HOST,
    port: process.env.HOST_PORT
  });
});

router.get('/cinema', (req, res) => {
    res.render('cinema', {
      title: 'Admin Cinema Management',
      linkPath: process.env.DB_HOST,
      port: process.env.HOST_PORT
    });
});

router.get('/request', (req, res) => {
    res.render('request', {
      title: 'Admin Request Management',
      linkPath: process.env.DB_HOST,
      port: process.env.HOST_PORT
    });
});

router.get('/login-website', (req, res) => {
  res.render('auth_page', {
    title: 'Login Authentication'
  });
});

module.exports = router;
