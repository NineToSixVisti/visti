import React, { useState } from 'react'
import CryptoJS from 'crypto-js';

const Test = () => {
    const [input, setInput] = useState('');
    const [encryptedText, setEncryptedText] = useState('');
    const [decryptText, setDecryptText] = useState('');

    const data = {
      storyboxId : '8Zh7RQ5VanVkALmBxBYBxw',
      expirationDay : '2023-09-30'
    }
    
    // Non-null assertion operator (!) 사용
    // 이 연산자는 해당 값이 절대 null 또는 undefined가 아님을 단언
    const salt = process.env.REACT_APP_SECRET_KEY!;
  
    // 암호화
    const encrypt = (data : any) => {
      if (!data) return '';
      const encrypted = CryptoJS.AES.encrypt(JSON.stringify(data), salt).toString(); 
      console.log(encrypted);
      setEncryptedText(encrypted);
      return encrypted
    }
  
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
  return (
    <>
    <div>
        <input
          value={input} onChange={(e) => {setInput(e.target.value)}}/>
        <button onClick={() => {encrypt(data)}}>암호화</button>
      </div>
      
      <div>
        Encrypted : {encryptedText}
      </div>

      <input
        value={encryptedText} onChange={(e) => {setEncryptedText(e.target.value)}}/>
      <button onClick={() => decrypt(encryptedText)}>복호화</button>

      <div>
        decrypted :  {JSON.stringify(decryptText)}
      </div>
    </>
  )
}

export default Test