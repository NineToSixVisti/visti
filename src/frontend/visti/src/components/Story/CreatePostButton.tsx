import React, { useState, useRef } from 'react';
import Fab from '@mui/material/Fab';
import AddIcon from '@mui/icons-material/Add';
import PhotoUploader from './PhotoUploader';
import { useSelectedImage } from './SelectImageContext';
import { useNavigate } from 'react-router-dom';

function CreatePostButton() {
    const [isMenuOpen, setIsMenuOpen] = useState(false);
    const fileInputRef = useRef<HTMLInputElement>(null);
    const { setSelectedImage } = useSelectedImage();
    const navigate = useNavigate();

    const handleMenuToggle = () => {
        setIsMenuOpen((prev) => !prev);
    };

    const handleGalleryClick = () => {
        fileInputRef.current?.click();
        setIsMenuOpen(false);
    };

    const handleTextClick = () => {
        console.log('Text clicked');
        setIsMenuOpen(false);
    };

    const handleImageUpload = (image: File) => {
        setSelectedImage(image);
        navigate('/storyCreator'); 
    };

    return (
        <div style={{ position: 'relative' }}>
            <Fab style={{ backgroundColor: '#F09E98' }} aria-label="add" onClick={handleMenuToggle}>
                <AddIcon style={{ color: '#FFFFFF' }} />
            </Fab>
            {isMenuOpen && (
                <div style={{ position: 'absolute', top: '60px', background: '#fff', border: '1px solid #ccc', borderRadius: '4px', zIndex: 1 }}>
                    <button style={{ display: 'block', width: '100%', padding: '8px', border: 'none', background: 'none', textAlign: 'left' }} onClick={handleGalleryClick}>사진 작성</button>
                    <button style={{ display: 'block', width: '100%', padding: '8px', border: 'none', background: 'none', textAlign: 'left' }} onClick={handleTextClick}>글 작성</button>
                </div>
            )}
            <PhotoUploader ref={fileInputRef} onImageUpload={handleImageUpload} />
        </div>
    );
}

export default CreatePostButton;
