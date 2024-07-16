const userServices = require('../services/UserService');

async function login(req, res) {
  const { username, password } = req.body;

  try {
    const body = await userServices.authentication(username, password);
    res.json({ body, message: "Login Successfully", status: true  });
  } catch (error) {
    res.status(401).json({ message: error.message, status: false, token: null });
  }
}

async function register(req,res){
  const {fullName, email, password} = req.body;

  try {
    const body = await userServices.createAccess(fullName, email, password);
    res.json({body})
  } catch (error) {
    res.status(401).json({ message: error.message, status: false, token: null });
  }
}

const verifyToken = async(req, res)=>{
  try {
    const {token, username} = req.body;

    const data = await userServices.authenticationToken(username, token);
    res.json({message: "Finish Verify Token", status: data})
  } catch (error) {
    res.status(401).json({ message: error.message, status: false});
  }
}

const getFavoriteList = async(req,res) =>{
  try {
    const {userId} = req.params;
    const body = await userServices.getFavoriteList(parseInt(userId));
    res.json({body, message: "Get favorite success", status: true});
  } catch (error) {
    res.json({body: null, message: "Get favorite fail", status: false});
  }
}

const addFavorite = async(req,res) => {
  try {
    const {userId, movieId} = req.body;
    const body = await userServices.addFavorite(parseInt(userId),parseInt(movieId));
    res.json(body);
  } catch (error) {
    res.json(body);
  }
}

const deleteFavorite = async(req,res) =>{
  try {
    const {userId, movieId} = req.param;
    const body = await userServices.deleteFavorite(parseInt(userId),parseInt(movieId));
    res.json(body);
  } catch (error) {
    res.json(body);
  }
}

module.exports = {
  login,
  register,
  verifyToken,
  getFavoriteList,
  addFavorite,
  deleteFavorite
};
