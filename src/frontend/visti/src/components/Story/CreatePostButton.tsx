import React, { useState } from 'react';
import Fab from '@mui/material/Fab';
import AddIcon from '@mui/icons-material/Add';

function PhotoUploader() {
    const [selectedImage, setSelectedImage] = useState<File | null>(null);

    const handleImageUpload = (e: React.ChangeEvent<HTMLInputElement>) => {
        if (e.target.files) {
            setSelectedImage(e.target.files[0]);
        }
    };

    return (
        <div>
            <input type="file" onChange={handleImageUpload} accept="image/*" />
            {selectedImage && <img src={URL.createObjectURL(selectedImage)} alt="Selected" width="100%" />}
        </div>
    );
}

function CreatePostButton() {
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const [showPhotoUploader, setShowPhotoUploader] = useState(false); // PhotoUploader 컴포넌트의 표시 여부를 제어하는 상태

  const handleMenuToggle = () => {
    setIsMenuOpen((prev) => !prev);
  };

  const handleCameraClick = () => {
    console.log('Camera clicked');
    setIsMenuOpen(false);
  };

  const handleGalleryClick = () => {
    console.log('Gallery clicked');
    setShowPhotoUploader(true); // PhotoUploader 컴포넌트를 표시
    setIsMenuOpen(false);
  };

  const handleTextClick = () => {
    console.log('Text clicked');
    setIsMenuOpen(false);
  };

  return (
    <div style={{ position: 'relative' }}>
      <Fab style={{ backgroundColor: '#F09E98' }} aria-label="add" onClick={handleMenuToggle}>
        <AddIcon style={{ color: '#FFFFFF' }} />
      </Fab>
      {isMenuOpen && (
        <div style={{ position: 'absolute', top: '60px', background: '#fff', border: '1px solid #ccc', borderRadius: '4px', zIndex: 1 }}>
          <button style={{ display: 'block', width: '100%', padding: '8px', border: 'none', background: 'none', textAlign: 'left' }} onClick={handleCameraClick}>카메라</button>
          <button style={{ display: 'block', width: '100%', padding: '8px', border: 'none', background: 'none', textAlign: 'left' }} onClick={handleGalleryClick}>갤러리</button>
          <button style={{ display: 'block', width: '100%', padding: '8px', border: 'none', background: 'none', textAlign: 'left' }} onClick={handleTextClick}>글 작성</button>
        </div>
      )}
      {showPhotoUploader && <PhotoUploader />} 
    </div>
  );
}

export default CreatePostButton;
