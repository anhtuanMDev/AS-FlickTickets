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

module.exports = {
  login,
  register,
  verifyToken
};
