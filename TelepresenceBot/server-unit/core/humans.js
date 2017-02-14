var humanModule = require("./human.js");

function Humans() {}

Humans.prototype.humanStack = {};

Humans.prototype.add = function(human) {
    this.humanStack[human.clientId] = human;
};

Humans.prototype.remove = function(human) {
    delete this.humanStack[human.clientId];
};

Humans.prototype.contains = function(human) {
    return this.humanStack[human.clientId] != undefined;
}

Humans.prototype.getHumanWith = function(clientId) {
    return this.humanStack[clientId];
}

Humans.prototype.toArray = function() {
    return Object.keys(this.humanStack).map(
        function(value) {
            return value;
        }
    );
}

module.exports = Humans;
