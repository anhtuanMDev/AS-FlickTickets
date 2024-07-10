const express = require("express");
const movieControls = require("../controller/MovieController");
const router = express.Router();

// get both normal movie list and recommment list
router.get("/list/:page", movieControls.getMovieList);

// get sepperately recommend and display movie list
router.get('/recommend', movieControls.getRecommendList);
router.get('/display', movieControls.getDisplayList);

router.get("/detail/:id/:userId", movieControls.getDetail);

module.exports = router;