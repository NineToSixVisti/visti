import React from 'react';
import styled from 'styled-components';
import { ReactComponent as TextColorIcon } from '../../assets/images/textcolor.svg';

const ColorPalette = styled.div`
  display: flex;
  align-items: center;
  margin-bottom: 10px;
`;

const ColorBox = styled.div<{ color: string }>`
  width: 20px;
  height: 20px;
  background-color: ${props => props.color};
  margin-right: 5px;
  border: 1px solid #ccc;
  cursor: pointer;
  border-radius: 50%;
`;

const ColorPickerButton = styled.input`
  width: 20px;
  height: 20px;
  border: none;
  border-radius: 50%;
  cursor: pointer;
  padding: 0;
  appearance: none;
  background-color: transparent;

  &::-webkit-color-swatch-wrapper {
    padding: 0;
  }

  &::-webkit-color-swatch {
    border: none;
    border-radius: 50%;
  }
`;

interface ColorEditorProps {
  setColor: (color: string) => void;
  currentColor: string;
}

const ColorEditor: React.FC<ColorEditorProps> = ({ setColor, currentColor }) => {
  const colors = ['#FF0000', '#00FF00', '#0000FF', '#FFFF00', '#FF00FF', '#00FFFF'];

  return (
    <ColorPalette>
      <TextColorIcon fill={currentColor} />
      {colors.map((c) => (
        <ColorBox key={c} color={c} onClick={() => setColor(c)} />
      ))}
      <ColorPickerButton type="color" onChange={(e) => setColor(e.target.value)} />
    </ColorPalette>
  );
}

export default ColorEditor;
