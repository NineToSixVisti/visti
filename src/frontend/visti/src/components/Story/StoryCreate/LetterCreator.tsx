import React, { useState } from 'react';
import styled from 'styled-components';
import { useSelector } from 'react-redux';
import TextEditor from './TextEditor';
import { RootState } from '../../../store';
import CreateImageComponent from './CreateImageButton';
import NewStoryBar from './NewStoryBar';
import BackGround1 from '../../../assets/images/background1.png'
import BackGround2 from '../../../assets/images/background2.png'
import BackGround3 from '../../../assets/images/background3.png'
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
   ;  
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
const LetterOption = styled.img`
  width: 90px;
  height: 150px;
  margin: 5px;
  cursor: pointer;
`;
const LetterOptionContainer = styled.div`
  position: relative;
  display: inline-block;
  margin: 10px;
`;

const LetterOptionText = styled.div`
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  color: black;
  font-size : 12px;
  font-weight: bold;
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5); 
`;


function LetterCompose() {
    const [selectedLetter, setSelectedLetter] = useState<string | null>(null);
    const [isImageDimmed, setImageDimmed] = useState(false);

    const handleEditorOpen = () => {
        setImageDimmed(true);
    };

    const handleEditorClose = () => {
        setImageDimmed(false);
    };

    const handleLetterSelect = (letterPath: string) => {
        setSelectedLetter(letterPath);
    };

    return (
      <MainContainer>
        <NewStoryBar />
        <StoryCreatorWrap>
          {!selectedLetter && (
            <div>
              <LetterOptionContainer>
                <LetterOptionText>배경 선택</LetterOptionText>
                <LetterOption src={BackGround1} onClick={() => handleLetterSelect(BackGround1)} alt="Background 1" />
              </LetterOptionContainer>
              <LetterOptionContainer>
                <LetterOptionText>배경 선택</LetterOptionText>
                <LetterOption src={BackGround2} onClick={() => handleLetterSelect(BackGround2)} alt="Background 2" />
              </LetterOptionContainer>
              <LetterOptionContainer>
                <LetterOptionText>배경 선택</LetterOptionText>
                <LetterOption src={BackGround3} onClick={() => handleLetterSelect(BackGround3)} alt="Background 3" />
              </LetterOptionContainer>
            </div>
          )}
          {selectedLetter && (
            <ImageContainer id="image-container" backgroundImage={selectedLetter}>
              <TextEditorStyle>
                <TextEditor onEditorOpen={handleEditorOpen} onEditorClose={handleEditorClose} />
              </TextEditorStyle>
            </ImageContainer>
          )}
          <CreateImageComponentStyle>
            <CreateImageComponent />
          </CreateImageComponentStyle>  
        </StoryCreatorWrap>
      </MainContainer>
    );
}
export default LetterCompose;
