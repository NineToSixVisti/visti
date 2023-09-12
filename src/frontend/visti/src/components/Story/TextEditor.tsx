import React, { useState } from 'react';
import styled from 'styled-components';
import FontSize from './FontSizeEditor';
import ColorEditor from './ColorEditor';
import Draggable from 'react-draggable'; 

const EditorContainer = styled.div`
  position: relative;
`;

const StyledTextarea = styled.textarea<{ fontSize: number, color: string }>`
  background: transparent;
  border: 1px solid #ccc;
  width: 300px;
  height: 100px;
  font-size: ${props => props.fontSize}px;
  color: ${props => props.color};
`;

const FinalText = styled.div<{ fontSize: number, color: string }>`
  font-size: ${props => props.fontSize}px;
  color: ${props => props.color};
`;

const TextEditor: React.FC = () => {
  const [editorText, setEditorText] = useState('');
  const [finalText, setFinalText] = useState('');
  const [fontSize, setFontSize] = useState(14);
  const [color, setColor] = useState('#000000');
  const [editorVisible, setEditorVisible] = useState(false);

  const handleComplete = () => {
    setFinalText(editorText);
    setEditorVisible(false);
  };

  return (
    <EditorContainer>
        <button onClick={() => setEditorVisible(!editorVisible)}>T</button>
        {editorVisible && (
            <>
                <ColorEditor setColor={setColor} currentColor={color} />
                <FontSize setFontSize={setFontSize} />
                <button onClick={handleComplete}>완료</button>
                <StyledTextarea 
                    value={editorText} 
                    onChange={(e) => setEditorText(e.target.value)} 
                    fontSize={fontSize}
                    color={color}
                />
            </>
        )}
     
        {!editorVisible && (
            <Draggable>
                <FinalText fontSize={fontSize} color={color}>
                    {finalText}
                </FinalText>
            </Draggable>
        )}
    </EditorContainer>
  );
}

export default TextEditor;