import React, {useState} from 'react';
import styled from 'styled-components';
import { useSelector } from 'react-redux';
import TextEditor from './TextEditor';
import { RootState } from '../../store';
import CreateImageComponent from './CreateImageButton';
import NewStoryBar from './NewStoryBar';

const MainContainer = styled.div`
  width: 100%;
  height: 100%;

  overflow-x: hidden;
  overflow-y: hidden;
`;

const StoryCreatorWrap = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;     
  height: 100vh;
  background-color: #f4f4f4;
`;

const ImageContainer = styled.div`
  position: relative;
  width: 100%;
  height: 70vh;
  margin-top: 5vh; 
  background-size: cover; 
  background-repeat: no-repeat;
  background-position: center;
  background-image: ${({ backgroundImage }: { backgroundImage: string }) => `url(${backgroundImage})`};
`;

const TextEditorStyle = styled.div`
  position: absolute;
  top: 0; 
  left: 0;
  width: 80%; 
  zIndex: 1;
`;

const CreateImageComponentStyle = styled.div`
  position: absolute;
  bottom: 0; 
  right: 0; 
  zIndex: 1;
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
      <MainContainer>
        <NewStoryBar />
        <StoryCreatorWrap>
          <ImageContainer id="image-container" backgroundImage={selectedImage || 'defaultImageUrl'}>
            <TextEditorStyle>
              <TextEditor onEditorOpen={handleEditorOpen} onEditorClose={handleEditorClose} />
            </TextEditorStyle>
            <CreateImageComponentStyle>
              {/* ... */}
            </CreateImageComponentStyle>
          </ImageContainer>
          <CreateImageComponent />
        </StoryCreatorWrap>
      </MainContainer>
    );
}

export default StoryCreator;
