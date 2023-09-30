import { createSlice, PayloadAction } from "@reduxjs/toolkit";

interface StorySlice {
  encryptedId : string;
  file : Blob | null;
};

const initialState : StorySlice = {
  encryptedId : '',
  file : null,
}

const storySlice = createSlice({
  name : 'story',
  initialState,
  reducers : {
    setStoryboxId : (state, action : PayloadAction<string>) => {
      state.encryptedId = action.payload;
    },
    setStoryboxFile : (state, action : PayloadAction<Blob>) => {
      state.file = action.payload
    }
  },
});

export const { setStoryboxId, setStoryboxFile } = storySlice.actions;
export default storySlice.reducer;
