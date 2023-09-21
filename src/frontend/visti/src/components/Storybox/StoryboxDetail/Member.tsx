import React, { useCallback, useEffect, useState } from 'react'
import styled from 'styled-components';
import { authInstance } from '../../../apis/utils/instance';

interface MemberList {
  nickname: string;
  profilePath: string;
  status: boolean;
  position : string;
};

interface MemberProps {
  id ?: string
}

const Member : React.FC<MemberProps> = ({id}) => {

  const [memberList, setMemberList] = useState<MemberList[]>([]);

  const getMemberList = useCallback(async () => {
    try{
      const data = await authInstance.get(`story-box/${id}/members`)
      if (data) {
        setMemberList(data.data.detail);
        console.log(data.data.detail);
      }
    }
    catch (err) {
      console.log('스토리박스 Member GET 중 에러 발생', err)
    }
  },[id])

  useEffect(()=>{
    getMemberList();
  },[getMemberList])

  return (
    <MemberWrap>
      {
        memberList.map((member, index) => (
          <MemberDiv key={index}>
            <ProfileImg bgImage={member.profilePath}/>
            <p>{member.nickname}</p>
          </MemberDiv>
        ))
      }
    </MemberWrap>
  )
}

const MemberWrap = styled.div`
  margin-top: 3px;
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
`

const MemberDiv = styled.div`
  height: 12%;
  /* background-color: lightblue; */
  display: flex;
  align-items: center;
  padding: 0 35px;
  border-bottom: 2px solid #DFDFE8;

  >p {
    margin-left: 35px;
    font-size: 20px;
    /* font-weight: 600; */
  }
`

type BoxWrapProps = {
  bgImage : string;
};

const ProfileImg = styled.div<BoxWrapProps>`
  width: 15vw;
  height: 15vw;
  border-radius: 42px;
  background-image: url(${props => props.bgImage});
  background-size: cover;
  background-repeat: no-repeat;
`

export default Member