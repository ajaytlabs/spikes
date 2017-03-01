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

    var human;

    beforeEach(function(done) {
        human = io.connect(socketURL, options);
        human.on('connect', function() {
            console.log('human_connected...');
            done();
        });
        human.on('disconnect', function(){
            console.log('human_disconnected...');
        });
    });

    afterEach(function(done) {
        if(human.connected) {
            console.log('human_disconnecting...');
            human.disconnect();
        }
        done();
    });


    it('Should add new human to list of humans on connection when a bot is present.', function(done) {
        var bot = io.connect(socketURL, options);
        var testObserver = io.connect(socketURL, options);

        bot.on('connect', function(data) {
            bot.emit('connect_bot');

            testObserver.on('connect_bot', function(data) {
                human.emit('connect_human');
            });

            testObserver.on('connect_human', function(actualConnections) {
                var expectedConnection = [new Connection(human.id, bot.id)];

                test.array(actualConnections)
                    .hasLength(1)
                    .contains(expectedConnection);

                bot.disconnect();
                testObserver.disconnect();
                done();
            });
        });
    });

    it('Should not add human to list of humans when a bot is absent.', function(done) {
        var testObserver = io.connect(socketURL, options);

        testObserver.on('connect', function(data) {
            human.emit('connect_human');

            testObserver.on('disconnect_human', function(actualConnections) {
                test.array(actualConnections)
                    .isEmpty();

                testObserver.disconnect();
                done();
            });

            testObserver.on('connect_human', function(actualConnections) {
                throw "this assert should be ignored";
            });
        });
    });

    it('Should remove human from list of humans on disconnection.', function(done) {
        var bot = io.connect(socketURL, options);
        var testObserver = io.connect(socketURL, options);

        bot.on('connect', function(data) {
            bot.emit('connect_bot');
            human.emit('connect_human');

            testObserver.on('connect_human', function(data) {
                human.disconnect();
            });

            testObserver.on('disconnect_human', function(actualConnections) {
                test.array(actualConnections)
                    .isEmpty();

                bot.disconnect();
                testObserver.disconnect();
                done();
            });
        });
    });

});
