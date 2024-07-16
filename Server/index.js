require("dotenv").config();
const express = require("express");
const cors = require("cors");
const { engine } = require("express-handlebars");
const path = require('path');

const userRoutes = require("./routes/UserRoute");
const movieRoutes = require("./routes/MovieRoute");
const adminRoutes = require("./routes/AdminRoute");
const app = express();

// Handlebars Middleware
app.engine('.hbs', engine({
  extname: '.hbs',
  defaultLayout: 'main',
  layoutsDir: path.join(__dirname, './views/layouts'),
  partialsDir: path.join(__dirname, 'views/layouts/partials')
}));
app.set('view engine', 'hbs');
app.set('views', path.join(__dirname, 'views'));
app.use(express.static(__dirname + '\\views\\public'));


app.use(express.json());
app.use(cors());

// Set the port number
const PORT = process.env.HOST_PORT || 8000;
const HOST = process.env.DB_HOST || 'localhost';

app.use('/user', userRoutes);
app.use('/movie', movieRoutes);
app.use('/admin', adminRoutes);

// Start the server
app.listen(PORT, HOST, () => {
  console.log(`Server is running on ${HOST}:${PORT}`);
});
