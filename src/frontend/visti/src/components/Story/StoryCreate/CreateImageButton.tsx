import React from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { RootState } from '../../../store';
import html2canvas from 'html2canvas';
import styled from 'styled-components';
import { ReactComponent as CompleteButton } from '../../../assets/images/complete_button.svg';
import { setImage, setCID } from '../../../store/slices/MergeImageSlice';

// const ipfs = create({
//   host: 'j9d102.p.ssafy.io',
//   port: 5001,
//   protocol: 'http'
// });

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

            // 이미지를 API로 보내기
            const formData = new FormData();
            formData.append('mainFileType', 'LETTER');
            formData.append('mainFilePath', blob, 'mergedImage.png');
            formData.append('subFileType', 'LETTER');
            formData.append('subFilePath', 'subImage.png'); // 이 부분은 실제 부차적인 이미지 경로로 변경해야 합니다.

            const response = await fetch('/api/story/create', {
              method: 'POST',
              body: formData,
            });

            if (response.ok) {
              console.log('이미지가 성공적으로 업로드되었습니다.');
            } else {
              console.error('이미지 업로드에 실패했습니다.');
            }
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
