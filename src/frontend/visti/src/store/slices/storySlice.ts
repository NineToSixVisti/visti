import { createSlice, PayloadAction } from "@reduxjs/toolkit";

interface StorySlice {
  encryptedId : string | null
};

const initialState : StorySlice = {
  encryptedId : null,
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
