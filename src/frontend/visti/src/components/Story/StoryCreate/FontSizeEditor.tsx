import React, { useState } from 'react';

interface FontSizeProps {
  setFontSize: (size: number) => void;
}

const FontSize: React.FC<FontSizeProps> = ({ setFontSize }) => {
  const [currentValue, setCurrentValue] = useState(30);  

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const newValue = Number(e.target.value);
    setCurrentValue(newValue);  
    setFontSize(newValue);
  };

  return (
    <div>
      <label>글자 크기: </label>
      <input 
        type="range" 
        min="10" 
        max="50" 
        value={currentValue}  
        onChange={handleChange} 
      />
    </div>
  );
}

export default FontSize;
