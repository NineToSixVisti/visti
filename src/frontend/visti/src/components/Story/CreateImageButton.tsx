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
        
        // 텍스트 스타일 설정
        ctx.font = `${fontSize+25}px Arial`;
        ctx.fillStyle = color;

        // 텍스트의 기준점을 중앙으로 설정
        ctx.textBaseline = 'middle';
        ctx.textAlign = 'center';

        // 텍스트 그리기
        ctx.fillText(text, position.x, position.y);

        // Blob 객체 생성 및 다운로드 URL 생성
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
      };
    }
};
  return <button onClick={handleCreateImage}>이미지 생성</button>;
}

export default CreateImageComponent; 
