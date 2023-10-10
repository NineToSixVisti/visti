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
    console.log(" 영수증 ", result)
    console.log("2 ========================");
    const tokenId = result.logs[0].args.tokenId;
    console.log(" 발급받은 토큰 id ", tokenId);
    console.log("3 ========================");
    assert.equal(await contract.tokenURI(tokenId), uri, "URI should be set correctly");
    console.log(" 토큰 id로 NFT조회 [event를 쏴서 바로 받아 볼 수 있도록 했습니다.]",await contract.tokenURI(tokenId));
    console.log("4 ========================");
  });

  it('should return all tokens of owner', async () => {
    const owner = accounts[0];
    console.log("1 ========================");
    const ownedTokens = await contract.tokensOfOwner(owner);
    console.log(" 발급받은 토큰 조회[List 형식] ", ownedTokens);
    console.log("2 ========================");
    assert.equal(ownedTokens.length, 1, "Owner should have one token");
    console.log(" 발급받은 토큰 갯수[지금은 1개 발급했으니 1입니다.] ", ownedTokens.length);
    console.log("3 ========================");
    assert.equal(ownedTokens[0], 1, "Token ID should be correct");
    console.log(" 토큰 id로 NFT조회 ",ownedTokens[0])
    console.log("4 ========================");
  });
});