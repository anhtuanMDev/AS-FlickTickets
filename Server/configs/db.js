require("dotenv").config();
const express = require("express");
const { driver } = require("mssql/lib/base");
const sql = require("mssql/msnodesqlv8");

const sqlConfig = {
  user: process.env.DB_USER,
  password: process.env.DB_PASSWORD,
  database: process.env.DB_DATABASE,
  server: process.env.DB_SERVER,
  options: {
    encrypt: true, // for Azure
    trustServerCertificate: true,
    trustedConnection: false,
    enableArithAbort: true,
    instancename: "MSSQLSERVER01",
  },
  port: 2101
};

// Create a connection pool
const poolPromise = new sql.ConnectionPool(sqlConfig)
  .connect()
  .then(pool => {
    console.log('Connected to SQL Server');
    return pool;
  })
  .catch(err => {
    console.error('SQL Server connection error', err);
    process.exit(1);
  });

// Test connection
poolPromise.then(pool => {
  console.log('Connection pool created successfully');
  // Perform any additional setup or checks here
}).catch(err => {
  console.error('Error creating connection pool', err);
  process.exit(1);
});


module.exports = {
  sql,
  poolPromise
};