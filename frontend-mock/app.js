document.addEventListener('DOMContentLoaded', function () {
    const positionsList = document.getElementById('positionsList');
    const clientIdInput = document.getElementById('clientId');
    const xCoordinateInput = document.getElementById('xCoordinate');
    const yCoordinateInput = document.getElementById('yCoordinate');
    const updatePositionButton = document.getElementById('updatePosition');

    const socket = new WebSocket('ws://localhost:8080/ws'); // CORS!!!

    // Handle incoming messages
    socket.onmessage = function(event) {
        const positionData = JSON.parse(event.data);
        console.log('Message from server:', event.data);
        displayPosition(positionData);
    };

    socket.onerror = function(event) {
        console.error('WebSocket error observed:', event);
    };
    
    // Handle successful connection
    socket.onopen = function(event) {
        console.log('WebSocket connection established:', event);
    };

    // Function to display the received position
    function displayPosition(position) {
        const listItem = document.createElement('li');
        listItem.textContent = `Client ID: ${position.clientId}, X: ${position.xCoordinate}, Y: ${position.yCoordinate}`;
        positionsList.appendChild(listItem);
    }

    // Update position when button is clicked
    updatePositionButton.onclick = function() {
        console.log("Trying to send information via socket connection...");
        const clientId = clientIdInput.value;
        const xCoordinate = xCoordinateInput.value;
        const yCoordinate = yCoordinateInput.value;

        // Create position update object
        const positionUpdate = {
            clientId: clientId,
            xCoordinate: parseFloat(xCoordinate),
            yCoordinate: parseFloat(yCoordinate)
        };

        // Ensure the socket is open before sending a message
        if (socket.readyState === WebSocket.OPEN) {
            socket.send(JSON.stringify(positionUpdate));
        } else {
            console.error("WebSocket is not open. Ready state: " + socket.readyState);
        }
    };
});
