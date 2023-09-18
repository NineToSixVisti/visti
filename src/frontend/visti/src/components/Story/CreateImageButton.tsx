import React from 'react';
import { useSelector } from 'react-redux';
import { RootState } from '../../store';
import html2canvas from 'html2canvas';

const CreateImageComponent: React.FC = () => {
  const { selectedImage } = useSelector((state: RootState) => state.image);

  const handleCreateImage = () => {
    const node = document.getElementById('image-container'); 
    const textToggleButton = document.getElementById('text-toggle-button'); // T 버튼의 ID
    if (textToggleButton) {
      textToggleButton.style.display = 'none'; // T 버튼 숨기기
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
        textToggleButton.style.display = 'block'; // T 버튼 다시 보이게 하기
      }
    });
  }
};

return <button onClick={handleCreateImage}>이미지 생성</button>;
}

export default CreateImageComponent;