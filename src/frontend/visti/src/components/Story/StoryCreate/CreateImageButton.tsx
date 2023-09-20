import React from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { RootState } from '../../../store';
import html2canvas from 'html2canvas';
import styled from 'styled-components';
import { ReactComponent as CompleteButton } from '../../../assets/images/complete_button.svg';
import { create } from 'ipfs-http-client';
import { setImage, setCID } from '../../../store/slices/MergeImageSlice'; // Redux actions를 import합니다.

const ipfs = create({
  host: 'j9d102.p.ssafy.io',
  port: 5001,
  protocol: 'http'
});

const CompleteButtonStyled = styled.button`
  background: transparent;
  border: none;
  cursor: pointer;
  margin-bottom: 10px;
`;

const CreateImageComponent: React.FC = () => {
  const dispatch = useDispatch();
  const { selectedImage } = useSelector((state: RootState) => state.image);

  const handleCreateImage = async () => {
    const node = document.getElementById('image-container'); 
    const textToggleButton = document.getElementById('text-toggle-button'); 
    if (textToggleButton) {
      textToggleButton.style.display = 'none'; 
    }

    if (node) {
      try {
        const canvas = await html2canvas(node, { scale: 2 });
        canvas.toBlob(async (blob) => {
          if (blob) {
            const url = URL.createObjectURL(blob);
            const downloadLink = document.createElement('a');
            downloadLink.href = url;
            downloadLink.download = 'mergedImage.png';
            document.body.appendChild(downloadLink);
            downloadLink.click();
            document.body.removeChild(downloadLink);
            URL.revokeObjectURL(url);
    
            // IPFS에 이미지 업로드
            // const file = new File([blob], 'mergedImage.png', { type: 'image/png' });
            // const added = await ipfs.add(file);
            // console.log("Uploaded to IPFS with CID:", added.path);
    
            // CID를 Redux store에 저장
            // dispatch(setCID(added.path));
          } 
        }, 'image/png');
      } catch (error) {
        console.error('Error generating image:', error);
      } finally {
        if (textToggleButton) {
          textToggleButton.style.display = 'block'; 
        }
      }
    }
  };

  return <CompleteButtonStyled onClick={handleCreateImage}><CompleteButton /></CompleteButtonStyled>;
}

export default CreateImageComponent;
