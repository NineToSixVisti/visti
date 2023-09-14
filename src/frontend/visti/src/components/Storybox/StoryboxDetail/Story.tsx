import React, { useState } from 'react'
import styled from 'styled-components';
import { ReactComponent as Empty } from '../../../assets/images/story_empty.svg'
import { ReactComponent as Favorite } from '../../../assets/images/favorite.svg'

type StoryProps = {
  isStory : boolean
};

const Story: React.FC<StoryProps> = ({isStory}) => {
  const [storyList, setStoryList] = useState([1,1,1,1,1,1,1,1,1,1,1,1,1])

  return (
    <>
    {
      (!isStory) ?
        <EmptyWrap>
          {/* <img src={process.env.PUBLIC_URL + "/assets/Visti_icon.png"} alt='보호된 이미지'/> */}
          <Empty/>
          <p>스토리가 하나도 없어요</p>
          <p>추억을 저장해 볼까요?</p>
        </EmptyWrap> :
        <StoryWrap>
          {
            storyList.map(() => {
              return <div>
                <FavoriteSvg/>
              </div>;
            })
          }
        </StoryWrap>  
    }
  </>
  )
}

const EmptyWrap = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  
  >svg {
    padding-top: 120px;
    padding-bottom: 20px;
  }
  
  >p {
    color: #000;
    text-align: center;
    font-size: 32px;
    font-weight: 600;
    margin: 10px 0;
  }
  `

const StoryWrap = styled.div`
  width: 100%;
  display: grid;
  grid-template-columns: repeat(3, 1fr);

  >div{
    height: 124px;
    width: 124px;
    background-color: lightcoral;
    background-image: url("https://pds.joongang.co.kr/news/component/htmlphoto_mmdata/202308/03/9f2025fe-1819-42a3-b5c1-13032da70bc8.jpg");
    background-size: cover;
    position: relative;
  }
`

const FavoriteSvg = styled(Favorite)`
  position: absolute;
  top : 2px;
  right: 2px;
`

export default Story;
