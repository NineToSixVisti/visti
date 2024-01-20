import Swal from 'sweetalert2'
import React, { useCallback, useEffect, useState } from 'react'
import styled from 'styled-components';
import { ReactComponent as GoBack } from "../../../assets/images/back_button.svg"
import { ReactComponent as Plus } from "../../../assets/images/plus_button_red.svg"
import './StoryboxCreate.css';

import dayjs, { Dayjs } from 'dayjs';
import 'dayjs/locale/ko'; 
import { DemoContainer } from '@mui/x-date-pickers/internals/demo';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import { useLocation, useNavigate } from 'react-router-dom';
import CheckModal from './CheckModal';
import { authInstance } from '../../../apis/utils/instance';
import { useDispatch, useSelector } from 'react-redux';
import { setTrigger } from '../../../store/slices/storySlice';
import { RootState } from '../../../store';
dayjs.locale('ko');

// window 객체 타입 확장
interface MyWindow extends Window {
  Android?: {
    openGallery: () => void;
    getSelectedImage: () => string | null;
  };
}

declare var window: MyWindow;

const StoryboxCreate = () => { 
  const navigate = useNavigate();
  const dispatch = useDispatch(); 
  const trigger = useSelector((state : RootState) => state.story.trigger)
  const location = useLocation(); // navigate로 보낸 stoyryboxId를 받기 위해 사용
  const storyboxId = location.state ? location.state.storyboxId : null;
  const isEditMode = !!storyboxId

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

  // base64 파일을 File 객체로 변환
  const base64ToFile = (base64: string, filename: string): File => {
    const arr = base64.split(',');
    const mime = arr[0].match(/:(.*?);/)?.[1];
    const byteCharacters = atob(arr[1]);
    const byteNumbers = new Array(byteCharacters.length);
    for (let i = 0; i < byteCharacters.length; i++) {
      byteNumbers[i] = byteCharacters.charCodeAt(i);
    }
    const byteArray = new Uint8Array(byteNumbers);
    return new File([byteArray], filename, { type: mime });
  };

  // 사진을 클릭했을때 input 창 반응
  const ImageClick = () => {
    if (window.Android) {
        if (window.Android.openGallery) {
          window.Android.openGallery();
          // 갤러리를 열고 난 후 선택된 이미지 URI 검색
          const selectedImageUri = window.Android.getSelectedImage();
          if (selectedImageUri) {
            // console.log(selectedImageUri);
            setGroupImage(selectedImageUri);

            // base64 문자열을 File 객체로 변환
            const imageFile = base64ToFile(selectedImageUri, "selectedImage.jpg");
            setFile(imageFile);  // File 객체 저장
          }
          // console.log('openGallety 호출 잘됨')
        } else {
          const inputElement = document.getElementById("ImageInput");
          inputElement?.click();
          // console.log('openGallety 호출 안됨')
        }
    } else {
      const inputElement = document.getElementById("ImageInput");
      inputElement?.click();
      // console.log('안드로이드 접근 안됨')
    }
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

  // 수정을 진행하는 함수
  const putStorybox = useCallback(async (formData: FormData) => {
    try {
      const response = await authInstance.put(`story-box/${storyboxId}/setting`, formData, {
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
  }, [storyboxId]);

  const showErrorAlert = (text: string): void => {
    Swal.fire({
      icon: 'error',
      title: text,
      confirmButtonText: '확인'
    });
  }
  

  // 스토리 박스 생성할때의 조건
  const checkData = () => {
    if (!groupName.trim()) {
      showErrorAlert('그룹 이름을 \n입력해주세요!');
      return false;
    }
    if (groupName.length >= 20) {
      showErrorAlert('그룹 이름은 18자 \n이하로 입력해주세요!')
      return false;
    }
    if (!groupDetail.trim()) {
      showErrorAlert("그룹 소개글을 \n입력해주세요!");
      return false;
    } 
    if (groupDetail.trim().length >= 100) {
      showErrorAlert("그룹 소개글은 100자 \n이하로 입력해주세요!");
      return false;
    }
    if (!value) {
      showErrorAlert("종료일자를 \n설정해주세요!");
      return false;
    }
    const today = dayjs();
    if (value.isBefore(today, 'day') || value.isSame(today, 'day')) {
      showErrorAlert("종료시간은 오늘 \n날짜 이후여야 합니다!");
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

  // 이미지를 file 형태로 변경
  const fetchImageAndSetFile = async (imageUrl: string) => {
    try {
      const response = await fetch(imageUrl);
      const imageBlob: Blob = await response.blob();
  
      // Blob을 파일로 변환
      const imageFile = new File([imageBlob], "filename.jpg", { type: imageBlob.type });
  
      // 파일 상태 업데이트
      setFile(imageFile);
    } catch (error) {
      console.error("Error fetching the image:", error);
    }
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

    // post / put 의 차이로 다른 제출 
    isEditMode ? putStorybox(formData) : postStorybox(formData);
    dispatch(setTrigger(true)); // 리랜더링 하기 위해
    setIsModalOpen(false);
    navigate('/storybox', { replace : true })
  }

  useEffect(()=>{
    console.log(trigger);
  },[trigger])

  // 수정하는 경우 기존의 박스 내용을 동기화
  const getStoryboxInfo = useCallback(async () => {
    if (!isEditMode) return;
    try {
      const data = await authInstance.get(`story-box/${storyboxId}/info`)
    if (data){
      // console.log(data.data.detail);
      const Info = data.data.detail
      fetchImageAndSetFile(Info.boxImgPath);
      setGroupImage(Info.boxImgPath);
      setGroupName(Info.name);
      setGroupDetail(Info.detail);
      setValue(dayjs(Info.finishedAt));
      setDisclosure(Info.blind);
    }
    }
    catch (err) {
      console.log('스토리박스 Info GET 중 에러 발생', err);
    }
  },[storyboxId, isEditMode]);

  // getStoryboxInfo를 통해 찍어볼 수 있게
  useEffect(()=>{
    getStoryboxInfo();
  },[getStoryboxInfo])

  return (
    <Wrap>
      <CheckModal 
        isModalOpen={isModalOpen} CloseModal={CloseModal}
        handleSubmit={handleSubmit} postStorybox={postStorybox}
        formDataToObject={formDataToObject} file={file}
        groupDetail={groupDetail} groupName={groupName}
        disclosure={disclosure} value={value}
        isEditMode={isEditMode}
        />
      <LogoWrap>
        <GoBackSvg onClick={()=>{navigate("/storybox")}}/>
        <img src={process.env.PUBLIC_URL + '/assets/Visti-red.png'} alt="Visti Logo"/> 
      </LogoWrap>

      <MainWrap>
        <Title>그룹이미지</Title>
        <BoxImage backgroundImage={groupImage || process.env.PUBLIC_URL + '/assets/box_Image_input.svg'}>
        </BoxImage>
        <input id="ImageInput" type='file' accept='image/*' onChange={ImageChange} style={{display: 'none'}} />

        <Title>그룹이름</Title>
        <GroupNameInput placeholder='그룹 이름' value={groupName}
        onChange={(e) => setGroupName(e.target.value)}/>
        
        <Title>그룹소개글</Title>
        <GroupDescription placeholder={`SSAFY 9기 구미 1반 D102팀 추억 저장을 위한 공간이야! 
잊을 수 없는 추억을 만들어보자~~`}
         rows={3} value={groupDetail}
         onChange={(e) => setGroupDetail(e.target.value)}/>

        <Title>종료시간</Title>
        <LocalizationProvider dateAdapter={AdapterDayjs}>
          <DemoContainer components={['DatePicker']}>
              <DatePicker 
                  value={value} 
                  onChange={(newValue) => setValue(newValue)} 
                  format="YYYY년 MM월 DD일"
                  className={isEditMode ? 'disabled-datepicker' : ''}
              />
          </DemoContainer>
      </LocalizationProvider>

        <Title>선택사항</Title>
        <Label>
          <input 
            onChange={() => !isEditMode && setDisclosure(!disclosure)}  
            checked={disclosure} 
            type="checkbox" 
            className={isEditMode ? 'disabled-checkbox' : ''} />
          <div className={isEditMode ? 'disabled-div' : ''}>
            끝나는 기간까지 스토리 비공개 하기
          </div></Label>
        <RequestBtn onClick={OpenModal}>
          {isEditMode ? `수정` : `완료`}
        </RequestBtn>
      </MainWrap>
    </Wrap>
  )
}


const Wrap  = styled.div`
  width: 100%;
  height: 100%;

  overflow-y: scroll; 
  scrollbar-width: none; // 파이어폭스
  &::-webkit-scrollbar { // 크롬, 사파리
    display: none;
  }
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

// const PlusSvg = styled(Plus)`
//   position : absolute;
//   top : 2vh;
//   right : 4vw;
//   cursor: pointer;
// `

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
  border: none;
  font-size: 1.5rem;
  width: calc(100vw - 40px);
  height: 3rem;
  background-color: #E03C31;
  color : #fff;
  margin-top: 1rem;
`

export default StoryboxCreate