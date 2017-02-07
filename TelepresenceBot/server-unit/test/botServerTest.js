var should = require('should');
var io = require('socket.io-client');
var test = require('unit.js');

var socketURL = 'http://0.0.0.0:5000'

var options ={
    transports: ['websocket'],
    'force new connection': true
};

describe("TelepresenceBot Server: Bot",function() {

    it('Should add new bot to list of bots on connection.', function(done) {
        var bot = io.connect(socketURL, options);

        bot.on('connect', function(data) {
            bot.emit('enable_test_client');
            bot.emit('connect_bot', assertThatBotIsAdded);
        });

        var assertThatBotIsAdded = function(actualBots) {
            var expectedBots = [bot.id];

            test.array(expectedBots)
                .is(actualBots);

            bot.disconnect();
            done();
        }

    });

    it('Should ignore multiple connections from same bot.', function(done) {
        var bot = io.connect(socketURL, options);

        bot.on('connect', function(data) {
            bot.emit('enable_test_client');
            bot.emit('connect_bot', assertThatBotIsAdded);
            bot.emit('connect_bot', assertIgnored);
        });

        var assertThatBotIsAdded = function(actualBots) {
            var expectedBots = [bot.id];

            test.array(expectedBots)
                .is(actualBots);

            bot.disconnect();
            done();
        }

        var assertIgnored = function() {
            throw "assert should be ignored.";
        }

    });

    it('Should remove bot from list of bots on disconnection.', function(done) {
        var bot = io.connect(socketURL, options);

        bot.on('connect', function(data) {
            bot.emit('enable_test_client');
            bot.emit('connect_bot', function(){});
            bot.emit('disconnect_bot', assertThatBotIsRemoved);
        });

        var assertThatBotIsRemoved = function(actualBots) {
            var expectedBots = [];

            test.array(expectedBots)
                .is(actualBots);

            bot.disconnect();
            done();
        }

    });

});
