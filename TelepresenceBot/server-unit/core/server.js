var io = require('socket.io').listen(5000);
var Connection = require("./connection.js");

var useTestClient = false;

var botClients = {};
var humanClients = {};

io.sockets.on('connection', function (client) {

    client.on('enable_test_client', function() {
        useTestClient = true;
    });

    client.on('connect_bot', function(callback) {
        connectBot(client, callback);
    });

    client.on('connect_human', function(callback) {
        connectHuman(client, callback);
    });

    client.on('disconnect_human', function(callback) {
        disconnectHuman(client, callback);
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
    var bot = new Connection(client, undefined);
    botClients[client.id] = bot;
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
    var human = new Connection(client, undefined);
    humanClients[client.id] = client;
    var bot = findAvailableBot();
    console.log('Bot available: ' + bot.client.id);
    determineCallback(callback, humanClients);
    console.log('Human connected: ' + client.id);
}

var findAvailableBot = function() {
    return Object.keys(botClients)
                 .map(toValues(botClients))
                 .find(firstUnconnectedBot);
}

var toValues = function(object) {
    return function(key) {
        return object[key];
    }
}

var firstUnconnectedBot = function(bot) {
    return bot.connectedTo == undefined;
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
