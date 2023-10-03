import styled from "styled-components";
import { ReactComponent as ReportButton } from "../../../assets/images/report_button.svg";
import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import Typography from "@mui/material/Typography";
import Modal from "@mui/material/Modal";
import Select from "@mui/material/Select";
import MenuItem from "@mui/material/MenuItem";
import FormControl from "@mui/material/FormControl";
import InputLabel from "@mui/material/InputLabel";
import { SelectChangeEvent } from "@mui/material/Select";
import IconButton from "@mui/material/IconButton";
import CloseIcon from "@mui/icons-material/Close";
import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";
import { authInstance } from "../../../apis/utils/instance";

const style = {
  position: "absolute" as "absolute",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: 300,
  height: 190,
  bgcolor: "background.paper",
  border: "2px solid #000",
  boxShadow: 24,
  p: 4,
  borderRadius: 8,
};
interface BasicModalProps {
  storyId: string | undefined;
}
export default function BasicModal({ storyId }: BasicModalProps) {
  const [open, setOpen] = React.useState(false);
  const [reportReason, setReportReason] = React.useState("");

  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);
  const handleSelectChange = (event: SelectChangeEvent<string>) => {
    setReportReason(event.target.value);
  };
  const handleReport = async () => {
    try {
      const response = await authInstance.post(`/report/storyid/${storyId}`, {
        reason4report: reportReason,
      });
      console.log(response.data);
      handleClose();
    } catch (error) {
      console.error("Failed to report:", error);
    }
  };

  return (
    <div>
      <Button onClick={handleOpen}>
        <ReportButton />
      </Button>
      <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Box sx={style}>
          <IconButton
            sx={{
              position: "absolute",
              right: 0,
              top: 0,
              padding: "15px",
            }}
            onClick={handleClose}
          >
            <CloseIcon />
          </IconButton>
          <Typography id="modal-modal-title" variant="h6" component="h2">
            신고 사유
          </Typography>
          <FormControl fullWidth variant="outlined" sx={{ mt: 3 }}>
            <InputLabel id="report-reason-label">신고 이유</InputLabel>
            <Select
              labelId="report-reason-label"
              value={reportReason}
              onChange={handleSelectChange}
              label="신고 이유"
            >
              <MenuItem value="선정적인 게시물">선정적인 게시물</MenuItem>
              <MenuItem value="스팸">스팸</MenuItem>
              <MenuItem value="계정 해킹 의심">계정 해킹 의심</MenuItem>
              <MenuItem value="기타 문제">기타 문제</MenuItem>
            </Select>
          </FormControl>
          <Button
            variant="contained"
            sx={{
              mt: 2,
              position: "absolute",
              right: 35,
              bottom: 60,
              backgroundColor: "#E03C31",
              color: "white",
            }}
            onClick={() => {
              handleReport();
              console.log(`신고 이유: ${reportReason}`);
              handleClose();
            }}
          >
            신고
          </Button>
        </Box>
      </Modal>
    </div>
  );
}
