const movieControls = require("../controller/MovieController");
const express = require("express");
const router = express.Router();

// get both normal movie list and recommment list
router.get("/list", movieControls.getMovieList);

// get sepperately recommend and display movie list
router.get('/list/recommend', movieControls.getRecommendList);
router.get('/list/display', movieControls.getDisplayList);

router.get("/detail/:id", movieControls.getMovieList);