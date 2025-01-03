import { Typography } from "@mui/material";
import React from "react";

export interface CardTitleProps {
  title: string;
  summary: JSX.Element;
}

export function CardTitle(props: CardTitleProps) {
  return (
    <div className="accordian-title">
      <Typography
        gutterBottom
        fontSize={'1.1rem'}
        fontWeight={700}>
        {props.title}
      </Typography>
      {props.summary}
    </div>
  )
}