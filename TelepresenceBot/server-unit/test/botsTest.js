var should = require('should');
var test = require('unit.js');
var Bots = require('../core/bots.js');

describe("Bots first class collection",function() {

    it('Should add new bot to list of bots.', function(done) {
        var bots = new Bots();
        var mockClient = { id: 'id', data: 'data'};

        bots.add(mockClient);

        test.object(bots.getBotWith(mockClient.id))
            .is(mockClient);

        done();
    });

    it('Should remove previous bot when a bot with the same id is added.', function(done) {
        var bots = new Bots();
        var mockClient = { id: 'id1', data: 'data1'};
        var mockClientWithSameId = { id: 'id1', data: 'data2'};

        bots.add(mockClient);
        bots.add(mockClientWithSameId);

        test.object(bots.getBotWith(mockClient.id))
            .is(mockClientWithSameId);

        done();
    });

    it('Should not contain bot when bot is removed from bots.', function(done) {
        var bots = new Bots();
        var mockClient = { id: 'id', data: 'data'};

        bots.add(mockClient);
        bots.remove(mockClient);

        test.bool(bots.contains(mockClient))
            .isNotTrue();

        done();
    });

});
