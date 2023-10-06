import React, { useState } from 'react';
import styled from 'styled-components';
import FontSize from './FontSizeEditor';
import ColorEditor from './ColorEditor';
import Draggable from 'react-draggable';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '../../../store'; 
import { setText, setFontSize, setColor, setPosition } from '../../../store/slices/TextSlices';
import { ReactComponent as TextButtonIcon } from '../../../assets/images/text_button.svg';

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

const TextButton = styled.button`
  background: transparent;
  border: none;
  cursor: pointer;
`;

const FinalText = styled.div<{ fontSize: number, color: string }>`
  font-size: ${props => props.fontSize}px;
  color: ${props => props.color};
`;

const TextEditor: React.FC<{ onEditorOpen: () => void, onEditorClose: () => void }> = ({ onEditorOpen, onEditorClose }) => {
  const dispatch = useDispatch();
  const { text, fontSize, color, position } = useSelector((state: RootState) => state.text);

  const [editorText, setEditorText] = useState(text);
  const [finalText, setFinalText] = useState('');
  const [editorVisible, setEditorVisible] = useState(false);

  const handleComplete = () => {
    dispatch(setText(editorText));
    setFinalText(editorText);
    setEditorVisible(false);
  };

  return (
    <EditorContainer>
        <TextButton 
            id="text-toggle-button"
            onClick={() => {
                setEditorVisible(!editorVisible);
                if (editorVisible) {
                    onEditorClose(); 
                } else {
                    onEditorOpen(); 
                }
            }}
        >
            <TextButtonIcon />
        </TextButton>
        {editorVisible && (
            <div id="editor-tools">
                <ColorEditor setColor={(color: string) => {
                  setColor(color);
                  dispatch(setColor(color));
                }} currentColor={color} />
                <FontSize setFontSize={(size: number) => {
                  setFontSize(size);
                  dispatch(setFontSize(size));
                }} />
                <button onClick={handleComplete}>완료</button>
                <StyledTextarea 
                    value={editorText} 
                    onChange={(e) => {
                      setEditorText(e.target.value);
                      dispatch(setText(e.target.value));
                    }} 
                    fontSize={fontSize}
                    color={color}
                />
            </div>
        )}
     
        {!editorVisible && (
            <Draggable 
              onStop={(e, data) => {
                console.log(`X: ${data.x}, Y: ${data.y}`);
                dispatch(setPosition({ x: data.x, y: data.y }));
              }}
            >
                <FinalText fontSize={fontSize} color={color}>
                    {finalText}
                </FinalText>
            </Draggable>
        )}
    </EditorContainer>
  );
}

export default TextEditor;
