import React, { forwardRef, ChangeEvent } from 'react';  
import { useDispatch } from 'react-redux';
import { setImageHeight } from '../../store/slices/ImageSlice';

type PhotoUploaderProps = { 
  onImageUpload: (image: File) => void;
};

const PhotoUploader = forwardRef<HTMLInputElement, PhotoUploaderProps>((props, ref) => {
  const dispatch = useDispatch();
  const { onImageUpload } = props;

  const handleImageUpload = (e: ChangeEvent<HTMLInputElement>) => {  
    if (e.target.files) {
      const image = e.target.files[0];
      onImageUpload(image);

      const img = new Image();
      img.src = URL.createObjectURL(image);
      img.onload = () => {
        const heightInPixels = img.height;
        dispatch(setImageHeight(heightInPixels));
      };
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