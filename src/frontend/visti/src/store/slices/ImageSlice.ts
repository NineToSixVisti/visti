import { createSlice, PayloadAction } from '@reduxjs/toolkit';

type ImageState = {
  selectedImage: string | null;
  imageHeight: number | null;  
};

const initialState: ImageState = {
  selectedImage: null,
  imageHeight: null,  
};

const imageSlice = createSlice({
  name: 'image',
  initialState,
  reducers: {
    setSelectedImage: (state, action: PayloadAction<string | null>) => {
      state.selectedImage = action.payload;
    },
    setImageHeight: (state, action: PayloadAction<number>) => {  
      state.imageHeight = action.payload;
    },
  },
});

export const { setSelectedImage, setImageHeight } = imageSlice.actions;  
export default imageSlice.reducer;
