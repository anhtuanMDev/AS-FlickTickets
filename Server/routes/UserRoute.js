const express = require('express');
const router = express.Router();
const userControllers = require('../controller/UserController');

router.post('/login', userControllers.login);
router.post('/register', userControllers.register);
router.post('/verify-token', userControllers.verifyToken);


module.exports = router;
