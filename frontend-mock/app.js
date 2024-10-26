// Canvas and checkerboard settings
const canvas = document.getElementById('checkerboard');
const ctx = canvas.getContext('2d');
const rows = 8, cols = 8;
const cellSize = canvas.width / cols;
const color1 = '#f0d9b5';
const color2 = '#b58863';

// Client settings
let clientId;  // Store the client's ID
let clientName; // Store the client's name
let clientPositions = {};  // Stores positions of all clients
let currentPosition = { x: 0, y: 0 };  // Initial position for this client
let stompClient;  // WebSocket client

// Initialize WebSocket connection
function connectWebSocket() {
  const socket = new SockJS('http://localhost:8080/ws');
  stompClient = Stomp.over(socket);

  stompClient.connect({}, () => {
    stompClient.subscribe('/topic/positions', (message) => {
      const data = JSON.parse(message.body);
      clientPositions[data.clientId] = { x: data.xCoordinate, y: data.yCoordinate };
      drawBoard();  // Redraw the board to show updated positions
    });
  });
}

// Draw checkerboard and all client circles
function drawBoard() {
  ctx.clearRect(0, 0, canvas.width, canvas.height);

  // Draw checkerboard pattern
  for (let row = 0; row < rows; row++) {
    for (let col = 0; col < cols; col++) {
      ctx.fillStyle = (row + col) % 2 === 0 ? color1 : color2;
      ctx.fillRect(col * cellSize, row * cellSize, cellSize, cellSize);
    }
  }

  // Draw circles for each client position
  for (const id in clientPositions) {
    const { x, y } = clientPositions[id];
    ctx.beginPath();
    ctx.arc(x * cellSize + cellSize / 2, y * cellSize + cellSize / 2, cellSize / 3, 0, 2 * Math.PI);
    ctx.fillStyle = id === clientId ? 'blue' : 'red';  // Blue for current client, red for others
    ctx.fill();
  }
}

// Send updated position over WebSocket
function sendPosition() {
  stompClient.send('/app/positions', {}, JSON.stringify({
    clientId: clientId,
    xCoordinate: currentPosition.x,
    yCoordinate: currentPosition.y
  }));
}

// Handle arrow key press
document.addEventListener('keydown', (event) => {
  if (!stompClient) return;  // Only handle keys if WebSocket is connected

  // Update current position based on arrow key pressed
  if (event.key === 'ArrowUp' && currentPosition.y > 0) {
    currentPosition.y -= 1;
  } else if (event.key === 'ArrowDown' && currentPosition.y < rows - 1) {
    currentPosition.y += 1;
  } else if (event.key === 'ArrowLeft' && currentPosition.x > 0) {
    currentPosition.x -= 1;
  } else if (event.key === 'ArrowRight' && currentPosition.x < cols - 1) {
    currentPosition.x += 1;
  }

  // Update current position and send to server
  clientPositions[clientId] = { ...currentPosition };
  sendPosition();
  drawBoard();
});

// Register client by sending a POST request
function registerClient() {
  clientId = document.getElementById('clientId').value;  // Get the client ID from input
  clientName = document.getElementById('clientName').value;  // Get the client name from input

  if (!clientId || !clientName) {
    alert("Please enter both client ID and name.");
    return;
  }

  // Register the client via POST request
  fetch('http://localhost:8080/api/clients/connect', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
      name: clientName,  // Include the client name in the registration
      id: clientId,      // Include the client ID in the registration
      status: "connected"
    })
  })
  .then(response => {
    if (response.ok) {
      alert("Registered successfully!");
      connectWebSocket();  // Connect WebSocket after successful registration
    } else {
      alert("Failed to register. Please try again.");
    }
  })
  .catch(error => {
    console.error("Error registering client:", error);
    alert("An error occurred. Please check the console for details.");
  });
}

// Initial draw of the checkerboard
drawBoard();
