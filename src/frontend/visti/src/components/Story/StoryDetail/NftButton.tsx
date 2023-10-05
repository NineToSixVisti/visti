import React from "react";
import { ethers } from "ethers";
import contractData from "../../../assets/VistiNFT.json";
import { ReactComponent as NFTOFF } from "../../../assets/images/nft-offbutton.svg";
import { create } from "ipfs-http-client";
import NftButton from "../../../assets/images/nftoffbutton2.png";

interface NFTButtonProps {
  imageURI: string;
}

const ipfs = create({
  host: "j9d102.p.ssafy.io",
  port: 5001,
  protocol: "http",
});

const NFTButton: React.FC<NFTButtonProps> = ({ imageURI }) => {
  const ethereum = (window as any).ethereum;

  if (!ethereum) {
    console.error("Ethereum provider not found. Please install MetaMask.");
    return null; // 또는 사용자에게 메시지를 표시하는 컴포넌트를 반환하십시오.
  }
  const provider = new ethers.providers.Web3Provider((window as any).ethereum);
  const signer = provider.getSigner();
  const contractAddress = "0x7424AA05747A46c5057DF5325dA04211a5c46643";
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
    <button
      style={{
        backgroundColor: "transparent",
        border: "none",
        padding: 0,
        width: "40px",
        height: "40px",
        cursor: "pointer",
        outline: "none",
      }}
      onClick={createAndSendNFT}
    >
      <img
        src={NftButton}
        alt="NFT Button"
        style={{
          width: "90%",
          height: "90%",
        }}
      />
    </button>
  );
};

export default NFTButton;
