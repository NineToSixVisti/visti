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
  height: 100vh;
  background-size: contain; 
  background-repeat: no-repeat;
  background-position: center;
  z-index:1;

  background-image: ${({ backgroundImage }: { backgroundImage: string }) => `url(${backgroundImage})`};
  @media (min-width: 768px) {
   ;  // 패드에서의 margin-top 값을 조정
  }
`;

const TextEditorStyle = styled.div`
  position: absolute;
  top: 0; 
  left: 0;
  width: 80%; 
  z-Index: 1;
`;

const CreateImageComponentStyle = styled.div`
  position: absolute;
  bottom: 0; 
  right: 0; 
  z-Index: 100;
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
      
          </ImageContainer>
          <CreateImageComponentStyle>
            <CreateImageComponent />
            </CreateImageComponentStyle>  
        </StoryCreatorWrap>
      </MainContainer>
    );
}

export default StoryCreator;
