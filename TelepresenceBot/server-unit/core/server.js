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

    socket.on('connect_bot', function(user, callback) {
        connectBot(socket, callback)
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

var connectBot = function(user, callback) {
    if(!bots.contains(user.id)) {
        bots.addBot(user.id);
        callback(bots.bots());
        console.log('Bot connected: ' + user.id);
    }
}

var disconnectBot = function(user) {
    if(bots.contains(user.id)) {
        bots.removeBot(user.id);
        console.log('Bot disconnected: ' + user.id);
    }
}
