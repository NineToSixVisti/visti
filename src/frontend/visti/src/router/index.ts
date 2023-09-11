import StoryboxHome from "../components/Storybox/StoryboxHome"
import StoryHome from "../components/Story/StoryHome"
import StoryboxCreate from "../components/Storybox/StoryboxCreate";


const routes = [
  {
    path : "/storybox",
    Component : StoryboxHome
  },
  {
    path : "/StoryHome",
    Component : StoryHome
  },
  {
    path : "/storybox/join",
    Component : StoryboxCreate
  },

]

export default routes;