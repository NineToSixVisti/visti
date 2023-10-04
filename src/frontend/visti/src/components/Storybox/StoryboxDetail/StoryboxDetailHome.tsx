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
isHost?: boolean;
createdAt: string;
finishedAt: string;
}

type DivProps = {
  bgImage ?: string;
}

const StoryboxDetail: React.FC = () => {
  const navigate = useNavigate();
  const { id } = useParams<{id:string}>();
  const [tap, setTap] = useState<string>('story');
  const [isLoading, setIsLoading] = useState<boolean>(false);

  const [storyboxInfo, setStoryboxInfo] = useState<StoryboxInfo>({name : '',createdAt : ' ', finishedAt : ' '});
  const [remainingTime, setRemainingTime] = useState<string>(''); // 종료시간까지의 타이머 시간
  
  const getStoryboxInfo = useCallback(async () => {
    setIsLoading(true);
    try  {
      const data = await authInstance.get(`story-box/${id}/info`)
      if (data) {
        setStoryboxInfo(data.data.detail);
        // console.log(data.data.detail);
      }
    } catch (err) {
      console.log('스토리박스 Info GET 중 에러 발생', err)
    } finally {
      setIsLoading(false);
    }
  }, [id]);
  
  
  const formatDate = (dateStr: string, includeYear: boolean = true): string => {
    const date = new Date(dateStr);
    const year = String(date.getFullYear()).slice(-2);    
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return includeYear ? `${year}.${month}.${day}` : `${year}.${month}.${day}`;
  }
  
  const formatRange = (start: string, end: string): string => {
    return `${formatDate(start)} ~ ${formatDate(end, false)}`;
  }

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
  
  useEffect(()=>{
    getStoryboxInfo();
  }, [getStoryboxInfo]);
  
  
  return (
    <>
      <TopWrap>
        <FirstTop>
          <GoBackSvg onClick={()=>{navigate("/storybox")}}/>
          <p>
          {storyboxInfo.name.length > 12 ? `${storyboxInfo.name.substring(0, 12)}...` : storyboxInfo.name}</p>
          {storyboxInfo.isHost && <ModifySvg onClick={()=>{navigate("/storybox/join", {state : { storyboxId : id }})}}/> } 
        </FirstTop> 
          <TopMian>
            <BgImageDiv bgImage={storyboxInfo.boxImgPath}></BgImageDiv>
            {
              isLoading ? 
              <LoadingWrap>
                <p>Loading...</p>
              </LoadingWrap>
              :
              remainingTime !== "00:00:00" ?
              <TLBoxInfo>
                <p>스토리 생성 가능 시간</p>
                <p>{remainingTime}</p>
                <p>{formatRange(storyboxInfo.createdAt, storyboxInfo.finishedAt)}</p>
              </TLBoxInfo> :
              <BoxInfo>
                <p>"인생은 단 한번의 추억여행이야"</p>
                {/* <p>추억 시간</p> */}
                <p>{formatRange(storyboxInfo.createdAt, storyboxInfo.finishedAt)}</p>
              </BoxInfo>
            }
          </TopMian>
      </TopWrap>
      <MainWrap>
        <Tap 
          tap={tap}
          setStory={() => { setTap('story'); }} 
          setMember={() => { setTap('member'); }} 
          setDetail={() => { setTap('detail'); }}/>
        {tap === 'story' && <Story id={id}/>}
        {tap === 'member' && <Member id={id}/>}
        {tap === 'detail' && <Detail id={id}/>}
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

const TopMian = styled.div`
  width: 100%;
  height: 70%;
  display: flex;

  /* >div:nth-child(2) {
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
  } */
`

const BgImageDiv = styled.div<DivProps>`
  width: 50%;
  height: 100%;
  background-image: url(${props => props.bgImage ? props.bgImage : '/assets/box_Image_input.svg'});
  background-size: cover;
  background-position: center;
  border-radius: 12px;
  display: flex;
`

const TLBoxInfo = styled.div`
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
`

const BoxInfo = styled.div`
  width: 50%;
  height: 100%;
  margin: 0 10px ;
  text-align: center;

  >p {
    font-weight: 600;
    margin : 10px 0;
    line-height: 32px;
  }

  >p:first-child {
    background-color: rgba(255, 235, 230, 0.7);
    font-size: 18px;
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

const LoadingWrap = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 100%;
  color: #000;
  font-weight: 600;
  font-size: 30px;
  z-index: 999;
`

export default StoryboxDetail