import { createSlice, PayloadAction } from '@reduxjs/toolkit';


interface ImageState {
  image: Blob | null;
  cid: string | null;
}

const initialState: ImageState = {
  image: null,
  cid: null,
};


const imageSlice = createSlice({
  name: 'image',
  initialState,
  reducers: {
    setImage: (state, action: PayloadAction<Blob>) => {
      state.image = action.payload;
    },
    setCID: (state, action: PayloadAction<string>) => {
      state.cid = action.payload;
    },
    clearImage: (state) => {
      state.image = null;
      state.cid = null;
    },
  },
});


export const { setImage, setCID, clearImage } = imageSlice.actions;
export default imageSlice.reducer;
