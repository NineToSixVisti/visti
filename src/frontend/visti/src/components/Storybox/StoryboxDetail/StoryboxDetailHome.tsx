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

interface StoryboxInfo {
name: string;
boxImgPath?: string;
blind?: boolean;
createdAt: string;
finishedAt: string;
}

interface StoryInfo {
  id: number;
  encryptedId: string;
  storyBoxId: number;
  member: {
    nickname: string;
    profilePath: string | null;
    status: boolean;
  };
  mainFileType: string;
  mainFilePath: string;
  blind: boolean;
  like: boolean;
  createdAt: string;
  finishedAt: string;
};

const StoryboxDetail: React.FC = () => {
  const navigate = useNavigate();
  const { id } = useParams<{id:string}>();
  const [tap, setTap] = useState<string>('story');
  
  const [storyboxInfo, setStoryboxInfo] = useState<StoryboxInfo>({name : '',createdAt : ' ', finishedAt : ' '});
  const [storyInfo, setStoryInfo] = useState<StoryInfo[]>([]);

  const [remainingTime, setRemainingTime] = useState<string>(''); // 종료시간까지의 타이머 시간

  useEffect(() => {
    const updateRemainingTime = () => {
      const now = new Date();
      const finishDate = new Date(storyboxInfo.finishedAt);
      const diff = finishDate.getTime() - now.getTime();

      if (diff <= 0) {
        setRemainingTime('00:00:00');
        return;
      }

      const hours = Math.floor(diff / (1000 * 60 * 60));
      const minutes = Math.floor((diff % (1000 * 60 * 60)) / (1000 * 60));
      const seconds = Math.floor((diff % (1000 * 60)) / 1000);
      
      const formattedTime = `${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}`;
      setRemainingTime(formattedTime);
    };

    updateRemainingTime();
    const intervalId = setInterval(updateRemainingTime, 1000);

    return () => {
      clearInterval(intervalId);
    };
  }, [storyboxInfo.finishedAt]);

  const getStoryboxInfo = useCallback(async () => {
    try  {
      const data = await authInstance.get(`story-box/${id}/info`)
      if (data) {
        setStoryboxInfo(data.data.detail);
        // console.log(data.data.detail)
      }
    }
    catch (err) {
      console.log('스토리박스 Info GET 중 에러 발생', err)
    }
  }, [id]);

  const getStoryInfo = useCallback(async () => {
    try {
      const data = await authInstance.get(`story-box/${id}/story-list`)
      if (data) {
        setStoryInfo(data.data.detail.content)
        console.log(data.data.detail.content)
      }
    }
    catch (err) {
      console.log('스토리Info GET 중 에러 발생', err)
    }
  }, [id])

  useEffect(()=>{
    getStoryboxInfo();
    getStoryInfo();
  }, [getStoryboxInfo, getStoryInfo]);

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
          <p>
          {storyboxInfo.name.length > 10 ? `${storyboxInfo.name.substring(0, 10)}...` : storyboxInfo.name}</p>
          <ModifySvg/>
        </FirstTop>
        <TopMian bgImage={storyboxInfo.boxImgPath}>
          <div></div>
          <div>
            <p>스토리 생성 가능 시간</p>
            <p>{remainingTime}</p>
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
        {tap === 'story' && <Story storyInfo={storyInfo}/>}
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