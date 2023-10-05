import express from 'express';
import { fileURLToPath } from 'url';
import path, { dirname } from 'path';
import CryptoJS from 'crypto-js';
import dotenv from 'dotenv'

dotenv.config();
const __filename = fileURLToPath(import.meta.url);
const __dirname = dirname(__filename);
// import invitation from './lib/invitation'

const app = express();
app.use(express.static('public'));
app.set('view engine', 'ejs');
app.set('views', path.join(__dirname, 'views'));

app.get('/fresh', function (req, res) {
    console.log(req.query);
    res.send('to fresh')
})

app.get('/rot', function (req, res) {
    res.send('to rot')
})

app.get("/visti/invite/:storyboxid", (req, res) => {

    // 유효기간 + 아이디가 암호화 해서 전달이됨.
    const encodeData = req.params.storyboxid;    
    const storyBoxInfo = atob(encodeData);


    const {expirationDay, storyboxId} = decrypt(storyBoxInfo);
    
    // parse Data 정보를 바탕으로 접근 제어
    if(isURLFresh(expirationDay) && storyboxId){
        
        // res.json({ok: true, storybox: storyboxId});
        const data = {
            modalText: '스토리 박스에 들어올래?',
            buttonText: '확인',
            apiEndpoint : "visti://deeplink/" + `${storyboxId}`
        };

        res.render('template', data);
        return;
    }

    const data = {
        modalText: '유효기간이 만료된 링크입니다.',
        buttonText: '나가기',
        apiEndpoint : process.env.API_ENDPOINT_ROTTED
    };

    res.render('template', data);
})

app.listen(9999, () => {
    console.log('listen t0 9999')
})

const decrypt = (data) => {
    // 값이 없으면 빈 문자열 반환
    if (!data) return '';
    
    const salt = process.env.SECRET_KEY;

    // const salt = "ac4f92829a5734445ecc9342ab207fc578ded38c51e8926eb80f94753b59991a"; // env 로 끌어오게해야함,.,
    try {
      const bytes = CryptoJS.AES.decrypt(data, salt); // 복호화 시도
      const decrypted = bytes.toString(CryptoJS.enc.Utf8);
      const parseData = JSON.parse(decrypted); 

      return parseData;
    } catch (err) {
      console.log('복호화시 에러 - 정보가 없거나 값이 없을경우에 발생합니다.', err);
      return '';
    }

}

const isURLFresh = (expirationDay) => {

    const expirationDate = new Date(expirationDay);
    console.log(expirationDate); 
    const currentDate = new Date();
    console.log(currentDate);

    if (expirationDate <= currentDate) {
        // expirationDay가 오늘보다 이후가 아닐 경우
        return false;
    }
    if (expirationDate > currentDate) {
        // expirationDay가 오늘보다 이후일 경우
        return true;
    }
}

