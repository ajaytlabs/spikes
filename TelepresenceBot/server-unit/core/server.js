var io = require('socket.io').listen(5000);
var Bots = require("./bots.js");

var bots = new Bots();
var useTestClient = false;

io.sockets.on('connection', function (client) {

    client.on('enable_test_client', function() {
        useTestClient = true;
    });

    client.on('connect_human', function(callback) {
        // find suitable bot.

        // if no bot available, callback with error and disconnect human.

        // if bot available
        // add bot to human
        // remove bot from bots
        connectBot(client, callback);
    });

    client.on('connect_bot', function(callback) {
        connectBot(client, callback);
    });

    client.on('disconnect_bot', function(callback) {
        disconnectBot(client, callback);

        // notify humans potentially disconnect them with error.
    });

    client.on('disconnect', function() {
        disconnectBot(client);
    });

});

var connectBot = function(client, callback) {
    bots.add(client);
    determineBotCallback(callback);
    console.log('Bot connected: ' + client.id);
}

var determineBotCallback = function(callback) {
    if(callback == undefined) {
        return;
    } else if(useTestClient) {
        callback(bots.toArray());
    } else {
        callback("message");
    }
}

var disconnectBot = function(client, callback) {
    bots.remove(client);
    determineBotCallback(callback);
    console.log('Bot disconnected: ' + client.id);
}
