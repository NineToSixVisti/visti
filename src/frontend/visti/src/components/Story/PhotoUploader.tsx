import React, { forwardRef } from 'react';

type PhotoUploaderProps = {
  onImageUpload: (image: File) => void;
};

const PhotoUploader = forwardRef<HTMLInputElement, PhotoUploaderProps>((props, ref) => {
  const { onImageUpload } = props;

  const handleImageUpload = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.files) {
      const image = e.target.files[0];
      onImageUpload(image); 
    }
  };

  return (
    <div>
      <input 
        type="file" 
        onChange={handleImageUpload} 
        accept="image/*" 
        ref={ref} 
        style={{ display: 'none' }} 
      />
  
    </div>
  );
});

export default PhotoUploader;