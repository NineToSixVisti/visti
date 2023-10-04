import React, { useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom';
import styled from 'styled-components';
import CryptoJS from 'crypto-js';
import { ReactComponent as Visti } from "../../../../assets/images/Visti-lightpink.svg";
import CheckModal from './CheckModal';

const InviteCheck = () => {
  // 비구조화 할당으로 파리미터 값 가져옴.
  const { encryptedData } = useParams();
  const decodedData = (window.atob as any)(encryptedData); // 디코딩을 진행
  console.log(decodedData);

  const [decryptText, setDecryptText] = useState('');
  const salt = process.env.REACT_APP_SECRET_KEY!;
  const navigate = useNavigate();

  const [isModalOpen, setIsModalOpen] = useState(true);

  // 복호화
  const decrypt = (data : any) => {
    // 값이 없으면 빈 문자열 반환
    if (!data) return '';

    try {
      const bytes = CryptoJS.AES.decrypt(data, salt); // 복호화 시도
      const decrypted = bytes.toString(CryptoJS.enc.Utf8);
      const parseData = JSON.parse(decrypted); 
      setDecryptText(parseData);
      // setDecryptText(decrypted);
      console.log(parseData);
      return parseData;
    } catch (err) {
      console.log('복호화시 에러', err);
      return '';
    }
  }

  const checkAndNavigate = (data: any) => {
    const decryptedData = decrypt(data);
    
    if (decryptedData) {
      const expirationDate = new Date(decryptedData.expirationDay);
      console.log(expirationDate); 
      const currentDate = new Date();
      console.log(currentDate);

      if (expirationDate <= currentDate) {
        // expirationDay가 오늘보다 이후가 아닐 경우
        alert('링크 유효기간이 지났습니다.');
      }
      if (expirationDate > currentDate) {
        // expirationDay가 오늘보다 이후일 경우
        navigate(`/storybox/detail/${decryptedData.storyboxId}`);
      }
    }
  }

  return (
    <Wrap>
      <CheckModal
        isModalOpen={isModalOpen}
        setIsModalOpen={setIsModalOpen}
        checkAndNavigate={checkAndNavigate}
        decodedData={decodedData}
      />    
      <Vistisvg/>
    </Wrap>
  )
}

const Wrap = styled.div`
  width: 100vw;
  height: 100vh;
  background-color: #FFF1F0;
  display: flex;
  justify-content: center;
  align-items: flex-start;
`

const Vistisvg = styled(Visti)`
  width: 232px;
  height: 107px;
  margin-top: 50%;
`

export default InviteCheck