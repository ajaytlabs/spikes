var io = require('socket.io').listen(5000);
var Connections = require("./connections.js");

var useTestClient = false;

var botClients = {};
var humanClients = {};
var connections = new Connections();

io.sockets.on('connection', function (client) {

    client.on('enable_test_client', function() {
        useTestClient = true;
    });

    client.on('connect_human', function(callback) {
        connectHuman(client, callback);
    });

    client.on('connect_bot', function(callback) {
        connectBot(client, callback);
    });

    client.on('disconnect_bot', function(callback) {
        disconnectBot(client, callback);
    });

    client.on('disconnect', function() {
        disconnectBot(client);
        disconnectHuman(client);
    });

});

var connectBot = function(client, callback) {
    botClients[client.id] = client;
    determineCallback(callback, botClients);
    console.log('Bot connected: ' + client.id);
}

var determineCallback = function(callback, clients) {
    if(callback == undefined) {
        return;
    } else if(useTestClient) {
        callback(toKeysArrayFrom(clients));
    } else {
        callback("message");
    }
}

var toKeysArrayFrom = function(objects) {
    return Object.keys(objects).map(
        function(value) {
            return value;
        }
    );
}

var connectHuman = function(client, callback) {
    humanClients[client.id] = client;
    determineCallback(callback, humanClients);
    console.log('Human connected: ' + client.id);
}

var disconnectHuman = function(client, callback) {
    if(humanClients[client.id] != undefined) {
        delete humanClients[client.id];
        determineCallback(callback, humanClients);
        console.log('Human disconnected: ' + client.id);
    }
}

var disconnectBot = function(client, callback) {
    if(botClients[client.id] != undefined) {
        delete botClients[client.id];
        determineCallback(callback, botClients);
        console.log('Bot disconnected: ' + client.id);
    }
}
