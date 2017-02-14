var should = require('should');
var test = require('unit.js');
var Clients = require('../core/clients.js');

describe("Clients first class collection",function() {

    it('Should add new bot to list of clients.', function(done) {
        var clients = new Clients();
        var mockClient = { id: 'id', data: 'data'};

        clients.add(mockClient);

        test.object(clients.getClientWith(mockClient.id))
            .is(mockClient);

        done();
    });

    it('Should remove previous bot when a bot with the same id is added.', function(done) {
        var clients = new Clients();
        var mockClient = { id: 'id1', data: 'data1'};
        var mockClientWithSameId = { id: 'id1', data: 'data2'};

        clients.add(mockClient);
        clients.add(mockClientWithSameId);

        test.object(clients.getClientWith(mockClient.id))
            .is(mockClientWithSameId);

        done();
    });

    it('Should not contain bot when bot is removed from clients.', function(done) {
        var clients = new Clients();
        var mockClient = { id: 'id', data: 'data'};

        clients.add(mockClient);
        clients.remove(mockClient);

        test.bool(clients.contains(mockClient))
            .isNotTrue();

        done();
    });

});
