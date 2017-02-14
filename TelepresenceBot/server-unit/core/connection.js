function Connection(client) {
    this.client = client;
    this.connectedTo = undefined;
}

Connection.prototype.connectTo = function(clientToConnectTo) {
    this.connectedTo = clientToConnectTo;
};

Connection.prototype.disconnect = function() {
    this.connectedTo = undefined;
}

module.exports = Connection;
