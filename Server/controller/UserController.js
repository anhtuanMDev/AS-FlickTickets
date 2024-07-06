const userServices = require('../services/UserService');

async function login(req, res) {
  const { username, password } = req.body;

  try {
    const data = await userServices.login(username, password);
    res.json({ data, message: "Login Successfully", status: true  });
  } catch (error) {
    res.status(401).json({ message: error.message, status: false, token: null });
  }
}

async function register(req,res){
  const {fullName, email, password} = req.body;

  try {
    const data = await userServices.createAccess(fullName, email, password);
    res.json({data})
  } catch (error) {
    res.status(401).json({ message: error.message, status: false, token: null });
  }
}

const verifyToken = async(req, res)=>{
  try {
    const {token, username} = req.body;

    const result = await userServices.authenticationToken(username, token);
    res.json({result})
  } catch (error) {
    
  }
}

module.exports = {
  login,
  register,
  verifyToken
};
