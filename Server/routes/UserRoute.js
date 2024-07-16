const express = require('express');
const router = express.Router();
const userControllers = require('../controller/UserController');

router.post('/login', userControllers.login);
router.post('/register', userControllers.register);
router.post('/verify-token', userControllers.verifyToken);
router.get('/favorite/list/:userId', userControllers.getFavoriteList);
router.post('/favorite/create', userControllers.addFavorite);
router.delete('/favorite/remove/:userId/:movieId', userControllers.deleteFavorite);
module.exports = router;
