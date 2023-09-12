import { configureStore } from '@reduxjs/toolkit';
import imageReducer from './slices/ImageSlice';

const store = configureStore({
  reducer: {
    image: imageReducer,
  },
});


export { store };

export type RootState = ReturnType<typeof store.getState>;