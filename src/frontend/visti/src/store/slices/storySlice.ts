import { createSlice, PayloadAction } from "@reduxjs/toolkit";

interface StorySlice {
  encryptedId : string;
  file : Blob | null;
  trigger : boolean
};

const initialState : StorySlice = {
  encryptedId : '',
  file : null,
  trigger: false,
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
    },
    setTrigger: (state, action : PayloadAction<boolean>) => {
      state.trigger = action.payload;
    },
  },
});

export const { setStoryboxId, setStoryboxFile, setTrigger } = storySlice.actions;
export default storySlice.reducer;
