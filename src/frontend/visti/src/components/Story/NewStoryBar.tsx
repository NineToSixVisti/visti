import React from 'react';
import styled from 'styled-components';
import { useNavigate } from 'react-router-dom';
import { ReactComponent as BackIcon } from '../../assets/images/back_button.svg';

const CenteredDiv = styled.div`
    display: flex;
    align-items: center;
    justify-content: flex-start;
    height: 100%;
    padding: 0 1rem;  
    position: relative;
    zIndex: 1;
`;

const StyledBackIcon = styled(BackIcon)`
    width: 24px;   
    height: 24px;
`;

const BackButton = styled.button`
    margin-right: auto;  
    padding: 0.2rem;
    display: flex;
    align-items: center;
    justify-content: center;
    border-radius: 4px;
    border: none;
    background-color: #FFF;
    cursor: pointer;
    

    &:hover {
        background-color: #ddd;
    }
`;

const CenterNewStory = styled.div`
    flex: 1; 
    display: flex;
    justify-content: center; 
    font-size: 15px; 
`;

function NewStoryBar() {
    const navigate = useNavigate();

    return (
        <CenteredDiv>
            <BackButton onClick={() => navigate(-1)}>
                <StyledBackIcon />
            </BackButton>
            <CenterNewStory>
                새스토리
            </CenterNewStory>
            <div></div>  
        </CenteredDiv>
    );
}

export default NewStoryBar;
