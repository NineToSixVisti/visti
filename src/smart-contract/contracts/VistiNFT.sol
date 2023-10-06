// SPDX-License-Identifier: MIT
pragma solidity ^0.8.19;

// import "./token/ERC721/ERC721.sol";
import "./token/ERC721/extensions/ERC721Enumerable.sol";
import "./access/Ownable.sol";
import "@openzeppelin/contracts/utils/Counters.sol";
/**
 * PJT Ⅰ - 과제 2) NFT Creator 구현
 * 상태 변수나 함수의 시그니처는 구현에 따라 변경할 수 있습니다.
 */
contract VistiNFT is ERC721Enumerable, Ownable {

    using Counters for Counters.Counter;
    Counters.Counter private _tokenIds;
    mapping(uint256 => string) private _tokenURIs;

    constructor() ERC721("VistiNFT", "VST") {

    }

    function current() public view returns (uint256) {
        return _tokenIds.current();
    }

    function _exists(uint256 tokenId) internal view returns (bool) {
        return bytes(_tokenURIs[tokenId]).length > 0;
    }

    function tokenURI(uint256 tokenId) public view override returns (string memory) {
        require(_exists(tokenId), "VistiNFT: URI query for nonexistent token");
        return _tokenURIs[tokenId];
    }

    function setTokenURI(uint256 tokenId, string memory _tokenURI) public onlyOwner {
        require(_exists(tokenId), "VistiNFT: URI set of nonexistent token");
        _tokenURIs[tokenId] = _tokenURI;
    }


    event Created(uint256 tokenId);
    function create(string memory _tokenURI) public onlyOwner returns (uint256) {
        _tokenIds.increment();
        uint256 newTokenId = _tokenIds.current();
        _safeMint(owner(), newTokenId);
        _tokenURIs[newTokenId] = _tokenURI;
        
        emit Created(newTokenId);

        return newTokenId;
    }

    function burn(uint256 tokenId) public onlyOwner {
        require(_exists(tokenId), "VistiNFT: Burn of nonexistent token");
        
        // Remove the token URI
        delete _tokenURIs[tokenId];
        
        // Burn the token
        _burn(tokenId);
    }

    function transfer(address to, uint256 tokenId) public {
        require(ownerOf(tokenId) == msg.sender, "VistiNFT: Transfer of token that is not own");
        safeTransferFrom(msg.sender, to, tokenId);
    }

    function tokensOfOwner(address owner) public view returns (uint256[] memory) {
        uint256 tokenCount = balanceOf(owner);
        uint256[] memory ownedTokens = new uint256[](tokenCount);
        for (uint256 i = 0; i < tokenCount; i++) {
            ownedTokens[i] = tokenOfOwnerByIndex(owner, i);
        }
        return ownedTokens;
    }

}