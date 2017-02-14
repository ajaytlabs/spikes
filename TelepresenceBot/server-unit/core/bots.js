function Bots() {}

Bots.prototype.botStack = {};

Bots.prototype.add = function(bot) {
    this.botStack[bot.id] = bot;
};

Bots.prototype.remove = function(bot) {
    delete this.botStack[bot.id];
};

Bots.prototype.contains = function(bot) {
    return this.botStack[bot.id] != undefined;
}

Bots.prototype.getBotWith = function(id) {
    return this.botStack[id];
}

Bots.prototype.toArray = function() {
    return Object.keys(this.botStack).map(
        function(value) {
            return value;
        }
    );
}

Bots.prototype.tryRetrieveBot = function() {
    return this.botStack.shift();
}

module.exports = Bots;
