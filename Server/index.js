require("dotenv").config();
const {poolPromise} = require("./configs/db")
const express = require("express");

const app = express();

app.use(express.json());

// Set the port number
const PORT = process.env.PORT || 6000;
const HOST = process.env.DB_HOST;

// Start the server
app.listen(PORT, () => {
  console.log(`Server is running on ${HOST}:${PORT}`);
});