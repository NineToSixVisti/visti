import React from 'react';
import { useSelector } from 'react-redux';
import { RootState } from '../../store';

const CreateImageComponent: React.FC = () => {
  const { selectedImage } = useSelector((state: RootState) => state.image);
  const { text, fontSize, color, position } = useSelector((state: RootState) => state.text);

  const handleCreateImage = () => {
    const canvas = document.createElement('canvas');
    const ctx = canvas.getContext('2d');

    if (ctx && selectedImage) {
      const img = new Image();
      img.src = selectedImage;

      img.onload = () => {
        canvas.width = img.width;
        canvas.height = img.height;
        ctx.drawImage(img, 0, 0);
        ctx.font = `${fontSize}px Arial`;
        ctx.fillStyle = color;
    
      
        ctx.fillText(text, position.x+130, position.y+590);

        const mergedImage = canvas.toDataURL();
const downloadLink = document.createElement('a');
downloadLink.href = mergedImage;
downloadLink.download = 'mergedImage.png';
downloadLink.innerText = 'Download Image';
document.body.appendChild(downloadLink);


      };
    }
  };

  return <button onClick={handleCreateImage}>이미지 생성</button>;
}

export default CreateImageComponent; 
