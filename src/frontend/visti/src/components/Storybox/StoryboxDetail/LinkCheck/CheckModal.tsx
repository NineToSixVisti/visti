import React from 'react'
import styled from 'styled-components';

interface CheckModalProps {
  isModalOpen: boolean;
  setIsModalOpen : (value: boolean) => void;
  checkAndNavigate : (value: string) => void;
  decodedData : string;
}

const CheckModal : React.FC<CheckModalProps> = ({isModalOpen, setIsModalOpen, checkAndNavigate, decodedData}) => {
  
  if (!isModalOpen) return null;

  return (
    <Overay>    
      <ModalContainer>
        <MainModal>
          <img src={process.env.PUBLIC_URL +"/assets/Visti_icon.png"} alt="Visti Logo"/>
          <p>
            스토리 박스에 들어올래?
          </p>
        </MainModal>
        <FootModal>
          <div onClick={()=>{checkAndNavigate(decodedData);}}>확인</div>
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
    width: 100%;
    height: 100%;
    background-color: #E03C31;
    border-bottom-right-radius: 8px;
    color : #fff;
  }
`

export default CheckModal