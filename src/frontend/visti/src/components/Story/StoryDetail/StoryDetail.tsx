import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { authInstance } from "../../../apis/utils/instance";
import styled from "styled-components";
import { ReactComponent as BackButtonIcon } from "../../../assets/images/storydetailclose_button.svg";
import { useNavigate } from "react-router-dom";
import BasicModal from "./ReportButton";

interface StoryData {
  id: number;
  encryptedId: string;
  mainFileType: string;
  mainFilePath: string;
  createdAt: string;
  member: {
    nickname: string;
    profilePath: string | null;
    status: boolean;
  };
}

const InfoContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  margin-left: 8px;
`;

const ProfileContainer = styled.div`
  position: absolute;
  bottom: 2%;
  left: 25%;
  display: flex;
  align-items: center;
  transform: translateX(-50%);
`;

const Nickname = styled.span`
  font-size: 16px;
  font-weight: bold;
  color: black;
  text-shadow: 0px 0px 5px rgba(0, 0, 0, 0.4);
  margin-bottom: 8px;
`;

const Container = styled.div`
  position: relative;
  width: 100vw;
  height: 100vh;
  overflow: hidden;
  background-color: #d3d3d3;
`;

const MainImage = styled.img`
  width: 100%;
  height: 90vh;
  object-fit: contain;
  border-radius: 15px;
`;

const ProfileImage = styled.img`
  width: 50px;
  height: 50px;
  border-radius: 50%;
  margin-right: 8px;
`;

const CreatedAt = styled.span`
  font-size: 14px;
  color: black;
  text-shadow: 0px 0px 5px rgba(0, 0, 0, 0.4);
  margin-left: 10px;
`;
const ButtonContainer = styled.div`
  position: absolute;
  top: 10px;
  right: 10px;
  display: flex;
  align-items: center;
`;

const BackButton = styled.button`
  background-color: transparent;
  border: none;
  cursor: pointer;
  margin-right: 10px;
  margin-top: 9px;
`;
const BasicModalStyled = styled(BasicModal)`
  background-color: transparent;
`;

function StoryDetail() {
  const { id } = useParams<{ id?: string }>();
  const [storyData, setStoryData] = useState<StoryData | null>(null);
  const navigate = useNavigate();
  const handleBack = () => {
    navigate(-1);
  };

  useEffect(() => {
    if (!id) return;

    authInstance
      .get(`story/${id}`)
      .then((response) => {
        setStoryData(response.data.detail);
      })
      .catch((error) => {
        console.error("Failed to fetch story data:", error);
      });
  }, [id]);

  if (!storyData) return <div>Loading...</div>;

  function formatDate(dateString: string): string {
    const date = new Date(dateString);
    return `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(
      2,
      "0"
    )}-${String(date.getDate()).padStart(2, "0")}`;
  }

  return (
    <Container>
      <ButtonContainer>
        <BasicModal storyId={id} />
        <BackButton onClick={handleBack}>
          <BackButtonIcon />
        </BackButton>
      </ButtonContainer>
      <MainImage src={storyData.mainFilePath} alt="Story" />
      <ProfileContainer>
        {storyData.member.profilePath && (
          <ProfileImage src={storyData.member.profilePath} alt="Profile" />
        )}
        <InfoContainer>
          <Nickname>{storyData.member.nickname}</Nickname>
          <CreatedAt>{formatDate(storyData.createdAt)}</CreatedAt>
        </InfoContainer>
      </ProfileContainer>
    </Container>
  );
}

export default StoryDetail;