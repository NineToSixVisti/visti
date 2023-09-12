import React, { useState } from 'react';
import { useSelector } from 'react-redux';
import TextEditor from './TextEditor';
import { RootState } from '../../store/store';

function StoryCreator() {
    const selectedImage = useSelector((state: RootState) => state.image.selectedImage);

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
                {/* <NewStoryBar/> */}
                <TextEditor /> 
            </div>
        </div>
    );
}

export default StoryCreator;
