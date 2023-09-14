import React, { useState } from 'react'
import styled from 'styled-components';
import { ReactComponent as Empty } from '../../../assets/images/story_empty.svg'

const Story = () => {

  const [isStory, setIsStory] = useState(false);

  return (
    <StoryWrap>
    {
      (!isStory) ?
        <EmptyWrap>
          {/* <img src={process.env.PUBLIC_URL + "/assets/Visti_icon.png"} alt='보호된 이미지'/> */}
          <Empty/>
          <p>스토리가 하나도 없어요</p>
          <p>추억을 저장해 볼까요?</p>
        </EmptyWrap> :
        <StoryWrap>
          <div></div>
        </StoryWrap>  
    }
  </StoryWrap>
  )
}

const StoryWrap = styled.div`
  width: 100%;
  height: 100%;
`

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

export default Story;
