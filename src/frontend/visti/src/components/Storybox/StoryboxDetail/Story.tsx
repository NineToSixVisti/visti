import React from 'react'
import styled from 'styled-components';
import { ReactComponent as Empty } from '../../../assets/images/story_empty.svg'
import { ReactComponent as Favorite } from '../../../assets/images/favorite.svg'

interface StoryInfo {
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

type StoryProps = {
  storyInfo: StoryInfo[];
};


const Story: React.FC<StoryProps> = ({ storyInfo }) => {
  return (
    <>
      {
        storyInfo.length === 0 ? (
          <EmptyWrap>
            <Empty />
            <p>스토리가 하나도 없어요</p>
            <p>추억을 저장해 볼까요?</p>
          </EmptyWrap>
        ) : (
          <StoryWrap>
            {
              storyInfo.map((story, index) => (
                <StoryDiv key={story.id} index={index} isPrivate={story.blind} storyImg={story.mainFilePath}>
                  {story.blind && <PrivateImg src={process.env.PUBLIC_URL + "/assets/Visti_icon.png"} alt='보호된 이미지' />}
                  {!story.blind && story.like && <FavoriteSvg />}
                </StoryDiv>
              ))
            }
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
  gap: 0.2%; // gap의 거리 차이를 두기 위해
`

const StoryDiv = styled.div<{ isPrivate : boolean; index : number; storyImg: string | undefined }>`
    height: 33vw;
    width: 100%;
    background-image: ${props => props.isPrivate ? 'none' : `url(${props.storyImg})`};
    background-color: ${props => (props.index % 6 >= 3 && props.isPrivate) ? '#FFF2F2' : '#fff'};
    background-size: cover;
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
