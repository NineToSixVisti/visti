const VistiNFT = artifacts.require('VistiNFT');

contract('VistiNFT', (accounts) => {
  let contract;
  
  before(async () => {
    contract = await VistiNFT.deployed();
  });

  it('should create a new token', async () => {
    const uri = "https://mytoken.com/1";
    console.log("1 ========================");
    const result = await contract.create(uri, { from: accounts[0] });
    const tokenId = result.logs[0].args.tokenId;
    console.log(tokenId);
    console.log("2 ========================");
    assert.equal(await contract.tokenURI(tokenId), uri, "URI should be set correctly");
    console.log(await contract.tokenURI(tokenId));
  });

  it('should return all tokens of owner', async () => {
    const owner = accounts[0];
    console.log("1 ========================");
    const ownedTokens = await contract.tokensOfOwner(owner);
    console.log("2 ========================");
    assert.equal(ownedTokens.length, 1, "Owner should have one token");
    console.log("3 ========================");
    assert.equal(ownedTokens[0], 1, "Token ID should be correct");
    console.log(ownedTokens[0])
    console.log("4 ========================");
  });
});