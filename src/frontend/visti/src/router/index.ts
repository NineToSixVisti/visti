import StoryboxHome from "../components/Storybox/StoryboxHome"
import StoryboxCreate from "../components/Storybox/StoryboxCreate/StoryboxCreate";
import StoryboxDetail from "../components/Storybox/StoryboxDetail/StoryboxDetailHome";


import StoryHome from "../components/Story/StoryCreate/StoryHome"
import StoryCreator from "../components/Story/StoryCreate/StoryCreator";

const routes = [
  {
    path : "/storybox",
    Component : StoryboxHome
  },
  {
    path : "/storyHome",
    Component : StoryHome
  },
  {
    path : "/storybox/join",
    Component : StoryboxCreate
  },
  {
    path : "/storyCreator",
    Component : StoryCreator
  },
  {
    path : "/storybox/detail",
    Component : StoryboxDetail 
  }
]

export default routes;