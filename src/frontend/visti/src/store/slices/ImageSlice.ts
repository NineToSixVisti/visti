import { createSlice, PayloadAction } from '@reduxjs/toolkit';

type ImageState = {
  selectedImage: File | null;
};

const initialState: ImageState = {
  selectedImage: null,
};

const imageSlice = createSlice({
  name: 'image',
  initialState,
  reducers: {
    setSelectedImage: (state, action: PayloadAction<File | null>) => {
      state.selectedImage = action.payload;
    },
  },
});

export const { setSelectedImage } = imageSlice.actions;
export default imageSlice.reducer;
