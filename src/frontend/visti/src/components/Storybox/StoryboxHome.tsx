import React, { useState } from 'react'
import styled from 'styled-components';

const StoryboxHome = () => {
  const [boxList, setBoxList] = useState(true)

  return (
    <StoryboxWWrap>
      <LogoWrap>
        <img src="/assets/Visti-red.svg" alt="Visti Logo"/>
      </LogoWrap>
      <TopWrap>
        <img src="/assets/storybox-create.svg" alt="create" />
        <Search/>
      </TopWrap>
      {
        boxList ? 
        <MainWrap>
          <img src="/assets/storybox-no.svg" alt="" />
          <p>스토리박스가<br/>하나도 없어요.</p>
        </MainWrap> :
        <MainBoxWrap>
          
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
  width: 100%;
  display: flex;
  justify-content: space-evenly;
  margin: 10px 0;
`

const Search = styled.div`
  width: 70%;
  background-color: red;
`


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
  display: flex;
  flex-direction: column;
`

const br = styled.br`

`

export default StoryboxHome