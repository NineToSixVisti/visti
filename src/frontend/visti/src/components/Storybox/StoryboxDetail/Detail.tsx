import React, { useCallback, useEffect, useState } from 'react'
import styled from 'styled-components';
import { authInstance } from '../../../apis/utils/instance';
import { useNavigate } from 'react-router-dom';
import CryptoJS from 'crypto-js';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from "../../../store"
import { setTrigger } from "../../../store/slices/storySlice"
import Loading from '../../Common/Loading';

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
  const dispatch = useDispatch();
  const trigger = useSelector((state : RootState) => state.story.trigger);
  const [isLoading, setIsLoading] = useState(true);

  const [storyboxDetail, setStoryboxDetail] = useState<storyboxDetail>();
  const [encryptedText, setEncryptedText] = useState('');

  // 만료 날짜를 가져오기 위함 함수
  const getExpirationData = () => {
    const today = new Date();
    today.setDate(today.getDate() + 2);
    
    const year = today.getFullYear();
    const month = String(today.getMonth() + 1).padStart(2, '0');
    const day = String(today.getDate()).padStart(2, '0');
    
    return `${year}-${month}-${day}`;
  }

  // 링크에 포함되어야 하는 정보
  const data = {
    storyboxId : id,
    expirationDay : getExpirationData()
  }

  const salt = process.env.REACT_APP_SECRET_KEY!;

  // 암호화
  const encrypt = (data : any) => {
    if (!data) return '';
    const encrypted = CryptoJS.AES.encrypt(JSON.stringify(data), salt).toString(); 
    const baseEnrypted = btoa(encrypted) // base64로 인코딩(특수기호(/) 때문에)
    // console.log(encrypted);
    setEncryptedText(baseEnrypted);
    return encrypted
  }
  
  const getStoryboxDetail = useCallback(async () => {
    setIsLoading(true);
    try{
      const data = await authInstance.get(`story-box/${id}/detail`)
      if (data) {
        setStoryboxDetail(data.data.detail);
        // console.log(data.data.detail);
      }
    }
    catch (err) {
      console.log('스토리박스 Detial GET 중 에러 발생', err)
    } finally {
      setIsLoading(false);
    }
  },[id])

  // 스토리 박스 나가기
  const deleteStorybox = useCallback(async ()=> {
    try {
      await authInstance.delete(`story-box/${id}/delete`)
      console.log('성공적으로 삭제가 완료되었습니다.')
      dispatch(setTrigger(true));
    } catch(err) {
      console.log('스토리박스 나가기 중 에러발생', err);
    } 
  },[id, dispatch])

  const storyboxOut = () => {
    deleteStorybox();
    dispatch(setTrigger(false));
    navigate('/storybox', { replace : true })
  }

  useEffect(()=>{
    getStoryboxDetail();
  },[getStoryboxDetail])

  useEffect(()=>{
    // console.log(`http://localhost:3000/invite/${encryptedText}`);
  },[encryptedText])

  return (
    <>
      {
        isLoading ? <Loading isLoading={isLoading}/> :
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
              {storyboxDetail ? `${storyboxDetail.name}` : 'Loading...'} 
            </p>
          </ExplainBox>
          <ExplainBox>
            <p>
              {storyboxDetail ? `${storyboxDetail.detail}` : 'Loading...'} 
            </p>
          </ExplainBox>
          {
            encryptedText ? 
              <StoryboxLink>
                <p>
                  {/* {`${process.env.REACT_APP_SERVER}/invite/${encryptedText}`} */}
                  {`http://localhost:3000/invite/${encryptedText}`}
                </p>
              </StoryboxLink>
            : null
          }
          <LinkCreate onClick={() => {encrypt(data)}}>링크 생성</LinkCreate>
          <BoxOut onClick={storyboxOut}>박스 나가기</BoxOut>
        </DetailWrap>

      }
    </>
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

const NameBox = styled.div`
  margin-top: 20px;
  width: 100%;
  border-radius: 10px;
  border: 2px solid #DBDBDB;
  padding: 10px;

  >p:first-child {
    font-size: 12px;
    margin-bottom: 5px;
  }

  >p:nth-child(2) {
    font-size: 20px;
    font-weight: 600;
  }
`;

const ExplainBox = styled.div`
  margin-top: 20px;
  width: 100%;
  border-radius: 10px;
  border: 2px solid #DBDBDB;
  white-space: pre-line;

  >p {
    margin: 0;
    padding: 10px;
    font-size: 16px;
  }
`
const StoryboxLink = styled.div`
  margin-top: 20px;
  width: 100%;
  border-bottom: 2px solid #DBDBDB;
  white-space: pre-line;

  >p {
    margin: 0;
    padding: 10px;
    font-size: 16px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
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