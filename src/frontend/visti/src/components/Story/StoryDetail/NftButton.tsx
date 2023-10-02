import React from "react";
import { ethers } from "ethers";
import contractData from "../../../assets/VistiNFT.json";
import { ReactComponent as NFTOFF } from "../../../assets/images/nft-offbutton.svg";
interface NFTButtonProps {
  imageURI: string;
}

const NFTButton: React.FC<NFTButtonProps> = ({ imageURI }) => {
  const provider = new ethers.providers.Web3Provider((window as any).ethereum);
  const signer = provider.getSigner();
  const contractAddress = "0x124927F5bEEE825697F2E5e1c2Aa0C87ef4a6CCf";
  const contract = new ethers.Contract(
    contractAddress,
    contractData.abi,
    signer
  );

  async function uploadToIPFS(imagePath: string): Promise<string> {
    const data = new FormData();
    data.append('file', imagePath);

    const response = await fetch("http://j9d110.p.ssafy.io:5001/api/v0/add", {
      method: 'POST',
      body: data
    });

    const result = await response.json();
    console.log("CID:", result.Hash);
    return result.Hash; 
  }

  const createAndSendNFT = async () => {
    try {
      const cid = await uploadToIPFS(imageURI);
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

  return <button onClick={createAndSendNFT}><NFTOFF/></button>;
};

export default NFTButton;
