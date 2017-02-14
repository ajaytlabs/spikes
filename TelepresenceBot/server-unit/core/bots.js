function Bots() {}

Bots.prototype.botStack = [];

Bots.prototype.add = function(bot) {
    if(!this.contains(bot)) {
        this.botStack.push(bot);
    }
};

Bots.prototype.remove = function(bot) {
    if(this.contains(bot)) {
        this.botStack.splice(this.botStack.indexOf(bot), 1);
    }
};

Bots.prototype.bots = function() {
    return this.botStack;
};

Bots.prototype.contains = function(bot) {
    return this.botStack.includes(bot);
}

Bots.prototype.tryRetrieveBot = function() {
    return this.botStack.shift();
}

module.exports = Bots;
