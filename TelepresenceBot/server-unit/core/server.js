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
        connectBot(client, callback);
    });

    client.on('connect_bot', function(callback) {
        connectBot(client, callback);
    });

    client.on('disconnect_bot', function(callback) {
        disconnectBot(client, callback);
    });

    client.on('disconnect', function() {
        disconnectBot(client);
    });

});

var connectBot = function(client, callback) {
    botClients[client.id] = client;
    determineBotCallback(callback);
    console.log('Bot connected: ' + client.id);
}

var determineBotCallback = function(callback) {
    if(callback == undefined) {
        return;
    } else if(useTestClient) {
        callback(toKeysArrayFrom(botClients));
    } else {
        callback("message");
    }
}

var disconnectBot = function(client, callback) {
    delete botClients[client.id];
    determineBotCallback(callback);
    console.log('Bot disconnected: ' + client.id);
}

var toKeysArrayFrom = function(objects) {
    return Object.keys(objects).map(
        function(value) {
            return value;
        }
    );
}
