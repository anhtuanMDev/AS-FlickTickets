const express = require('express');
const router = express.Router();
const userControllers = require('../controller/UserController');

router.get('/login', userControllers.login);
router.post('/register', userControllers.register);
module.exports = router;
