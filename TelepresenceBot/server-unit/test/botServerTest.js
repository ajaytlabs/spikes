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

    var bot;

    beforeEach(function(done) {
        bot = io.connect(socketURL, options);
        bot.on('connect', function() {
            console.log('bot_connected...');
            done();
        });
        bot.on('disconnect', function(){
            console.log('bot_disconnected...');
        });
    });

    afterEach(function(done) {
        if(bot.connected) {
            console.log('bot_disconnecting...');
            bot.disconnect();
        }
        done();
    });

    it('Should add new bot to list of bots on connection.', function(done) {
        var bot = io.connect(socketURL, options);

        bot.on('connect', function(actualConnections) {
            bot.emit('connect_bot');

            bot.on('connect_bot', function(actualConnections) {
                var expectedConnection = [new Connection(bot.id, undefined)];

                test.array(actualConnections)
                    .hasLength(1)
                    .contains(expectedConnection);

                bot.disconnect();
                done();
            });
        });
    });

    it('Should remove bot from list of bots on disconnection.', function(done) {
        var bot = io.connect(socketURL, options);
        var testObserver = io.connect(socketURL, options);

        bot.on('connect', function(data) {
            bot.emit('connect_bot');

            testObserver.on('connect_bot', function(data) {
                bot.disconnect();
            });

            testObserver.on('disconnect_bot', function(actualConnections) {
                console.log(actualConnections);
                test.array(actualConnections)
                    .isEmpty();

                testObserver.disconnect();
                done();
            });
        });

    });

});
