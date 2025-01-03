import { Divider, Typography } from "@mui/material";
import React from "react";
import "./SectionTitle.scss";

export interface SectionTitleProps {
  children?: any;
  className?: string;
}

export function SectionTitle(props: SectionTitleProps) {
  return (
    <div className="sectionTitle-div">
      <Typography
        className={`sectionTitle-base ${props.className}`}
        fontWeight={700}
        variant={'h5'}>
        {props.children}
      </Typography>
      <Divider />
    </div>
  )
}