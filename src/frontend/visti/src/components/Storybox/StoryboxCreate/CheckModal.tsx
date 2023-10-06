import React from 'react'
import styled from 'styled-components';
import { Dayjs } from 'dayjs';

interface CheckModalProps {
  isModalOpen: boolean;
  file: File | null;
  groupName: string;
  groupDetail: string;
  disclosure: boolean;
  value: Dayjs | null;
  isEditMode : boolean;
  CloseModal: () => void;
  handleSubmit: () => Promise<void>;
  postStorybox: (formData: FormData) => Promise<void>;
  formDataToObject: (formData: FormData) => any;
}

const CheckModal : React.FC<CheckModalProps> = ({isModalOpen, file, groupName, groupDetail, disclosure, value, isEditMode,
    CloseModal, handleSubmit}) => {
  if (!isModalOpen) return null;

  return (
    <Overay>    
      <ModalContainer>
        <MainModal>
          <img src={process.env.PUBLIC_URL +"/assets/Visti_icon.png"} alt="Visti Logo"/>
          <p>
            {
              isEditMode ? 
              `스토리 박스를 수정하시겠습니까?` :
              `스토리 박스를 생성하시겠습니까?` 
            }
          </p>
        </MainModal>
        <FootModal>
          <div onClick={CloseModal}>취소</div>
          <div onClick={()=>{handleSubmit();}}>확인</div>
        </FootModal>
      </ModalContainer>
    </Overay>
  )
}

const Overay = styled.div`
  position: fixed;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.4);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 4;
  overflow-y: hidden;
`

const ModalContainer = styled.div`
  background-color: #fff;
  border-radius: 8px;
  width: 280px;
  height: 170px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.25);
  z-index: 5;
`

const MainModal = styled.div`
  width: 100%;
  height: 70%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  >img {
    width: 30px;
    height: 30px;
  }

  >p {
    font-weight: 600;
  }
`

const FootModal = styled.div`
  width: 100%;
  height: 30%;
  display: flex;
  border-top: 1px solid rgba(0, 0, 0, 0.2);

  >div {
    display: flex;           
    justify-content: center; 
    align-items: center;     
    width: 50%;
    height: 100%;
  }

  >:first-child {
    width: 50%;
    height: 100%;
  }

  >:nth-child(2) {
    width: 50%;
    height: 100%;
    background-color: #E03C31;
    border-bottom-right-radius: 8px;
    color : #fff;
  }
`

export default CheckModal