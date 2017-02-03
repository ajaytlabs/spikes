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
        joinAsBot(socket, callback)
    })

});

var addClient = function(socket) {
    console.log("new client connected: " + socket.id);
    sockets = socket;
}

var joinAsBot = function(user, callback) {
    if(!bots.contains(user.id)) {
        bots.addBot(user.id);
        callback(bots.bots());
        console.log('bot connected: ' + user.id);
    }
}
