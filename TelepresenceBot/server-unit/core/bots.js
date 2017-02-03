function Bot() {}

Bot.prototype.botStack = [];

Bot.prototype.addBot = function(bot) {
    this.botStack.push(bot);
};

Bot.prototype.removeBot = function(bot) {
    this.botStack.splice(botStack.indexOf(bot), 1);
};

Bot.prototype.bots = function() {
    return this.botStack;
};

Bot.prototype.isBotAvailable = function() {
    return botStack.length > 0;
}

Bot.prototype.contains = function(bot) {
    return this.botStack.includes(bot);
}

module.exports = Bot;
