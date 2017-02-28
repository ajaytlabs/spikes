function Clients() {};

Clients.prototype.humanClients = {};
Clients.prototype.botClients = {};
Clients.prototype.communicatingClients = {}; // bot key and human value;


Clients.prototype.addHuman = function(client) {
    this.humanClients[client.id] = client;
};

Clients.prototype.beginCommunicationBetween(botClient, humanClient) {
    this.communicatingClients[botClient.id] = humanClient.id;
};

Clients.prototype.removeHuman = function(client) {
    delete this.humanClients[client.id];
};

Clients.prototype.addBot = function(client) {
    this.botClients[client.id] = client;
};

Clients.prototype.removeBot = function(client) {
    delete this.botClients[client.id];
};

Clients.prototype.isBotConnected = function(client) {
    return this.communicatingClients[client.id];
};

Clients.prototype.tryGetAvailableBot = function() {
    for(var bot in botClients) {
        if(communicatingClients[bot.id] == undefined) {
            return bot;
        }
    }
    return undefined;
};

Clients.prototype.toArray = function() {
    return Object.keys(this.ClientsStack).map(
        function(value) {
            return value;
        }
    );
};

module.exports = Clients;
