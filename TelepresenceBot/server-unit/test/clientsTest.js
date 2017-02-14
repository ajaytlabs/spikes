var should = require('should');
var test = require('unit.js');
var Connections = require('../core/connections.js');

describe("Connections first class collection",function() {

    it('Should add new bot to list of connections.', function(done) {
        var connections = new Connections();
        var mockClient = { id: 'id', data: 'data'};

        connections.addConnectionFor(mockClient);

        test.object(connections.getConnectionWith(mockClient))
            .is(mockClient);

        done();
    });

    it('Should remove previous bot when a bot with the same id is added.', function(done) {
        var connections = new Connections();
        var mockClient = { id: 'id1', data: 'data1'};
        var mockClientWithSameId = { id: 'id1', data: 'data2'};

        connections.addConnectionFor(mockClient);
        connections.addConnectionFor(mockClientWithSameId);

        test.object(connections.getConnectionWith(mockClient))
            .is(mockClientWithSameId);

        done();
    });

    it('Should not contain bot when bot is removed from connections.', function(done) {
        var connections = new Connections();
        var mockClient = { id: 'id', data: 'data'};

        connections.addConnectionFor(mockClient);
        connections.removeConnectionWith(mockClient);

        test.bool(connections.containsConnectionWith(mockClient))
            .isNotTrue();

        done();
    });

});
