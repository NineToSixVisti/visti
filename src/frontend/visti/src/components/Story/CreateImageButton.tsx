import React from 'react';
import { useSelector } from 'react-redux';
import { RootState } from '../../store';
import html2canvas from 'html2canvas';
import styled from 'styled-components';
import { ReactComponent as CompleteButton } from '../../assets/images/complete_button.svg';

const CompleteButtonStyled = styled.button`
  background: transparent;
  border: none;
  cursor: pointer;
  margin-botton: 10px;
`;

const CreateImageComponent: React.FC = () => {
  const { selectedImage } = useSelector((state: RootState) => state.image);

  const handleCreateImage = () => {
    const node = document.getElementById('image-container'); 
    const textToggleButton = document.getElementById('text-toggle-button'); 
    if (textToggleButton) {
      textToggleButton.style.display = 'none'; 
    }

    if (node) {
      html2canvas(node, { scale: 2 }).then(canvas => {
        canvas.toBlob((blob) => {
          if (blob) {
            const url = URL.createObjectURL(blob);
            const downloadLink = document.createElement('a');
            downloadLink.href = url;
            downloadLink.download = 'mergedImage.png';
            document.body.appendChild(downloadLink);
            downloadLink.click();
            document.body.removeChild(downloadLink);
            URL.revokeObjectURL(url);
          }
        });
      }).catch((error) => {
        console.error('Error generating image:', error);
      }).finally(() => {
        if (textToggleButton) {
          textToggleButton.style.display = 'block'; 
        }
      });
    }
  };

  return <CompleteButtonStyled onClick={handleCreateImage}><CompleteButton /></CompleteButtonStyled>;
}

export default CreateImageComponent;
