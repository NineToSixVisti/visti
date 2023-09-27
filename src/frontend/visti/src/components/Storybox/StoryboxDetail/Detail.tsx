import React, { useCallback, useEffect, useState } from 'react'
import styled from 'styled-components';
import { authInstance } from '../../../apis/utils/instance';
import { useNavigate } from 'react-router-dom';

interface storyboxDetail {
  name : string;
  detail : string;
  createdAt: string;
  finishedAt : string;
  memberNum : Number;
  storyNum : Number;
  blind : boolean;
}

interface boxDetailProps {
  id ?: string 
}

const Detail : React.FC<boxDetailProps> = ({id}) => {
  const navigate = useNavigate();
  const [storyboxDetail, setStoryboxDetail] = useState<storyboxDetail>();

  const getStoryboxDetail = useCallback(async () => {
    try{
      const data = await authInstance.get(`story-box/${id}/detail`)
      if (data) {
        setStoryboxDetail(data.data.detail);
        console.log(data.data.detail);
      }
    }
    catch (err) {
      console.log('스토리박스 Detial GET 중 에러 발생', err)
    }
  },[id])

  // 스토리 박스 나가기
  const deleteStorybox = useCallback(async ()=> {
    try {
      await authInstance.delete(`story-box/${id}/delete`)
      console.log('성공적으로 삭제가 완료되었습니다.')
    } catch(err) {
      console.log('스토리박스 나가기 중 에러발생', err);
    } 
  },[id])

  //

  const storyboxOut = () => {
    deleteStorybox();
    navigate('/storybox', { replace : true })
  }

  useEffect(()=>{
    getStoryboxDetail();
  },[getStoryboxDetail])

  return (
    <DetailWrap>
       <MemberStoryWrap>
          <MemberBox>
            <p>멤버 수</p>
            <p>{storyboxDetail ? `${storyboxDetail.memberNum}/30` : 'Loading...'}</p>
          </MemberBox>
          <StoryBox>
            <p>스토리 수</p>
            <p>{storyboxDetail ? `${storyboxDetail.storyNum}/100` : 'Loading...'}</p>
          </StoryBox>
       </MemberStoryWrap>
       <ExplainBox>
        <p>
          {storyboxDetail ? `${storyboxDetail.detail}` : 'Loading...'} 
        </p>
       </ExplainBox>
       <LinkCreate>링크 생성</LinkCreate>
       <BoxOut onClick={storyboxOut}>박스 나가기</BoxOut>
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

const BoxOut = styled.div`
  margin-top : 20px;
  width: 100%;
  height: 44px;
  border-radius: 6px;
  background-color: #717070;
  color : #fff;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
`

export default Detail