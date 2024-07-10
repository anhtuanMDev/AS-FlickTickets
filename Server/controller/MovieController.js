const movieServices = require("../services/MovieService");

async function getMovieList(req, res) {
    const { page } = req.params;
    try {
        const body = await movieServices.getMoviesData(parseInt(page));
        res.json({ body, status: true, message: "Get movie list successfully" });
    } catch (error) {
        res.json({ body: null, status: false, message: error.message });
    }
}

async function getRecommendList(req, res) {
    const { page} = req.query;
    console.log(`page ${page}`)
    try {
        const body = await movieServices.getRecommend(parseInt(page));
        res.json({ body, status: true, message: "Get recommend movie list successfully" });
    } catch (error) {
        res.json({ body: null, status: false, message: error.message });
    }
}

async function getDisplayList(req, res) {
    const { page} = req.query;

    try {
        const body = await movieServices.getDisplay(parseInt(page));
        res.json({ body, status: true, message: "Get display movie list successfully" });
    } catch (error) {
        res.json({ body: null, status: false, message: error.message });
    }
}

async function getDetail(req, res) {
    const { id, userId } = req.params;
    try {
        const body = await movieServices.getMovieDetail(parseInt(id), parseInt(userId));
        res.status(200).json(body);
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
}

module.exports = {
    getMovieList,
    getDetail,
    getRecommendList,
    getDisplayList
};
