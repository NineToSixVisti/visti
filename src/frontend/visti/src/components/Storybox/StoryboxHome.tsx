import React, { useCallback, useEffect, useState } from 'react'
import styled from 'styled-components';
import { useNavigate } from 'react-router-dom';
import { authInstance } from '../../apis/utils/instance'

import { ReactComponent as Lock } from "../../assets/images/lock_white_fill.svg"
import { ReactComponent as CreateBox } from "../../assets/images/storybox-create.svg"
import { ReactComponent as SearchIcon } from '../../assets/images/search_button.svg'

interface Storybox {
  id: number;
  blind : boolean;
  boxImgPath : string;
  createdAt: string;
  finishedAt : string;
  name : string; 
}

type BoxWrapProps = {
  bgImage: string;
};

const StoryboxHome = () => {
  const [storyboxList, setStoryboxList] = useState<Storybox[]>([]);

  const [search, setSerch] = useState("");
  const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setSerch(e.target.value)
  }

  const navigate = useNavigate();
  
  const getStoryboxList = useCallback(async () => {
    try {
      const data = await authInstance.get('story-box/storybox');
      if (data) {
        setStoryboxList(data.data.detail.content);
      }
    } catch (err) {
      console.log('스토리박스 GET 중 에러 발생:', err);
    }
  }, []);
  
  useEffect(() => {
    getStoryboxList();
  }, [getStoryboxList]);
  

  return (
    <StoryboxWWrap>
      <LogoWrap>
        <img src={process.env.PUBLIC_URL +"/assets/Visti-red.png"} alt="Visti Logo"/>
      </LogoWrap>
      <TopWrap>
        <CreateBoxSvg onClick={()=>{navigate("/storybox/join")}}/>
        <SearchWrap>
          <input type="text" value={search} onChange={onChange} />
          <SearchSvg/>
        </SearchWrap>
      </TopWrap>
      {
        storyboxList.length > 0 ? 
          <MainBoxWrap>
            {
              storyboxList.map(storybox => (
                <BoxWrap
                  key={storybox.id}
                  bgImage={storybox.boxImgPath}
                  onClick={()=>{navigate(`/storybox/detail/${storybox.id}`)}}>
                  <NameWrap>
                  <p>
                    {storybox.name.length > 15 ? `${storybox.name.substring(0, 15)}...` : storybox.name}
                    {storybox.blind ? <LockSvg/> : null}
                  </p>
                  </NameWrap>
                </BoxWrap>
              ))
            }            
          </MainBoxWrap> :
          <MainWrap>
            <img src="/assets/storybox-no.svg" alt="" />
            <p>스토리박스가<br/>하나도 없어요.</p>
          </MainWrap>
        }      
    </StoryboxWWrap>
  )
}

const StoryboxWWrap = styled.div`
  width: 100%;
  height: 100%;
`

const LogoWrap = styled.div`
  width: 100%;
  padding: 20px 0;
  display: flex;
  justify-content: center;
  align-items: center;

>img {
  width: 75px;
  height: 33px;
}
`

const TopWrap = styled.div`
  display: flex;
  justify-content: space-between; 
  align-items: center; 
  margin: 0 25px 20px 22px;
  padding: 0 10px; 

  >img {
    margin-right: 10px; 
  }
`;

const SearchWrap = styled.div`
  /* flex-grow: 1;  */
  width: 250px;
  height: 35px;
  position: relative; 

  >input {
    width: 100%;
    height: 100%;
    background-color: #ededed;
    border-radius : 4px;
    border: 0px;
  }
`;


const MainWrap = styled.div`
  width: 100%;
  /* height: calc(100% - 200px); */
  height: 450px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  /* padding: 5px; */

>img {
  width: 103px;
  height: 100px;
}

>p {
  font-weight: 1000;
  font-size: 32px;
  line-height: 1.7;
  margin : 10px 0;
}
`

const MainBoxWrap = styled.div`
  width: 100%;
  /* height: calc(100vh - 121px); */
  display: flex;
  flex-direction: column;

  overflow-y: auto; 
  scrollbar-width: none; // 파이어폭스
  &::-webkit-scrollbar { // 웹킷 기반 브라우저
    display: none;
  }
`

const BoxWrap = styled.div<BoxWrapProps>`
  height: 220px;
  background-image: url(${props => props.bgImage ? props.bgImage : '/assets/box_Image_input.svg'});
  background-size: cover;
  background-repeat: no-repeat;
  background-position: center center;
  border-radius: 12px;

  margin : 0 20px 15px 20px;
  display: flex;
  flex-direction: column-reverse;
`

const NameWrap = styled.div`
  width: auto;
  max-width: 100%;  
  height: 42px;
  border-radius: 12px;
  margin : 8px;

  background-color: rgba(86, 84, 84, 0.55); // 마지막은 투명도
  color : #fff;
  font-size : 18px;
  display: flex;
  justify-content: center;
  align-items: center;
  align-self: flex-start;

>p {
  margin : 0 15px;
  display: flex;
  align-items: center;
  justify-content: center;
}
`

const LockSvg = styled(Lock)`
  margin-left: 3px;
`

const CreateBoxSvg = styled(CreateBox)`
  width: 28px;
  height: 28px;
`

const SearchSvg = styled(SearchIcon)`
  position: absolute; 
  right: 5px; 
  top: 55%; 
  transform: translateY(-50%); 
  width: 20px; 
  height: 20px; 
  cursor: pointer; 
`

export default StoryboxHome