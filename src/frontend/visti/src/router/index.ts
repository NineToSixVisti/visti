import StoryboxHome from "../components/Storybox/StoryboxHome"
import StoryboxCreate from "../components/Storybox/StoryboxCreate/StoryboxCreate";
import StoryboxDetail from "../components/Storybox/StoryboxDetail/StoryboxDetailHome";

import StoryHome from "../components/Story/StoryCreate/StoryHome";
import StoryCreator from "../components/Story/StoryCreate/StoryCreator";
import StoryDetail from "../components/Story/StoryDetail/StoryDetail";
import LetterCreator from "../components/Story/StoryCreate/LetterCreator"
import Test from "../components/Storybox/StoryboxDetail/LinkCheck/Test";
import InviteCheck from "../components/Storybox/StoryboxDetail/LinkCheck/InviteCheck";




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
    path : "/lettercreator",
    Component : LetterCreator
  },
  {
    path : '/',
    Component : Test
  },
  {
    path : "/invite/:encryptedData",
    Component : InviteCheck
  },
]
 

export default routes;