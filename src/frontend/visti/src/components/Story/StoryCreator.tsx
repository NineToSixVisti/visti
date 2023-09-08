import React from 'react';
import { useSelectedImage } from './SelectImageContext';

function StoryCreator() {
    const { selectedImage } = useSelectedImage();

    return (
        <div>
            {selectedImage && <img src={URL.createObjectURL(selectedImage)} alt="Selected" />}
        </div>
    );
}

export default StoryCreator;