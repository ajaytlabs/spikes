var io = require('socket.io').listen(5000);
var botModule = require("./bots.js");

var bots = new botModule();
var useTestSocket = false;

io.sockets.on('connection', function (socket) {

    socket.on('use_test_socket', function() {
        useTestSocket = true;
    });

    socket.on('join_as_bot', function(user, callback) {
        console.log('bot joined ' + user.name);
        bots.addBot(user);

        if(useTestSocket) {
            callback(bots.bots());
        }
    })

});
