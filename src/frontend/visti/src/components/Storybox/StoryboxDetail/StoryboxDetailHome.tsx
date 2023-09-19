import React, { useCallback, useEffect, useState } from 'react'
import styled from 'styled-components';
import { useNavigate, useParams } from 'react-router-dom';

import { ReactComponent as GoBack } from '../../../assets/images/back_button.svg'
import { ReactComponent as Modify } from '../../../assets/images/modify_button(pen).svg'

import Tap from './Tap'
import Story from './Story';
import Member from './Member';
import Detail from './Detail';
import { authInstance } from '../../../apis/utils/instance';

const StoryboxDetail: React.FC = () => {
  interface StoryboxInfo {
  name: string;
  boxImgPath?: string;
  blind?: boolean;
  createdAt: string;
  finishedAt: string;
}

  const navigate = useNavigate();
  const { id } = useParams<{id:string}>();
  const [tap, setTap] = useState<string>('story');
  const [storyboxInfo, setStoryboxInfo] = useState<StoryboxInfo>({name : '',createdAt : ' ', finishedAt : ' '});
  const [isStory, setIsStory] = useState<boolean>(false);
  const [isPrivate, setPrivate] = useState<boolean>(false);

  const getStoryboxInfo = useCallback(async () => {
    try  {
      const data = await authInstance.get(`story-box/${id}/info`)
      if (data) {
        setStoryboxInfo(data.data.detail);
        console.log(data.data.detail)
      }
    }
    catch (err) {
      console.log('스토리박스 Info GET 중 에러 발생', err)
    }
  }, [id]);

  useEffect(()=>{
    getStoryboxInfo();
  }, [getStoryboxInfo]);

  const formatDate = (dateStr: string, includeYear: boolean = true): string => {
    const date = new Date(dateStr);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return includeYear ? `${year}.${month}.${day}` : `${month}.${day}`;
  }
  
  const formatRange = (start: string, end: string): string => {
    return `${formatDate(start)} ~ ${formatDate(end, false)}`;
  }

  return (
    <>
      <TopWrap>
        <FirstTop>
          <GoBackSvg onClick={()=>{navigate("/storybox")}}/>
          <p onClick={()=>{setPrivate(!isPrivate)}}>{storyboxInfo.name}</p>
          <ModifySvg/>
        </FirstTop>
        <TopMian bgImage={storyboxInfo.boxImgPath}>
          <div onClick={() => setIsStory(!isStory)}></div>
          <div>
            <p>스토리 생성 가능 시간</p>
            <p>15:30:30</p>
            <p>{formatRange(storyboxInfo.createdAt, storyboxInfo.finishedAt)}</p>
          </div>
        </TopMian>
      </TopWrap>
      <MainWrap>
        <Tap 
          tap={tap}
          setStory={() => { setTap('story'); }} 
          setMember={() => { setTap('member'); }} 
          setDetail={() => { setTap('detail'); }}/>
        {tap === 'story' && <Story isStory={isStory} isPrivate={isPrivate}/>}
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
  display: flex;
  align-items: center; 
  position: relative;

  >p {
    position: absolute;
    left: 40px;
    margin : 0;
    font-weight: 600;
    font-size: 24px;
  }

  >svg:nth-child(3) {
    position: absolute;
    right: 0px;
  }
`;

type DivProps = {
  bgImage ?: string;
}

const TopMian = styled.div<DivProps>`
  width: 100%;
  height: 70%;
  /* background-color: lightgreen; */
  display: flex;

  >div:first-child{
    width: 50%;
    height: 100%;
    background-image: url(${props => props.bgImage ? props.bgImage : '/assets/box_Image_input.svg'});
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
  padding: 10px;
`

const ModifySvg = styled(Modify)`
  margin-right: 10px;
`

export default StoryboxDetail