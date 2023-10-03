import StoryboxHome from "../components/Storybox/StoryboxHome"
import StoryboxCreate from "../components/Storybox/StoryboxCreate/StoryboxCreate";
import StoryboxDetail from "../components/Storybox/StoryboxDetail/StoryboxDetailHome";

import StoryHome from "../components/Story/StoryCreate/StoryHome";
import StoryCreator from "../components/Story/StoryCreate/StoryCreator";
import StoryDetail from "../components/Story/StoryDetail/StoryDetail";
<<<<<<< HEAD
import LetterCreator from "../components/Story/StoryCreate/LetterCreator"
=======
import Test from "../components/Storybox/StoryboxDetail/LinkCheck/Test";
import InviteCheck from "../components/Storybox/StoryboxDetail/LinkCheck/InviteCheck";



>>>>>>> 404f0d8c88391110c30d7f1e9c7eb87619a2c84c

const routes = [
  {
    path : "/storybox",
    Component : StoryboxHome
  },
  {
    path : "/storyhome",
    Component : StoryHome
  },
  {
    path : "/storybox/join",
    Component : StoryboxCreate
  },
  {
    path : "/storycreator",
    Component : StoryCreator
  },
  {
    path : "/storybox/detail/:id",
    Component : StoryboxDetail 
  },
  {
    path : "/storydetail/:id",
    Component : StoryDetail
  },
  {
<<<<<<< HEAD
    path : "/lettercreator",
    Component : LetterCreator
  }
=======
    path : '/',
    Component : Test
  },
  {
    path : "/invite/:encryptedData",
    Component : InviteCheck
  },
>>>>>>> 404f0d8c88391110c30d7f1e9c7eb87619a2c84c
]
 

export default routes;