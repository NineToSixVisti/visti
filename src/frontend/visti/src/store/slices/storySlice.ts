import { createSlice, PayloadAction } from "@reduxjs/toolkit";

interface StorySlice {
  encryptedId : string
};

const initialState : StorySlice = {
  encryptedId : '',
}

const storySlice = createSlice({
  name : 'story',
  initialState,
  reducers : {
    setStoryboxId : (state, action : PayloadAction<string>) => {
      state.encryptedId = action.payload;
    },
  },
});

export const { setStoryboxId } = storySlice.actions;
export default storySlice.reducer;
