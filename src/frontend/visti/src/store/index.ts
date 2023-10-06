import { configureStore } from '@reduxjs/toolkit';
import imageReducer from './slices/ImageSlice';
import textReducer from './slices/TextSlices'; 
import storyReducer from './slices/storySlice';

const store = configureStore({
  reducer: {
    image: imageReducer,
    text: textReducer, 
    story: storyReducer,
  },
});

export { store };

export type RootState = ReturnType<typeof store.getState>;
