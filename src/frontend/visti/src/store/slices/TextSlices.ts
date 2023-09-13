import { createSlice, PayloadAction } from '@reduxjs/toolkit';

type TextState = {
  text: string;
  fontSize: number;
  color: string;
  position: { x: number; y: number };
};

const initialState: TextState = {
  text: '',
  fontSize: 14,
  color: '#000000',
  position: { x: 0, y: 0 },
};

const textSlice = createSlice({
  name: 'text',
  initialState,
  reducers: {
    setText: (state, action: PayloadAction<string>) => {
      state.text = action.payload;
    },
    setFontSize: (state, action: PayloadAction<number>) => {
      state.fontSize = action.payload;
    },
    setColor: (state, action: PayloadAction<string>) => {
      state.color = action.payload;
    },
    setPosition: (state, action: PayloadAction<{ x: number; y: number }>) => {
      state.position = action.payload;
    },
  },
});

export const { setText, setFontSize, setColor, setPosition } = textSlice.actions;
export default textSlice.reducer;
