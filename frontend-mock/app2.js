document.addEventListener('DOMContentLoaded', function () {
    const positionsList = document.getElementById('positionsList');
    const clientIdInput = document.getElementById('clientId');
    const xCoordinateInput = document.getElementById('xCoordinate');
    const yCoordinateInput = document.getElementById('yCoordinate');
    const updatePositionButton = document.getElementById('updatePosition');

    // Create SockJS and STOMP client
    const socket = new SockJS('http://localhost:8080/ws');
    const stompClient = Stomp.over(socket);

    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);

        // Subscribe to the /topic/positions broadcast
        stompClient.subscribe('/topic/positions', function (messageOutput) {
            const positionData = JSON.parse(messageOutput.body);
            displayPosition(positionData);
        });
    }, function (error) {
        console.error('Error connecting to WebSocket: ' + error);
    });

    // Function to display the received position
    function displayPosition(position) {
        const listItem = document.createElement('li');
        listItem.textContent = `Client ID: ${position.clientId}, X: ${position.xCoordinate}, Y: ${position.yCoordinate}`;
        positionsList.appendChild(listItem);
    }

    // Update position when button is clicked
    updatePositionButton.onclick = function () {
        console.log("Trying to send information via WebSocket connection...");
        const clientId = clientIdInput.value;
        const xCoordinate = parseFloat(xCoordinateInput.value);
        const yCoordinate = parseFloat(yCoordinateInput.value);

        // Create position update object
        const positionUpdate = {
            clientId: clientId,
            xCoordinate: xCoordinate,
            yCoordinate: yCoordinate
        };

        // Send the message through STOMP
        stompClient.send("/app/positions", {}, JSON.stringify(positionUpdate));
    };
});
