var should = require('should');
var io = require('socket.io-client');
var test = require('unit.js');

var socketURL = 'http://0.0.0.0:5000'

var options ={
    transports: ['websocket'],
    'force new connection': true
};

describe("TelepresenceBot Server",function() {

    it('Should add new bot to list of bots', function(done) {
        var bot = io.connect(socketURL, options);

        bot.emit('use_test_socket');

        bot.on('connect', function(data) {
            console.log('test bot connected');

            bot.emit('connect_as_bot', bot.id, callback);
        });

        var callback = function(text) {
            console.log('callback received');

            var expectedBots = [bot.id];

            test.array(expectedBots)
                .is(text);

            bot.disconnect;
            done();
        }

    });

});
