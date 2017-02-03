function Bot() {}

Bot.prototype.botStack = [];

Bot.prototype.addBot = function(client) {
    this.botStack.push(client);
};

Bot.prototype.bots = function() {
    return this.botStack;
};

Bot.prototype.isBotAvailable = function() {
    return botStack.length > 0;
}

module.exports = Bot;
