var should = require('should');
var io = require('socket.io-client');
var test = require('unit.js');

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
        });

        human.on('connect', function(data) {
            human.emit('enable_test_client');
            human.emit('connect_human', assertThatHumanIsAdded);
        });

        var assertThatHumanIsAdded = function(actualHumans) {
            var expectedHumans = [human.id];

            test.array(actualHumans)
                .is(expectedHumans);

            human.disconnect();
            bot.disconnect();
            done();
        }

    });

    it('Should not add human to list of humans when a bot is absent.', function(done) {
            var human = io.connect(socketURL, options);

            human.on('connect', function(data) {
                human.emit('enable_test_client');
                human.emit('connect_human', assertIgnored);
            });

            human.on('disconnect', function() {
                done();
            })

            var assertIgnored = function(actualHumans) {
                throw "assert should be ignored.";
                done();
            }
    });

it('Should ignore multiple connections from same human.', function(done) {
        var bot = io.connect(socketURL, options);
        var human = io.connect(socketURL, options);

        bot.on('connect', function(data) {
            bot.emit('connect_bot');
        });

        human.on('connect', function(data) {
            human.emit('enable_test_client');
            human.emit('connect_human', assertThatHumanIsAdded);
            human.emit('connect_human', assertIgnored);
        });

        var assertThatHumanIsAdded = function(actualHuman) {
            var expectedHuman = [human.id];

            test.array(actualHuman)
                .is(expectedHuman);

            human.disconnect();
            bot.disconnect();
            done();
        }

        var assertIgnored = function() {
            throw "assert should be ignored.";
        }

    });

    it('Should remove human from list of humans on disconnection.', function(done) {
        var bot = io.connect(socketURL, options);
        var human = io.connect(socketURL, options);

        bot.on('connect', function(data) {
            bot.emit('connect_bot');
        });

        human.on('connect', function(data) {
            human.emit('enable_test_client');
            human.emit('connect_human', function(){});
            human.emit('disconnect_human', assertThatHumanIsRemoved);
        });

        var assertThatHumanIsRemoved = function(actualHuman) {
            var expectedHuman = [];

            test.array(actualHuman)
                .is(expectedHuman);

            human.disconnect();
            done();
        }

    });

});
