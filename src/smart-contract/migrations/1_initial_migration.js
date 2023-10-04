const Migrations = artifacts.require("Migrations");
const VistiNFT = artifacts.require("VistiNFT");

module.exports = function (deployer) {
  deployer.deploy(Migrations);
  deployer.deploy(VistiNFT)
};