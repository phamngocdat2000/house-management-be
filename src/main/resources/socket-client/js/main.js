'use strict';

var messageArea = document.querySelector('#messageArea');
var stompClient = null;
var username = null;

function connect(event) {
    var socket = new SockJS('http://localhost:8080/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, onConnected, onError);
    event.preventDefault();
}


function onConnected() {
    stompClient.subscribe('/post/49', onMessageReceived);
}


function onError(error) {

}



function onMessageReceived(payload) {
    console.log(payload);
    var messageElement = document.createElement('li');
    messageElement.classList.add('event-message');

    var textElement = document.createElement('p');
    var messageText = document.createTextNode(payload.body);
    textElement.appendChild(messageText);

    messageElement.appendChild(textElement);

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}


usernameForm.addEventListener('submit', connect, true)
