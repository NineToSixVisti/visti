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
import CheckModal from './CheckModal';

const StoryboxCreate = () => { 
  const [groupImage, setGroupImage] = useState<string | null>(null);
  const [disclosure, setDisclosure] = useState<boolean>(false);
  const [value, setValue] = React.useState<Dayjs | null>(null);
 
  const [isModalOpen, setIsModalOpen] = useState(false);
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

  const OpenModal = () => {
    setIsModalOpen(true)  
  }

  const CloseModal = () => {
    setIsModalOpen(false)
  } 

  return (
    <Wrap>
      <CheckModal isModalOpen={isModalOpen} CloseModal={CloseModal}></CheckModal>
      <LogoWrap>
        <GoBackSvg onClick={()=>{navigate("/storybox")}}/>
        <img src={process.env.PUBLIC_URL + '/assets/Visti-red.svg'} alt="Visti Logo"/> 
      </LogoWrap>

      <MainWrap>
        <Title style={{ margin: "0 0 10px 10px" }}>그룹이미지</Title>
        <BoxImage onClick={ImageClick} backgroundImage={groupImage || process.env.PUBLIC_URL + '/assets/box_Image_input.svg'}>
          <PlusSvg/>
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

        <RequestBtn onClick={OpenModal}>완료</RequestBtn>
      </MainWrap>
    </Wrap>
  )
}

const Wrap  = styled.div`
  width: 100%;
  height: 100%;
` 

const LogoWrap = styled.div`
  width: 100%;
  padding: 10px 0;
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;

>img {
  width: 70px;
  height: 28px;
  margin: auto;
}
`

const GoBackSvg = styled(GoBack)`
  position: absolute;
  top: 15px;
  left: 20px;
`

const MainWrap = styled.div`
  height: calc(100vh - 30px);
  margin : 10px 20px;

  overflow-y: auto; 
  scrollbar-width: none; // 파이어폭스
  &::-webkit-scrollbar { // 크롬, 사파리
    display: none;
  }
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
  background-image: url(${props => props.backgroundImage});
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
  resize: none; 
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