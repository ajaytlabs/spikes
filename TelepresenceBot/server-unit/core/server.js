var io = require('socket.io').listen(5000);
var Connection = require("./connection.js");

var useTestClient = false;

var botClients = {};
var humanClients = {};

io.sockets.on('connection', function (client) {

    console.log('connecting: ' + client.id);

    client.on('join_as_bot', function(callback, acknowledgement) {
        connectBot(client);
        acknowledgement('bot connected: ' + client.id);
    });

    client.on('join_as_human', function(callback, acknowledgement) {
        connectHuman(client);
        acknowledgement('human connected: ' + client.id);
    });

    client.on('move_in', function(direction) {
        var humanConnection = humanClients[client.id];

        if(humanConnection.connectedTo != undefined) {
            console.log('move_in', direction);
            var botConnectedWith = botClients[humanConnection.connectedTo].client;
            io.sockets.emit('move_in', direction);
            console.log('sendingTo: ', botConnectedWith.id);
        }
    });

    client.on('disconnect', function() {
        console.log('disconnecting: ' + client.id);
        disconnectHuman(client);
        disconnectBot(client);

        io.sockets.emit('disconnect_human', toKeysArrayFrom(humanClients));
        io.sockets.emit('disconnect_bot', toKeysArrayFrom(botClients));
    });

});

function connectBot(client) {
    var bot = new Connection(client, undefined);
    botClients[client.id] = bot;
    console.log('Bot connected: ' + client.id);
    io.sockets.emit('connect_bot', toKeysArrayFrom(botClients));
}

function toKeysArrayFrom(objects) {
    return Object.keys(objects).map(toConnectionWithOnlyIds(objects));
}

function toConnectionWithOnlyIds(objects) {
    return function(key) {
        return new Connection(key, objects[key].connectedTo);
    }
}

function connectHuman(client) {
    var bot = findAvailableBot();

    if(bot == undefined) {
        client.disconnect();
    } else {
        console.log('Bot available: ' + bot.client.id);

        var human = new Connection(client, bot.client.id);
        var bot = new Connection(bot.client, client.id);
        humanClients[client.id] = human;
        console.log('Human connected: ' + client.id);
        io.sockets.emit('connect_human', toKeysArrayFrom(humanClients));
    }
}

function disconnectHuman(client) {
    var human = humanClients[client.id];
    if(human != undefined) {
        delete humanClients[client.id];
        if(human.connectedTo != undefined && botClients[human.connectedTo] != undefined) {
            var bot = botClients[human.connectedTo];
            bot.client.disconnect();
        }
    }
}

function disconnectBot(client) {
    var bot = botClients[client.id];
    if(bot != undefined) {
        delete botClients[client.id];
        if(bot.connectedTo != undefined && botClients[human.connectedTo] != undefined) {
            var human = humanClients[bot.connectedTo];
            human.client.disconnect();
        }
    }
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
