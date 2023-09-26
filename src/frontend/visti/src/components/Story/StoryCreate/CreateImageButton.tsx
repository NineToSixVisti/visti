import React, { useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { RootState } from '../../../store';
import html2canvas from 'html2canvas';
import styled from 'styled-components';
import { ReactComponent as CompleteButton } from '../../../assets/images/complete_button.svg';
import { setImage, setCID } from '../../../store/slices/MergeImageSlice';
import { authInstance } from '../../../apis/utils/instance';

const CompleteButtonStyled = styled.button`
  background: transparent;
  border: none;
  cursor: pointer;
  margin-bottom: 10px;
`;

const CreateImageComponent: React.FC = () => {
  const dispatch = useDispatch();
  const { selectedImage } = useSelector((state: RootState) => state.image);
  const storyBoxId = useSelector((state: RootState) => state.story.encryptedId); 

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
            // 이미지를 브라우저에서 다운로드
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
            formData.append('storyBoxId', storyBoxId || '');
            formData.append('mainFileType', 'LETTER');
            formData.append('mainFilePath', blob, 'mergedImage.png');
            formData.append('subFileType', 'LETTER');
            formData.append('subFilePath', 'subImage.png'); 
            try {
              const response = await authInstance.post('/story/create', formData);
              if (response.status === 200) {
                console.log('이미지가 성공적으로 업로드되었습니다.');
              } else {
                console.error('이미지 업로드에 실패했습니다.');
              }
            } catch (error) {
              console.error('API 요청 중 오류가 발생했습니다:', error);
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
  useEffect(()=>{
    console.log(storyBoxId)},[storyBoxId])
  return <CompleteButtonStyled onClick={handleCreateImage}><CompleteButton /></CompleteButtonStyled>;
}

export default CreateImageComponent;
