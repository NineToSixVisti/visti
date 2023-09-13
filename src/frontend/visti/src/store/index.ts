import { configureStore } from '@reduxjs/toolkit';
import imageReducer from './slices/ImageSlice';
import textReducer from './slices/TextSlices'; 

const store = configureStore({
  reducer: {
    image: imageReducer,
    text: textReducer, 
  },
});

export { store };

export type RootState = ReturnType<typeof store.getState>;
