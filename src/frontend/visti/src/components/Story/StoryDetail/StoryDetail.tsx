import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { authInstance } from '../../../apis/utils/instance';
import styled from 'styled-components';

interface StoryData {
  id: number;
  encryptedId: string;
  mainFileType: string;
  mainFilePath: string;
  member: {
    nickname: string;
    profilePath: string | null;
    status: boolean;
  };
}

const Container = styled.div`
  position: relative;
  width: 100vw;
  height: 100vh;
  overflow: hidden;
`;

const MainImage = styled.img`
  width: 100%;
  height: 100%;
  object-fit: contain;
`;

const ProfileContainer = styled.div`
  position: absolute;
  top: 2%;
  left: 2%;
  display: flex;
  align-items: center;
`;

const ProfileImage = styled.img`
  width: 40px;
  height: 40px;
  border-radius: 50%;
  margin-right: 8px;
`;

const Nickname = styled.span`
  font-size: 16px;
  font-weight: bold;
  color: white;
  text-shadow: 0px 0px 5px rgba(0, 0, 0, 0.5);
`;

function StoryDetail() {
  const { id } = useParams<{ id?: string }>();
  const [storyData, setStoryData] = useState<StoryData | null>(null);

  useEffect(() => {
    if (!id) return;

    authInstance.get(`story/${id}`)
      .then(response => {
        setStoryData(response.data.detail);
      })
      .catch(error => {
        console.error("Failed to fetch story data:", error);
      });
  }, [id]);

  if (!storyData) return <div>Loading...</div>;

  return (
    <Container>
      <MainImage src={storyData.mainFilePath} alt="Story" />
      <ProfileContainer>
        {storyData.member.profilePath && <ProfileImage src={storyData.member.profilePath} alt="Profile" />}
        <Nickname>{storyData.member.nickname}</Nickname>
      </ProfileContainer>
    </Container>
  );
}

export default StoryDetail;
