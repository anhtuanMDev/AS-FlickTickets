const movieServices = require("../services/MovieService")

async function getMovieList(req,res){
    const {page, pageSize} = req.body;

    try {
        const data = await movieServices.getHomePage(page,pageSize);
        res.json({data, status: true, message: "Get movie list successfully"});
    } catch (error) {
        res.json({data: null, status: false, message: error});
    }

}

async function getRecommendList(req,res){
    const {page, pageSize} = req.body;

    try {
        const data = await movieServices.getRecommend(page,pageSize);
        res.json({data, status: true, message: "Get recommend movie list successfully"});
    } catch (error) {
        res.json({data: null, status: false, message: error});
    }

}

async function getDisplayList(req,res){
    const {page, pageSize} = req.body;

    try {
        const data = await movieServices.getDisplay(page,pageSize);
        res.json({data, status: true, message: "Get display movie list successfully"});
    } catch (error) {
        res.json({data: null, status: false, message: error});
    }

}
 
async function getDetail (req, res) {
    const { id, userId } = req.params;
    try {
        const movieDetail = await movieServices.getMovieDetail(id,userId);
        res.status(200).json(movieDetail);
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
};

module.exports = {
    getMovieList,
    getDetail,
    getRecommendList,
    getDisplayList
}