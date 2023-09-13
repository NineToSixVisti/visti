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
      <Box mode={tap==='story'} onClick={setStory}>
        <ArticleSvg active={tap === 'story'}/>
      </Box>
      <Box mode={tap === 'member'} onClick={setMember}>
        <MemberSvg active={tap === 'member'}/>
      </Box>
      <Box mode={tap === 'detail'} onClick={setDetail}>
        <DetailSvg active={tap === 'detail'}/>
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

const Box = styled.div<{ mode?: boolean }>`
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  border-bottom: ${props => props.mode ? '3px solid #000' : 'none'};
`

const ArticleSvg = styled(Article)<{ active: boolean }>`
  stroke: ${props => props.active ? '#000000' : '#C2C2C2'};
`

const MemberSvg = styled(Member)<{ active: boolean }>`
  fill: ${props => props.active ? '#000000' : '#C2C2C2'};
`

const DetailSvg = styled(Detail)<{ active: boolean }>`
  fill: ${props => props.active ? '#000000' : '#C2C2C2'};
`





export default Tap