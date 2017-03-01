var io = require('socket.io').listen(5000);
var Connection = require("./connection.js");

var useTestClient = false;

var botClients = {};
var humanClients = {};

io.sockets.on('connection', function (client) {

    client.on('test_fetch_bots', function(callback) {
        callback(toKeysArrayFrom(botClients));
    })

    client.on('test_fetch_humans', function(callback) {
        console.log('test_fetch_humans');
        callback(toKeysArrayFrom(humanClients));
    })

    client.on('connect_bot', function() {
        connectBot(client);
    });

    client.on('connect_human', function() {
        connectHuman(client);
    });

    client.on('disconnect', function() {
        var human = humanClients[client.id];
        if(human != undefined && human.connectedTo != undefined) {
            var bot = botClients[human.connectedTo];
            botClients[bot.client.id] = new Connection(bot, undefined);
        }

        var bot = botClients[client.id];
        if(bot != undefined && bot.connectedTo != undefined) {
            humanClients[bot.connectedTo].disconnect();
        }

        delete humanClients[client.id];
        delete botClients[client.id];

        console.log('disconnected: ' + client.id);
    });

});

function connectBot(client, callback) {
    var bot = new Connection(client, undefined);
    botClients[client.id] = bot;
    console.log('Bot connected: ' + client.id);
}

function toKeysArrayFrom(objects) {
    return Object.keys(objects).map(toConnectionWithOnlyIds(objects));
}

function toConnectionWithOnlyIds(objects) {
    return function(key) {
        return new Connection(key, objects[key].connectedTo);
    }
}

function connectHuman(client, callback) {
    var bot = findAvailableBot();

    if(bot == undefined) {
        client.disconnect();
    } else {
        var human = new Connection(client, bot.client.id);
        var bot = new Connection(bot.client, client.id);
        humanClients[client.id] = human;

        console.log('Bot available: ' + bot.client.id);
        console.log('Human connected: ' + client.id);
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
