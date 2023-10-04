import React from "react";
import { ethers } from "ethers";
import contractData from "../../../assets/VistiNFT.json";
import { ReactComponent as NFTOFF } from "../../../assets/images/nft-offbutton.svg";
import { create } from "ipfs-http-client";

interface NFTButtonProps {
  imageURI: string;
}

const ipfs = create({
  host: "j9d102.p.ssafy.io",
  port: 5001,
  protocol: "http",
});

const NFTButton: React.FC<NFTButtonProps> = ({ imageURI }) => {
  const provider = new ethers.providers.Web3Provider((window as any).ethereum);
  const signer = provider.getSigner();
  const contractAddress = "0xE67Af4B30a1ebE1845f2042C3870b8DDc40A3963";
  const contract = new ethers.Contract(
    contractAddress,
    contractData.abi,
    signer
  );

  async function uploadImageToIPFS(imageURL: string): Promise<string> {
    const response = await fetch(imageURL);
    const imageData = await response.blob();
    const added = await ipfs.add(imageData);
    await ipfs.pin.add(added.path);
    console.log("Uploaded to IPFS with CID:", added.path);
    return added.path;
  }

  const createAndSendNFT = async () => {
    try {
      const cid = await uploadImageToIPFS(imageURI);

      const tx = await contract.create(cid);
      await tx.wait();

      const tokenId = await contract.current();
      const userAddress = await signer.getAddress();
      const transferTx = await contract.transfer(userAddress, tokenId);
      await transferTx.wait();

      console.log("NFT successfully created and sent!");
    } catch (error) {
      console.error("Error creating or sending NFT:", error);
    }
  };

  return (
    <button onClick={createAndSendNFT}>
      <NFTOFF />
    </button>
  );
};

export default NFTButton;
