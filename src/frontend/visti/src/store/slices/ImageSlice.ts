import { createSlice, PayloadAction } from '@reduxjs/toolkit';

type ImageState = {
  selectedImage: string | null;  
};

const initialState: ImageState = {
  selectedImage: null,
};

const imageSlice = createSlice({
  name: 'image',
  initialState,
  reducers: {
    setSelectedImage: (state, action: PayloadAction<string | null>) => {  
      state.selectedImage = action.payload;
    },
  },
});

export const { setSelectedImage } = imageSlice.actions;
export default imageSlice.reducer;
