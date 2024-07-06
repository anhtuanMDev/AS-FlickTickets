const userServices = require('../services/UserService');

async function login(req, res) {
  const { username, password } = req.body;

  try {
    const token = await userServices.login(username, password);
    res.json({ token, message: "Login Successfully", status: true  });
  } catch (error) {
    res.status(401).json({ message: error.message, status: false, token: null });
  }
}

async function register(req,res){
  const {fullName, email, password} = req.body;

  try {
    const result = await userServices.createAccess(fullName, email, password);
    res.json({result})
  } catch (error) {
    res.status(401).json({ message: error.message, status: false, token: null });
  }
}

module.exports = {
  login,
  register
};
