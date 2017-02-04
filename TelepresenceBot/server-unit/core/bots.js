function Bots() {}

Bots.prototype.botStack = [];

Bots.prototype.addBot = function(bot) {
    if(!this.contains(bot)) {
        this.botStack.push(bot);
    }
};

Bots.prototype.removeBot = function(bot) {
    if(this.contains(bot)) {
        this.botStack.splice(this.botStack.indexOf(bot), 1);
    }
};

Bots.prototype.bots = function() {
    return this.botStack;
};

Bots.prototype.isBotAvailable = function() {
    return botStack.length > 0;
}

Bots.prototype.contains = function(bot) {
    return this.botStack.includes(bot);
}

module.exports = Bots;
