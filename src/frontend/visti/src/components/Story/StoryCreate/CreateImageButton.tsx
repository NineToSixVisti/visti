import React, { useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import { RootState } from "../../../store";
import html2canvas from "html2canvas";
import styled from "styled-components";
import { ReactComponent as CompleteButton } from "../../../assets/images/complete_button.svg";
import { setImage, setCID } from "../../../store/slices/MergeImageSlice";
import { authInstance } from "../../../apis/utils/instance";
import { useNavigate } from "react-router-dom";

const CompleteButtonStyled = styled.button`
  background: transparent;
  border: none;
  cursor: pointer;
  margin-bottom: 10px;
`;

const CreateImageComponent: React.FC = () => {
  const dispatch = useDispatch();
  const { selectedImage } = useSelector((state: RootState) => state.image);
  const storyBoxId = useSelector((state: RootState) => state.story.encryptedId);
  const navigate = useNavigate();

  const handleCreateImage = async () => {
    const node = document.getElementById("image-container");
    const textToggleButton = document.getElementById("text-toggle-button");
    if (textToggleButton) {
      textToggleButton.style.display = "none";
    }

    if (node) {
      try {
        const canvas = await html2canvas(node, { scale: 2 });
        canvas.toBlob(async (blob) => {
          if (blob) {
            // Blob을 Base64 문자열로 변환
            const reader = new FileReader();
            reader.onloadend = async () => {
              const base64String = reader.result as string;

              // Base64 문자열을 다시 File 객체로 변환
              const file = new File([blob], "mergedImage.png", {
                type: "image/png",
              });

              // FormData에 추가
              const formData = new FormData();

              // storyInfo를 JSON 형식으로 만든 후 Blob 객체로 변환하여 추가
              const storyInfo = {
                storyBoxId: storyBoxId || "",
                mainFileType: "LETTER",
              };
              const storyInfoBlob = new Blob([JSON.stringify(storyInfo)], {
                type: "application/json",
              });
              formData.append("storyInfo", storyInfoBlob);

              // 합성된 이미지 파일 추가
              formData.append("file", file);

              try {
                const response = await authInstance.post(
                  "/story/create",
                  formData,
                  {
                    headers: {
                      "Content-Type": "multipart/form-data",
                    },
                  }
                );
                if (response.status === 200) {
                  console.log("이미지가 성공적으로 업로드되었습니다.");
                  alert("완료되었습니다!"); 
                  navigate(-1); 
                } else {
                  console.error("이미지 업로드에 실패했습니다.");
                }
              } catch (error) {
                console.error("API 요청 중 오류가 발생했습니다:", error);
              }
            };
            reader.readAsDataURL(blob);
          }
        }, "image/png");
      } catch (error) {
        console.error("Error generating image:", error);
      } finally {
        if (textToggleButton) {
          textToggleButton.style.display = "block";
        }
      }
    }
  };
  useEffect(() => {
    console.log(storyBoxId);
  }, [storyBoxId]);
  return (
    <CompleteButtonStyled onClick={handleCreateImage}>
      <CompleteButton />
    </CompleteButtonStyled>
  );
};

export default CreateImageComponent;
