var should = require('should');
var io = require('socket.io-client');
var test = require('unit.js');
var Connection = require("../core/connection");

var socketURL = 'http://0.0.0.0:5000'

var options ={
    transports: ['websocket'],
    'force new connection': true
};

describe("TelepresenceHuman Server: Human",function() {

    it('Should add new human to list of humans on connection when a bot is present.', function(done) {
        var bot = io.connect(socketURL, options);
        var human = io.connect(socketURL, options);

        bot.on('connect', function(data) {
            bot.emit('connect_bot');
            human.emit('connect_human');

            var testObserver = io.connect(socketURL, options);

            testObserver.on('connect', function(data) {
                testObserver.emit('test_fetch_humans', assertThatHumanIsAdded);
            })

            var assertThatHumanIsAdded = function(actualConnections) {
                var expectedConnection = [new Connection(human.id, bot.id)];

                test.array(actualConnections)
                    .hasLength(1)
                    .contains(expectedConnection);

                human.disconnect();
                bot.disconnect();
                testObserver.disconnect();
                done();
            }
        });
    });

    it('Should not add human to list of humans when a bot is absent.', function(done) {
        var human = io.connect(socketURL, options);

        human.on('connect', function(data) {
            human.emit('enable_test_client');
            human.emit('connect_human');

            var testObserver = io.connect(socketURL, options);

            testObserver.on('connect', function(data) {
                testObserver.emit('test_fetch_humans', assertNoHumansAdded);
            })

            var assertNoHumansAdded = function(actualConnections) {
                test.array(actualConnections)
                    .isEmpty();

                human.disconnect();
                testObserver.disconnect();
                done();
            }
        });
    });

    it('Should ignore multiple connections from same human.', function(done) {
        var bot = io.connect(socketURL, options);
        var human = io.connect(socketURL, options);

        bot.on('connect', function(data) {
            bot.emit('connect_bot');
            human.emit('connect_human');
            human.emit('connect_human');

            var testObserver = io.connect(socketURL, options);

            testObserver.on('connect', function(data) {
                testObserver.emit('test_fetch_humans', assertThatHumanIsAdded);
            });

            var assertThatHumanIsAdded = function(actualConnections) {
                var expectedConnection = [new Connection(human.id, bot.id)];

                test.array(actualConnections)
                    .hasLength(1)
                    .contains(expectedConnection);

                human.disconnect();
                bot.disconnect();
                testObserver.disconnect();
                done();
            }
        });
    });

    it('Should remove human from list of humans on disconnection.', function(done) {
        var bot = io.connect(socketURL, options);
        var human = io.connect(socketURL, options);

        bot.on('connect', function(data) {
            bot.emit('connect_bot');
            human.emit('connect_human');

            human.disconnect();

            var testObserver = io.connect(socketURL, options);

            testObserver.on('connect', function(data) {
                testObserver.emit('test_fetch_humans', assertThatHumanIsRemoved);
            })

            var assertThatHumanIsRemoved = function(actualConnections) {
                var expectedConnection = [new Connection(human.id, bot.id)];

                test.array(actualConnections)
                    .isEmpty();

                bot.disconnect();
                testObserver.disconnect();
                done();
            }
        });
    });
});
