import React from "react";
import { ethers } from "ethers";
import contractData from "../../../assets/VistiNFT.json"; // visitnft.json 파일의 경로를 지정하세요.

interface NFTButtonProps {
  imageURI: string;
}

const NFTButton: React.FC<NFTButtonProps> = ({ imageURI }) => {
  // 메인넷에 연결된 기본 제공자를 사용합니다.
  const provider = new ethers.providers.Web3Provider((window as any).ethereum);
  const signer = provider.getSigner();
  const contractAddress = "0x124927F5bEEE825697F2E5e1c2Aa0C87ef4a6CCf";
  const contract = new ethers.Contract(
    contractAddress,
    contractData.abi,
    signer
  );

  const createAndSendNFT = async () => {
    try {
      const tx = await contract.create(imageURI);
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

  return <button onClick={createAndSendNFT}>Create NFT</button>;
};

export default NFTButton;
