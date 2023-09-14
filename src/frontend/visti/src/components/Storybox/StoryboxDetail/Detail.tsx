import React from 'react'
import styled from 'styled-components';

const Detail = () => {
  return (
    <DetailWrap>
       <MemberStoryWrap>
          <MemberBox>
            <p>인원 수</p>
            <p>8/30</p>
          </MemberBox>
          <StoryBox>
            <p>스토리 수</p>
            <p>12/100</p>
          </StoryBox>
       </MemberStoryWrap>
       <ExplainBox>
        <p>
          {`9기 버니즈의 추억을 위한 공간입니다.
            OOO하기위해 OOOO ~~
            매일 한개씩 업로드 필수입니다!`}  
        </p>
       </ExplainBox>
       <LinkCreate>링크 생성</LinkCreate>
    </DetailWrap>
  )
}

const DetailWrap = styled.div`
  margin-top: 3px;
  height: 100%;
  padding: 20px;
  display: flex;
  flex-direction: column;
`

const MemberStoryWrap = styled.div`
  display: flex;
  justify-content: space-between;
`

const MemberBox = styled.div`
  width: 144px;
  height: 55px;
  border-radius: 10px;
  border: 2px solid #DBDBDB;
  position: relative;
  /* background-color: lightblue; */

 >p{
  margin: 0px;
 } 

  >p:first-child {
    font-size: 12px;
    margin: 5px 0 0 5px;
  }

  >p:nth-child(2) {
    font-size: 20px;
    font-weight: 600;
    position: absolute;
    right: 25px;
  }
`

const StoryBox = styled.div`
  width: 173px;
  height: 54px;
  border-radius: 10px;
  border: 2px solid #DBDBDB;
  position: relative;
  /* background-color: lightgreen; */

  >p{
  margin: 0px;
 } 

  >p:first-child {
    margin: 5px 0 0 5px;
    font-size: 12px;
  }

  >p:nth-child(2) {
    font-size: 20px;
    font-weight: 600;
    position: absolute;
    right: 25px;
  }
`

const ExplainBox = styled.div`
  margin-top: 20px;
  width: 100%;
  /* height: 73px; */
  border-radius: 10px;
  border: 2px solid #DBDBDB;
  white-space: pre-line;
  /* background-color: lightsalmon; */

  >p {
    margin: 0;
    padding: 10px;
    font-size: 16px;
  }
`

const LinkCreate = styled.div`
  margin-top : 20px;
  width: 100%;
  height: 44px;
  border-radius: 6px;
  background-color: #EF9D97;
  color : #fff;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
`

// const LinkBox = styled.div`
//   margin-top: 20px;
//   width: 100%;
//   height: 30px;
//   background-color: lightblue;
// `

export default Detail