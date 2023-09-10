import React from 'react';
import { useSelectedImage } from './SelectImageContext';
import TextEditor from './TextEditor';

function StoryCreator() {
    const { selectedImage } = useSelectedImage();

    const containerStyle: React.CSSProperties = {
        position: 'relative',
        width: '100%', 
        height: '500px', 
        backgroundImage: selectedImage ? `url(${URL.createObjectURL(selectedImage)})` : undefined,
        backgroundSize: 'cover',
        backgroundPosition: 'center',
    };

    const editorStyle: React.CSSProperties = {
        position: 'absolute',
        top: '50%', 
        left: '50%',
        transform: 'translate(-50%, -50%)',
        width: '80%', 
        zIndex: 1,
    };

    return (
        <div style={containerStyle}>
            <div style={editorStyle}>
                <TextEditor />
            </div>
        </div>
    );
}

export default StoryCreator;
