import React, { useState } from 'react'
import styled from 'styled-components';
import { ReactComponent as GoBack } from "../../assets/images/back_button.svg"
import { ReactComponent as Plus } from "../../assets/images/plus_button_red.svg"

// import TextField from '@mui/material/TextField';
import { Dayjs } from 'dayjs';
import { DemoContainer } from '@mui/x-date-pickers/internals/demo';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import { useNavigate } from 'react-router-dom';

const StoryboxCreate = () => { 
  const [groupImage, setGroupImage] = useState<string | null>(null);
  const [disclosure, setDisclosure] = useState<boolean>(false);
  const [value, setValue] = React.useState<Dayjs | null>(null);
 
  const navigate = useNavigate();

  const ImageClick = () => {
    const inputElement = document.getElementById("ImageInput");
    inputElement?.click();
  }

  const ImageChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (file) {
      const reader = new FileReader();
      reader.onloadend = () => {
        const base64String = reader.result as string;
        setGroupImage(base64String);
      };
      reader.readAsDataURL(file);
    }
  };

  return (
    <Wrap>
      <LogoWrap>
        <GoBackSvg onClick={()=>{navigate("/storybox")}}/>
        <img src={process.env.PUBLIC_URL + '/assets/Visti-red.svg'} alt="Visti Logo"/> 
      </LogoWrap>

      <MainWrap>
        <Title>그룹이미지</Title> 
        <BoxImage onClick={ImageClick} backgroundImage={groupImage || process.env.PUBLIC_URL + '/assets/box_Image_input.svg'}>
          <PlusSvg onClick={ImageClick}/>
        </BoxImage>
        <input id="ImageInput" type='file' accept='image/*' onChange={ImageChange} style={{display: 'none'}} />

        <Title>그룹이름</Title>
        <GroupNameInput placeholder='그룹 이름'/>
        {/* <TextField id="group-name" label="그룹 이름" variant="outlined" /> */}
        
        <Title>그룹소개글</Title>
        <GroupDescription placeholder={`9기 버니즈의 추억을 위한 공간입니다. OOO하기위해 OOOO ~~ 매일 한개씩 업로드 필수입니다!`} rows={3}/>

        <Title>종료시간</Title>
        <LocalizationProvider dateAdapter={AdapterDayjs}>
          <DemoContainer components={['DatePicker']}>
            <DatePicker value={value} onChange={(newValue) => setValue(newValue)} />
          </DemoContainer>
        </LocalizationProvider>

        <Title>선택사항</Title>
        <Label><input onChange={()=>setDisclosure(!disclosure)} checked={disclosure} type="checkbox"/><div>끝나는 기간까지 스토리 비공개 하기</div></Label>

        <RequestBtn onClick={()=>{navigate("/storybox")}}>완료</RequestBtn>
      </MainWrap>
    </Wrap>
  )
}

const Wrap  = styled.div`
  min-width: 100vw;
  min-height: 100vh;
` 

const LogoWrap = styled.div`
  width: 100%;
  margin: 10px 0;
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;

>img {
  width: 75px;
  height: 33px;
  margin: auto;
}
`

const GoBackSvg = styled(GoBack)`
  position: absolute;
  top: 6.5px;
  left: 20px;
`

const MainWrap = styled.div`
  height: calc(100vh - 30px);
  /* background-color: red; */
  margin : 10px 20px;
`

const Title = styled.div`
  font-size: 16px;
  font-weight: 600;
  margin: 10px 0 10px 10px;
`

const BoxImage = styled.div<{ backgroundImage: string }>`
  width: calc(100vw - 40px);
  height : 200px;
  border-radius: 18px;
  background-image: url(${props => props.backgroundImage || process.env.PUBLIC_URL + '/assets/box_Image_input.svg'});
  background-size: cover;
  background-repeat:no-repeat;
  background-position: center;
  position: relative;
`

const PlusSvg = styled(Plus)`
  position : absolute;
  top : 2vh;
  right : 4vw;
  cursor: pointer;
`

const Label = styled.label`
  /* margin-left: 16px; */
  display: flex;
  cursor: pointer;
  & > input{
    border-radius: 10px;
    width: 18px;
    height: 18px;
    accent-color: #E03C31;
  }

  & > div{
    margin-left: 8px;
    /* line-height: 3rem; */
  }
`

const GroupNameInput = styled.input`
  border-radius: 8px;
  border: 1px solid #e3e3e3;
  padding: 16px;
  font-size: 16px;
  width: calc(100% - 40px);
`;

const GroupDescription = styled.textarea`
  border-radius: 8px;
  border: 1px solid #e3e3e3;
  padding: 16px;
  font-size: 14px;
  width: calc(100% - 40px);
  resize: none; // 사용자가 세로 크기만 조절할 수 있게 함
`;

const RequestBtn = styled.button`
  border-radius: 1rem;
  font-size: 1.5rem;
  width: calc(100vw - 40px);
  height: 3rem;
  background-color: #E03C31;
  color : #fff;
  margin-top: 1rem;
`

export default StoryboxCreate