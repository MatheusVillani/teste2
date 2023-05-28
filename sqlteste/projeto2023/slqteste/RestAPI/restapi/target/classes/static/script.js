var contas = [];

const express = require('express');
const app = express();
const port = 3000; // Use any port number you prefer

// API endpoint to handle creating a new conta
app.post('/api/contas', (req, res) => {
  // Handle creating a new conta here
  // Parse the request body and perform the necessary operations
  // Return the appropriate response
});

// Start the server
app.listen(port, () => {
  console.log(`Server running on port ${port}`);
});


///////// CRUD //////////

async function createConta() {
  const id = document.getElementById('new_conta_id').value;
  const nome = document.getElementById('new_conta_nome').value;
  const saldo = document.getElementById('new_conta_saldo').value;
  const agencia = document.getElementById('new_conta_agencia').value;

  const postOptions = {
    method: 'POST',
    body: JSON.stringify({ id, nome, saldo, agencia }),
    headers: {
      "Content-Type": "application/json",
    },
  }

  try { 
    const response = await fetch('/api/contas', postOptions);
    await showContas();
  } catch(err) {
    console.error(err);
  }
}

