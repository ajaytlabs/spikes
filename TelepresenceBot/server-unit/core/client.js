function Client(client) {
    this.client = client;
    this.connectedTo = undefined;
}

Client.prototype.connectTo = function(clientToConnectTo) {
    this.connectedTo = clientToConnectTo;
};

module.exports = Client;
