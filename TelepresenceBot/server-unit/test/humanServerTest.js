var should = require('should');
var io = require('socket.io-client');
var test = require('unit.js');

var socketURL = 'http://0.0.0.0:5000'

var options ={
    transports: ['websocket'],
    'force new connection': true
};

describe("TelepresenceHuman Server: Human",function() {

    it('Should add new human to list of humans on connection.', function(done) {
        var human = io.connect(socketURL, options);

        human.on('connect', function(data) {
            human.emit('enable_test_client');
            human.emit('connect_human', assertThatHumanIsAdded);
        });

        var assertThatHumanIsAdded = function(actualHumans) {
            var expectedHumans = [human.id];

            test.array(expectedHumans)
                .is(actualHumans);

            human.disconnect();
            done();
        }

    });

});
