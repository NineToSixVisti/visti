import React, { useState } from 'react';
import styled from 'styled-components';
import { useSelector } from 'react-redux';
import TextEditor from './TextEditor';
import { RootState } from '../../store';
import CreateImageComponent from './CreateImageButton';
import NewStoryBar from './NewStoryBar';

const StoryCreatorWrap = styled.div`
  width: 100%;
  height: 100%;
  overflow-x: hidden;
  overflow-y: hidden;
`;

const ImageContainer = styled.div`
  position: relative;
  width: 100%;
  height: 500px;
  background-color: #f4f4f4;
`;

const ImageTag = styled.img`
  width: 100%;
  height: 100%;
  object-fit: contain;
`;

const EditorContainer = styled.div`
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 80%;
  z-index: 1;
`;

const TextEditorStyle = styled.div`
  position: absolute;
  top: 0;
  left: 0;
  width: 80%;
  z-index: 1;
`;

const CreateImageComponentStyle = styled.div`
  position: absolute;
  bottom: 0;
  right: 0;
  z-index: 1;
`;

function StoryCreator() {
  const selectedImage = useSelector((state: RootState) => state.image.selectedImage);
  const [isImageDimmed, setImageDimmed] = useState(false);

  const handleEditorOpen = () => {
    setImageDimmed(true);
  };

  const handleEditorClose = () => {
    setImageDimmed(false);
  };

  return (
    <StoryCreatorWrap>
      <NewStoryBar />
      <ImageContainer id="image-container">
        <ImageTag src={selectedImage || 'defaultImageUrl'} alt="Selected Story Image" />
        <TextEditorStyle>
          <TextEditor onEditorOpen={handleEditorOpen} onEditorClose={handleEditorClose} />
        </TextEditorStyle>
        <CreateImageComponentStyle>
        </CreateImageComponentStyle>
      </ImageContainer>
      <CreateImageComponent />
    </StoryCreatorWrap>
  );
}

export default StoryCreator;
