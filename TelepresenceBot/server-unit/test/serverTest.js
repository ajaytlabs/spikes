var should = require('should');
var io = require('socket.io-client');

var socketURL = 'http://0.0.0.0:5000'

var options ={
    transports: ['websocket'],
    'force new connection': true
};

var chatUser1 = {'name':'Tom'};
var chatUser2 = {'name':'Sally'};
var chatUser3 = {'name':'Dana'};

describe("Chat Server",function() {

    it('Should add new Bot to botStack', function(done) {
        var human = io.connect(socketURL, options);
        var bot = io.connect(socketURL, options);

        human.emit('use_test_socket');

        human.on('connect', function(data) {
            console.log('test human connected');
        })

        bot.on('connect', function(data) {
            console.log('test bot connected');

            bot.emit('join_as_bot', chatUser1, function(text) {
                text.forEach(function (arrayItem) {
                    var x = arrayItem.name;
                    console.log(x);
                });

                bot.disconnect;
                human.disconnect;
                done();
            });
        });

    });

});
