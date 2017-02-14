function Clients() {}

Clients.prototype.clientStack = {};

Clients.prototype.add = function(client) {
    this.clientStack[client.id] = client;
};

Clients.prototype.remove = function(client) {
    delete this.clientStack[client.id];
};

Clients.prototype.contains = function(client) {
    return this.clientStack[client.id] != undefined;
}

Clients.prototype.getClientWith = function(id) {
    return this.clientStack[id];
}

Clients.prototype.toArray = function() {
    return Object.keys(this.clientStack).map(
        function(value) {
            return value;
        }
    );
}

module.exports = Clients;
