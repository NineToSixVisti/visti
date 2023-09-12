import React from 'react';

interface FontSizeProps {
  setFontSize: (size: number) => void;
}

const FontSize: React.FC<FontSizeProps> = ({ setFontSize }) => {
  return (
    <div>
      <label>글자 크기: </label>
      <input type="range" min="10" max="50" onChange={(e) => setFontSize(Number(e.target.value))} />
    </div>
  );
}

export default FontSize;
