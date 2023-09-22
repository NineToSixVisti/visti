import React, { useCallback, useEffect, useRef, useState } from 'react'
import styled from 'styled-components';
import { ReactComponent as Empty } from '../../../assets/images/story_empty.svg'
import { ReactComponent as Favorite } from '../../../assets/images/favorite.svg'
import { useNavigate } from 'react-router-dom';
import CreatePostButton from '../../Story/StoryCreate/CreatePostButton';
import { authInstance } from '../../../apis/utils/instance';
import { setStoryboxId } from '../../../store/slices/storySlice';
import { useDispatch } from 'react-redux';


interface StoryList {
  id: number;
  encryptedId: string;
  storyBoxId: number;
  member: {
    nickname: string;
    profilePath: string | null;
    status: boolean;
  };
  mainFileType: string;
  mainFilePath: string;
  blind: boolean;
  like: boolean;
  createdAt: string;
  finishedAt: string;
}

interface StoryProps {
  id ?: string
};

const Story : React.FC<StoryProps> = ({id}) => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  
  const [storyList, setStoryList] = useState<StoryList[]>([]);
  const [page, setPage] = useState<number>(0);
  const [hasMore, setHasMore] = useState<boolean>(true);
  const observer = useRef<IntersectionObserver | null>(null);  

  const lastBoxElementRef = useCallback(
    (node : HTMLDivElement | null) => {
      if (observer.current) observer.current.disconnect();
      observer.current = new IntersectionObserver((entries) => {
        if (entries[0].isIntersecting && hasMore) {
          setPage((prevPage) => prevPage + 1);
        }
      });
      if (node) observer.current.observe(node);
    },[hasMore]
  );

  const getStoryList = useCallback(async () => {
    try {
      const { data } = await authInstance.get(`story-box/${id}/story-list?page=${page}&size=12`)
      console.log("Returned data:", data)
      if (data) {
        setStoryList((prevStoryList) => [
          ...prevStoryList,
          ...data.detail.content
        ]);
        setHasMore(!data.detail.last);
      }
      if (page === 0){ // 한번만 불러오기 위해서
        dispatch(setStoryboxId(data.detail.content.encryptedId));
      }
    }
    catch (err) {
      console.log('스토리List GET 중 에러 발생', err);
    }
  }, [id, page, dispatch])

  useEffect(()=>{
    getStoryList();
  }, [getStoryList, page]); // page가 바꿔도 다시 함수를 불러야 한다.
  
  useEffect(()=>{
    console.log("Current storyList:", storyList);
  },[storyList]);

  useEffect(()=>{
    console.log("Current page:", page)
  },[page]);
  
  useEffect(()=>{
    console.log("hasMore:", hasMore)
  },[hasMore]);

  return (
    <>
      {
        storyList.length === 0 ? (
          <EmptyWrap>
            <Empty />
            <p>스토리가 하나도 없어요</p>
            <p>추억을 저장해 볼까요?</p>
          </EmptyWrap>
        ) : (
          <StoryWrap>
            {
              storyList.map((story, index) => (
                <StoryDiv 
                  ref={index === storyList.length -1 ? lastBoxElementRef : null}
                  key={story.id} 
                  index={index} 
                  isPrivate={story.blind} 
                  storyImg={story.mainFilePath}
                  onClick={()=>{
                    navigate(`/storydetail/${story.encryptedId}`)}}>
                  {story.blind && <PrivateImg src={process.env.PUBLIC_URL + "/assets/Visti_icon.png"} alt='보호된 이미지' />}
                  {!story.blind && story.like && <FavoriteSvg />}
                </StoryDiv>
              ))
            }
            <CreatePostButton/>
          </StoryWrap>
        )
      }
    </>
  )
}
const EmptyWrap = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  
  >svg {
    padding-top: 120px;
    padding-bottom: 20px;
  }
  
  >p {
    color: #000;
    text-align: center;
    font-size: 32px;
    font-weight: 600;
    margin: 10px 0;
  }
  `

const StoryWrap = styled.div`
  margin-top: 3px;
  width: 100%;
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 0.05%; // gap의 거리 차이를 두기 위해
`

const StoryDiv = styled.div<{ isPrivate : boolean; index : number; storyImg: string | undefined }>`
  height: 33vw;
  width: 100%;
  background-image: ${props => props.isPrivate ? 'none' : `url(${props.storyImg})`};
  background-color: ${props => (props.index % 6 >= 3 && props.isPrivate) ? '#FFF2F2' : '#fff'};
  background-size: cover;
  background-position: center;
  position: relative;
  display : flex;
  justify-content : center;
  align-items : center;
`

const PrivateImg = styled.img`
  width: 80px;
  height: 85px;
`

const FavoriteSvg = styled(Favorite)`
  position: absolute;
  top : 2px;
  right: 2px;
`

export default Story;
