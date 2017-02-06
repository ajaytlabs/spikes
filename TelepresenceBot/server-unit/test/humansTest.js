var should = require('should');
var test = require('unit.js');
var Humans = require('../core/humans.js');
var Human = require('../core/human.js');

describe("Humans first class collection",function() {

    it('Should add new human to list of humans.', function(done) {
        var humans = new Humans();
        var human = new Human("human1", "bot1");

        humans.add(human);

        test.object(humans.getHumanWith(human.clientId))
            .is(human);

        done();
    });

    it('Should remove previous human when a human with the same id is added.', function(done) {
        var humans = new Humans();
        var human = new Human("human1", "bot1");
        var humanWithSameClientId = new Human("human1", "bot2");

        humans.add(human);
        humans.add(humanWithSameClientId);

        test.object(humans.getHumanWith(human.clientId))
            .is(humanWithSameClientId);

        done();
    });

    it('Should not contain human when human is removed from humans.', function(done) {
        var humans = new Humans();
        var human = new Human("human1", "bot1");

        humans.add(human);
        humans.remove(human);

        test.bool(humans.contains(human))
            .isNotTrue();

        done();
    });

});
