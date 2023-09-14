import React, { useState } from 'react'
import styled from 'styled-components';

const Member = () => {

  const [memberList, setMemberList] = useState([1,1,1,1,1])
 
  return (
    <MemberWrap>
      {
        memberList.map((a, index) => (
          <MemberDiv key={index}>
            <ProfileImg/>
            <p>강민석</p>
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

  >p {
    margin-left: 35px;
    font-size: 20px;
    /* font-weight: 600; */
  }
`

const ProfileImg = styled.div`
  width: 15vw;
  height: 15vw;
  border-radius: 42px;
  background-image: url("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQgc9Cml752xYTJwa1Yc6OG3V5-MqWTOSbflw&usqp=CAU");
  background-size: cover;
  background-repeat: no-repeat;
`

export default Member