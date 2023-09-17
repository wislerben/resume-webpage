const express = require('express');
const fs = require('fs'); // Require the 'fs' module
const cors = require('cors'); // Import the 'cors' middleware
const app = express();
// Use the 'cors' middleware to enable CORS
app.use(cors());
//const port = 3000; // Specify the port you want your server to listen on
// Define your API routes and middleware here
app.get('/resume', (req, res) => {
  // Read the contents of 'db.json' synchronously
  try {
    const path = require('path');
    const filePath = path.join(__dirname, 'db.json');
    const jsonData = fs.readFileSync(filePath, 'utf8');
    const data = JSON.parse(jsonData);
    res.json(data);
  } catch (error) {
    console.error('Error reading db.json:', error);
    res.status(500).json({ error: 'Internal server error' });
  }
});
// Start your server
const port = process.env.PORT || 3000;
app.listen(port, () => {
  console.log(`Server is running on port ${port}`);
});
//// Start the server
//app.listen(port, () => {
//  console.log(`Server is running on port ${port}`);
//});
const { exec } = require('child_process');

// Function to get the current Git branch name
function getCurrentGitBranch() {
  return new Promise((resolve, reject) => {
    exec('git rev-parse --abbrev-ref HEAD', (error, stdout, stderr) => {
      if (error) {
        reject(error);
        return;
      }
      const branchName = stdout.trim();
      resolve(branchName);
    });
  });
}

// Example route to get the Git branch name
app.get('/api/git-branch', async (req, res) => {
  try {
    const branchName = await getCurrentGitBranch();
    res.json({ branchName });
  } catch (error) {
    console.error('Error getting Git branch:', error);
    res.status(500).json({ error: 'Internal Server Error' });
  }
});
