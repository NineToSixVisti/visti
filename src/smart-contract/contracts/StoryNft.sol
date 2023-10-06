// SPDX-License-Identifier: MIT
pragma solidity ^0.8.19;

import "@openzeppelin/contracts/token/ERC721/extensions/ERC721Enumerable.sol";
import "@openzeppelin/contracts/access/Ownable.sol";
import "@openzeppelin/contracts/utils/Counters.sol";

contract StoryNFT is ERC721Enumerable, Ownable {
    using Counters for Counters.Counter;
    Counters.Counter private _tokenIdCounter;
    mapping(uint256 => string) private _tokenURIs;

    constructor() ERC721("StoryNFT", "STORY") {}

    function mint(address recipient, string memory uri) external onlyOwner {
        _tokenIdCounter.increment();
        uint256 newTokenId = _tokenIdCounter.current();
        _safeMint(recipient, newTokenId);
        _setTokenURI(newTokenId, uri);
    }

    function _setTokenURI(uint256 tokenId, string memory uri) internal virtual {
        require(bytes(uri).length > 0, "URI cannot be empty");
        _tokenURIs[tokenId] = uri;
    }

    function tokenURI(
        uint256 tokenId
    ) public view virtual override returns (string memory) {
        string memory uri = _tokenURIs[tokenId];
        require(bytes(uri).length > 0, "Token URI not set");
        return uri;
    }
}
