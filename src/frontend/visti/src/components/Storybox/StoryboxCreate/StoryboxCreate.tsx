import React, { useCallback, useState } from 'react'
import styled from 'styled-components';
import { ReactComponent as GoBack } from "../../../assets/images/back_button.svg"
import { ReactComponent as Plus } from "../../../assets/images/plus_button_red.svg"
// import { ReactComponent as Calendar } from "../../../assets/images/calendar.svg"
import './StoryboxCreate.css';

import { Dayjs } from 'dayjs';
import dayjs from 'dayjs';
import 'dayjs/locale/ko'; 
import { DemoContainer } from '@mui/x-date-pickers/internals/demo';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import { useNavigate } from 'react-router-dom';
import CheckModal from './CheckModal';
import { authInstance } from '../../../apis/utils/instance';
dayjs.locale('ko');

const StoryboxCreate = () => { 
  const navigate = useNavigate();
  
  const [groupImage, setGroupImage] = useState<string | null>(null);
  const [file, setFile] = useState<File | null>(null); 
  const [value, setValue] = React.useState<Dayjs | null>(null);
  const [groupName, setGroupName] = useState('');
  const [groupDetail, setGroupDetail] = useState('');
  const [disclosure, setDisclosure] = useState(false);

  const [isModalOpen, setIsModalOpen] = useState(false);

  type JSONPayload = {
    name: string;
    detail: string;
    blind: string;
    finishedAt?: string;
  };

  // 사진을 클릭했을때 input 창 반응
  const ImageClick = () => {
    const inputElement = document.getElementById("ImageInput");
    inputElement?.click();
  }

  // 이미지를 변경할 때의 로직
  const ImageChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const fileBlob = e.target.files?.[0];
    if (fileBlob) {
      const reader = new FileReader();
      reader.onloadend = () => {
        const base64String = reader.result as string;
        setGroupImage(base64String);
      };
      reader.readAsDataURL(fileBlob);
      setFile(fileBlob);  // 원본 파일 저장
    }
  };

  const OpenModal = () => {
    if (checkData()){
      setIsModalOpen(true);
    }
  }

  const CloseModal = () => {
    setIsModalOpen(false)
  } 
  
  // Post 하기 위한 함수
  const postStorybox = useCallback(async (formData: FormData) => {
    try {
      const response = await authInstance.post('story-box/create', formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      });
      if (response.data.statusCode === 200) {
        console.log('스토리박스가 성공적으로 생성되었습니다.');
      } else {
        console.log('스토리박스 생성 실패:');
      }
    } catch (err) {
      console.log('스토리박스 POST 중 에러 발생:', err);
    }
  }, []);

  // 입력시의 조건
  const checkData = () => {
    if (!groupName.trim()) {
      alert('그룹 이름을 입력해주세요!')
      return false;
    }
    if (groupName.length >= 20) {
      alert('그룹 이름은 18자 이하로 입력해주세요!')
      return false;
    }
    if (!groupDetail.trim()) {
      alert("그룹 소개글을 입력해주세요.");
      return false;
    } 
    if (groupDetail.trim().length >= 100) {
      alert("그룹 소개글은 100자 이하로 입력해주세요.");
      return false;
    }
    if (!value) {
      alert("종료일자를 설정해주세요.");
      return false;
    }
    const today = dayjs();
    if (value.isBefore(today, 'day')) {
      alert("종료시간은 오늘 날짜 이후여야 합니다.");
      return false;
  }
    return true;
  }

  // formData 출력을 하기 위해
  const formDataToObject = (formData: FormData) => {
    let object: any = {};
    formData.forEach((value, key) => {
      if (object[key]) {
        if (!Array.isArray(object[key])) {
          object[key] = [object[key]];
        }
        object[key].push(value);
      } else {
        object[key] = value;
      }
    });
    return object;
  };

  // 파일 제출 시 해야 되는 구조
  const handleSubmit = async () => {
    const formData = new FormData();
    if (file) {
      formData.append('file', file); 
    }
  
    const json: JSONPayload = {
      name : groupName,
      detail : groupDetail,
      blind: String(disclosure),
    };
  
    if (value) {
      json.finishedAt = value.format('YYYY-MM-DD');
    }

    // console.log(json);
    formData.append("storyBoxInfo", new Blob([JSON.stringify(json)], {type: 'application/json'}));

    // console.log(formDataToObject(formData));
    postStorybox(formData);
    setIsModalOpen(false);
    navigate('/storybox')
  }
  
  
  return (
    <Wrap>
      <CheckModal 
        isModalOpen={isModalOpen} CloseModal={CloseModal}
        handleSubmit={handleSubmit} postStorybox={postStorybox}
        formDataToObject={formDataToObject} file={file}
        groupDetail={groupDetail} groupName={groupName}
        disclosure={disclosure} value={value}
        />
      <LogoWrap>
        <GoBackSvg onClick={()=>{navigate("/storybox")}}/>
        <img src={process.env.PUBLIC_URL + '/assets/Visti-red.png'} alt="Visti Logo"/> 
      </LogoWrap>

      <MainWrap>
        <Title>그룹이미지</Title>
        <BoxImage onClick={ImageClick} backgroundImage={groupImage || process.env.PUBLIC_URL + '/assets/box_Image_input.svg'}>
          <PlusSvg/>
        </BoxImage>
        <input id="ImageInput" type='file' accept='image/*' onChange={ImageChange} style={{display: 'none'}} />

        <Title>그룹이름</Title>
        <GroupNameInput placeholder='그룹 이름' value={groupName}
        onChange={(e) => setGroupName(e.target.value)}/>
        
        <Title>그룹소개글</Title>
        <GroupDescription placeholder={`9기 버니즈의 추억을 위한 공간입니다. OOO하기위해 OOOO ~~ 매일 한개씩 업로드 필수입니다!`}
         rows={3} value={groupDetail}
         onChange={(e) => setGroupDetail(e.target.value)}/>

        <Title>종료시간</Title>
        <LocalizationProvider dateAdapter={AdapterDayjs}>
          <DemoContainer components={['DatePicker']}>
              <DatePicker 
                  value={value} 
                  onChange={(newValue) => setValue(newValue)} 
                  format="YYYY년 MM월 DD일"
              />
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
  left: 5px;
  padding: 15px;
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