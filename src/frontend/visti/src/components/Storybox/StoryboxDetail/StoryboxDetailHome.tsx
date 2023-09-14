import React, { useState } from 'react'
import styled from 'styled-components';
import { useNavigate } from 'react-router-dom';

import { ReactComponent as GoBack } from '../../../assets/images/back_button.svg'
import { ReactComponent as Modify } from '../../../assets/images/modify_button(pen).svg'

import Tap from './Tap'
import Story from './Story';
import Member from './Member';
import Detail from './Detail';

const StoryboxDetail: React.FC = () => {
  const navigate = useNavigate();
  
  const [tap, setTap] = useState<string>('story');
  const [isStory, setIsStory] = useState(false);

  return (
    <>
      <TopWrap>
        <FirstTop>
          <GoBackSvg onClick={()=>{navigate("/storybox")}}/>
          <p>버니즈</p>
          <ModifySvg/>
        </FirstTop>
        <TopMian>
          <div onClick={() => setIsStory(!isStory)}></div>
          <div>
            <p>스토리 생성 가능 시간</p>
            <p>15:30:30</p>
            <p>2023.07.15 ~ 08.09</p>
          </div>
        </TopMian>
      </TopWrap>
      <MainWrap>
        <Tap 
          tap={tap}
          setStory={() => { setTap('story'); }} 
          setMember={() => { setTap('member'); }} 
          setDetail={() => { setTap('detail'); }}/>
        {tap === 'story' && <Story isStory={isStory}/>}
        {tap === 'member' && <Member />}
        {tap === 'detail' && <Detail />}
      </MainWrap>
    </>
  )
}

const TopWrap = styled.div`
  height: 171px;
  padding : 10px 20px;
`

const FirstTop = styled.div`
  width: 100%;
  height: 30%;
  /* background-color: lightcoral; */
  display: flex;
  justify-content: space-between; 
  align-items: center; 

  >p {
    margin : 0 150px 0 0;
    font-weight: 600;
    font-size: 24px;
  }
`;

const TopMian = styled.div`
  width: 100%;
  height: 70%;
  /* background-color: lightgreen; */
  display: flex;

  >div:first-child {
    width: 50%;
    height: 100%;
    background-image: url('https://www.job-post.co.kr/news/photo/202302/69349_71769_752.png');
    background-size: cover;
    background-position: center;
    border-radius: 12px;
    display: flex;
  }

  >div:nth-child(2) {
    width: 50%;
    height: 100%;
    margin: 0 10px ;
    text-align: center;
  
  >p {
    font-weight: 600;
    margin : 10px 0;
  }

  >p:nth-child(2) {
    font-size: 30px;
  }
  }
`

const MainWrap = styled.div`
  width: 100%;
  height: calc(100vh - 191px);
  /* background-color: lightsalmon; */
`

const GoBackSvg = styled(GoBack)`
  margin-left: 10px;
`

const ModifySvg = styled(Modify)`
  margin-right: 10px;
`

export default StoryboxDetail