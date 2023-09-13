import React from 'react'
import styled from 'styled-components';

import { ReactComponent as Article } from "../../../assets/images/storybox_story.svg"
import { ReactComponent as Member } from "../../../assets/images/storybox_member.svg"
import { ReactComponent as Detail } from "../../../assets/images/stotybox_detail(paper).svg"


type TapProps = {
  tap : string,
  setStory: () => void;
  setMember: () => void;
  setDetail: () => void;
};

const Tap: React.FC<TapProps> = ({ tap, setStory, setMember, setDetail }) => {
  return (
    <TapWrap>
      <Box isMode={tap === 'story'} onClick={setStory}>
        <ArticleSvg isActive={tap === 'story'}/>
      </Box>
      <Box isMode={tap === 'member'} onClick={setMember}>
        <MemberSvg isActive={tap === 'member'}/>
      </Box>
      <Box isMode={tap === 'detail'} onClick={setDetail}>
        <DetailSvg isActive={tap === 'detail'}/>
      </Box>
    </TapWrap>
  )
}

const TapWrap = styled.div`
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  height: 40px;
  /* background-color: lightcoral; */
`

const Box = styled.div<{ isMode: boolean }>`
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  border-bottom: ${props => props.isMode ? '3px solid #000' : 'none'};
`

type ArticleSvgProps = {
  isActive: boolean;
};

const ArticleSvg: React.FC<ArticleSvgProps> = ({ isActive }) => {
  const strokeColor = isActive ? '#000000' : '#C2C2C2';
  return <Article stroke={strokeColor} />;
};

const MemberSvg: React.FC<ArticleSvgProps> = ({ isActive }) => {
  const fillColor = isActive ? '#000000' : '#C2C2C2';
  return <Member fill={fillColor} />;
};

const DetailSvg: React.FC<ArticleSvgProps> = ({ isActive }) => {
  const fillColor = isActive ? '#000000' : '#C2C2C2';
  return <Detail fill={fillColor} />;
};

export default Tap