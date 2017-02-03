var io = require('socket.io').listen(5000);
var botModule = require("./bots.js");

var bots = new botModule();
var useTestSocket = false;

var sockets = {};

io.sockets.on('connection', function (socket) {

    addClient(socket);

    socket.on('use_test_socket', function() {
        useTestSocket = true;
    });

    socket.on('connect_as_bot', function(user, callback) {
        connectAsBot(socket, callback)
    })

    socket.on('disconnect', function() {
        removeClient(socket);
        disconnectBot(socket)
    })

});

var addClient = function(socket) {
    console.log("Client connected: " + socket.id);
    sockets = socket;
}

var removeClient = function(socket) {
    console.log("Client disconnected: " + socket.id);
    sockets[socket.id] = undefined;
}

var connectAsBot = function(user, callback) {
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
