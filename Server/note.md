2 way of fetch data
```js
app.get('/test', async (req, res) => {
  try {
    // Connect to the database
    await sql.connect(sqlConfig);
    
    // Query the database
    const result = await sql.query('SELECT * FROM testDB');
    
    // Send the result as a JSON response
    res.json(result.recordset);
  } catch (err) {
    // Handle any errors
    console.error(err);
    res.status(500).send("Server error");
  }
});
```

```js
app.get('/test', async (req, res) => {
  try {
    // Get a connection from the pool
    const pool = await poolPromise;

    // Query the database
    const result = await pool.request().query('SELECT * FROM testDB');

    // Send the result as a JSON response
    res.json(result.recordset);
  } catch (err) {
    // Handle any errors
    console.error(err);
    res.status(500).send("Server error");
  }
});
```