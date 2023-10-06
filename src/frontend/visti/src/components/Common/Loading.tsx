import React from 'react'
import styled from 'styled-components';
// import { FadeLoader } from 'react-spinners';

interface loadingProps {
  isLoading : boolean
}

const Loading : React.FC<loadingProps> = ({isLoading}) => {
  return (
    <LoadingWrap isLoading={isLoading}>
      <p>Loading...</p>
    </LoadingWrap>
  )
}

const LoadingWrap = styled.div<{ isLoading : boolean }>`
  left: 0;
  top: 0;
  position: fixed;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 100%;
  color: #000;
  font-weight: 600;
  font-size: 30px;
  z-index: 999;
  display: ${props=>props.isLoading ? "flex" : "none"};
`

export default Loading