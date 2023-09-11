import React, { useState } from 'react';
import ReactQuill from 'react-quill';
import 'react-quill/dist/quill.snow.css'; 

const TextEditor: React.FC = () => {
  const [editorVisible, setEditorVisible] = useState(false);
  const [text, setText] = useState('');
  const [fontSize, setFontSize] = useState('14px');
  const [color, setColor] = useState('#000000');

  const modules = {
    toolbar: [
      [{ 'header': '1'}, {'header': '2'}, { 'font': [] }],
      [{size: []}],
      ['bold', 'italic', 'underline'],
      [{'list': 'ordered'}, {'list': 'bullet'}],
      [{'align': []}],
      ['color'],
      ['clean']                                         
    ],
  };

  return (
    <div style={{ position: 'relative' }}>
      <button onClick={() => setEditorVisible(!editorVisible)}> T</button>
      {editorVisible && (
        <div style={{ position: 'absolute', top: '50px', width: '300px' }}>
          <ReactQuill 
            value={text} 
            onChange={setText} 
            modules={modules}
            style={{ fontSize, color }}
          />
          <div>
            <label>글자 크기: </label>
            <input type="number" value={parseInt(fontSize, 10)} onChange={(e) => setFontSize(`${e.target.value}px`)} />
          </div>
          <div>
            <label>글자 색상: </label>
            <input type="color" value={color} onChange={(e) => setColor(e.target.value)} />
          </div>
        </div>
      )}
    </div>
  );
}

export default TextEditor;
