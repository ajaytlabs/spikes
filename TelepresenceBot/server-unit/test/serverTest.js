var should = require('should');
var io = require('socket.io-client');
var test = require('unit.js');

var socketURL = 'http://0.0.0.0:5000'

var options ={
    transports: ['websocket'],
    'force new connection': true
};

var chatUser1 = {'name':'Tom'};
var chatUser2 = {'name':'Sally'};
var chatUser3 = {'name':'Dana'};

var expectedArray = [chatUser1];

describe("TelepresenceBot Server",function() {

    it('Should add new bot to list of bots', function(done) {
        var bot = io.connect(socketURL, options);

        bot.emit('use_test_socket');

        bot.on('connect', function(data) {
            console.log('test bot connected');

            bot.emit('connect_as_bot', chatUser1, callback);
        });

        var callback = function(text) {
            console.log('callback received');

            test.array(expectedArray)
                .is(text);

            bot.disconnect;
            done();
        }

    });

});
