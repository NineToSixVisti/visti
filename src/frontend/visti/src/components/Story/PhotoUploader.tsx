import React, { useState } from 'react';

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

export default PhotoUploader;
