import React, { useState, useRef } from 'react';
import Fab from '@mui/material/Fab';
import AddIcon from '@mui/icons-material/Add';
import PhotoUploader from './PhotoUploader';
import { useNavigate } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { setSelectedImage } from '../../../store/slices/ImageSlice';

function CreatePostButton() {
    const [isMenuOpen, setIsMenuOpen] = useState(false);
    const fileInputRef = useRef<HTMLInputElement>(null);
    const dispatch = useDispatch();
    const navigate = useNavigate();

    const handleMenuToggle = () => {
        setIsMenuOpen((prev) => !prev);
    };

    const handleGalleryClick = () => {
        fileInputRef.current?.click();
        setIsMenuOpen(false);
    };

    const handleTextClick = () => {
        navigate('/LetterCreator');
        setIsMenuOpen(false);
    };

    const handleImageUpload = (image: File) => {
        const imageUrl = URL.createObjectURL(image);
        dispatch(setSelectedImage(imageUrl));
        navigate('/storyCreator'); 
    };
    
    return (
      <div style={{ position: 'fixed', left: '10px', bottom: '10px', zIndex: 1000 }}>
        <div style={{ position: 'relative' }}>
          <Fab style={{ backgroundColor: '#F09E98', zIndex: 2, position: 'relative' }} aria-label="add" onClick={handleMenuToggle}>
              <AddIcon style={{ color: '#FFFFFF' }} />
          </Fab>
          {isMenuOpen && (
              <div style={{ position: 'absolute', bottom: '60px', width: '68px', background: '#fff', border: '1px solid #ccc', borderRadius: '4px', zIndex: 1 }}>
                <button style={{ display: 'block', width: '100%', padding: '8px 4px', border: 'none', background: 'none', textAlign: 'left', fontSize: '14px'}} onClick={handleGalleryClick}>사진 작성</button>
                <button style={{ display: 'block', width: '100%', padding: '8px 4px', border: 'none', background: 'none', textAlign: 'left',  fontSize: '14px'}} onClick={handleTextClick}>글 작성</button>
              </div>
          )}
          <PhotoUploader ref={fileInputRef} onImageUpload={handleImageUpload} />
        </div>
      </div>
    );
}

export default CreatePostButton;
