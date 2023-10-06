import React, { useCallback, useEffect, useState } from 'react'
import styled from 'styled-components';
import { authInstance } from '../../../apis/utils/instance';
import Loading from '../../Common/Loading';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCrown } from "@fortawesome/free-solid-svg-icons";


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
  const [isLoading, setIsLoading] = useState(true);
  const [memberList, setMemberList] = useState<MemberList[]>([]);

  const getMemberList = useCallback(async () => {
    setIsLoading(true);
    try{
      const data = await authInstance.get(`story-box/${id}/members`)
      if (data) {
        setMemberList(data.data.detail);
        // console.log(data.data.detail);
      }
    }
    catch (err) {
      console.log('스토리박스 Member GET 중 에러 발생', err)
    } finally {
      setIsLoading(false);
    }
  },[id])

  useEffect(()=>{
    getMemberList();
  },[getMemberList])

  return (
    <>
      {
        isLoading ? <Loading isLoading={isLoading}/> :
        <MemberWrap>
          {
            memberList.map((member, index) => (
              <MemberDiv key={index}>
                <ProfileImg 
                  src={member.profilePath} 
                  onError={(e: React.SyntheticEvent<HTMLImageElement, Event>) => {
                    e.currentTarget.src = DefaultImage;
                  }}
                />
                <p>{member.nickname}{member.position === 'HOST' && <StyledIcon icon={faCrown}/>}</p>
              </MemberDiv>
            ))
          }
        </MemberWrap>
      }
  </>
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
  display: flex;
  align-items: center;
  padding: 0 35px;
  border-bottom: 2px solid #DFDFE8;

  >p {
    margin-left: 35px;
    font-size: 20px;
  }
`

const DefaultImage = "/assets/profile_image.png";

const ProfileImg = styled.img`
  width: 14vw;
  height: 14vw;
  border-radius: 42px;
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
`;

const StyledIcon = styled(FontAwesomeIcon)`
  color: #fba38db3;

`

export default Member