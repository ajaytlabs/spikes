var io = require('socket.io').listen(5000);
var Bots = require("./bots.js");
var Humans = require("./humans.js");
var Human = require("./human.js");

var bots = new Bots();
var humans = new Humans();
var useTestClient = false;

var clients = {};

io.sockets.on('connection', function (client) {

    connectClient(client);

    client.on('enable_test_client', function() {
        useTestClient = true;
    });

    client.on('connect_bot', function(callback) {
        connectBot(client, callback);
    });

    client.on('connect_human', function(callback) {
        connectHuman(client, callback);
    });

    client.on('disconnect_bot', function(callback) {
        disconnectBot(client, callback);
    });

    client.on('disconnect', function() {
        disconnectClient(client);
        disconnectBot(client);
    });

});

var connectClient = function(client) {
    clients = client;
    console.log("Client connected: " + client.id);
}

var connectBot = function(client, callback) {
    bots.add(client.id);
    determineBotCallback(callback);
    console.log('Bot connected: ' + client.id);
}

var determineBotCallback = function(callback) {
    if(callback == undefined) {
        return;
    } else if(useTestClient) {
        callback(bots.bots());
    } else {
        callback("message");
    }
}

var connectHuman = function(client, callback) {
    var human = new Human(client.id, undefined);
    humans.add(human);
    determineHumanCallback(callback);
    console.log('Human connected: ' + client.id);
}

var determineHumanCallback = function(callback) {
    if(callback == undefined) {
        return;
    } else if(useTestClient) {
        var humansValues = humans.toArray();
        callback(humansValues);
    } else {
        callback("message");
    }
}

var disconnectBot = function(client, callback) {
    bots.remove(client.id);
    determineBotCallback(callback);
    console.log('Bot disconnected: ' + client.id);
}

var disconnectClient = function(client) {
    delete clients[client.id];
    console.log("Client disconnected: " + client.id);
}
