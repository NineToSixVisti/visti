const StoryNFT = artifacts.require("StoryNFT");

module.exports = function (deployer) {
    deployer.deploy(StoryNFT);
};