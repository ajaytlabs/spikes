var should = require('should');
var io = require('socket.io-client');
var test = require('unit.js');
var Connection = require("../core/connection");

var socketURL = 'http://0.0.0.0:5000'

var options ={
    transports: ['websocket'],
    'force new connection': true
};

describe("TelepresenceBot Server: Bot",function() {

    it('Should add new bot to list of bots on connection.', function(done) {
        var bot = io.connect(socketURL, options);
        var testObserver = io.connect(socketURL, options);

        bot.on('connect', function(data) {
            bot.emit('connect_bot');
            testObserver.emit('test_fetch_bots', assertThatBotIsAdded);
        });

        var assertThatBotIsAdded = function(actualConnections) {
            var expectedConnection = [new Connection(bot.id)];

            test.array(actualConnections)
                .hasLength(1)
                .contains(expectedConnection);

            bot.disconnect();
            done();
        }

    });

    it('Should ignore multiple connections from same bot.', function(done) {
        var bot = io.connect(socketURL, options);

        bot.on('connect', function(data) {
            bot.emit('enable_test_client');
            bot.emit('connect_bot');
            bot.emit('connect_bot');

            var testObserver = io.connect(socketURL, options);

            testObserver.on('connect', function(data) {
                testObserver.emit('test_fetch_bots', assertThatBotIsAdded);
            });

            var assertThatBotIsAdded = function(actualConnections) {
                var expectedConnection = [new Connection(bot.id)];
                test.array(actualConnections)
                    .hasLength(1)
                    .contains(expectedConnection);

                bot.disconnect();
                done();
            }
        });
    });

    it('Should remove bot from list of bots on disconnection.', function(done) {
        var bot = io.connect(socketURL, options);

        bot.on('connect', function(data) {
            bot.emit('enable_test_client');
            bot.emit('connect_bot');
            bot.disconnect();

            var testObserver = io.connect(socketURL, options);
            testObserver.on('connect', function(data) {
                testObserver.emit('test_fetch_bots', assertThatBotIsRemoved);
            });

            var assertThatBotIsRemoved = function(actualConnections) {
                test.array(actualConnections)
                    .isEmpty();

                bot.disconnect();
                done();
            }
        });

    });

});
