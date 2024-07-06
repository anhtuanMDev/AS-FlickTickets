require("dotenv").config();
const express = require("express");
const cors = require("cors");
const userRoutes = require("./routes/UserRoute");
const movieRoutes = require("./routes/MovieRoute");

const app = express();


app.use(express.json());
app.use(cors());

// Set the port number
const PORT = process.env.HOST_PORT || 6000;
const HOST = process.env.DB_HOST || 'localhost';

app.use('/user', userRoutes);
app.use('/movie',movieRoutes);

// Start the server
app.listen(PORT, HOST, () => {
  console.log(`Server is running on ${HOST}:${PORT}`);
});
