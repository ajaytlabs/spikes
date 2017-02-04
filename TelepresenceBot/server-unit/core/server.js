var io = require('socket.io').listen(5000);
var botModule = require("./bots.js");

var bots = new botModule();
var useTestSocket = false;

var sockets = {};

io.sockets.on('connection', function (socket) {

    connectClient(socket);

    socket.on('enable_test_socket', function() {
        useTestSocket = true;
    });

    socket.on('connect_bot', function(callback) {
        connectBot(socket, callback)
    })

    socket.on('disconnect_bot', function(callback) {
        disconnectBot(socket, callback)
    })

    socket.on('disconnect', function() {
        disconnectClient(socket);
        disconnectBot(socket);
    })

});

var connectClient = function(socket) {
    sockets = socket;
    console.log("Client connected: " + socket.id);
}

var disconnectClient = function(socket) {
    sockets[socket.id] = undefined;
    console.log("Client disconnected: " + socket.id);
}

var connectBot = function(bot, callback) {
    if(!bots.contains(bot.id)) {
        bots.addBot(bot.id);
        callback(bots.bots());
        console.log('Bot connected: ' + bot.id);
    }
}

var disconnectBot = function(bot, callback) {
    if(bots.contains(bot.id)) {
        bots.removeBot(bot.id);
        determineBotCallback(callback);
        console.log('Bot disconnected: ' + bot.id);
    }
}

var determineBotCallback = function(callback) {
    if(callback == undefined) {
        return;
    } else if(useTestSocket) {
        callback(bots.bots());
    } else {
        callback("message");
    }
}
