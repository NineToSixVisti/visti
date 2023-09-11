import React, { useState } from 'react'
import styled from 'styled-components';
import { ReactComponent as Lock } from "../../assets/images/lock_white_fill.svg"
import { useNavigate } from 'react-router-dom';

const StoryboxHome = () => {
  const [boxList, setBoxList] = useState(false)
  const [storyboxList, setStoryboxList] = useState(
    {
      "message": "string",
      "statusCode": 0,
      "detail": [
        {
          "id": 0,
          "box_img_path": 'https://www.job-post.co.kr/news/photo/202302/69349_71769_752.png',
          "name": "버니즈",
          "created_at": "2023-09-06T02:28:55.241Z",
          "finished_at": "2023-12-12T02:28:55.241Z",
          "blind": false
        },
        {
          "id": 1,
          "box_img_path": 'https://www.job-post.co.kr/news/photo/202302/69349_71769_752.png',
          "name": "소원",
          "created_at": "2023-07-08T02:28:55.241Z",
          "finished_at": "2023-09-08T02:28:55.241Z",
          "blind": true
        },
        {
          "id": 3,
          "box_img_path": 'https://www.job-post.co.kr/news/photo/202302/69349_71769_752.png',
          "name": "싸피 D102",
          "created_at": "2023-09-08T02:28:55.241Z",
          "finished_at": "2023-10-10T02:28:55.241Z",
          "blind": false
        }
      ]
    }
  )
  const [search, setSerch] = useState("");
  const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setSerch(e.target.value)
  }

  const navigate = useNavigate();

  return (
    <StoryboxWWrap>
      <LogoWrap>
        <img src={process.env.PUBLIC_URL +"/assets/Visti-red.svg"} alt="Visti Logo" onClick={() => setBoxList(boxList => !boxList)}/>
      </LogoWrap>
      <TopWrap>
        <img src={process.env.PUBLIC_URL +"/assets/storybox-create.svg"} alt="create" onClick={()=>{navigate("/storybox/join")}}/>
        <SearchWrap>
          <input type="text" value={search} onChange={onChange} placeholder='검색어 입력'/>
          <img src={process.env.PUBLIC_URL + '/assets/search_button.svg'} alt="search"/>
        </SearchWrap>
      </TopWrap>
      {
        boxList ? 
        <MainWrap>
          <img src="/assets/storybox-no.svg" alt="" />
          <p>스토리박스가<br/>하나도 없어요.</p>
        </MainWrap> :
        <MainBoxWrap>
          <BoxWrap>
            <NameWrap>
              <p>버니즈(2023.01.01 ~ 12.12)<LockSVG></LockSVG></p>
            </NameWrap>
          </BoxWrap>
          <BoxWrap>
            <NameWrap>
              <p>버니즈(2023.01.01 ~ 12.12)</p>
            </NameWrap>
          </BoxWrap>
          <BoxWrap>
            <NameWrap>
              <p>버니즈(2023.01.01 ~ 12.12)</p>
            </NameWrap>
          </BoxWrap>
          <BoxWrap>
            <NameWrap>
              <p>버니즈(2023.01.01 ~ 12.12)</p>
            </NameWrap>
          </BoxWrap>
        </MainBoxWrap>
      }      
    </StoryboxWWrap>
  )
}

const StoryboxWWrap = styled.div`
  min-width: 100vw;
  min-height: 100vh;
`

const LogoWrap = styled.div`
  width: 100%;
  margin: 20px 0;
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
  margin: 10px 25px 20px 22px;
  padding: 0 10px; 

  >img {
    margin-right: 10px; 
  }
`;

const SearchWrap = styled.div`
  flex-grow: 1; 
  width: 272px;
  height: 20px;
  position: relative; 

  >input {
    width: 100%;
    height: 100%;
    border-radius : 8px;
  }

  >img {
    position: absolute; 
    right: 5px; 
    top: 60%; 
    transform: translateY(-50%); 
    width: 15px; 
    height: 15px; 
    cursor: pointer; 
  }
`;


const MainWrap = styled.div`
  width: 100%;
  height: calc(100vh - 200px);
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

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

const BoxWrap = styled.div`
  height: 220px;
  background-image: url('https://www.job-post.co.kr/news/photo/202302/69349_71769_752.png');
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
  max-width: 300px;  // 최대 너비 설정 
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

const LockSVG = styled(Lock)`
  margin-left: 3px;
`

export default StoryboxHome