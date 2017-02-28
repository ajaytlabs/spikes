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

function connectBot(client, callback) {
    var bot = new Connection(client, undefined);
    botClients[client.id] = bot;
    determineCallback(callback, botClients);
    console.log('Bot connected: ' + client.id);
}

function determineCallback(callback, clients) {
    if(callback == undefined) {
        return;
    } else if(useTestClient) {
        callback(toKeysArrayFrom(clients));
    } else {
        callback("message");
    }
}

function toKeysArrayFrom(objects) {
    return Object.keys(objects).map(
        function(value) {
            return value;
        }
    );
}

function connectHuman(client, callback) {
    var human = new Connection(client, undefined);
    humanClients[client.id] = client;
    var bot = findAvailableBot();
    console.log('Bot available: ' + bot.client.id);
    determineCallback(callback, humanClients);
    console.log('Human connected: ' + client.id);
}

function findAvailableBot() {
    return toValues(botClients)
            .find(firstUnconnectedBot());
}

function toValues(object) {
    return Object.keys(object).map(function(key) {
        return object[key];
    });
}

function firstUnconnectedBot() {
    return function(bot) {
        return bot.connectedTo == undefined;
    }
}

function disconnectHuman(client, callback) {
    if(humanClients[client.id] != undefined) {
        delete humanClients[client.id];
        determineCallback(callback, humanClients);
        console.log('Human disconnected: ' + client.id);
    }
}

function disconnectBot(client, callback) {
    if(botClients[client.id] != undefined) {
        delete botClients[client.id];
        determineCallback(callback, botClients);
        console.log('Bot disconnected: ' + client.id);
    }
}
