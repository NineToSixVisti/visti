import StoryboxHome from "../components/Storybox/StoryboxHome"
import StoryHome from "../components/Story/StoryHome"
import StoryCreator from "../components/Story/StoryCreator";
const routes = [
  {
    path : "/",
    Component : StoryboxHome
  },
  {
    path : "/storyHome",
    Component : StoryHome
  },
  {
    path : "/storyCreator",
    Component : StoryCreator
  }

]

export default routes;