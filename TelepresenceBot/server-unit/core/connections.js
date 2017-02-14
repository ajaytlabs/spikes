var Connection = require('./connection.js');

function Connection() {}

Connection.prototype.connectionStack = {};

Connection.prototype.addConnectionTo = function(client) {
    var connection = new Connection(client);
    this.connectionStack[client.id] = client;
};

Connection.prototype.removeConnectionFrom = function(client) {
    delete this.connectionStack[client.id];
};

Connection.prototype.containsConnectionWith = function(client) {
    return this.connectionStack[client.id] != undefined;
}

Connection.prototype.getConnectionWith = function(client) {
    return this.connectionStack[client.id];
}

Connection.prototype.toArray = function() {
    return Object.keys(this.connectionStack).map(
        function(value) {
            return value;
        }
    );
}

module.exports = Connection;
