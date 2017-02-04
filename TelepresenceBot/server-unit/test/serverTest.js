var should = require('should');
var io = require('socket.io-client');
var test = require('unit.js');

var socketURL = 'http://0.0.0.0:5000'

var options ={
    transports: ['websocket'],
    'force new connection': true
};

describe("TelepresenceBot Server",function() {

    it('Should add new bot to list of bots on connection.', function(done) {
        var bot = io.connect(socketURL, options);

        bot.emit('use_test_socket');

        bot.on('connect', function(data) {
            bot.emit('connect_bot', callback);
        });

        var callback = function(text) {
            var expectedBots = [bot.id];

            test.array(expectedBots)
                .is(text);

            bot.disconnect();
            done();
        }

    });

    it('Should ignore multiple connections from same bot.', function(done) {
        var bot = io.connect(socketURL, options);

        bot.emit('enable_test_socket');

        bot.on('connect', function(data) {
            bot.emit('connect_bot', callback);
            bot.emit('connect_bot', callback);
        });

        var callback = function(text) {
            var expectedBots = [bot.id];

            test.array(expectedBots)
                .is(text);

            bot.disconnect();
            done();
        }

    });

    it('Should remove bot from list of bots on disconnection.', function(done) {
        var bot = io.connect(socketURL, options);

        bot.emit('enable_test_socket');

        bot.on('connect', function(data) {
            bot.emit('connect_bot', function(){});
            bot.emit('disconnect_bot', callback);
        });

        var callback = function(text) {
            var expectedBots = [];

            test.array(expectedBots)
                .is(text);

            bot.disconnect();
            done();
        }

    });

});
