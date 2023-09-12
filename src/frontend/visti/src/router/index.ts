import StoryboxHome from "../components/Storybox/StoryboxHome"
import StoryboxCreate from "../components/Storybox/StoryboxCreate";

import StoryHome from "../components/Story/StoryHome"
import StoryCreator from "../components/Story/StoryCreator";

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
  }

]

export default routes;