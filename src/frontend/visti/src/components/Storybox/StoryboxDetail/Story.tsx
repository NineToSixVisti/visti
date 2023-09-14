import React, { useState } from 'react'
import styled from 'styled-components';
import { ReactComponent as Empty } from '../../../assets/images/story_empty.svg'
import { ReactComponent as Favorite } from '../../../assets/images/favorite.svg'

type StoryProps = {
  isStory : boolean,
  isPrivate : boolean
};

const Story: React.FC<StoryProps> = ({isStory, isPrivate}) => {
  const [storyList, setStoryList] = useState([1,1,1,1,1,1,1,1,1,1,1,1,1])

  return (
    <>
    {
      (!isStory) ?
        <EmptyWrap>
          <Empty/>
          <p>스토리가 하나도 없어요</p>
          <p>추억을 저장해 볼까요?</p>
        </EmptyWrap> :
        <StoryWrap>
          {
            storyList.map((a, index) => {
              return <StoryDiv key={index} isPrivate={isPrivate} index={index}>
                  {
                    isPrivate ? <PrivateImg src={process.env.PUBLIC_URL + "/assets/Visti_icon.png"} alt='보호된 이미지'/> : null
                  }
                  {
                    !isPrivate ? <FavoriteSvg/> : null
                  }
              </StoryDiv>;
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
  margin-top: 3px;
  width: 100%;
  display: grid;
  grid-template-columns: repeat(3, 1fr);
`

const StoryDiv = styled.div<{ isPrivate : boolean, index : number }>`
    height: 124px;
    width: 124px;
    background-image: ${props => props.isPrivate ? null : `url("https://pds.joongang.co.kr/news/component/htmlphoto_mmdata/202308/03/9f2025fe-1819-42a3-b5c1-13032da70bc8.jpg")`};
    background-color: ${props => (props.index % 6 >= 3 && props.isPrivate) ? '#FFF2F2' : '#fff'};
    background-size: cover;
    position: relative;
    display : flex;
    justify-content : center;
    align-items : center;
`

const PrivateImg = styled.img`
  width: 80px;
  height: 85px;
`

const FavoriteSvg = styled(Favorite)`
  position: absolute;
  top : 2px;
  right: 2px;
`

export default Story;
